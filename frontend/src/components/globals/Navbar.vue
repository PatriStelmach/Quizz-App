<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useNavbarStore } from '@/store/useNavbarStore.ts'
import useAuthStore from '@/store/useAuthStore.ts'

import {
  Menubar,
  MenubarMenu,
  MenubarTrigger,
} from '@/components/ui/menubar'
import { onMounted, onBeforeUnmount } from 'vue'
import { Button } from '@/components/ui/button'


const router = useRouter()
const authStore = useAuthStore()
const navbarStore = useNavbarStore()
const showDropdown = ref(false)
const searchContainer = ref<HTMLElement | null>(null)
const roomId = ref('');

onMounted(() =>
{
  document.addEventListener('click', clickOutside)
})

onBeforeUnmount(() =>
{
  document.removeEventListener('click', clickOutside)
})
const pushHome = async () =>
{
  await router.push({ name: 'home' })
}

const pushAll = async () =>
{
  await router.push({ name: 'all-quizes' })
}

const pushCreate = async () =>
{
  await router.push({ name: 'create-quiz' })
}

const pushLoggin = async () =>
{
  await router.push({ name: 'login' })
}




function enterRoom()
{
  if (roomId.value.trim())
  {
    router.push({ name: 'room', params: { roomId: roomId.value } });
  } else {
    alert('Please enter a valid room ID');
  }
}

function triggerClass(active: boolean)
{
  return [
    'px-8 py-2 mx-2 rounded-md cursor-pointer transition duration-200 ease-in-out',
    active ? 'bg-primary hover:bg-primary' : 'hover:bg-primary'
  ]
}


function clickOutside(event: MouseEvent)
{
  if(searchContainer.value && !searchContainer.value.contains(event.target as Node))
  {
    showDropdown.value = false;
  }
}



</script>

<template>
  <Menubar
    class="fixed top-2 left-1/2 transform -translate-x-1/2 z-50 w-10/12 shadow-md shadow-primary px-4 h-14 flex items-center justify-between bg-card text-card-foreground rounded-b-md relative"
  >
    <div ref="searchContainer" class="flex items-center space-x-2 z-10">
      <MenubarMenu>
        <MenubarTrigger
          :class="triggerClass(navbarStore.isHome)"
          :disabled="navbarStore.isHome"
          @click="pushHome"
        >
          Home
        </MenubarTrigger>
      </MenubarMenu>

      <MenubarMenu>
        <MenubarTrigger
          :class="triggerClass(navbarStore.isAllQuizes)"
          @click="pushAll"
          :disabled="navbarStore.isAllQuizes"
        >
          Quizzusie
        </MenubarTrigger>
      </MenubarMenu>
      <MenubarMenu>
        <MenubarTrigger
          :class="triggerClass(navbarStore.isCreateQuiz)"
          @click="pushCreate"
          :disabled="navbarStore.isCreateQuiz"
        >
          Create Quizzuś
        </MenubarTrigger>
      </MenubarMenu>

    </div>


    <div class="absolute left-1/2 transform -translate-x-1/2">
      <img class="w-120" src="@/assets/QUIZZUŚ.PNG" alt="logo" />
    </div>


    <div class="flex items-center z-10">


      <input
        v-model="roomId"
        @keyup.enter="enterRoom"
        type="text"
        placeholder="Paste room ID…"
        class="px-3 py-1 mr-4 border rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
      />
      <Button
        @click="enterRoom"
        class="px-3 py-1 w-30 bg-primary rounded-md hover:bg-primary/80 transition"
      >
        Enter
      </Button>


<MenubarMenu>
  <MenubarTrigger
  :class="triggerClass(navbarStore.isLogging)"
  @click="pushLoggin"
  v-if="!authStore.loggedIn"
>
  Log in
</MenubarTrigger>
</MenubarMenu>

    </div>
  </Menubar>
</template>
