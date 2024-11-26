'use client';

import { useState } from 'react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@radix-ui/react-label";
import Link from "next/link";
import Image from 'next/image';
import { useRouter } from 'next/navigation';
import PublicNavbar from '@/components/base/public-navbar';
import { z } from 'zod';
import loginSchema from '@/schema/login-schema';
import { FormError } from '@/components/base/formError';

export default function Signin() {
    const router = useRouter();
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });
    const [error, setError] = useState<string | null>(null);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { id, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [id]: value,
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        try {

            loginSchema.parse(formData)

            const response = await fetch('http://localhost:8080/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                const errorResponse = await response.json();
                throw new Error(errorResponse.message || 'Erro ao fazer login');
            }

            const data = await response.json();
            const { token, role, id: userId } = data;

            localStorage.setItem('accessToken', token);
            localStorage.setItem('role', role);
            localStorage.setItem('userId', userId);

            router.push('/home');
        } catch (err: any) {
            if (err instanceof z.ZodError) {
                setError(err.errors[0].message);
            }else { 
                setError(err.message); // Define a mensagem de erro para exibição
            }
        }
    };

    return (
        <div>
            <PublicNavbar />
            <div className="w-full lg:grid lg:min-h-[600px] lg:grid-cols-2 xl:min-h-[800px]">
                <div className="flex items-center justify-center py-12">
                    <div className="mx-auto grid w-[350px] gap-6">
                        <div className="grid gap-2 text-center">
                            <h1 className="text-3xl font-bold">Entrar</h1>
                            <p className="text-balance text-muted-foreground">
                                Você está a apenas alguns passos de se juntar a um Developer Hub confiável e de código aberto, especializado em conectar Devs e POs!
                            </p>
                        </div>
                        <form onSubmit={handleSubmit} className="grid gap-4">
                            <div className="grid gap-2">
                                <Label htmlFor="email">Email</Label>
                                <Input
                                    id="email"
                                    type="email"
                                    placeholder="johndoe@example.com"
                                    required
                                    value={formData.email}
                                    onChange={handleInputChange}
                                />
                            </div>
                            <div className="grid gap-2">
                                <div className="flex items-center">
                                    <Label htmlFor="password">Senha</Label>
                                </div>
                                <Input
                                    id="password"
                                    type="password"
                                    placeholder="******"
                                    required
                                    value={formData.password}
                                    onChange={handleInputChange}
                                />
                            </div>
                            <FormError error={error || undefined} />
                            <Button type="submit" className="w-full">
                                Entrar
                            </Button>
                        </form>
                        <div className="text-center text-sm grid gri">
                            Ainda não tem conta?{" "}
                            <Link href="/auth/po-signup" className="underline">
                                Cadastre-se como PO
                            </Link>
                            <Link href="/auth/hunter-signup" className="underline">
                                Cadastre-se como Hunter
                            </Link>
                        </div>
                    </div>
                </div>
                <div className="hidden lg:flex items-center justify-center bg-muted">
                    <Image src="/signIn.svg" width={400} height={400} alt="SignIn Illustration" className="max-w-[400px]" />
                </div>
            </div>
        </div>
    );
}
