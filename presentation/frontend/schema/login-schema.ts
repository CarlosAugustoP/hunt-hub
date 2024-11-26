import { z } from 'zod';

const loginSchema = z.object({
    email: z.string()
        .email({ message: "Precisa ser um email válido" })
        .min(1, { message: "Email não pode ser vazio" }),
    password: z.string()
        .min(8, { message: "A senha deve ter pelo menos 8 caracteres" })
        .max(20, { message: "A senha não pode ter mais de 20 caracteres" })
        .min(1, { message: "Senha não pode ser vazia" }),
});


export default loginSchema;
