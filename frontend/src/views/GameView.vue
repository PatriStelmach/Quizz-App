<template>
  <div class="p-6">
    <h1 class="text-2xl font-bold">Quiz Game Started!</h1>
    <p>More game logic coming here...</p>
  </div>
</template>

<script lang="ts" setup>
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';

const roomId = useRoute().params.roomId as string;

onMounted(() => {
  console.log('Game started for room:', roomId);
});
</script>
