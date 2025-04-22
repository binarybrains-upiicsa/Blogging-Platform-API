import { Hono } from 'hono';
import { validator } from 'hono/validator';
import prisma from '@/database/prisma.ts';
import { validateSchema } from '@/util/validator.ts';
import { CreatePostSchema } from '@/schemas/posts.ts';

const app = new Hono();

app.get('/', async (c) => {
  const posts = await prisma.post.findMany();
  return c.json(posts);
});
app.post(
  '/',
  validateSchema('json', CreatePostSchema),
  (c) => {
    console.log(c.req.valid('json'));
    return c.json({ message: 'POST' });
  },
);
app.put('/:id', (c) => c.text('PUT'));
app.get('/:id', (c) => c.text('GET'));

export default app;
