<style>
@import "@/assets/globals.css";
</style>
<script setup lang="ts">
import { RouterView } from 'vue-router'
import ViewMode from "@/components/globals/ViewMode.vue";
import Navbar from '@/components/globals/Navbar.vue'
import FriendsSidebar from '@/components/profile/FriendsSidebar.vue'
</script>

<template>
  <header class="mb-25">
    <ViewMode />
    <Navbar />
  </header>

  <main class="flex ">
    <RouterView />
    <div class="friends-list-portal" />
  </main>
  <FriendsSidebar/>
</template>


