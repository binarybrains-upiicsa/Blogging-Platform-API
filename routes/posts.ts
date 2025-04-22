import { Hono } from 'hono';
import prisma from '@/database/prisma.ts';

const app = new Hono();

app.get('/', async (c) => {
  const posts = await prisma.post.findMany();
  return c.json(posts);
});
app.put('/:id', (c) => c.text('PUT'));
app.get('/:id', (c) => c.text('GET'));
app.post(
  '/',
  async (c) => {


    return c.json({ message: 'POST' });
  },
);

export default app;
