import { Hono } from 'hono';

const app = new Hono();

app.get('/', (c) => c.text('GET ALL'));
app.put('/:id', (c) => c.text('PUT'));
app.get('/:id', (c) => c.text('GET'));
app.post(
  '/',
  (c) => {
    return c.json({ message: 'POST' });
  },
);

export default app;
