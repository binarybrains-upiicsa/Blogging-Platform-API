import { Hono } from 'hono';
import { logger } from 'hono/logger';
import postsRoutes from './routes/posts.ts';

const app = new Hono().basePath('/api');

app.use(logger());

app.get('/', (c) => {
  c.status(418);
  return c.json("I'm a teapot!");
});

app.route('/posts', postsRoutes);

Deno.serve(app.fetch);
