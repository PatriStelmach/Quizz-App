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

  <header class="mb-20">
    <ViewMode />
    <Navbar />
  </header>

  <main class="flex min-h-screen">
    <div class="z-50">
      <FriendsSidebar />
    </div>

    <div class="flex-1 absolute left-1/3 right-1/3 p-6">
      <RouterView />
    </div>
  </main>
</template>


