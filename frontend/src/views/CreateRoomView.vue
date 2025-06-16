<template>
  <div class="max-w-md mx-auto p-6">
    <h2 class="text-2xl font-bold mb-4">Host a Quiz Room</h2>
    <HostRoomButton @roomCreated="navigateToRoom" />
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router';
import HostRoomButton from '@/components/HostRoomButton.vue';

const router = useRouter();

function navigateToRoom(roomId: string) {
  router.push({ name: 'room', params: { roomId } });
}
</script>
