import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import NoteCard from './note_management/components/NoteCard.vue'
import NoteManagement from './note_management/pages/NoteManagement.vue'
import NotePanel from './note_management/components/NotePanel.vue'
import ChipCategory from './note_management/components/ChipCategory.vue'
import NoteDialog from './note_management/components/NoteDialog.vue'
const app = createApp(App);
app.use(router);
app.component('note-card', NoteCard);
app.component('note-management', NoteManagement);
app.component('note-panel', NotePanel);
app.component('chip-category', ChipCategory);
app.component('note-dialog', NoteDialog);
app.mount('#app');

