<template>
  <div class="bg-white p-4 shadow-md rounded-xl">
    <h2 class="text-lg font-semibold mb-2">Players in Room</h2>
    <ul>
      <li v-for="player in players" :key="player">{{ player }}</li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
defineProps<{ players: string[] }>();
</script>
