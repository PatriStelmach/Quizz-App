<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useNavbarStore } from '@/store/navbar.store'
import authStore from '@/store/auth.store.ts'

import {
  Menubar,
  MenubarContent,
  MenubarItem,
  MenubarMenu,
  MenubarTrigger,
} from '@/components/ui/menubar'
import { Search } from 'lucide-vue-next'

const router = useRouter()
const useAuthStore = authStore()
const navbarStore = useNavbarStore()

const searchTerm = ref('')

const pushHome = async () =>
{
  await router.push({ name: 'home' })
}

const pushTop = async () =>
{
  await router.push({ name: 'top-players' })
}

const pushCreate = async () =>
{
  await router.push({ name: 'create-quiz' })
}

const pushLoggin = async () =>
{
  await router.push({ name: 'login' })
}

function triggerClass(active: boolean)
{
  return [
    'px-6 py-4 mx-2 rounded-lg cursor-pointer transition duration-200 ease-in-out',
    active ? 'bg-primary hover:bg-primary' : 'hover:bg-primary'
  ]
}

function onSearch()
{
  if (searchTerm.value.trim())
  {
    console.log(searchTerm.value)
    // router.push({ name: 'QuizSearch', query: { q: searchTerm.value } })
  }
}

function goToDashboard()
{
   router.push({ name: 'dashboard' })
}

function goToProfile()
{
  console.log('Go to profile')
  // router.push({ name: 'Profile', params: { userId: user.value?.id } })
}

const logout =  async () =>
{
  await useAuthStore.logout()
  await router.push({ name: 'login' })
}
</script>

<template>
  <Menubar
    class="fixed top-8 left-1/2 transform -translate-x-1/2 z-50 w-8/12 shadow-md border border-gray-300 px-4 h-24 flex items-center justify-between bg-background rounded-b-xl"
  >

  <div class="flex items-center space-x-4 focus:outline-none">
      <MenubarMenu>
        <MenubarTrigger
          :class="triggerClass(navbarStore.isHome)"
          :disabled="navbarStore.isHome"
          @click="pushHome">
          Home
        </MenubarTrigger>

      </MenubarMenu>

      <MenubarMenu>
        <div class="relative">
          <input
            v-model="searchTerm"
            @keyup.enter="onSearch"
            type="text"
            placeholder="Search quizzes..."
            class="pl-10 pr-4 py-2 border-4 rounded-full focus:outline-none focus:ring-2 focus:ring-primary"
          />
          <Search class="w-5 h-5 absolute left-3 top-1/2 transform -translate-y-1/2" />
        </div>
      </MenubarMenu>
    </div>

    <div class="flex items-center">
      <MenubarMenu>
        <MenubarMenu>
          <MenubarTrigger
            :class="triggerClass(navbarStore.isTopPlayers)"
            @click="pushTop"
            :disabled="navbarStore.isTopPlayers"
          >
            Top Players
          </MenubarTrigger>
        </MenubarMenu>

      <MenubarMenu>
        <MenubarTrigger
          :class="triggerClass(navbarStore.isCreateQuiz)"
          @click="pushCreate"
          :disabled="navbarStore.isCreateQuiz"
        >
          Create Quiz
        </MenubarTrigger>

      </MenubarMenu>

      <MenubarMenu v-if="useAuthStore.loggedIn">
        <MenubarTrigger as-child>
          <button class="px-1 py-1 rounded-lg focus:outline-none cursor-pointer hover:bg-primary transition duration-300 ease-in-out">
            <img
              src="../images/profile-dark-mode.png"
              alt="profile icon"
              class="w-10 h-10"
            />
          </button>
        </MenubarTrigger>
        <MenubarContent align="end">
          <MenubarItem class="cursor-pointer" @click="goToDashboard">Dashboard</MenubarItem>
          <MenubarItem class="cursor-pointer" @click="goToProfile">My Profile</MenubarItem>
          <MenubarItem class="cursor-pointer" @click="logout">Logout</MenubarItem>
        </MenubarContent>
      </MenubarMenu>

        <MenubarTrigger
          :class="triggerClass(navbarStore.isLogging)"
          @click="pushLoggin"
          v-if="!useAuthStore.loggedIn"
        >
          Log in
        </MenubarTrigger>
      </MenubarMenu>
    </div>
  </Menubar>
</template>
