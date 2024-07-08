import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({

    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '',
            redirect: '/note-management'
        },
        {
            path: '/note-management',
            component: () => import('../note_management/pages/NoteManagement.vue')
        }
    ],

});

export default router;