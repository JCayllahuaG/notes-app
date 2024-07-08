import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import NoteCard from './note_management/components/NoteCard.vue'
import NoteManagement from './note_management/pages/NoteManagement.vue'
import ChipCategory from './note_management/components/ChipCategory.vue'
import NoteDialog from './note_management/components/NoteDialog.vue'
import TagDialog from './note_management/components/TagDialog.vue'
const app = createApp(App);
app.use(router);
app.component('note-card', NoteCard);
app.component('note-management', NoteManagement);
app.component('chip-category', ChipCategory);
app.component('note-dialog', NoteDialog);
app.component('tag-dialog', TagDialog);

app.mount('#app');

