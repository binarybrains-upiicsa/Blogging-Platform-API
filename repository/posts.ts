import { Post, UpdatePost } from '@/schemas/posts.ts';
import { TODO } from '@egamagz/todo';

export class PostsRepository {
  getAllPosts(term?: string): Post[] {
    TODO('Method not implemented.');
  }
  getPostById(id: number): Post {
    TODO('Method not implemented.');
  }
  deletePost(id: number): Post {
    TODO('Method not implemented.');
  }
  updatePost(id: number, post: UpdatePost): Post {
    TODO('Method not implemented.');
  }
}
