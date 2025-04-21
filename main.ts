import { Hono } from "hono";

const app = new Hono();

app.get("/", (c) => {
	c.status(418)
	return c.json("I'm a teapot!")
});

Deno.serve(app.fetch);
