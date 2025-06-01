<script setup lang="ts">
import { useQuery } from '@vue/apollo-composable'
import gql from 'graphql-tag'
import {watch} from "vue";

export default {
  setup () {
    const { result } = useQuery(gql`
      query getUsers{
        users {
          username
          roles
          email
          avatar
          bio
          isActive
          accountLocked
          enabled
          createdAt
          updatedAt
          birthDate
          userLevel
          streak
          score
          finished_amount
          finished_quizzes
          quizScore
          friendsList
          quizzes
        }
      }
    `)
    return {
      result,
    }
  },
}



</script>

<template>
  <ul>
    <li v-for="user of result.users" :key="user.id">
      {{ user.firstname }} {{ user.lastname }}
    </li>
  </ul>
</template>

<style scoped>

</style>
