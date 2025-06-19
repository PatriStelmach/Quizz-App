<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import useAuthStore from '@/store/useAuthStore.ts'

const roomId = ref('');
const authStore = useAuthStore();
const route = useRoute()
const router = useRouter()

onMounted(async () =>
{
  const token = route.query.token as string;
  if (token)
  {
    try {
      await authStore.googleLogin(token);
      await router.replace({ path: '/home' });
      console.log(token);
    } catch (err) {
      console.error('Google login failed:', err);
    }
  }
});



function enterRoom() {
  if (roomId.value.trim()) {
    router.push({ name: 'room', params: { roomId: roomId.value } });
  } else {
    alert('Please enter a valid room ID');
  }
}
</script>

<template>
  <BUTTON></BUTTON>
<h1>HOOOOOOOOOOOOOOOOOOOOME</h1>

  <input v-model="roomId" placeholder="Enter Room ID" />
  <Button
    @click="enterRoom()">
    test button
  </Button>
</template>

<style scoped>

</style>
