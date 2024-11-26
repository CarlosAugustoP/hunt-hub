'use client';
import { useEffect, useState } from "react";
import { TaskSummary, TaskEnum } from "@/components/Tasks/task-component-hunter";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";
import EvaluateTaskHunter from "@/components/Tasks/evaluateTaskHunter";
import { EvaluateTask } from "@/components/Tasks/evaluateTask";
export default function Page() {
    const [tasks, setTasks] = useState<TaskSummary[]>([]);
    const [error, setError] = useState<string | null>(null);
    const { toast } = useToast();

    const [isEvaluateDialogOpen, setIsEvaluateDialogOpen] = useState(false);
    const [selectedTaskId, setSelectedTaskId] = useState<string | null>(null);


    const [role, setRole] = useState<string | null>(null);
    const [userId, setUserId] = useState<string | null>(null);
    const [accessToken, setAccessToken] = useState<string | null>(null);


    useEffect(() => {
        setRole(localStorage.getItem("role"));
        setUserId(localStorage.getItem("userId"));
        setAccessToken(localStorage.getItem("accessToken"));
    }, []);


    useEffect(() => {
        if (accessToken && userId) {
            getAllTasksByUserId();
        }
    }, [accessToken, userId]);

    const getAllTasksByUserId = async () => {
        if (!accessToken || !userId) {
            setError("Token de acesso ou ID de usuário não encontrado");
            return;
        }

        let url: string;

        if (role === "ROLE_HUNTER") {
            url = `http://localhost:8080/api/task/hunter/${userId}`;
        } else {
            url = `http://localhost:8080/api/task/po/${userId}`;
        }

        try {
            const response = await fetch(url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${accessToken}`,
                },
            });

            if (!response.ok) {
                throw new Error(`Erro ao buscar tarefas: ${response.statusText}`);
            }

            const text = await response.text();
            if (!text) {
                setTasks([]); // No tasks
                return;
            }

            const tasksData: TaskSummary[] = JSON.parse(text);
            setTasks(tasksData);
        } catch (err) {
            if (err instanceof SyntaxError) {
                console.error("Resposta não é um JSON válido:", err);
                setError("Erro: Resposta inesperada do servidor");
            } else {
                console.error(err);
                setError("Erro ao buscar tarefas");
            }
        }
    };

    const requestPayment = async (taskId: string) => {
        const response = await fetch(`http://localhost:8080/api/task/hunter/${userId}/request-payment/${taskId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${accessToken}`,
            },
        });
        if (!response.ok) {
            toast({
                title: "Erro",
                description: "Avalie os usuários antes de solicitar o pagamento",
                variant: "destructive",
            });
            throw new Error("Erro ao solicitar pagamento");
        }
        toast({
            title: "Sucesso",
            description: "Pagamento solicitado com sucesso",
        });
    };

    const completeTask = async (taskId: string) => {
        if (!accessToken) {
            setError("Token de acesso não encontrado");
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/api/task/${taskId}/complete`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${accessToken}`,
                },
            });

            if (!response.ok) {
                throw new Error("Erro ao completar a tarefa");
            }

            setTasks((prevTasks) =>
                prevTasks.map((task) =>
                    task.id === taskId ? { ...task, status: TaskEnum.DONE } : task
                )
            );
            toast({
                title: "Sucesso",
                description: "Tarefa completada com sucesso",
            });
            getAllTasksByUserId();
        } catch (err) {
            console.error(err);
            setError("Erro ao completar a tarefa");
            toast({
                title: "Erro",
                description: "Erro ao completar a tarefa",
                variant: "destructive",
            });
        }
    };

    const openEvaluateDialog = (taskId: string) => {
        setSelectedTaskId(taskId);
        setIsEvaluateDialogOpen(true);
    };

    const navigateToTask = (taskId: string) => {
        window.location.href = `/task/${taskId}`;
    };

    if (role === null || userId === null || accessToken === null) {
        return <div>Loading...</div>;
    }

    return (
        <div className="p-6 bg-gray-900 text-white min-h-screen">
            <h1 className="text-3xl font-bold mb-6">Your Tasks</h1>
            {error ? (
                <p className="text-red-500">{error}</p>
            ) : tasks.length > 0 ? (
                <ul className="space-y-4">
                    {tasks.map((task) => (
                        <li
                            key={task.id}
                            className="p-4 bg-gray-800 rounded-lg shadow hover:shadow-lg transition-shadow"
                        >
                            <h2 className="text-xl font-bold">{task.title}</h2>
                            <p className="text-gray-400">{task.description}</p>
                            <p className="mt-2">
                                <span className="text-yellow-400 font-medium">Reward:</span>{" "}
                                {task.reward} Gold
                            </p>
                            <p className="mt-1">
                                <span className="text-blue-400 font-medium">Rating Required:</span>{" "}
                                {task.ratingRequired}
                            </p>
                            <div className="flex flex-wrap gap-2 mt-3">
                                {task.tags.map((tag, index) => (
                                    <span
                                        key={index}
                                        className="bg-gray-700 px-3 py-1 rounded-full text-sm text-white"
                                    >
                                        {tag}
                                    </span>
                                ))}
                            </div>
                            <div className="mt-4 flex gap-4">
                                <Button
                                    onClick={() => navigateToTask(task.id)}
                                    className="text-blue-400 hover:text-blue-300 text-sm transition-colors"
                                >
                                    View Details
                                </Button>
                                {task.taskStatus === TaskEnum.DONE ? (
                                    <>
                                        <Button
                                            onClick={() => openEvaluateDialog(task.id)}
                                            className="text-black bg-yellow-500 hover:bg-yellow-300 text-sm transition-colors"
                                        >
                                            Rate
                                        </Button>
                                        {role === "ROLE_HUNTER" && (
                                            <Button
                                                onClick={() => requestPayment(task.id)}
                                                className="text-black bg-purple-500 hover:bg-purple-300 text-sm transition-colors"
                                            >
                                                Request Payment
                                            </Button>
                                        )}
                                    </>
                                ) : role === "ROLE_PO" ? (
                                    <Button
                                        onClick={() => completeTask(task.id)}
                                        className="text-black bg-green-500 hover:bg-green-200 text-sm transition-colors"
                                    >
                                        Complete Task
                                    </Button>
                                ) : null}
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p className="text-gray-400">Você não está em nenhuma task no momento.</p>
            )}

            {selectedTaskId && (
                role === "ROLE_HUNTER" ? (
                    <div>
                        <EvaluateTaskHunter
                            taskId={selectedTaskId}
                            isOpen={isEvaluateDialogOpen}
                            onClose={() => setIsEvaluateDialogOpen(false)}
                        />
                    </div>

                ) : (
                    <EvaluateTask
                        taskId={selectedTaskId}
                        isOpen={isEvaluateDialogOpen}
                        onClose={() => setIsEvaluateDialogOpen(false)}
                    />
                )
            )}
        </div>
    );
}
