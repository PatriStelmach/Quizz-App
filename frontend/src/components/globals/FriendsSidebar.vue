<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { ScrollArea } from '@/components/ui/scroll-area'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'
import useAuthStore from '@/store/useAuthStore.ts'

const authStore = useAuthStore()
const isMinimizing = ref(false)
const isMinimized = ref(false)



type Friend = {
  id: number
  name: string
  isActive: boolean
}

const friends = ref<Friend[]>([])

const fetchFriends = async () =>
{
  friends.value = [
    { id: 1, name: 'Ania', isActive: true },
    { id: 2, name: 'Bartek', isActive: false },
    { id: 3, name: 'Celina', isActive: false },
    { id: 4, name: 'Darek', isActive: true },
    { id: 5, name: 'Emil', isActive: true },
  ]
}

onMounted(fetchFriends)
</script>

<template>
  <Card
    class="fixed bottom-10 w-4xl right-10 border justify-center rounded-xl shadow-lg z-50 bg-background transition-all duration-500 ease-in-out overflow-hidden"
    :class="[
      authStore.loggedIn ? 'opacity-100 scale-100 translate-y-0' : 'opacity-0 scale-95 translate-y-4',
      isMinimized ? 'w-52 h-16' : 'w-62 h-[400px]',
    ]"
  >
    <div class="flex justify-between items-center h-16 px-4"
    :class="{
       ' pb-2 border-b' : !isMinimized
    }">
      <CardTitle class="text-sm ">Lista znajomych</CardTitle>
      <Button
        variant="secondary"
        size="sm"
        @click="isMinimized = !isMinimized"
        class="text-xl px-2 py-1 border:none"
      >
        {{ isMinimized ? '↑' : '↓' }}
      </Button>
    </div>

    <CardContent v-show="!isMinimized" class="p-0">
      <ScrollArea class="h-[320px] px-4">
        <div class="space-y-4 pb-4">
          <div
            v-for="friend in friends"
            :key="friend.id"
            class="cursor-pointer flex items-center gap-3 p-3 rounded-lg hover:bg-muted transition-colors"
          >
            <Avatar class="w-9 h-9">
              <AvatarFallback>{{ friend.name.slice(0, 2).toUpperCase() }}</AvatarFallback>
            </Avatar>
            <div class="flex flex-col">
              <span class="text-sm font-medium">{{ friend.name }}</span>
              <span
                :class="{
                  'text-green-500': friend.isActive,
                  'text-muted-foreground': !friend.isActive,
                }"
                class="text-xs"
              >
                {{ friend.isActive ? 'Online' : 'Offline' }}
              </span>
            </div>
          </div>
        </div>
      </ScrollArea>
    </CardContent>
  </Card>
</template>

