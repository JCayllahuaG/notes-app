import type { Category } from "./Category";

export class Note {
    id: number;
    title: string;
    content: string;
    archived: boolean = false;
    categories: Category[] = [];
    
    constructor(title: string, content: string) {
        this.id = 0;
        this.title = title;
        this.content = content;
        this.archived = false;
    }

}