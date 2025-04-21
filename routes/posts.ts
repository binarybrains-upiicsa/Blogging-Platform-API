import { Hono } from 'hono';

const postsRoutes = new Hono();
postsRoutes.get('/', (c) => c.text("GET ALL"));
postsRoutes.post('/', (c) => c.text('POST'));
postsRoutes.put('/:id', (c) => c.text('PUT'));
postsRoutes.get('/:id', (c) => c.text('GET'));

export default postsRoutes;
