<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { connectSocket, sendRoomMessage } from '@/lib/socket.ts';
import axios from 'axios';
import PlayerList from '@/components/PlayerList.vue';
import useAuthStore from "@/store/useAuthStore.ts";
import {
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from '@/components/ui/card'
import { Label } from '@/components/ui/label'
import { Button } from '@/components/ui/button'
import { Alert, AlertTitle, AlertDescription } from '@/components/ui/alert'
import {computed} from 'vue'
const authStore = useAuthStore();
const route = useRoute();
const router = useRouter();
const roomId = route.params.roomId as string;
const players = ref<string[]>([]);
const copySuccess = ref(false);
const ownerName = ref<string>('');


const userName = authStore.username ?? "UserNameError";
onMounted(async () => {
  if (!authStore.username) {
    await router.push('/login');
    return;
  }

  try {
    const response = await axios.get(`http://localhost:10000/rooms/get?roomId=${roomId}`);
    players.value = response.data.players || [];
  } catch (err) {
    console.error("Failed to fetch room:", err);
  }

  connectSocket(() =>
  {
    sendRoomMessage(roomId, { type: 'join', playerName: userName });
  }, message =>
  {
    if (message.players && message.owner)
    {
      players.value = message.players;
      ownerName.value = message.owner;
    }
    else if (message.type === 'quiz-start') {
      router.push({ name: 'game', params: { roomId } });
    }
  }, roomId);
});

const startGame = () => {
  sendRoomMessage(roomId, { type: 'start' });
};

const copyLink = async () => {
  try {
    const url = `${window.location.origin}/room/${roomId}`;
    await navigator.clipboard.writeText(url);
    copySuccess.value = true;
    setTimeout(() => { copySuccess.value = false; }, 2000);
  } catch (err) {
    console.error('Failed to copy:', err);
  }
};
</script>

<template>
  <Card class="w-[1000px] mx-auto mt-8 shadow-secondary/50 shadow-xl">
    <CardHeader>
      <CardTitle class="text-4xl text-center text-shadow-sky-800">Room Lobby</CardTitle>
      <CardDescription class="text-center">Invite players or start when ready</CardDescription>
    </CardHeader>

    <CardContent>
      <div class="mb-8">
        <Label class="block text-2xl text-center font-medium">Room ID</Label>
        <p class="text-3xl text-primary/80 text-center">{{ roomId }}</p>
      </div>


      <div class="mt-8">
        <PlayerList :players="players" :currentUserName="userName" class="mt-2" />
      </div>
    </CardContent>


    <CardFooter class="justify-between items-center">
      <Button @click="startGame" :disabled="players.length < 1">
        Start Game
      </Button>
      <div class="flex items-center">
        <Button @click="copyLink">
          Copy room link to clipboard
        </Button>
        <span v-if="copySuccess" class="ml-4 text-green-500">Link copied!</span>
      </div>
    </CardFooter>

    <div v-if="!userName" class="text-center mt-6">
      <Alert>
        <AlertTitle>User not logged in</AlertTitle>
        <AlertDescription>
          Please log in to join or create a room.
        </AlertDescription>
      </Alert>
    </div>
  </Card>
</template>


