

<script lang="ts" setup>
import { ref, onMounted, onUnmounted, watch, nextTick, computed } from 'vue';
import { useRoute } from 'vue-router';
import { connectSocket, sendRoomMessage } from '@/lib/socket';
import useAuthStore from '@/store/useAuthStore.ts';
import Scoreboard from '@/components/Scoreboard.vue';
import QuizSummary from '@/components/QuizSummary.vue';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert'
import { Button } from '@/components/ui/button'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'

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

onMounted(() =>
{
  clearInterval(intervalId);

  connectSocket(
    () =>
    {
      console.log('✅ Socket connected to room:', roomId);
    },
    (message) =>
    {
      console.log('📩 Game message received:', message);

      if (message.type === 'quiz-start')
      {
        if (Array.isArray(message.players))
        {
          scoreboard.value = message.players.map((p: any) =>
            ({
              player: p.player,
              score: p.score,
            }));
          roundResults.value = message.players.map((p: any) =>
            ({
              player: p.player,
              roundScore: 0,
            }));
        }
      }

      if (message.type === 'question')
      {
        if (!message.question || !Array.isArray(message.answers))
        {
          console.warn('⚠️ Invalid question format:', message);
          return;
        }

        questionCount.value++;
        currentQuestion.value =
          {
            question: message.question,
            answers: message.answers,
            duration: message.duration ?? 20
          };

        feedback.value = null;
        selectedAnswer.value = null;
        answered.value = false;

        roundResults.value = scoreboard.value.map(p =>
          ({
            player: p.player,
            roundScore: 0
          }));
      }

      if (message.type === 'reveal')
      {
        feedback.value = {
          correctAnswer: message.correctAnswer,
          selected: selectedAnswer.value
        };

        if (Array.isArray(message.results))
        {
          scoreboard.value = message.results.map((r: any) =>
            ({
              player: r.player,
              score: r.score
            }));

          roundResults.value = message.results.map((r: any) =>
            ({
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

<template>
  <div v-if="userName" class="flex flex-col items-center m-auto justify-center min-h-screen p-6 bg-gray-900">
    <!-- Quiz content container -->
    <div class="w-full max-w-4xl mx-auto">
      <!-- Quiz ended screen -->
      <div v-if="quizEnded" class=" justify-center">
        <QuizSummary
          :finalScores="finalScores"
          :maxScore="maxScore"
          :currentUser="userName"
        />
      </div>

      <!-- Active quiz content -->
      <div v-else class="space-y-8">
        <!-- Get ready screen -->
        <div v-if="!currentQuestion" class="text-center py-12">
          <h2 class="text-3xl font-bold text-yellow-400 animate-pulse">
            Get ready...
          </h2>
          <p class="text-gray-400 mt-2">The quiz will start soon</p>
        </div>

        <!-- Question screen -->
        <Card v-if="currentQuestion" class="bg-gray-800 rounded-xl shadow-lg">
          <CardHeader class="border-b border-gray-700">
            <div class="flex justify-between items-center">
              <CardTitle class="text-2xl font-bold text-white">
                {{ currentQuestion.question }}
              </CardTitle>
              <Badge
                class="text-lg font-bold py-2 px-4 rounded-full"
                :class="timerColor"
              >
                <span v-if="answered">⏸️</span>
                {{ secondsLeft }}s
              </Badge>
            </div>
          </CardHeader>

          <CardContent class="py-6">
            <!-- Answers grid -->
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <Button
                v-for="(answer, index) in currentQuestion.answers"
                :key="index"
                class="h-20 text-lg font-medium transition-all duration-300"
                :class="buttonClass(index)"
                @click="submitAnswer(index)"
                :disabled="answered"
              >
                <span class="truncate">{{ answer }}</span>
              </Button>
            </div>

            <!-- Answer feedback -->
            <div v-if="answered && !feedback" class="mt-6 text-center">
              <Alert class="bg-blue-900 border-blue-700">
                <AlertTitle class="text-yellow-400 font-bold">
                  Answer sent! Waiting for others...
                </AlertTitle>
              </Alert>
            </div>

            <!-- Results feedback -->
            <div v-if="feedback" class="mt-8 space-y-4">
              <div class="grid grid-cols-2 gap-4">
                <Alert class="bg-green-900/30 border-green-800">
                  <AlertTitle class="font-bold">Correct Answer:</AlertTitle>
                  <AlertDescription class="text-green-400 font-semibold">
                    {{ currentQuestion.answers[feedback.correctAnswer] }}
                  </AlertDescription>
                </Alert>

                <Alert :class="feedback.correctAnswer === feedback.selected ? 'bg-green-900/30 border-green-800' : 'bg-red-900/30 border-red-800'">
                  <AlertTitle class="font-bold">Your Answer:</AlertTitle>
                  <AlertDescription :class="feedback.correctAnswer === feedback.selected ? 'text-green-400' : 'text-red-400'">
                    <span v-if="feedback.selected !== null">
                      {{ currentQuestion.answers[feedback.selected] }}
                    </span>
                    <span v-else class="italic text-gray-300">No answer submitted</span>
                  </AlertDescription>
                </Alert>
              </div>
            </div>
          </CardContent>
        </Card>


        <Scoreboard
          :players="scoreboard"
          :roundScores="roundResults"
          class="mt-8"
        />
      </div>
    </div>
  </div>
</template>
