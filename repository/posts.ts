import { CreatePost, Post, UpdatePost } from '../schemas/post.ts';
import { TODO } from '@egamagz/todo';
import { ResultAsync } from "neverthrow";
import prisma from '@/database/prisma.ts';
import { DatabaseData, DatabaseError } from '@/schemas/database-data.ts';


export class PostsRepository {
  createPost(post: CreatePost): ResultAsync<DatabaseData<Post>, DatabaseError> {
    const result = ResultAsync.fromPromise(
      prisma.$transaction(async (tx) => {
        const tags = await Promise.all(
          post.tags.map(name =>
            tx.tag.upsert({
              where: { name },
              create: { name },
              update: { name }
            })
          )
        );

        const createdPost = await tx.post.create({
          data: {
            category: post.category,
            content: post.content,
            title: post.title,
            tags: {
              connect: tags.map(tag => ({ id: tag.id }))
            }
          },
          include: {
            tags: true
          }
        });

        return {
          data: {
            ...createdPost,
            tags: createdPost.tags.map(tag => tag.name),
            createdAt: createdPost.createdAt.toISOString(),
            updatedAt: createdPost.updatedAt.toISOString()
          },
          message: "Post created succesfully"
        };
      }),
      (error) => ({
        type: "DATABASE_ERROR",
        message: error instanceof Error ? error.message : 'Failed to create post'
      } as DatabaseError)
    );

    return result;
  }
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
