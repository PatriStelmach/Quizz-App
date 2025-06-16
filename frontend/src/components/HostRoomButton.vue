<template>
  <button
    class="bg-blue-600 text-white px-4 py-2 rounded"
    @click="createRoom"
  >
    Host Game
  </button>
</template>

<script lang="ts" setup>
import axios from 'axios';

const emit = defineEmits<{ (e: 'roomCreated', roomId: string): void }>();

async function createRoom() {
  try {
    const res = await axios.post('http://localhost:8080/rooms/create', null, {
      withCredentials: true,
    });
    emit('roomCreated', res.data.id);
  } catch (err) {
    console.error('Error creating room:', err);
  }
}
</script>
