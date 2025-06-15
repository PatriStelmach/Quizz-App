<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ScrollArea } from '@/components/ui/scroll-area'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'

type Friend = {
  id: number
  name: string
  status: 'online' | 'offline'
}

const friends = ref<Friend[]>([])

const fetchFriends = async () => {
  friends.value = [
    { id: 1, name: 'Ania', status: 'online' },
    { id: 2, name: 'Bartek', status: 'offline' },
    { id: 3, name: 'Celina', status: 'online' },
    { id: 4, name: 'Darek', status: 'offline' },
    { id: 5, name: 'Emil', status: 'online' },
  ]
}

onMounted(fetchFriends)
</script>

<template>
  <Card
    class="fixed bottom-10 right-10 w-64 h-[400px] border rounded-xl shadow-lg z-50 bg-background"
  >
    <CardHeader>
      <CardTitle class="text-lg text-center">Znajomi</CardTitle>
    </CardHeader>
    <CardContent class="p-0">
      <ScrollArea class="h-[320px] px-4">
        <div class="space-y-4 pb-4">
          <div
            v-for="friend in friends"
            :key="friend.id"
            class="cursor-pointer flex items-center gap-3 p-2 rounded-lg hover:bg-muted transition-colors"
          >
            <Avatar class="w-9 h-9">
              <AvatarFallback>{{ friend.name.slice(0, 2).toUpperCase() }}</AvatarFallback>
            </Avatar>
            <div class="flex flex-col">
              <span class="text-sm font-medium">{{ friend.name }}</span>
              <span
                :class="{
                  'text-green-500': friend.status === 'online',
                  'text-muted-foreground': friend.status === 'offline'
                }"
                class="text-xs"
              >
                {{ friend.status === 'online' ? 'Online' : 'Offline' }}
              </span>
            </div>
          </div>
        </div>
      </ScrollArea>
    </CardContent>
  </Card>
</template>

