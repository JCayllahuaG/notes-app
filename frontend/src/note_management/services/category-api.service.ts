import http from '../../shared/services/http-common'
import { Category } from '../model/Category';

export class CategoryService{
    getCategories():Promise<any> {
        return http.get('/categories');
      }
      update(id:number, data:Category):Promise<Category>{
        return http.put(`/categories/${id}`, data);
      }
      create(data:Category):Promise<any> {
        return http.post('/categories', data);
      }
      delete(id:number):Promise<string> {
        return http.delete(`/categories/${id}`);
      }
}