<template>
  <Card class="mt-8 max-w-md border-secondary/50 shadow-md shadow-primary border mx-auto">
    <CardHeader>
      <CardTitle class="text-3xl  text-center">Quiz Summary</CardTitle>
    </CardHeader>
    <CardContent class="space-y-4">
      <div class="flex justify-between items-center">
        <span class="font-medium">Your Score:</span>
        <Badge class="text-lg font-semibold">{{ userScore }} / {{ maxScore }}</Badge>
      </div>

    </CardContent>
    <CardFooter class="flex justify-end">
    </CardFooter>
  </Card>
  <Scoreboard :players="finalScores" :roundScores="[]" />
</template>


<script setup lang="ts">
import { computed } from 'vue';
import Scoreboard from './Scoreboard.vue';
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'

const props = defineProps<{
  finalScores: Array<{ player: string; score: number }>;
  maxScore: number;
  currentUser: string;
}>();

const userScore = computed(() =>
{
  const user = props.finalScores.find(p => p.player === props.currentUser);
  return user ? user.score : 0;
});
</script>
