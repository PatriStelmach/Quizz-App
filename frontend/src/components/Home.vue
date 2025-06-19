<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import authStore from '@/store/auth.store.ts'

const useAuthStore = authStore()
const route = useRoute()
const router = useRouter()

onMounted(async () =>
{
  const token = route.query.token as string;
  if (token)
  {
    try {
      await useAuthStore.googleLogin(token);
      await router.replace({ path: '/home' });
      console.log(token);
    } catch (err) {
      console.error('Google login failed:', err);
    }
  }
});

</script>

<template>
<h1>HOOOOOOOOOOOOOOOOOOOOME</h1>
</template>

<style scoped>

</style>
