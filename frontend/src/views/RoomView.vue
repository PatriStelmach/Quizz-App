<template>
  <div v-if="userName" class="p-6">
    <h1 class="text-2xl font-bold mb-4">Room ID: {{ roomId }}</h1>
    <PlayerList :players="players" :currentUserName="userName" />
    <button @click="startGame" class="mt-4 p-3 bg-green-500 text-white rounded-xl">Start Game</button>
  </div>
  <div v-else>
    <p class="text-red-600">User not logged in.</p>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { connectSocket, sendRoomMessage } from '@/lib/socket';
import axios from 'axios';
import PlayerList from '@/components/PlayerList.vue';
import authStore from "@/store/auth.store.ts";

const useAuthStore = authStore();
const route = useRoute();
const router = useRouter();
const roomId = route.params.roomId as string;
const players = ref<string[]>([]);

const userName = useAuthStore.user?.username ?? '';

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:10000/rooms/get?roomId=${roomId}`);
    console.log('response:', response.data.players);

    players.value = response.data.players || [];

    console.log('players.value:', players.value);
  } catch (err) {
    console.error("Failed to fetch room:", err);
  }

  connectSocket(() => {
    sendRoomMessage(roomId, { type: 'join', playerName: userName });
  }, message => {
    //if msg is [] players
    if (Array.isArray(message)) {
      players.value = message;
    }
    //if msg is quiz-start -> start game
    else if (message.type === 'quiz-start') {
      router.push({ name: 'game', params: { roomId } });
    }
  }, roomId);
});

const startGame = () => {
  sendRoomMessage(roomId, { type: 'start' });
};
</script>
