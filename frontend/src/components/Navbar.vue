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

const pushHome = async () => {
  await router.push({ name: 'home' })
}

const pushTop = async () => {
  await router.push({ name: 'top-players' })
}

const pushCreate = async () => {
  await router.push({ name: 'create-quiz' })
}

const pushLoggin = async () => {
  await router.push({ name: 'login' })
}

function onSearch() {
  if (searchTerm.value.trim()) {
    console.log(searchTerm.value)
    // router.push({ name: 'QuizSearch', query: { q: searchTerm.value } })
  }
}

function goToDashboard() {
  console.log('Dashboard')
  // router.push({ name: 'Dashboard' })
}

function goToProfile() {
  console.log('Go to profile')
  // router.push({ name: 'Profile', params: { userId: user.value?.id } })
}

const logout =  async () => {
  await useAuthStore.logout()
  await router.push({ name: 'login' })
}
</script>

<template>
  <Menubar class="w-9/12 shadow-md border border-gray-300 px-4 content-center m-auto h-15 flex items-center justify-between focus:outline-none">
    <div class="flex items-center space-x-4 focus:outline-none">
      <MenubarMenu>
        <MenubarTrigger
          v-if="!navbarStore.isHome"
          class="text-xl py-2 px-8 font-bold rounded-lg cursor-pointer focus:outline-none hover:bg-primary transition duration-300 ease-in-out"
          :disabled="navbarStore.isHome"
          @click="pushHome">
          Home
        </MenubarTrigger>
        <MenubarTrigger
          v-if="navbarStore.isHome"
          class="text-xl bg-primary py-2 px-8 font-bold rounded-lg cursor-pointer focus:outline-none hover:bg-primary transition duration-300 ease-in-out"
          :disabled="!navbarStore.isHome"
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
        <MenubarTrigger
          class="px-4 py-2 rounded-lg cursor-pointer hover:bg-primary transition duration-300 ease-in-out"
          v-if="!navbarStore.isTopPlayers"
          @click="pushTop"
          :disabled="navbarStore.isTopPlayers">
          Top Players
        </MenubarTrigger>
        <MenubarTrigger
          class="px-4 py-2 bg-primary rounded-lg cursor-pointer hover:bg-primary transition duration-300 ease-in-out"
          v-if="navbarStore.isTopPlayers"
          @click="pushTop"
          :disabled="!navbarStore.isTopPlayers">
          Top Players
        </MenubarTrigger>
      </MenubarMenu>

      <MenubarMenu>
        <MenubarTrigger
          class="px-4 py-2 rounded-lg cursor-pointer hover:bg-primary transition duration-300 ease-in-out"
          v-if="!navbarStore.isCreateQuiz"
          @click="pushCreate"
          :disabled="navbarStore.isCreateQuiz">
          Create Quiz
        </MenubarTrigger>
        <MenubarTrigger
          class="px-4 py-2 bg-primary rounded-lg cursor-pointer hover:bg-primary transition duration-300 ease-in-out"
          v-if="navbarStore.isCreateQuiz"
          @click="pushCreate"
          :disabled="!navbarStore.isCreateQuiz">
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

      <MenubarMenu v-else>
        <MenubarTrigger
          @click="pushLoggin"
          v-if="!navbarStore.isLogging"
          :disabled="navbarStore.isLogging"
          class="px-4 py-2 rounded-lg cursor-pointer hover:bg-primary transition duration-300 ease-in-out"
        >
          Log in
        </MenubarTrigger>
        <MenubarTrigger
          @click="pushLoggin"
          v-if="navbarStore.isLogging"
          :disabled="!navbarStore.isLogging"
          class="px-4 py-2 bg-primary rounded-lg cursor-pointer hover:bg-primary transition duration-300 ease-in-out"
        >
          Log in
        </MenubarTrigger>
      </MenubarMenu>
    </div>
  </Menubar>
</template>
