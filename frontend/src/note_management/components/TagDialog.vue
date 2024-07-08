<script lang="ts" setup>
import { ref } from 'vue';
import { Category } from '../model/Category';
import { TagDialogMode } from '../utilities/enums';

const props = defineProps<{
  visible: boolean;
  tags: Category[];
  mode: TagDialogMode;
  selectedTags: Category[];
}>();
const includeTag = (tag: Category) => {
  if (props.selectedTags.length === 0) return false;

  if (props.selectedTags.includes(tag)) return true;
  return false;
};

const selectedColor = ref('#fefefe');

const emits = defineEmits(['close', 'update-tags', 'save-tag']);
const localTags = ref<Category[]>({ ...props.tags });
const close = () => {
  emits('close');
};
</script>
<template>
  <dialog v-if="visible" @click.self="close" class="dialog backdrop-blur-md">
    <div class="flex flex-col w-1/3 h-fit p-5 rounded-md gap-10">
      <div class="flex flex-row pb-2 border-b items-center justify-between">
        <h2 class="text-3xl font-semibold">Tags</h2>
        <button
          class="flex flex-row items-center drop-shadow-md p-2 rounded-full hover:bg-gray-800 transition-colors duration-300 ease-linear"
          title="Add Tag"
          type="button"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="size-6 hover:scale-125 transition-transform duration-300 ease-linear"
          >
            <path stroke-linecap="round" stroke-linejoin="round" d="M12 4.5v15m7.5-7.5h-15" />
          </svg>
        </button>
      </div>
      <div class="flex flex-col gap-2 pb-6 border-b">
        <div class="flex flex-row gap-2">
          <label class="text-lg w-2/12 font-semibold" for="category_name">Name:</label>
          <input
            id="category_name"
            class="outline-none py-1 h-9 px-0.5 border rounded-md border-gray-400 bg-transparent"
            type="text"
            name="category-name"
          />
        </div>
        <div class="flex flex-row gap-2">
          <label class="w-2/12 text-lg font-semibold" for="category_color">Color:</label>

          <input
            class="h-9 bg-gray-400 cursor-pointer rounded-md"
            type="color"
            v-model="selectedColor"
            ref="colorPicker"
          />
        </div>
        <div class="flex flex-row gap-2">
          <button
            title="Add Category"
            type="button"
            class="flex justify-center text-sm items-center border bg-opacity-40 bg-blue-500 border-gray-400 w-fit h-fit px-4 py-1 gap-2 text-white rounded-md hover:scale-110 transition-all duration-300 ease-linear"
          >
            Save
          </button>
          <button
            title="Cancel"
            type="button"
            class="flex justify-center text-sm text-center items-center border bg-opacity-40 bg-red-500 border-gray-400 w-16 h-fit px-1 py-1 text-white rounded-md hover:scale-110 transition-all duration-300 ease-linear"
          >
            Cancel
          </button>
        </div>
      </div>

      <div class="grid grid-flow-col auto-cols-max gap-2 h-fit lg:w-2/3 md:2/3 w-full">
        <chip-category
          v-for="tag in tags"
          :key="tag.id"
          :text="tag.name"
          :color="tag.color"
          :active="selectedTags.includes(tag)"
        ></chip-category>
      </div>
    </div>
  </dialog>
</template>
<style scoped>
input[type='color']::-webkit-color-swatch-wrapper {
  padding: 0;
}
input[type='color']::-webkit-color-swatch {
  border: none;
  @apply rounded-md;
}
</style>
