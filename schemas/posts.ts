import { z } from 'zod';

export const PostSchema = z.object({
  id: z.string(),
  title: z.string(),
  content: z.string(),
  category: z.string(),
  tags: z.array(z.string()),
  createdAt: z.string(),
  updatedAt: z.string(),
});

export type Post = z.infer<typeof PostSchema>;

export const CreatePostSchema = PostSchema.omit({
  id: true,
  createdAt: true,
  updatedAt: true,
});

export type CreatePost = z.infer<typeof CreatePostSchema>;

export const UpdatePostSchema = PostSchema.omit({
  id: true,
  updatedAt: true,
  createdAt: true
});

export type UpdatePost = z.infer<typeof UpdatePostSchema>;