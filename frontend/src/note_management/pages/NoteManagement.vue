<script lang="ts" setup>
//imports and setup
import { Note } from '../model/Note';
import { NoteService } from '../services/note-api.service';
import { CategoryService } from '../services/category-api.service';
import { ref, computed } from 'vue';
import { NoteWatch, TagDialogMode } from '../utilities/enums';
import type { Category } from '../model/Category';
import { onMounted } from 'vue';

//services
const noteService = new NoteService();
const categoryService = new CategoryService();

//Data variables
let noteArray = ref<Note[]>([]);
let categoryArray = ref<Category[]>([]);
let activeNote = ref<Note | null>(null);

//Filters Variables
let searchTerm = ref('');
let mode = ref('');

let visionMode = ref<NoteWatch>(NoteWatch.EVERYTHING);
let filtersActive = ref<boolean>(false);
let selectedCategoriesFilter = ref<Category[]>([]);

const dialogVisible = ref(false);
const tagsDialogVisible = ref(false);
const tagsDialogMode = ref<TagDialogMode>(TagDialogMode.WATCH);

//App Management
//On Mounted
onMounted(() => {
  fetchNotes();
  fetchCategories();
});

//Categories Management

//Requests
const fetchCategories = async () => {
  await categoryService.getCategories().then((res) => {
    categoryArray.value = res.data as Category[];
    console.log(categoryArray.value);
  });
};

//Note Management

//Requests
const addNote = async (note: Note) => {
  await noteService
    .create(note)
    .then((response) => {
      noteArray.value.push(response.data as Note);
      console.log(noteArray.value);
    })
    .catch((err) => {
      console.error(err);
      noteArray.value.pop();
    });
};
const fetchNotes = async () => {
  await noteService.getNotes().then((res) => {
    noteArray.value = res.data as Note[];
    console.log('Notes fetched: ', noteArray.value);
  });
};
const updateNote = (updatedNote: Note) => {
  const index = noteArray.value.findIndex((note) => note.id === updatedNote.id);
  if (index !== -1) {
    const originalNote = noteArray.value[index];
    noteArray.value[index] = { ...updatedNote };
    try {
      noteService.update(updatedNote.id, updatedNote).then((response) => {
        console.log(response);
      });
    } catch (error) {
      console.error('Error updating note:', error);
      noteArray.value[index] = originalNote;
    }
  }
};
const deleteNote = async (id: number) => {
  const originalNotes = [...noteArray.value];
  try {
    noteArray.value = noteArray.value.filter((n) => n.id !== id);

    await noteService.delete(id).then((response) => {
      console.log(response);
    });
  } catch (error) {
    console.error('Error deleting note:', error);
    noteArray.value = originalNotes;
  }
};
const archiveNote = async (id: number) => {
  try {
    noteArray.value.find((n) => n.id === id)!.archived = true;
    await noteService.archiveNote(id).then((response) => {
      console.log(response);
    });
  } catch (error) {
    console.error('Error archiving note:', error);
    noteArray.value.find((n) => n.id === id)!.archived = false;
  }
};
const unarchiveNote = async (id: number) => {
  try {
    noteArray.value.find((n) => n.id === id)!.archived = false;
    await noteService.unarchiveNote(id).then((response) => {
      console.log(response);
    });
  } catch (error) {
    console.error('Error archiving note:', error);
    noteArray.value.find((n) => n.id === id)!.archived = true;
  }
};
//Actions
const setActiveNote = (note: Note) => {
  activeNote.value = note;
  mode.value = 'edit';
  dialogVisible.value = true;
};
const startCreation = () => {
  activeNote.value = new Note('', '');
  mode.value = 'create';
  dialogVisible.value = true;
};

const showCategories = (mode: TagDialogMode) => {
  tagsDialogMode.value = mode;
  tagsDialogVisible.value = true;
};

//Filters
const filteredNotes = computed(() => {
  let filtered: Note[] = [...noteArray.value];
  //vision mode filters
  if (visionMode.value === NoteWatch.ARCHIVED) filtered = filtered.filter((note) => note.archived);
  else if (visionMode.value === NoteWatch.ACTIVES)
    filtered = filtered.filter((note) => !note.archived);
  else filtered = [...noteArray.value];
  //category filters

  if (selectedCategoriesFilter.value.length > 0)
    filtered = filtered.filter((note) =>
      note.categories.some((category) =>
        selectedCategoriesFilter.value.some((selected) => selected.id === category.id)
      )
    );

  //apply search term filtering
  const searchTermLower = searchTerm.value.trim().toLowerCase();
  if (searchTermLower !== '') {
    filtered = filtered.filter(
      (note) =>
        note.title.toLowerCase().includes(searchTermLower) ||
        note.content.toLowerCase().includes(searchTermLower)
    );
  }
  return filtered;
});
const toggleCategory = (category: Category) => {
  const index = selectedCategoriesFilter.value.findIndex((c) => c.id === category.id);
  if (index !== -1) selectedCategoriesFilter.value.splice(index, 1);
  else selectedCategoriesFilter.value.push(category);
};
const activeFilters = () => {
  filtersActive.value = !filtersActive.value;
};
const changeVisionMode = (note: NoteWatch) => {
  visionMode.value = note;
};
</script>

