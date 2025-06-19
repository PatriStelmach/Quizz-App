<template>
  <div class="summary">
    <h2>Quiz Summary</h2>
    <p>Your Score: {{ userScore }} / {{ maxScore }}</p>

    <Scoreboard :players="finalScores" :roundScores="[]" />
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import Scoreboard from './Scoreboard.vue';

const props = defineProps<{
  finalScores: Array<{ player: string; score: number }>;
  maxScore: number;
  currentUser: string;
}>();

const userScore = computed(() => {
  const user = props.finalScores.find(p => p.player === props.currentUser);
  return user ? user.score : 0;
});
</script>
