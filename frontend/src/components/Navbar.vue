<script setup lang="ts">
import { ref, watchEffect } from 'vue'
import { useRouter } from 'vue-router'

import {
  Menubar,
  MenubarContent,
  MenubarItem,
  MenubarMenu,
  MenubarTrigger,
} from '@/components/ui/menubar'
import { Search } from 'lucide-vue-next'
import authStore from '@/store/auth.store.ts'

const router = useRouter()
const useAuthStore = authStore()


const searchTerm = ref('')
function onSearch() {
  if (searchTerm.value.trim())
  { console.log(searchTerm.value)
   // router.push({ name: 'QuizSearch', query: { q: searchTerm.value } })
  }
}

function goToDashboard()
{
  console.log('Dashboard')
  // router.push({ name: 'Dashboard' })
}
function goToProfile()
{
  console.log('Go to profile')
  //router.push({ name: 'Profile', params: { userId: user.value?.id } })
}

const logout =  async () =>
{
  await useAuthStore.logout()
  await router.push({ name: 'login' })
}


</script>

<template>
  <Menubar class="w-9/12 shadow-md px-4 content-center m-auto h-15 flex items-center justify-between focus:outline-none">
    <div class="flex items-center space-x-4 focus:outline-none">
      <MenubarMenu>
        <MenubarTrigger as-child>
          <RouterLink
            to="/home"
            class="text-xl py-1 px-4 font-bold cursor-pointer focus:outline-none"
          >
            Home
          </RouterLink>
        </MenubarTrigger>
      </MenubarMenu>

      <MenubarMenu>
        <div class="relative">
          <input
            v-model="searchTerm"
            @keyup.enter="onSearch"
            type="text"
            placeholder="Search quizzes..."
            class="pl-10 pr-4 py-2 border-4 rounded-full focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <Search class="w-5 h-5 absolute left-3 top-1/2 transform -translate-y-1/2" />
        </div>
      </MenubarMenu>
    </div>

    <div class="flex items-center ">
      <MenubarMenu>
        <MenubarTrigger as-child>
          <RouterLink
            to="/top-players"
            class="px-4 py-2 rounded-lg cursor-pointer"
          >
            Top Players
          </RouterLink>
        </MenubarTrigger>
      </MenubarMenu>

      <MenubarMenu>
        <MenubarTrigger as-child>
          <RouterLink
            to="/create-quiz"
            class="px-4 py-2 rounded-lg cursor-pointer"
          >
            Create Quiz
          </RouterLink>
        </MenubarTrigger>
      </MenubarMenu>

      <MenubarMenu v-if="useAuthStore.loggedIn">
        <MenubarTrigger as-child>
          <button class="focus:outline-none cursor-pointer">
            <img
              src="../images/profile-dark-mode.png"
              alt="profile icon"
              class="w-10 h-10"
            />
          </button>
        </MenubarTrigger>
        <MenubarContent align="end">
          <MenubarItem @click="goToDashboard">Dashboard</MenubarItem>
          <MenubarItem @click="goToProfile">My Profile</MenubarItem>
          <MenubarItem @click="logout">Logout</MenubarItem>
        </MenubarContent>
      </MenubarMenu>

      <MenubarMenu v-else>
        <MenubarTrigger as-child>
          <RouterLink
            to="/login"
            class="font-medium cursor-pointer"
          >
            Zaloguj się
          </RouterLink>
        </MenubarTrigger>
      </MenubarMenu>
    </div>
  </Menubar>
</template>


