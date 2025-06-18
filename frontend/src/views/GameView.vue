<template>
  <div v-if="userName" class="p-6 space-y-4">
    <div v-if="!currentQuestion">
      <h2 class="text-2xl font-semibold text-yellow-400">Get ready...</h2>
    </div>
    <div v-else>
      <h2 class="text-2xl font-semibold">{{ currentQuestion.question }}</h2>
      <div class="grid grid-cols-2 gap-4">
        <button
          v-for="(answer, index) in currentQuestion.answers"
          :key="index"
          class="p-4 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
          @click="submitAnswer(index)"
          :disabled="answered"
        >
          {{ answer }}
        </button>
      </div>
      <div v-if="answered" class="mt-4 text-green-600 font-bold">Answer sent!</div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { connectSocket, sendRoomMessage } from '@/lib/socket';
import authStore from '@/store/auth.store.ts';

const useAuthStore = authStore();
const userName = useAuthStore.username;
const route = useRoute();
const roomId = route.params.roomId as string;

const currentQuestion = ref<{ question: string; answers: string[] } | null>(null);
const answered = ref(false);

onMounted(() => {
  connectSocket(() => {
    console.log("Game socket connected");
  }, (message) => {
    console.log("Game message received:", message); // <--- this logs it
    if (message.type === 'question') {
      console.log("Setting current question...");    // <--- add this
      currentQuestion.value = {
        question: message.question,
        answers: message.answers
      };
      answered.value = false;
    }
  }, roomId);
});

const submitAnswer = (index: number) => {
  sendRoomMessage(roomId, {
    type: 'answer',
    playerName: userName,
    answerIndex: index
  });
  answered.value = true;
};
</script>
