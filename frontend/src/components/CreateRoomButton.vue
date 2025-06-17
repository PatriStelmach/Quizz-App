<template>
  <button @click="createRoom" class="p-3 bg-blue-500 text-white rounded-xl">Create Room</button>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import axios from 'axios';

const router = useRouter();

const createRoom = async () => {
  const response = await axios.post('http://localhost:10000/rooms/create');
  const roomId = response.data.id;
  await router.push({name: 'room', params: {roomId}});
};
</script>
