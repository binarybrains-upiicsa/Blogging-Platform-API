import { Hono } from 'hono';
import prisma from '@/database/prisma.ts';
import { validateSchema } from '@/util/validator.ts';
import { CreatePostSchema, Post } from '@/schemas/posts.ts';
import { PostsRepository } from '@/repository/posts.ts';
import { ApiResponse } from '@/schemas/api-response.ts';

const app = new Hono();

app.get('/', async (c) => {
  const posts = await prisma.post.findMany();
  return c.json(posts);
});
app.post(
  '/',
  validateSchema('json', CreatePostSchema),
  async (c,) => {
    const post = c.req.valid('json');
    const repository = new PostsRepository();
    const createdPost = await repository.createPost(post);

    if (createdPost.isErr()) {

      const apiResponse: ApiResponse = {
        error: {
          code: 'SERVER_ERROR',
          message: createdPost.error.message
        },
        success: false
      }

      return c.json(apiResponse, 500)

    }

    const { data, message } = createdPost.value;
    const apiResponse: ApiResponse<Post> = {
      message,
      data,
      success: true
    }

    return c.json(apiResponse, 201)

  },
);
app.put('/:id', (c) => c.text('PUT'));
app.get('/:id', (c) => c.text('GET'));

export default app;
