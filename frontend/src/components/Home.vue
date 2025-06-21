<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import useAuthStore from '@/store/useAuthStore.ts'


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


</script>

<template>


</template>

<style scoped>

</style>
