<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold mb-4">Room ID: {{ roomId }}</h1>
    <PlayerList :players="players" />
    <button @click="startGame" class="mt-4 p-3 bg-green-500 text-white rounded-xl">Start Game</button>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { connectSocket, sendRoomMessage } from '@/lib/socket';
import axios from 'axios';
import PlayerList from '@/components/PlayerList.vue';

const route = useRoute();
const router = useRouter();
const roomId = route.params.roomId as string;
const players = ref<string[]>([]);

onMounted(async () => {
  const { data: room } = await axios.get(`/rooms/get?roomId=${roomId}`);
  players.value = room.players;

  connectSocket(() => {
    sendRoomMessage(roomId, { type: 'join', playerName: null });
  }, message => {
    if (Array.isArray(message)) {
      players.value = message;
    } else if (message.type === 'quiz-start') {
      router.push({ name: 'game', params: { roomId } });
    }
  }, roomId);
});

const startGame = () => {
  sendRoomMessage(roomId, { type: 'start' });
};
</script>
