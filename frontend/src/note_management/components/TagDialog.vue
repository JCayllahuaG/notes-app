<script lang="ts" setup>
import { ref, TransitionGroup, watch } from 'vue';
import { onMounted } from 'vue';
import { Category } from '../model/Category';
import { TagDialogMode } from '../utilities/enums';

const props = defineProps<{
  visible: boolean;
  tags: Category[];
  mode: TagDialogMode;
  selectedTags: Category[];
}>();

//variables declaration
const localTags = ref();
let tagsNotSelected = ref();
const emits = defineEmits(['close', 'update-tags', 'create-tag']);
const localSelectedTags = ref([...props.selectedTags]);
let blockers = ref<string[]>([]);

const newTag = ref<Category>(new Category('', '#000000'));
const creatingTag = ref(false);
onMounted(() => {
  if (props.mode === TagDialogMode.ASSIGN) {
    localTags.value = tagsNotSelected.value;

    if (props.selectedTags.length !== 0) {
      tagsNotSelected.value = props.tags.filter(
        (tag) => !localSelectedTags.value.some((selected) => selected.id === tag.id)
      );
    } else {
      tagsNotSelected.value = [...props.tags];
    }
  } else {
    localTags.value = [...props.tags];
  }
});

//create watch for changes in tags props

const updateTags = () => {
  emits('update-tags', localSelectedTags.value);
  emits('close');
};

watch(
  () => props.tags,
  (newTags: Category[]) => {
    localTags.value = [...newTags];
  }
);
const disassociateTag = (tag: Category) => {
  localSelectedTags.value = localSelectedTags.value.filter((t: Category) => t.id !== tag.id);

  tagsNotSelected.value.push(tag);
  blockers.value = blockers.value.filter((blocker) => blocker !== 'no-tags');
};
const addTagToNote = (tag: Category) => {
  localSelectedTags.value.push(tag);

  tagsNotSelected.value = tagsNotSelected.value.filter((t: Category) => t.id !== tag.id);
  if (tagsNotSelected.value.length === 0) {
    blockers.value.push('no-tags');
  }
};

const addTag = () => {
  creatingTag.value = false;
  localTags.value.push(newTag.value);
  emits('create-tag', newTag.value);
  newTag.value = new Category('', '#000000');
};

const close = () => {
  emits('close');
};
</script>
<template>
  <dialog v-if="visible" @click.self="close" class="dialog">
    <div class="flex flex-col gap-2 w-1/3 h-fit p-5 rounded-md">
      <div class="flex flex-row pb-2 border-b items-center justify-between">
        <h2 class="text-3xl font-semibold">Tags</h2>
        <button
          class="flex flex-row items-center drop-shadow-md p-2 rounded-full hover:bg-gray-800 transition-colors duration-300 ease-linear"
          title="Add Tag"
          type="button"
          @click="creatingTag = !creatingTag"
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
      <TransitionGroup name="creating">
        <!-- Section for tag creation and assignment -->
        <div
          v-if="creatingTag && blockers.length === 0"
          class="flex h-fit items-center w-full"
          :class="{ 'border-b': TagDialogMode.ASSIGN === mode }"
        >
          <!-- Section when is global tag mode -->
          <div class="flex flex-col justify-center gap-2 pb-6" v-if="mode === TagDialogMode.WATCH">
            <div class="flex flex-row space-x-5">
              <label class="text-lg w-2/12 font-semibold" for="category_name">Name:</label>
              <input
                autocomplete="off"
                v-model="newTag.name"
                placeholder="Category Name"
                id="category_name"
                class="outline-none py-1 h-9 px-1 border rounded-md border-gray-400 bg-transparent"
                type="text"
                name="category-name"
              />
            </div>
            <div class="flex flex-row space-x-5">
              <label class="w-2/12 text-lg font-semibold" for="category_color">Color:</label>

              <input
                class="h-9 bg-gray-400 cursor-pointer rounded-md"
                type="color"
                v-model="newTag.color"
                ref="colorPicker"
              />
            </div>
            <div class="flex flex-row gap-2">
              <button
                @click="addTag"
                title="Add Category"
                type="button"
                class="flex justify-center text-sm items-center border bg-opacity-40 bg-blue-500 border-gray-400 w-fit h-fit px-4 py-1 gap-2 text-white rounded-md hover:scale-110 transition-all duration-300 ease-linear"
              >
                Save
              </button>
              <button
                @click="creatingTag = false"
                title="Cancel"
                type="button"
                class="flex justify-center text-sm text-center items-center border bg-opacity-40 bg-red-500 border-gray-400 w-16 h-fit px-1 py-1 text-white rounded-md hover:scale-110 transition-all duration-300 ease-linear"
              >
                Cancel
              </button>
            </div>
          </div>
          <!-- Section when is tagging note mode -->
          <div v-else class="flex flex-col gap-3 py-5">
            <h3 class="text-2xl font-semibold text-white">Add</h3>
            <div class="flex flex-wrap gap-2 h-fit">
              <chip-category
                class="hover:cursor-pointer hover:scale-125 animate-fade-in-left animate-duration-300 animate-delay-300 transition-all duration-200"
                v-for="tag in tagsNotSelected"
                @click="addTagToNote(tag)"
                :key="tag.id"
                :text="tag.name"
                :color="tag.color"
                :active="selectedTags.includes(tag)"
              ></chip-category>
            </div>
          </div>
        </div>
      </TransitionGroup>
      <!-- Section for show existing tags when is global tag mode -->
      <div v-if="mode === TagDialogMode.WATCH" class="mt-2 p-2 flex flex-wrap gap-2 h-96 border">
        <chip-category
          class="animate-fade-in-left animate-duration-300 animate-delay-300"
          v-for="tag in localTags"
          :key="tag.id"
          :text="tag.name"
          :color="tag.color"
          :active="false"
        ></chip-category>
      </div>
      <!-- Section for show tag associated to a note -->
      <div v-else class="flex flex-col py-2 gap-3">
        <h3 class="text-2xl font-semibold text-white">My Tags</h3>
        <div class="flex flex-wrap gap-2 h-fit">
          <chip-category
            class="animate-fade-in-left animate-duration-300 animate-delay-300 hover:cursor-pointer"
            v-for="tag in localSelectedTags"
            @click="disassociateTag(tag)"
            :key="tag.id"
            :text="tag.name"
            :color="tag.color"
            :active="true"
          ></chip-category>
        </div>
      </div>

      <div class="flex self-center gap-5">
        <button
          v-if="mode === TagDialogMode.ASSIGN"
          title="Save"
          type="button"
          @click="updateTags"
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
<style scoped>
input[type='color']::-webkit-color-swatch-wrapper {
  padding: 0;
}
input[type='color']::-webkit-color-swatch {
  border: none;
  border-radius: 0.375rem;
}
.creating-enter-from,
.creating-enter-active,
.creating-enter-to {
  @apply animate-slide-in-top animate-duration-200 animate-ease-out;
}
</style>
