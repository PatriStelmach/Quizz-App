<template>
  <div class="max-w-md mx-auto p-6">
    <h2 class="text-2xl font-bold mb-4">Join a Room</h2>

    <JoinRoomForm @joinSuccess="navigateToRoom" />
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import JoinRoomForm from '@/components/JoinRoomForm.vue';

const router = useRouter();

function navigateToRoom(roomId: string, playerName: string) {
  router.push({ name: 'room', params: { roomId }, query: { playerName } });
}
</script>
