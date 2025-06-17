<template>
  <div class="bg-blue-600 p-4 shadow-md rounded-xl text-white">
    <h2 class="text-lg font-semibold mb-2">Players in Room</h2>
    <ul>
      <li
        v-for="player in players"
        :key="player"
        :class="player === currentUserName ? 'font-bold text-yellow-300' : ''"
      >
        {{ player }}
        <span v-if="player === currentUserName" class="text-green-300 font-medium">
          (You)
        </span>
      </li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
defineProps<{
  players: string[];
  currentUserName: string;
}>();
</script>
