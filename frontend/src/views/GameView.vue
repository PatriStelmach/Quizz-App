<template>
  <div v-if="userName" class="p-6 space-y-4">

    <!-- If quiz has ended, show ONLY the summary -->
    <div v-if="quizEnded">
      <QuizSummary
        :finalScores="finalScores"
        :maxScore="maxScore"
        :currentUser="userName"
      />
    </div>

    <!-- otherwise, show the quiz content -->
    <div v-else>
      <!-- Get ready screen -->
      <div v-if="!currentQuestion">
        <h2 class="text-2xl font-semibold text-yellow-400">Get ready...</h2>
      </div>

      <!-- question screen -->
      <div v-if="currentQuestion">
        <div class="flex justify-between items-center mb-2">
          <h2 class="text-2xl font-semibold text-white">
            {{ currentQuestion.question || 'Loading question...' }}
          </h2>
          <span
            class="text-sm font-medium transition-colors duration-300"
            :class="timerColor">
              <span v-if="answered">⏸️</span> Time Left: {{ secondsLeft }}s
          </span>

        </div>

        <div class="grid grid-cols-2 gap-4">
          <button
            v-for="(answer, index) in currentQuestion.answers"
            :key="index"
            class="p-4 rounded-lg transition duration-200"
            :class="buttonClass(index)"
            @click="submitAnswer(index)"
            :disabled="answered"
          >
            {{ answer }}
          </button>
        </div>

        <div v-if="answered && !feedback" class="mt-4 text-yellow-400 font-bold">
          Answer sent! Waiting for others...
        </div>

        <div v-if="feedback" class="mt-4 space-y-1 text-white">
          <p class="text-lg font-bold">
            Correct Answer:
            <span class="text-green-400">
              {{ currentQuestion.answers[feedback.correctAnswer] || 'Unknown' }}
            </span>
          </p>
          <p :class="feedback.correctAnswer === feedback.selected ? 'text-green-400' : 'text-red-400'">
            You selected:
            <span v-if="feedback.selected !== null">
              {{ currentQuestion.answers[feedback.selected] || 'Invalid answer' }}
            </span>
            <span v-else class="italic text-gray-300">No answer</span>
          </p>
        </div>
      </div>

      <!-- Scoreboard only during quiz -->
      <Scoreboard :players="scoreboard" :roundScores="roundResults" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted, watch, nextTick, computed } from 'vue';
import { useRoute } from 'vue-router';
import { connectSocket, sendRoomMessage } from '@/lib/socket';
import useAuthStore from '@/store/useAuthStore.ts';
import Scoreboard from '@/components/Scoreboard.vue';
import QuizSummary from '@/components/QuizSummary.vue';

const authStore = useAuthStore();
const userName = authStore.username;
const route = useRoute();
const roomId = route.params.roomId as string;

const scoreboard = ref<Array<{ player: string; score: number }>>([]);
const roundResults = ref<Array<{ player: string; roundScore: number }>>([]);

const currentQuestion = ref<{ question: string; answers: string[]; duration: number } | null>(null);
const answered = ref(false);
const selectedAnswer = ref<number | null>(null);
const feedback = ref<{ correctAnswer: number; selected: number | null } | null>(null);
const secondsLeft = ref(15);

const quizEnded = ref(false);
const finalScores = ref<Array<{ player: string; score: number }>>([]);
const maxScore = ref(0);
const questionCount = ref(0);

let intervalId: ReturnType<typeof setInterval>;

onMounted(() => {
  clearInterval(intervalId);

  connectSocket(
    () => {
      console.log('✅ Socket connected to room:', roomId);
      // Removed get-current-state request here
    },
    (message) => {
      console.log('📩 Game message received:', message);

      if (message.type === 'quiz-start') {
        if (Array.isArray(message.players)) {
          scoreboard.value = message.players.map((p: any) => ({
            player: p.player,
            score: p.score,
          }));
          roundResults.value = message.players.map((p: any) => ({
            player: p.player,
            roundScore: 0,
          }));
        }
      }

      if (message.type === 'question') {
        if (!message.question || !Array.isArray(message.answers)) {
          console.warn('⚠️ Invalid question format:', message);
          return;
        }

        questionCount.value++;
        currentQuestion.value = {
          question: message.question,
          answers: message.answers,
          duration: message.duration ?? 20
        };

        feedback.value = null;
        selectedAnswer.value = null;
        answered.value = false;

        roundResults.value = scoreboard.value.map(p => ({
          player: p.player,
          roundScore: 0
        }));
      }

      if (message.type === 'reveal') {
        feedback.value = {
          correctAnswer: message.correctAnswer,
          selected: selectedAnswer.value
        };

        if (Array.isArray(message.results)) {
          scoreboard.value = message.results.map((r: any) => ({
            player: r.player,
            score: r.score
          }));

          roundResults.value = message.results.map((r: any) => ({
            player: r.player,
            roundScore: r.correct ? 1 : 0
          }));
        } else {
          console.warn('⚠️ Invalid results format in reveal:', message);
        }
      }

      if (message.type === 'quiz-end') {
        console.log('🏁 Quiz has ended');
        quizEnded.value = true;
        finalScores.value = [...scoreboard.value];
        maxScore.value = questionCount.value;
        currentQuestion.value = null;
        clearInterval(intervalId);
      }
    },
    roomId
  );
});

onUnmounted(() => {
  clearInterval(intervalId);
});

watch(currentQuestion, async (newQuestion) => {
  if (newQuestion) {
    secondsLeft.value = newQuestion.duration ?? 15;
    clearInterval(intervalId);

    answered.value = false;
    selectedAnswer.value = null;
    feedback.value = null;

    await nextTick();

    intervalId = setInterval(() => {
      if (answered.value) return;

      secondsLeft.value -= 1;

      if (secondsLeft.value <= 0) {
        clearInterval(intervalId);

        if (!answered.value) {
          console.log('⏱️ Time out - sending null answer');
          sendRoomMessage(roomId, {
            type: 'answer',
            playerName: userName,
            answerIndex: null,
          });
          answered.value = true;
          selectedAnswer.value = null;
        }
      }
    }, 1000);
  }
});

const submitAnswer = (index: number) => {
  if (answered.value || index < 0 || !currentQuestion.value?.answers[index]) {
    console.warn('⚠️ Invalid answer submission:', index);
    return;
  }

  console.log('✅ Answer submitted:', index);
  selectedAnswer.value = index;
  sendRoomMessage(roomId, {
    type: 'answer',
    playerName: userName,
    answerIndex: index
  });
  answered.value = true;
};

const buttonClass = (index: number) => {
  if (!feedback.value) return 'bg-blue-500 text-white hover:bg-blue-600';

  const isCorrect = index === feedback.value.correctAnswer;
  const isSelected = index === feedback.value.selected;

  if (isCorrect) return 'bg-green-500 text-white';
  if (isSelected) return 'bg-red-500 text-white';
  return 'bg-gray-400 text-white';
};

const timerColor = computed(() => {
  if (secondsLeft.value > 10) return 'text-green-400';
  if (secondsLeft.value > 5) return 'text-yellow-400';
  return 'text-red-500';
});
</script>
