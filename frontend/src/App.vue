<style>
@import "@/assets/globals.css";
</style>
<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
import ViewMode from "@/components/ViewMode.vue";
import Navbar from '@/components/Navbar.vue'
</script>

<template>
  <ViewMode></ViewMode>
  <header>
<Navbar/>
  </header>

  <RouterView/>
</template>


