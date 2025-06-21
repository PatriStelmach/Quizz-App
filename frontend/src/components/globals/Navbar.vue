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
import { Search } from 'lucide-vue-next'
import { onMounted, onBeforeUnmount } from 'vue'
import { useQuery } from '@vue/apollo-composable'
import { computed } from 'vue'
import { AllQuizzesDocument, type AllQuizzesQuery } from "@/generated/graphql.ts";
import CreateRoomButton from '@/components/CreateRoomButton.vue'
import { Button } from '@/components/ui/button'


const router = useRouter()
const authStore = useAuthStore()
const navbarStore = useNavbarStore()
const showDropdown = ref(false)
const searchTerm = ref('')
const searchContainer = ref<HTMLElement | null>(null)
const roomId = ref('');

const { result } = useQuery<AllQuizzesQuery>(AllQuizzesDocument)
const quizList = computed(() => result.value?.allQuizzes ?? [])

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

const pushTop = async () =>
{
  await router.push({ name: 'top-players' })
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

function filteredList()
{
  return quizList.value.filter((quiz) =>
    quiz.title.toLowerCase().includes(searchTerm.value.toLowerCase())
  );
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

function onSearch()
{
  if (searchTerm.value.trim())
  {
    showDropdown.value = true;
  }
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
    class="fixed top-2 left-1/2 transform -translate-x-1/2 z-50 w-8/12 shadow-md shadow-primary  px-4 h-14 flex items-center justify-between bg-card text-card-foreground rounded-b-md"
  >

  <div ref="searchContainer" class="flex items-center space-x-4 focus:outline-none">
      <MenubarMenu>
        <MenubarTrigger
          :class="triggerClass(navbarStore.isHome)"
          :disabled="navbarStore.isHome"
          @click="pushHome">
          Home
        </MenubarTrigger>

      </MenubarMenu>

    <div class="flex items-center space-x-2 px-4">
           <input
             v-model="roomId"
             @keyup.enter="enterRoom"
             type="text"
             placeholder="Paste room ID…"
             class="px-3 py-1 border rounded-md focus:outline-none focus:ring-2 focus:ring-primary"
           />
           <Button
             @click="enterRoom"
             class="px-3 py-1 bg-primary  rounded-md hover:bg-primary/80 transition"
           >
             Enter
           </Button>
         </div>
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

        <MenubarMenu>
          <MenubarTrigger
            :class="triggerClass(navbarStore.isAllQuizes)"
            @click="pushAll"
            :disabled="navbarStore.isAllQuizes"
          >
            Quizzusie
          </MenubarTrigger>
      </MenubarMenu>

        <MenubarTrigger>
         <CreateRoomButton></CreateRoomButton>
        </MenubarTrigger>


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
