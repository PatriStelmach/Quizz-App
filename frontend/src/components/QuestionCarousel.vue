<script setup lang="ts">
import { watchOnce } from '@vueuse/core'
import { ref } from 'vue'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select'
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel'
import type { QuestionType} from "@/generated/graphql.ts";

const questionType = ref<QuestionType>()
const emblaThumbnailApi = ref<CarouselApi>()
const selectedIndex = ref(0)

function onSelect() {
  if (!emblaMainApi.value || !emblaThumbnailApi.value)
    return
  selectedIndex.value = emblaMainApi.value.selectedScrollSnap()
  emblaThumbnailApi.value.scrollTo(emblaMainApi.value.selectedScrollSnap())
}

function onThumbClick(index: number) {
  if (!emblaMainApi.value || !emblaThumbnailApi.value)
    return
  emblaMainApi.value.scrollTo(index)
}

watchOnce(questionType, (emblaMainApi) => {
  if (!emblaMainApi)
    return

  onSelect()
  emblaMainApi.on('select', onSelect)
  emblaMainApi.on('reInit', onSelect)
})
</script>

<template>
  <div class="w-full m-auto sm:w-auto">
    <Carousel
      class="relative w-full max-w-xs"
      @init-api="(val) => questionType = val"
    >
      <CarouselContent>
        <CarouselItem v-for="(_, index) in 10" :key="index">
          <div class="p-1">
            <Card class="">
              <CardHeader>
                <CardTitle>Create project</CardTitle>
                <CardDescription>Deploy your new project in one-click.</CardDescription>
              </CardHeader>
              <CardContent>
                <form>
                  <div class="grid items-center w-full gap-4">
                    <div class="flex flex-col space-y-1.5">
                      <Label for="name">Name</Label>
                      <Input id="name" placeholder="Question" />
                    </div>
                    <div class="flex flex-col space-y-1.5">
                      <Label for="question">Question type</Label>
                      <Select>
                        <SelectTrigger id="question">
                          <SelectValue placeholder="Select question type" />
                        </SelectTrigger>
                        <SelectContent position="popper">
                          <SelectItem
                            v-if="openQuestion"
                            value="openQ">
                            Open answer
                          </SelectItem>
                          <SelectItem
                            v-if="multipleQuestion"
                            value="multiQ">
                            Multiple choice
                          </SelectItem>
                          <SelectItem
                            v-if="signleQuestion"
                            value="singleQ">
                            Single Choice
                          </SelectItem>
                          <SelectItem
                            v-if="trueFalseQuestion"
                            value="trueFalse">
                            True / False
                          </SelectItem>
                        </SelectContent>
                      </Select>
                    </div>
                  </div>
                </form>
              </CardContent>
              <CardFooter class="flex justify-between px-6 pb-6">
                <Button variant="outline">
                  Cancel
                </Button>
                <Button>Deploy</Button>
              </CardFooter>
            </Card>
          </div>
        </CarouselItem>
      </CarouselContent>
      <CarouselPrevious />
      <CarouselNext />
    </Carousel>

    <Carousel
      class="relative w-full max-w-xs"
      @init-api="(val) => emblaThumbnailApi = val"
    >
      <CarouselContent class="flex gap-1 ml-0">
        <CarouselItem v-for="(_, index) in 10" :key="index" class="pl-0 basis-1/4 cursor-pointer" @click="onThumbClick(index)">
          <div class="p-1" :class="index === selectedIndex ? '' : 'opacity-50'">
            <Card>
              <CardContent class="flex aspect-square items-center justify-center p-6">
                <span class="text-4xl font-semibold">{{ index + 1 }}</span>
              </CardContent>
            </Card>
          </div>
        </CarouselItem>
      </CarouselContent>
    </Carousel>
  </div>
</template>
