<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import useAuthStore from '@/store/useAuthStore.ts'

import { ScrollArea } from '@/components/ui/scroll-area'
import { CardContent, CardHeader, CardTitle } from '@/components/ui/card'
import { Avatar, AvatarFallback } from '@/components/ui/avatar'
import { Button } from '@/components/ui/button'


type Friend = {
  id: number
  name: string
  isActive: boolean
}

const authStore = useAuthStore()
const router = useRouter()

const isMinimized = ref(false)
const friends = ref<Friend[]>([])

const fetchFriends = async () => {
  friends.value = [
    { id: 1, name: 'Ania', isActive: true },
    { id: 2, name: 'Bartek', isActive: false },
    { id: 3, name: 'Celina', isActive: false },
    { id: 4, name: 'Darek', isActive: true },
    { id: 5, name: 'Emil', isActive: true },
    { id: 11, name: 'Ania', isActive: true },
    { id: 22, name: 'Bartek', isActive: false },
    { id: 33, name: 'Celina', isActive: false },
    { id: 43, name: 'Darek', isActive: true },
    { id: 45, name: 'Emil', isActive: true },
  ]
}

onMounted(fetchFriends)

const goToDashboard = () => router.push({ name: 'dashboard' })
const goToProfile   = () => router.push({ name: 'Profile', params: { userId: authStore.user?.id } })
const logout        = async () => {
  await authStore.logout()
  await router.push({ name: 'login' })
}
</script>

<template>
  <aside v-if="authStore.loggedIn"
         class="fixed top-60 right-0 h-full bg-card text-card-foreground border border-secondary/30 shadow-lg rounded-lg transition-all duration-300 ease-in-out z-50 flex flex-col"
    :class="isMinimized ? 'w-16' : 'w-64'"
  >
    <div class="flex items-center justify-between h-16 px-3 border-b border-secondary/30">
      <Button size="sm" @click="isMinimized = !isMinimized" class="pb-0.5 text-2xl w-8 h-8">
        {{ isMinimized ? '←' : '→' }}
      </Button>
      <CardTitle class="text-base" v-if="!isMinimized">Social</CardTitle>
    </div>

    <div class="px-3 py-4 border-b border-secondary/30 flex items-center gap-3">
      <Avatar class="w-10 h-10">
        <AvatarFallback>{{ authStore.username?.slice(0, 2).toUpperCase() }}</AvatarFallback>
      </Avatar>
      <div v-if="!isMinimized" class="flex flex-col">
        <span class="font-medium">{{ authStore.username }}</span>
        <div class="flex space-x-2 mt-1">
          <button @click="goToDashboard" class="text-xs cursor-pointer hover:underline">Dashboard</button>
          <button @click="goToProfile"   class="text-xs cursor-pointer hover:underline">Profil</button>
          <button @click="logout"        class="text-xs cursor-pointer text-red-500 hover:underline">Logout</button>
        </div>
      </div>
    </div>

    <div class="flex-1 overflow-y-auto">
      <template v-if="!isMinimized">
        <CardHeader class="px-3 pt-4 pb-2">
          <CardTitle class="text-sm">Friends</CardTitle>
        </CardHeader>
        <CardContent class="p-0">
          <ScrollArea class="h-[550px] px-3 pb-4">
            <div class="space-y-3">
              <div
                v-for="friend in friends"
                :key="friend.id"
                class="flex items-center gap-3 p-2 cursor-pointer rounded hover:bg-primary/50 transition"
              >
                <Avatar class="w-8 h-8 relative">
                  <AvatarFallback>{{ friend.name.slice(0,2).toUpperCase() }}</AvatarFallback>
                </Avatar>
                <div class="flex-1">
                  <span class="block text-sm">{{ friend.name }}</span>
                  <span
                    class="text-xs"
                    :class="friend.isActive ? 'text-green-500' : 'text-muted-foreground'"
                  >
                    {{ friend.isActive ? 'Online' : 'Offline' }}
                  </span>
                </div>
              </div>
            </div>
          </ScrollArea>
        </CardContent>
      </template>

      <template v-else>
        <div class="flex flex-col items-center space-y-4 py-4 ">
          <div
            v-for="friend in friends"
            :key="friend.id"
            class="relative cursor-pointer p-1 rounded-4xl hover:bg-primary"
          >
            <Avatar class="w-8 h-8">
              <AvatarFallback>{{ friend.name.slice(0,2).toUpperCase() }}</AvatarFallback>
            </Avatar>
            <span
              v-if="friend.isActive"
              class="absolute bottom-0 right-0 block w-2 h-2 bg-green-500 rounded-full border border-black"
            ></span>
          </div>
        </div>
      </template>
    </div>
  </aside>
</template>
