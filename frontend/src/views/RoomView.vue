<template>
  <div class="p-6 max-w-xl mx-auto">
    <h2 class="text-2xl font-bold mb-2">Room: {{ roomId }}</h2>
    <p>Invite link:</p>
    <pre class="bg-gray-100 p-2 rounded">{{ inviteLink }}</pre>

    <div v-if="room">
      <h3 class="mt-4 text-lg font-semibold">Players:</h3>
      <ul class="list-disc ml-6">
        <li v-for="player in room.players" :key="player">{{ player }}</li>
      </ul>
    </div>

    <button
      v-if="isOwner && !room?.isStarted"
      class="mt-6 bg-blue-600 text-white px-4 py-2 rounded"
      @click="startQuiz"
    >
      Start Quiz
    </button>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';
import { connectWebSocket, disconnectWebSocket } from '@/utils/socket';

const route = useRoute();
const router = useRouter();
const roomId = route.params.roomId as string;
const inviteLink = `${window.location.origin}/room/${roomId}`;

const room = ref<{ id: string; ownerName: string; players: string[]; isStarted: boolean } | null>(null);
const isOwner = ref(false);

function onMessage(data: any) {
  if (data.type === 'room-update') {
    room.value = data.room;
  } else if (data.type === 'quiz-start') {
    router.push({ name: 'game', params: { roomId } });
  }
}

onMounted(async () => {
  try {
    await axios.post('http://localhost:8080/rooms/join', null, {
      params: { roomId },
      withCredentials: true,
    });

    const res = await axios.get('http://localhost:8080/rooms/get', {
      params: { roomId },
      withCredentials: true,
    });
    room.value = res.data;

    const me = await axios.get('http://localhost:8080/user/me', { withCredentials: true });
    isOwner.value = me.data.name === room.value.ownerName;

    connectWebSocket(roomId, onMessage);
  } catch (err) {
    console.error('Join room failed', err);
  }
});

onBeforeUnmount(() => {
  disconnectWebSocket();
});

async function startQuiz() {
  try {
    await axios.post('http://localhost:8080/rooms/start', null, {
      params: { roomId },
      withCredentials: true,
    });
  } catch (err) {
    console.error('Start quiz failed', err);
  }
}
</script>
