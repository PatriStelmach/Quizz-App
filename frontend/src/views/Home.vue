<script setup lang="ts">

import { Button } from '@/components/ui/button';
import authService from "@/services/auth.service.ts";
import authHeader from "@/services/data.service.ts";

const onClick =  async () =>
{
  try
  {
    const headers = authHeader();
    const response = await authService.getall(headers);
    console.log(response.data)
  }
  catch (error)
  {
    console.error('Error: ', error)
  }
}
</script>

<template>
  <h1 class = "text-center text-3xl text-pink-500">
    HOOOOOOOOOOOOOOMEEEEEEEE
  </h1>
  <Button @click="onClick"
          class ="bg-red-600 cursor-pointer"
  >
    SPRAWDŹ TOKEN</Button>
</template>

