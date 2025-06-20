<script setup lang="ts">
import type { HTMLAttributes } from 'vue'
import { cn } from '@/lib/utils'

const props = defineProps<{
  class?: HTMLAttributes['class']
}>()
</script>

<template>
  <tr
    data-slot="table-row"
    :class="cn('hover:bg-primary/50 data-[state=selected]:bg-muted border-b h-15 text-left border-secondary/50 cursor-pointer transition-colors', props.class)"
  >
    <slot />
  </tr>
</template>
