'use client';

import { useState } from 'react';
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Label } from "@radix-ui/react-label";
import { useRouter } from 'next/navigation';
import Image from 'next/image';
import PublicNavbar from '@/components/base/public-navbar';
import { FormError } from '@/components/base/formError';
import { X } from 'lucide-react';
import createHunterSchema from '@/schema/hunter-schema';

export default function HunterPage() {
    const router = useRouter();
    const [formData, setFormData] = useState({
        cpf: '',
        name: '',
        email: '',
        password: '',
        username: '',
        linkPortfolio: '',
        bio: 'N/A',
        profilePicture: '',
        certifications: [] as string[],
        links: [],
        achievements: [],
        projects: [],
        rating: 5,
        level: 0,
    });

    const [error, setError] = useState<string | null>(null);
    const [newCertification, setNewCertification] = useState('');

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({
            ...prev,
            [name]: value,
        }));
    };

    const handleAddCertification = () => {
        if (newCertification.trim()) {
            setFormData((prev) => ({
                ...prev,
                certifications: [...prev.certifications, newCertification.trim()]
            }));
            setNewCertification('');
        }
    };

    const handleRemoveCertification = (index: number) => {
        setFormData((prev) => ({
            ...prev,
            certifications: prev.certifications.filter((_, i) => i !== index)
        }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const validationResult = createHunterSchema.safeParse(formData);

        if (!validationResult.success) {
            setError(validationResult.error.errors[0].message);
            return;
          }

        try {
            const response = await fetch('http://localhost:8080/api/hunters', {
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

            setError(null);
            router.push('/auth/signin');
        } catch (error: any) {
            setError(error.message);
        }
    };

    return (
        <div>
            <PublicNavbar />
            <div className="w-full lg:grid lg:min-h-[600px] lg:grid-cols-2 xl:min-h-[800px]">
                <div className="flex items-center justify-center py-12">
                    <div className="mx-auto grid w-[350px] gap-6">
                        <div className="grid gap-2 text-center">
                            <h1 className="text-3xl font-bold">Cadastrar Hunter</h1>
                            <p className="text-muted-foreground">
                                Preencha suas informações para se cadastrar como Hunter.
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
                            <div className="grid gap-2">
                                <Label htmlFor="linkPortfolio">Link do Portfólio (opcional)</Label>
                                <Input
                                    id="linkPortfolio"
                                    name="linkPortfolio"
                                    placeholder="Digite o link do seu portfólio"
                                    value={formData.linkPortfolio}
                                    onChange={handleInputChange}
                                />
                            </div>
                            <div className="grid gap-2">
                                <Label htmlFor="certifications">Certificações (opcional)</Label>
                                <div className="flex gap-2">
                                    <Input
                                        id="certifications"
                                        placeholder="Adicione uma certificação"
                                        value={newCertification}
                                        onChange={(e) => setNewCertification(e.target.value)}
                                    />
                                    <Button type="button" onClick={handleAddCertification}>
                                        Adicionar
                                    </Button>
                                </div>
                                <div className="mt-2">
                                    {formData.certifications.map((cert, index) => (
                                        <div key={index} className="flex items-center gap-2 mb-2">
                                            <span className="bg-muted text-muted-foreground px-2 py-1 rounded-md flex-grow">
                                                {cert}
                                            </span>
                                            <Button
                                                type="button"
                                                variant="ghost"
                                                size="icon"
                                                onClick={() => handleRemoveCertification(index)}
                                            >
                                                <X className="h-4 w-4" />
                                                <span className="sr-only">Remover certificação</span>
                                            </Button>
                                        </div>
                                    ))}
                                </div>
                            </div>
                            <div className="grid gap-2">
                                <Label htmlFor="bio">Bio (opcional)</Label>
                                <textarea
                                    id="bio"
                                    name="bio"
                                    placeholder="Conte um pouco sobre você"
                                    value={formData.bio}
                                    onChange={(e) => setFormData(prev => ({ ...prev, bio: e.target.value }))}
                                    className="min-h-[100px] w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
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
                    <Image src="/hunter.svg" width={400} height={400} alt="Hunter Illustration" className="max-w-[400px]" />
                </div>
            </div>
        </div>
    );
}
