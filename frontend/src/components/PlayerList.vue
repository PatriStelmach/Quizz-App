

<script lang="ts" setup>
import { Card, CardHeader, CardTitle, CardContent } from '@/components/ui/card'
import { Badge } from '@/components/ui/badge'

defineProps<{
  players: string[];
  currentUserName: string;
}>()
</script>

<template>
  <Card class="bg-blue-600 text-white shadow-md rounded-xl">
    <CardHeader>
      <CardTitle class="text-lg text-white">Players in Room</CardTitle>
    </CardHeader>
    <CardContent class="space-y-2">
      <div
        v-for="player in players"
        :key="player"
        class="flex items-center justify-between px-4 py-2 bg-blue-700 rounded-lg"
      >
        <span :class="player === currentUserName ? 'font-bold text-yellow-300' : ''">
          {{ player }}
        </span>
        <Badge
          v-if="player === currentUserName"
          variant="outline"
          class="text-green-300"
        >
          You
        </Badge>
      </div>
    </CardContent>
  </Card>
</template>
