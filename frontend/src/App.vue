<style>
@import "@/assets/globals.css";
</style>
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import ViewMode from "@/components/ViewMode.vue";
import LoginForm from "@/components/LoginForm.vue";
import UsersList from '@/components/UsersList.vue'
</script>

<template>
  <ViewMode></ViewMode>
  <header>
<UsersList/>
  </header>
<LoginForm></LoginForm>
  <RouterView/>
</template>


