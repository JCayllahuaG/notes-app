<script lang="ts" setup>
import { defineProps, ref } from 'vue';
let props = defineProps<{
  text: string;
  color: string;
  active: boolean;
}>();

let color = ref(props.color);
const convertHexToRGBA = (hexCode: string, opacity: number) => {
  hexCode = hexCode.replace('#', '');

  const r = parseInt(hexCode.substring(0, 2), 16);
  const g = parseInt(hexCode.substring(2, 4), 16);
  const b = parseInt(hexCode.substring(4, 6), 16);

  const alpha = opacity / 100;

  return `rgba(${r}, ${g}, ${b}, ${alpha})`;
};
</script>
<template>
  <div
    class="w-fit h-7 flex justify-center items-center border rounded-lg py-1.5 px-3 font-sans text-xs font-semibold uppercase text-gray-200"
    :style="{
      backgroundColor: active ? convertHexToRGBA(color, 100) : convertHexToRGBA(color, 10),
      borderColor: props.color
    }"
  >
    {{ props.text }}
  </div>
</template>
