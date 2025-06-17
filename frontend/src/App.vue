<style>
@import "@/assets/globals.css";
</style>
<script setup lang="ts">
import { RouterView } from 'vue-router'
import ViewMode from "@/components/ViewMode.vue";
import Navbar from '@/components/Navbar.vue'
import FriendsSidebar from '@/components/FriendsSidebar.vue'
</script>

<template>
  <header class="mb-40">
    <ViewMode />
    <Navbar />
  </header>

  <main class="flex ">
    <RouterView />
    <div class="friends-list-portal" />
  </main>
  <FriendsSidebar/>
</template>


