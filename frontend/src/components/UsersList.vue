<template>
  <div>
    <h2>Users</h2>
    <ul>
      <li v-for="user in users" :key="user.id">{{ user?.email }}</li>
    </ul>
  </div>
</template>

<script lang="ts" setup>
import { useQuery } from '@vue/apollo-composable'
import { computed } from 'vue'
import {UsersDocument, type UsersQuery} from "@/generated/graphql.ts";

const { result } = useQuery<UsersQuery>(UsersDocument)
const users = computed(() => result.value?.allUsers ?? [])


</script>
