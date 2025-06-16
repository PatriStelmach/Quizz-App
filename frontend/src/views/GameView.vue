<template>
  <div class="p-6 max-w-xl mx-auto">
    <h2 class="text-2xl font-bold mb-4">Quiz Room: {{ roomId }}</h2>

    <div v-if="question">
      <h3 class="text-lg font-semibold mb-2">{{ question.text }}</h3>
      <ul class="space-y-2">
        <li
          v-for="(option, idx) in question.options"
          :key="idx"
          class="bg-gray-100 p-2 rounded hover:bg-blue-100 cursor-pointer"
          @click="submitAnswer(idx)"
        >
          {{ option }}
        </li>
      </ul>
    </div>
    <p v-else class="italic text-gray-500">Waiting for question...</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRoute } from 'vue-router';
import { connectWebSocket, disconnectWebSocket, sendRoomMessage } from '@/utils/socket';

const route = useRoute();
const roomId = route.params.roomId as string;

const question = ref<{ text: string; options: string[] } | null>(null);

function onMessage(data: any) {
  if (data.type === 'quiz-question') {
    question.value = data.question;
  }
}

onMounted(() => {
  connectWebSocket(roomId, onMessage);
});

onBeforeUnmount(() => {
  disconnectWebSocket();
});

function submitAnswer(optionIndex: number) {
  sendRoomMessage(roomId, {
    type: 'answer-submitted',
    answerIndex: optionIndex,
  });
}
</script>
