import http from '../../shared/services/http-common'
import type { Note } from '../model/Note'

export class NoteService{
    getNotes():Promise<any> {
        return http.get('/notes');
      }
      update(id:number, data:Note):Promise<Note>{
        return http.put(`/notes/${id}`, data);
      }
      create(data:Note):Promise<any> {
        return http.post('/notes', data);
      }
      archiveNote(id:number):Promise<boolean> {
        return http.patch(`/notes/${id}/archive`);
      }
      unarchiveNote(id:number):Promise<boolean> {
        return http.patch(`/notes/${id}/unarchive`);
      }
      delete(id:number):Promise<string> {
        return http.delete(`/notes/${id}`);
      }
}