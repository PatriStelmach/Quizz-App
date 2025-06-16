<template>
  <div class="max-w-xl mx-auto p-6">
    <h2 class="text-2xl font-bold mb-4">Room ID: {{ roomId }}</h2>

    <div class="mb-4">
      <strong>Invite Link:</strong>
      <pre class="bg-gray-100 p-2">{{ inviteLink }}</pre>
    </div>

    <div v-if="room">
      <h3 class="text-lg font-semibold">Players:</h3>
      <ul class="list-disc pl-6">
        <li v-for="p in room.players" :key="p">{{ p }}</li>
      </ul>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const roomId = route.params.roomId as string;

const room = ref<{ players: string[] } | null>(null);
const inviteLink = `${window.location.origin}/room/${roomId}`;

onMounted(async () => {
  try {
    // Only join using session-based user info (will do later)
    await axios.post(`http://localhost:8080/rooms/join`, null, {
      params: { roomId },
    });

    const res = await axios.get(`http://localhost:8080/rooms/get`, {
      params: { roomId },
    });

    room.value = res.data;
  } catch (err) {
    console.error('Failed to join or fetch room:', err);
  }
});
</script>
