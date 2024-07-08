<script lang="ts" setup>
import { defineProps, ref, watch } from 'vue';
import { Note } from '../model/Note';

const props = defineProps<{ visible: boolean; note: Note; mode: string }>();
const emits = defineEmits(['close', 'update-note', 'save-note']);

const localNote = ref({ ...props.note });

const updateTitle = () => {
  const target = document.getElementById('title') as HTMLHeadingElement;
  localNote.value.title = target.innerText;
};

const close = () => {
  updateTitle();
  if (localNote.value.title !== props.note.title || localNote.value.content !== props.note.content)
    emits('update-note', localNote.value);
  emits('close');
};
const save = () => {
  emits('save-note', localNote.value);
  emits('close');
};
watch(
  () => props.note,
  (newNote) => {
    localNote.value = { ...newNote };
  }
);
</script>
<template>
  <dialog v-if="visible" @click.self="close" class="py-5 dialog">
    <div class="flex flex-col w-1/2 h-fit p-5 rounded-md">
      <h2
        id="title"
        class="outline-none mt-4 p-2 text-xl border-x border-t border-gray-400 rounded-t-md"
        contenteditable="true"
      >
        {{ localNote.title }}
      </h2>
      <textarea
        class="outline-none bg-transparent p-2 min-h-60 max-h-96 text-sm border-x border-b border-gray-400 rounded-b-md"
        contenteditable="true"
        v-model="localNote.content"
      >
      </textarea>
      <div class="flex self-center gap-5">
        <button
          v-if="props.mode !== 'edit'"
          title="Save"
          type="button"
          @click="save"
          class="w-fit mt-4 p-2 hover:bg-gray-800 text-white rounded-full hover:scale-125 transition-all duration-300 ease-linear"
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
              d="M20.25 6.375c0 2.278-3.694 4.125-8.25 4.125S3.75 8.653 3.75 6.375m16.5 0c0-2.278-3.694-4.125-8.25-4.125S3.75 4.097 3.75 6.375m16.5 0v11.25c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125V6.375m16.5 0v3.75m-16.5-3.75v3.75m16.5 0v3.75C20.25 16.153 16.556 18 12 18s-8.25-1.847-8.25-4.125v-3.75m16.5 0c0 2.278-3.694 4.125-8.25 4.125s-8.25-1.847-8.25-4.125"
            />
          </svg>
        </button>
        <button
          tittle="Close"
          type="button"
          @click="close"
          class="w-fit mt-4 p-2 hover:bg-gray-800 rounded-full text-white hover:scale-125 transition-all duration-300 ease-linear"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="1.5"
            stroke="currentColor"
            class="size-6"
          >
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18 18 6M6 6l12 12" />
          </svg>
        </button>
      </div>
    </div>
  </dialog>
</template>
