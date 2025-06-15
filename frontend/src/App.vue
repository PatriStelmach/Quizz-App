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
  <ViewMode />
  <header>
    <Navbar />
  </header>

  <div class=" z-50 flex min-h-screen">
    <FriendsSidebar />
    <main class="flex-1 p-6">
      <RouterView />
    </main>
  </div>
</template>


