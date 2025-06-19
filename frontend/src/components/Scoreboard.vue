<template>
  <div class="mt-6 space-y-2">
    <h3 class="text-xl font-bold text-white mb-4">🏆 Live Scoreboard</h3>

    <ul class="space-y-2">
      <li
        v-for="(player, index) in sortedPlayers"
        :key="player.player"
        class="flex items-center justify-between px-4 py-2 rounded-lg bg-gray-800 text-white shadow"
      >
        <div class="flex items-center space-x-3">
          <span class="text-lg font-bold text-yellow-400">{{ index + 1 }}.</span>
          <span class="font-medium">{{ player.player }}</span>
        </div>

        <div class="flex items-center space-x-3">
          <span class="text-sm">Score: {{ player.score }}</span>

          <!-- only show green badge if there's a non-zero round score -->
          <span
            v-if="visibleRoundScores[player.player] !== undefined"
            class="text-green-400 bg-green-900 px-2 py-0.5 rounded text-xs font-semibold"
          >
            +{{ visibleRoundScores[player.player] }}
          </span>
        </div>
      </li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
import { ref, watch, computed } from 'vue';

const props = defineProps<{
  players: Array<{ player: string; score: number }>;
  roundScores: Array<{ player: string; roundScore: number }>;
}>();

const sortedPlayers = computed(() =>
  [...props.players].sort((a, b) => b.score - a.score)
);

const visibleRoundScores = ref<Record<string, number>>({});

watch(() => props.roundScores, (newScores) => {
  const newVisibleScores: Record<string, number> = {};
  newScores.forEach(({ player, roundScore }) => {
    if (roundScore > 0) newVisibleScores[player] = roundScore;
  });
  visibleRoundScores.value = newVisibleScores;

  if (Object.keys(newVisibleScores).length > 0) {
    setTimeout(() => {
      visibleRoundScores.value = {};
    }, 2000);
  }
}, { immediate: true });
</script>