<template>
  <main class="w-1/2 p-5 space-y-5 h-fit flex flex-col items-center">
    <h1>My Notes</h1>
    <div class="flex justify-between lg:w-2/3 md:2/3 w-full items-center gap-2">
      <div class="w-4/5">
        <input
          v-model="searchTerm"
          type="text"
          placeholder="Search Notes"
          class="bg-transparent outline-none border w-full border-gray-700 p-2 rounded-md"
        />
      </div>
      <button
        class="flex flex-row self-end items-center drop-shadow-md p-2 rounded-full hover:bg-gray-800 transition-colors duration-300 ease-linear"
        title="Tags"
        type="button"
        @click="showCategories(TagDialogMode.WATCH)"
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
      <button
        @click="activeFilters"
        class="flex flex-row self-end items-center drop-shadow-md p-2 rounded-full hover:bg-gray-800 transition-colors duration-300 ease-linear"
        title="Show Filters"
        type="button"
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
            d="M10.5 6h9.75M10.5 6a1.5 1.5 0 1 1-3 0m3 0a1.5 1.5 0 1 0-3 0M3.75 6H7.5m3 12h9.75m-9.75 0a1.5 1.5 0 0 1-3 0m3 0a1.5 1.5 0 0 0-3 0m-3.75 0H7.5m9-6h3.75m-3.75 0a1.5 1.5 0 0 1-3 0m3 0a1.5 1.5 0 0 0-3 0m-9.75 0h9.75"
          />
        </svg>
      </button>

      <button
        class="flex flex-row self-end items-center drop-shadow-md p-2 rounded-full hover:bg-gray-800 transition-colors duration-300 ease-linear"
        title="Add Note"
        type="button"
        @click="startCreation"
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
            d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10"
          />
        </svg>
      </button>
    </div>
    <Transition name="categories">
      <div
        v-if="filtersActive"
        class="grid grid-flow-col auto-cols-max gap-2 h-fit lg:w-2/3 md:2/3 w-full"
      >
        <div
          class="flex items-center accent-blue-500 bg-gray-800 px-2 py-1 rounded-md border"
          :style="{
            borderColor: category.color,
            boxShadow: `0 0 0.2rem ${category.color}`
          }"
          v-for="category in categoryArray"
          v-bind:key="category.id"
        >
          <label :for="`checkbox-${category.name}`">
            <input
              :id="`checkbox-${category.name}`"
              type="checkbox"
              :checked="selectedCategoriesFilter.includes(category)"
              @change="toggleCategory(category)"
            />
            {{ category.name }}
          </label>
        </div>
      </div>
    </Transition>
    <div class="flex justify-between lg:w-2/3 md:2/3 w-full items-center">
      <label
        for="everything"
        class="rounded-l option"
        :class="{ active: visionMode == NoteWatch.EVERYTHING }"
        @click="changeVisionMode(NoteWatch.EVERYTHING)"
      >
        <input id="everything" type="checkbox" :checked="visionMode === NoteWatch.EVERYTHING" />
        Everything
      </label>
      <label
        for="archived"
        class="option"
        :class="{ active: visionMode == NoteWatch.ARCHIVED }"
        @click="changeVisionMode(NoteWatch.ARCHIVED)"
      >
        <input id="archived" type="checkbox" :checked="visionMode === NoteWatch.ARCHIVED" />
        Archived
      </label>
      <label
        for="actives"
        class="rounded-r option"
        :class="{ active: visionMode == NoteWatch.ACTIVES }"
        @click="changeVisionMode(NoteWatch.ACTIVES)"
      >
        <input id="actives" type="checkbox" :checked="visionMode === NoteWatch.ACTIVES" />
        Actives
      </label>
    </div>

    <div v-if="filteredNotes.length !== 0" class="lg:w-2/3 md:2/3 w-full flex flex-col gap-3">
      <note-card
        v-for="note in filteredNotes"
        :key="note.id"
        :note="note"
        @click-note="setActiveNote"
        @delete-note="deleteNote"
        @archive-note="archiveNote"
        @unarchive-note="unarchiveNote"
      />
    </div>
    <div v-else class="lg:w-2/3 md:2/3 w-full flex flex-col gap-3">
      <span class="self-center">No notes found :(</span>
    </div>
  </main>

  <note-dialog
    v-if="dialogVisible"
    :visible="dialogVisible"
    :note="activeNote"
    :mode="mode"
    @save-note="addNote"
    @close="dialogVisible = false"
    @update-note="updateNote"
  />
  <tag-dialog
    v-if="tagsDialogVisible"
    :tags="categoryArray"
    @close="tagsDialogVisible = false"
    :visible="tagsDialogVisible"
    :mode="tagsDialogMode"
    :selectedTags="activeNote?.categories || []"
  >
  </tag-dialog>
</template>
<style scoped>
.option {
  @apply text-blue-500 font-medium p-2 cursor-pointer text-sm border border-blue-500 w-full;
}
.option input[type='checkbox'] {
  @apply hidden;
}
.active {
  @apply bg-blue-500 text-white transition-all duration-200 ease-in;
}

.categories-enter-from,
.categories-enter-active,
.categories-enter-to {
  @apply animate-slide-in-top animate-duration-300 animate-linear;
}
.categories-leave-from,
.categories-leave-active,
.categories-leave-to {
  @apply animate-slide-out-top animate-duration-300 animate-linear;
}
</style>
