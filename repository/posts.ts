import { CreatePost, Post, UpdatePost } from '../schemas/post.ts';
import { TODO } from '@egamagz/todo';
import { ResultAsync } from "neverthrow";
import prisma from '@/database/prisma.ts';
import { DatabaseData, DatabaseError } from '@/schemas/database-data.ts';


export class PostsRepository {
  createPost(post: CreatePost): ResultAsync<DatabaseData<Post>, DatabaseError> {
    const result = ResultAsync.fromPromise(
      prisma.$transaction(async (tx) => {
        let category = await tx.category.findFirst({
          where: { name: post.category }
        });

        if (!category) {
          category = await tx.category.create({
            data: { name: post.category }
          });
        }

        const tags = await Promise.all(
          post.tags.map(name =>
            tx.tag.upsert({
              where: { name },
              create: { name },
              update: {},
            })
          )
        );

        const createdPost = await tx.post.create({
          data: {
            categoryId: category.id,
            content: post.content,
            title: post.title,
            tags: {
              connect: tags.map(tag => ({ id: tag.id }))
            },
          },
          include: {
            category: true,
            tags: true
          }
        });

        return {
          data: {
            id: createdPost.id,
            title: createdPost.title,
            content: createdPost.content,
            category: createdPost.category.name,
            tags: createdPost.tags.map(tag => tag.name),
            createdAt: createdPost.createdAt.toISOString(),
            updatedAt: createdPost.updatedAt.toISOString()
          },
          message: "Post created successfully"
        };
      }),
      (error) => ({
        type: "DATABASE_ERROR",
        message: error instanceof Error ? error.message : 'Failed to create post'
      } as DatabaseError)
    );

    return result;
  }

  getAllPosts(term?: string): ResultAsync<DatabaseData<Post[]>, DatabaseError> {
    TODO("SAMPLE")
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
