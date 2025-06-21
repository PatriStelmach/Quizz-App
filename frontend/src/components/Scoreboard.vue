
<script lang="ts" setup>
import { ref, watch, computed } from 'vue';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'

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

<template>
  <Card class="mt-6  w-4xl text-white shadow-2xl shadow-primary">
    <CardHeader>
      <CardTitle class="mx-auto text-3xl">
        <span class="mr-2">🏆</span>Live Scoreboard
      </CardTitle>
    </CardHeader>
    <CardContent class="space-y-2">
      <div
        v-for="(player, index) in sortedPlayers"
        :key="player.player"
        class="flex items-center justify-between p-4 bg-gray-900 rounded-lg shadow"
      >
        <div class="flex items-center space-x-3">
          <Badge variant="secondary" class="w-6 h-6 flex items-center justify-center">
            {{ index + 1 }}
          </Badge>
          <span class="font-medium text-white">{{ player.player }}</span>
        </div>

        <div class="flex items-center space-x-3">
          <span class="text-white">{{ player.score }}</span>
          <Badge
            v-if="visibleRoundScores[player.player] !== undefined"
            variant="destructive"
            class="text-sm"
          >
            +{{ visibleRoundScores[player.player] }}
          </Badge>
        </div>
      </div>
    </CardContent>
  </Card>
</template>

