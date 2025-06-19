<template>
  <button @click="createRoom" class="p-3 bg-blue-500 text-white rounded-xl">
    Create Room
  </button>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import axios from 'axios';
import useAuthStore from "@/store/useAuthStore.ts";

const authStore = useAuthStore();

const router = useRouter();

const createRoom = async () => {
  const token = authStore.token;
  if (!token) {
    alert('No token found. Please log in first.');
    return;
  }

  try {
    const response = await axios.post(
      'http://localhost:10000/rooms/create',
      {},
      {
            headers: {
          Authorization: `Bearer ${token}`
        }
      }
    );
    const roomId = response.data.id;
    console.log('Room212:', roomId);
    await router.push({ name: 'room', params: { roomId } });
  } catch (error) {
    console.error('Room creation failed:', error);
    alert('Failed to create room. Are you logged in?');
  }
};
</script>
