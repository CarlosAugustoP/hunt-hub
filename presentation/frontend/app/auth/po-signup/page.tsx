'use client';

import { useState } from 'react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@radix-ui/react-label";
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import PublicNavbar from '@/components/base/public-navbar';
import { FormError } from '@/components/base/formError';
import { z } from 'zod';
import createPoSchema from '@/schema/po-schema';

export default function PoPage() {
    const router = useRouter();
    const [formData, setFormData] = useState({
        cpf: '',
        name: '',
        email: '',
        password: '',
        username: '',
    });
    const [error, setError] = useState<string | null>(null);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const validationResult = createPoSchema.safeParse(formData);

        if (!validationResult.success) {
            setError(validationResult.error.errors[0].message);
            return;
        }

        try {
            const response = await fetch('http://localhost:8080/api/po', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                const errorResponse = await response.json();
                throw new Error(errorResponse.message || 'Erro ao enviar o formulário');
            }

            setError(null); // Limpa qualquer erro existente
            router.push('/auth/signin');
        } catch (error: any) {
            setError(error.message); // Define a mensagem de erro
        }
    };

    return (
        <div>
            <PublicNavbar />
            <div className="w-full lg:grid lg:min-h-[600px] lg:grid-cols-2 xl:min-h-[800px]">
                <div className="flex items-center justify-center py-12">
                    <div className="mx-auto grid w-[350px] gap-6">
                        <div className="grid gap-2 text-center">
                            <h1 className="text-3xl font-bold">Cadastrar PO</h1>
                            <p className="text-muted-foreground">
                                Preencha suas informações para se cadastrar como PO.
                            </p>
                        </div>
                        <form onSubmit={handleSubmit} className="grid gap-4">
                            <div className="grid gap-2">
                                <Label htmlFor="cpf">CPF</Label>
                                <Input
                                    id="cpf"
                                    name="cpf"
                                    placeholder="Digite seu CPF"
                                    value={formData.cpf}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="grid gap-2">
                                <Label htmlFor="name">Nome</Label>
                                <Input
                                    id="name"
                                    name="name"
                                    placeholder="Digite seu nome"
                                    value={formData.name}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="grid gap-2">
                                <Label htmlFor="email">E-mail</Label>
                                <Input
                                    id="email"
                                    name="email"
                                    type="email"
                                    placeholder="Digite seu e-mail"
                                    value={formData.email}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="grid gap-2">
                                <Label htmlFor="password">Senha</Label>
                                <Input
                                    id="password"
                                    name="password"
                                    type="password"
                                    placeholder="Digite sua senha"
                                    value={formData.password}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <div className="grid gap-2">
                                <Label htmlFor="username">Nome de usuário</Label>
                                <Input
                                    id="username"
                                    name="username"
                                    placeholder="Digite seu nome de usuário"
                                    value={formData.username}
                                    onChange={handleInputChange}
                                    required
                                />
                            </div>
                            <FormError error={error || undefined} />
                            <Button type="submit" className="w-full">
                                Cadastrar
                            </Button>
                        </form>
                    </div>
                </div>
                <div className="hidden lg:flex items-center justify-center bg-muted">
                    <Image src="/po.svg" width={400} height={400} alt="PO Illustration" className="max-w-[400px]" />
                </div>
            </div>
        </div>
    );
}
