import { Shield, Coins, Search } from "lucide-react"
import { Card, CardContent } from "@/components/ui/card"

export default function FeaturesSection() {
    const features = [
        {
            icon: Shield,
            title: "Ambiente Seguro",
            description: "No Hunt Hub, nossa prioridade é defender nossos Product Owners e Hunters de golpes e explorações. Por isso, criamos um sistema de mensagens seguro no aplicativo e um ambiente de pagamento seguro para criar uma experiência sem estresse para todos os usuários."
        },
        {
            icon: Coins,
            title: "Pagamento Fácil",
            description: "Os caçadores podem adquirir ouro completando tarefas em todo o mundo e, em seguida, resgatar seu ouro pela opção de pagamento de sua preferência (atualmente, suportando apenas o Real Brasileiro)"
        },
        {
            icon: Search,
            title: "Trabalho Especificado",
            description: "Pesquise exatamente o tipo de trabalho que você precisa para concluir esta tarefa. Os caçadores podem ser cientistas de dados, desenvolvedores web, designers e praticamente tudo o que você possa pensar sobre tecnologia!"
        }
    ]

    const steps = [
        {
            title: "Publique um novo trabalho",
            description: "Publique o que você deseja criar!"
        },
        {
            title: "Revise os Candidatos",
            description: "Selecione designers e desenvolvedores qualificados para tornar sua visão realidade!"
        },
        {
            title: "Desenvolva seu projeto",
            description: "Comunique-se com sua equipe e desenvolva o projeto que você procura, da maneira que você achar melhor!"
        },
        {
            title: "Entrega do seu produto!",
            description: "Termine o trabalho e recompense seus desenvolvedores pelo trabalho duro!"
        }
    ]

    return (
        <div className="flex flex-col items-center gap-20 py-20 px-4 md:px-10">
            <section className="w-full max-w-6xl">
                <h2 className="text-3xl font-bold text-center mb-10">Por que escolher o Hunt Hub?</h2>
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                    {features.map((feature, index) => (
                        <Card key={index} className="bg-gray-100 border-none">
                            <CardContent className="flex flex-col items-center p-6 text-center">
                                <div className="h-12 w-12 rounded-lg bg-white flex items-center justify-center mb-4">
                                    <feature.icon className="h-6 w-6" />
                                </div>
                                <h3 className="font-semibold mb-2">{feature.title}</h3>
                                <p className="text-sm text-gray-600">{feature.description}</p>
                            </CardContent>
                        </Card>
                    ))}
                </div>
            </section>

            <section className="w-full max-w-6xl">
                <h2 className="text-3xl font-bold text-center mb-10">Como usar o Hunt Hub</h2>
                <div className="relative">
                    <div className="absolute top-5 left-0 w-full h-0.5 bg-gray-200">
                        <div className="absolute right-0 top-1/2 -translate-y-1/2 -translate-x-1/2">
                            <div className="h-6 w-6 bg-yellow-400 rotate-45 transform" />
                        </div>
                    </div>

                    <div className="grid grid-cols-1 md:grid-cols-4 gap-6 relative">
                        {steps.map((step, index) => (
                            <div key={index} className="flex flex-col items-center text-center">
                                <div className="w-10 h-10 rounded-full bg-white border-2 border-gray-200 flex items-center justify-center mb-4 relative z-10">
                                    <div className="w-3 h-3 rounded-full bg-gray-500" />
                                </div>
                                <h3 className="font-semibold mb-2">{step.title}</h3>
                                <p className="text-sm text-gray-600">{step.description}</p>
                            </div>
                        ))}
                    </div>
                </div>
            </section>
        </div>
    )
}