<script lang="ts" setup>
import { onMounted, ref, computed } from 'vue';
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

const authStore = useAuthStore();
const route = useRoute();
const router = useRouter();
const roomId = route.params.roomId as string;
const players = ref<string[]>([]);
const ownerName = ref<string>('');
const isOwner = computed(() => ownerName.value === userName);

const userName = authStore.username ?? "UserNameError";
onMounted(async () => {
  if (!authStore.username) {
    await router.push('/login');
    return;
  }

  try {
    const response = await axios.get(`http://localhost:10000/rooms/get?roomId=${roomId}`);
    console.log('response:', response.data.players);

    players.value = response.data.players || [];

    console.log('players.value:', players.value);
  } catch (err) {
    console.error("Failed to fetch room:", err);
  }

  connectSocket(() =>
  {
    sendRoomMessage(roomId, { type: 'join', playerName: userName });
  }, message => {
    //if msg is [] -> players
    if (message.players && message.owner)
    {
      players.value = message.players;
      ownerName.value = message.owner;
    }
    //if msg is quiz-start -> start game
    else if (message.type === 'quiz-start') {
      router.push({ name: 'game', params: { roomId } });
    }
  }, roomId);
});

const startGame = () => {
  sendRoomMessage(roomId, { type: 'start' });
};
</script>

<template>
  <Card class="w-[1000px] mx-auto mt-8 shadow-secondary/50 shadow-xl">
    <CardHeader>
      <CardTitle class="text-4xl text-center text-shadow-sky-800" >Room Lobby</CardTitle>
      <CardDescription class="text-center">Invite players or start when ready</CardDescription>
    </CardHeader>

    <CardContent>
      <div class="mb-8">
        <Label class="block text-2xl text-center font-medium ">Room ID</Label>
        <p class="text-3xl text-primary/80 text-center">{{roomId}}</p>
      </div>


      <div class="mt-8">
        <PlayerList :players="players" :currentUserName="userName" class="mt-2" />
      </div>
    </CardContent>

    <CardFooter v-if="isOwner" class="flex justify-end">
      <Button @click="startGame" :disabled="players.length < 1">
        Start Game
      </Button>
    </CardFooter>
    <CardFooter v-else class="flex justify-end">
      <p class="text-muted-foreground italic">Waiting for the host ({{ ownerName }}) to start...</p>
    </CardFooter>

  </Card>

  <div v-if="!userName" class="text-center mt-6">
    <Alert>
      <AlertTitle>User not logged in</AlertTitle>
      <AlertDescription>
        Please log in to join or create a room.
      </AlertDescription>
    </Alert>
  </div>
</template>


