import { z } from 'zod';

const createPOSchema = z.object({
  name: z.string().min(1, { message: "O nome é obrigatório." }),

  cpf: z.string()
    .min(11, { message: "O CPF deve ter pelo menos 11 caracteres." })
    .max(14, { message: "O CPF deve ter no máximo 14 caracteres." }),

  email: z.string()
    .email({ message: "E-mail inválido." })
    .min(1, { message: "O e-mail é obrigatório." }),

  password: z.string()
    .min(8, { message: "A senha deve ter no mínimo 8 caracteres." }),

  linkPortfolio: z.string()
    .max(255, { message: "O link do portfólio deve ter no máximo 255 caracteres." })
    .optional(),

  bio: z.string()
    .max(500, { message: "A bio deve ter no máximo 500 caracteres." })
    .optional(),

  profilePicture: z.string()
    .max(255, { message: "O link da foto de perfil deve ter no máximo 255 caracteres." })
    .optional(),

  rating: z.number()
    .min(1, { message: "O rating deve ser no mínimo 1." })
    .max(5, { message: "O rating deve ser no máximo 5." })
    .default(5), // valor padrão como no DTO, já que no DTO ele está definido como 5

  certifications: z.array(z.string()).optional(),
  links: z.array(z.string()).optional(),
  achievements: z.array(z.string()).optional(),
  projects: z.array(z.string()).optional(),
});

export default createPOSchema;
