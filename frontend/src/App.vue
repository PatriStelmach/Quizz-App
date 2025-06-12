<style>
@import "@/assets/globals.css";
</style>
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import ViewMode from "@/components/ViewMode.vue";
import LoginForm from "@/components/LoginForm.vue";
import { computed } from 'vue'


const users = computed(() => result.value?.allUsers ?? []);
console.log(users);
</script>

<template>
  <ViewMode></ViewMode>
  <header>

  </header>
<LoginForm></LoginForm>
  <RouterView/>
</template>


