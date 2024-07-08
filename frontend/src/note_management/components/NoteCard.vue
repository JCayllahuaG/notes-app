<script lang="ts" setup>
import { Note } from '../model/Note';
import { watch, ref } from 'vue';
let props = defineProps<{
  note: Note;
}>();

const emits = defineEmits(['delete-note', 'click-note', 'archive-note', 'unarchive-note', 'tags']);
let localNote = ref(props.note);

watch(
  () => props.note,
  (newNote: Note) => {
    console.log('Note Updated', localNote.value);
    console.log('New Note', newNote);
    localNote.value = { ...newNote }; // Update localNote with the new value
  }
);

//emits
const deleteNote = () => {
  emits('delete-note', props.note.id);
};
const archiveNote = () => {
  emits('archive-note', props.note.id);
};
const unarchiveNote = () => {
  emits('unarchive-note', props.note.id);
};
</script>
<template>
  <div class="flex w-full max-h-48 flex-row animate-fade-in-down animate-duration-300">
    <div class="w-1/6 flex flex-col justify-center items-center">
      <button
        title="Tag"
        @click="emits('tags', localNote)"
        class="w-fit h-fit p-2 hover:bg-gray-800 hover:text-opacity-100 hover:scale-110 text-yellow-500 text-opacity-70 rounded-full transition-all duration-300 ease-linear"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="size-5 hover:scale-125 transition-transform duration-300 ease-linear"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M9.568 3H5.25A2.25 2.25 0 0 0 3 5.25v4.318c0 .597.237 1.17.659 1.591l9.581 9.581c.699.699 1.78.872 2.607.33a18.095 18.095 0 0 0 5.223-5.223c.542-.827.369-1.908-.33-2.607L11.16 3.66A2.25 2.25 0 0 0 9.568 3Z"
          />
          <path stroke-linecap="round" stroke-linejoin="round" d="M6 6h.008v.008H6V6Z" />
        </svg>
      </button>
    </div>
    <div
      @click="emits('click-note', localNote)"
      class="w-full p-3 border gap-3 border-gray-400 rounded-md drop-shadow-xl hover:scale-105 transition-all duration-200 ease-linear"
    >
      <h3 class="max-h-16">
        {{ localNote.title }}
      </h3>
      <p class="text-sm max-h-20 overflow-hidden">
        {{ localNote.content }}
      </p>
      <div class="flex flex-wrap gap-2 h-fit">
        <chip-category
          class="mt-2"
          v-for="category in localNote.categories"
          :key="category.id"
          :text="category.name"
          :color="category.color"
          :active="false"
        ></chip-category>
      </div>
    </div>

    <div class="w-1/6 flex flex-col justify-center items-center">
      <button
        title="Delete"
        class="w-fit h-fit p-2 hover:bg-gray-800 hover:text-opacity-100 hover:scale-110 text-red-500 text-opacity-70 rounded-full transition-all duration-300 ease-linear"
        @click="deleteNote"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="size-6"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="m14.74 9-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 0 1-2.244 2.077H8.084a2.25 2.25 0 0 1-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 0 0-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 0 1 3.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 0 0-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 0 0-7.5 0"
          />
        </svg>
      </button>
      <button
        title="Archive"
        v-if="!props.note.archived"
        class="w-fit h-fit p-2 hover:bg-gray-800 hover:text-opacity-100 hover:scale-110 text-gray-300 text-opacity-70 rounded-full transition-all duration-300 ease-linear"
        @click="archiveNote"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="size-6"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="m20.25 7.5-.625 10.632a2.25 2.25 0 0 1-2.247 2.118H6.622a2.25 2.25 0 0 1-2.247-2.118L3.75 7.5m8.25 3v6.75m0 0-3-3m3 3 3-3M3.375 7.5h17.25c.621 0 1.125-.504 1.125-1.125v-1.5c0-.621-.504-1.125-1.125-1.125H3.375c-.621 0-1.125.504-1.125 1.125v1.5c0 .621.504 1.125 1.125 1.125Z"
          />
        </svg>
      </button>
      <button
        title="Unarchive"
        v-if="props.note.archived"
        class="w-fit h-fit p-2 hover:bg-gray-800 hover:text-opacity-100 hover:scale-110 text-gray-300 text-opacity-70 rounded-full transition-all duration-300 ease-linear"
        @click="unarchiveNote"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 24 24"
          stroke-width="1.5"
          stroke="currentColor"
          class="size-6"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M7.5 7.5h-.75A2.25 2.25 0 0 0 4.5 9.75v7.5a2.25 2.25 0 0 0 2.25 2.25h7.5a2.25 2.25 0 0 0 2.25-2.25v-7.5a2.25 2.25 0 0 0-2.25-2.25h-.75m0-3-3-3m0 0-3 3m3-3v11.25m6-2.25h.75a2.25 2.25 0 0 1 2.25 2.25v7.5a2.25 2.25 0 0 1-2.25 2.25h-7.5a2.25 2.25 0 0 1-2.25-2.25v-.75"
          />
        </svg>
      </button>
    </div>
  </div>
</template>
