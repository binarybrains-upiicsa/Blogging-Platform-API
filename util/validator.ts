import { ZodSchema } from 'zod';
import type { ValidationTargets } from 'hono';
import { validator } from 'hono/validator';

export const validateSchema = <
  T extends ZodSchema,
  Target extends keyof ValidationTargets,
>(target: Target, schema: T) => {
  return validator(target, (value, c) => {
    const parsed = schema.safeParse(value);
    if (!parsed.success) {
      return c.json({ message: parsed.error.message }, 400);
    }
    return parsed.data;
  });
};
