<template>
  <div v-if="userName" class="p-6 space-y-4">
    <div v-if="!currentQuestion">
      <h2 class="text-2xl font-semibold text-yellow-400">Get ready...</h2>
    </div>
    <div v-else>
      <div class="flex justify-between items-center">
        <h2 class="text-2xl font-semibold">{{ currentQuestion.question }}</h2>
        <span class="text-sm text-gray-400">Time left: {{ secondsLeft }}s</span>
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

      <div v-if="answered && !feedback" class="mt-4 text-green-600 font-bold">
        Answer sent! Waiting for others...
      </div>

      <div v-if="feedback" class="mt-4 space-y-1 text-white">
        <p class="text-lg font-bold">
          ✅ Correct Answer:
          <span class="text-green-400">{{ currentQuestion.answers[feedback.correctAnswer] }}</span>
        </p>
        <p :class="feedback.correctAnswer === feedback.selected ? 'text-green-400' : 'text-red-400'">
          You selected:
          <span v-if="feedback.selected !== null">
            {{ currentQuestion.answers[feedback.selected] }}
          </span>
          <span v-else class="italic text-gray-300">No answer</span>
        </p>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, onUnmounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { connectSocket, sendRoomMessage } from '@/lib/socket';
import authStore from '@/store/auth.store.ts';

const useAuthStore = authStore();
const userName = useAuthStore.username;
const route = useRoute();
const roomId = route.params.roomId as string;

const currentQuestion = ref<{ question: string; answers: string[]; duration: number } | null>(null);
const answered = ref(false);
const selectedAnswer = ref<number | null>(null);
const feedback = ref<{ correctAnswer: number; selected: number | null } | null>(null);
const secondsLeft = ref(15);

let intervalId: number;

onMounted(() => {
  clearInterval(intervalId);
  connectSocket(() => {
    console.log("Game socket connected");
  }, (message) => {
    console.log("Game message received:", message);
    if (message.type === 'question') {
      currentQuestion.value = {
        question: message.question,
        answers: message.answers,
        duration: message.duration ?? 15
      };
      feedback.value = null;
      selectedAnswer.value = null;
      answered.value = false;
    } else if (message.type === 'reveal') {
      feedback.value = {
        correctAnswer: message.correctAnswer,
        selected: selectedAnswer.value
      };
    }
  }, roomId);
});

onUnmounted(() => {
  clearInterval(intervalId);
});

watch(currentQuestion, (newVal) => {
  if (newVal) {
    secondsLeft.value = newVal.duration;
    clearInterval(intervalId);

    intervalId = setInterval(() => {
      secondsLeft.value -= 1;

      if (secondsLeft.value <= 0) {
        clearInterval(intervalId);

        // Send null if no answer selected
        if (!answered.value) {
          sendRoomMessage(roomId, {
            type: 'answer',
            playerName: userName,
            answerIndex: null
          });
          answered.value = true;
          selectedAnswer.value = null;
        }
      }
    }, 1000);
  }
});

const submitAnswer = (index: number) => {
  if (answered.value) return;

  selectedAnswer.value = index;
  sendRoomMessage(roomId, {
    type: 'answer',
    playerName: userName,
    answerIndex: index
  });
  answered.value = true;
};

const buttonClass = (index: number) => {
  if (!feedback.value) {
    return 'bg-blue-500 text-white hover:bg-blue-600';
  }

  const isCorrect = index === feedback.value.correctAnswer;
  const isSelected = index === feedback.value.selected;

  if (isCorrect) {
    return 'bg-green-500 text-white';
  } else if (isSelected) {
    return 'bg-red-500 text-white';
  } else {
    return 'bg-gray-400 text-white';
  }
};
</script>
