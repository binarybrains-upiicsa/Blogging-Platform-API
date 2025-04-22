import { Hono } from 'hono';
import { logger } from 'hono/logger';
import { cors } from 'hono/cors';
import postsRoutes from './routes/posts.ts';
import "@std/dotenv/load";

const app = new Hono().basePath('/api');

app.use(logger());
app.use(cors());

app.route('/posts', postsRoutes);

Deno.serve(app.fetch);
