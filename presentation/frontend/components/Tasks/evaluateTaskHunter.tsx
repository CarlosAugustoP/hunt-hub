import React, { useEffect, useState } from "react";
import {
    AlertDialog,
    AlertDialogContent,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogFooter,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";
import { Star } from "lucide-react";

interface Hunter {
    id: string;
    name: string;
    rating: number;
}

interface PO {
    id: string;
    name: string;
    rating: number; 
}

interface TaskDetails {
    id: string;
    title: string;
    hunters: Hunter[];
    po: PO;
}

interface EvaluateTaskProps {
    taskId: string;
    isOpen: boolean;
    onClose: () => void;
}

export default function EvaluateTaskHunter({ taskId, isOpen, onClose }: EvaluateTaskProps) {
    const [taskDetails, setTaskDetails] = useState<TaskDetails | null>(null);
    const { toast } = useToast();
    const [userId, setUserId] = useState<string | null>(null);

    const fetchTaskDetails = async () => {
        try {
            const accessToken = localStorage.getItem("accessToken");
            const localUserId = localStorage.getItem("userId");
            setUserId(localUserId);
            if (!accessToken || !localUserId) {
                throw new Error("Access token or user ID not found.");
            }

            const res = await fetch(`http://localhost:8080/api/task/${taskId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${accessToken}`,
                },
            });

            if (!res.ok) {
                throw new Error(`Failed to fetch task details. Status: ${res.status}`);
            }

            const data = await res.json();

            // Filtrar o hunter logado
            const filteredHunters = data.hunters
                .filter((hunter: Hunter) => hunter.id !== localUserId)
                .map((hunter: Hunter) => ({
                    id: hunter.id,
                    name: hunter.name,
                    rating: 0,
                }));

            setTaskDetails({
                id: data.id,
                title: data.title,
                hunters: filteredHunters,
                po: {
                    id: data.po.id,
                    name: data.po.name,
                    rating: 0,
                },
            });
        } catch (err) {
            console.error("Error fetching task details:", err);
            toast({
                title: "Erro",
                description: "Falha ao carregar os detalhes da tarefa. Por favor, tente novamente.",
                variant: "destructive",
            });
        }
    };

    const handleStarClick = (id: string, rating: number, type: "hunter" | "po") => {
        if (taskDetails) {
            if (type === "hunter") {
                const updatedHunters = taskDetails.hunters.map((hunter) =>
                    hunter.id === id ? { ...hunter, rating } : hunter
                );
                setTaskDetails({ ...taskDetails, hunters: updatedHunters });
            } else if (type === "po") {
                setTaskDetails({ ...taskDetails, po: { ...taskDetails.po, rating } });
            }
        }
    };

    const handleSubmit = async () => {
        try {
            const accessToken = localStorage.getItem("accessToken");
            const localUserId = userId;
            if (!accessToken || !localUserId || !taskDetails) {
                throw new Error("Required data not found.");
            }
    
            // Avaliar PO
            if (taskDetails.po.rating > 0) {
                const poUrl = `http://localhost:8080/api/rating/hunter/${localUserId}/po/${taskDetails.po.id}/task/${taskId}`;
                console.log("Avaliar PO - URL:", poUrl);
                console.log("Payload:", { rating: taskDetails.po.rating });
    
                const poRatingResponse = await fetch(poUrl, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${accessToken}`,
                    },
                    body: JSON.stringify({ rating: taskDetails.po.rating }),
                });
    
                if (!poRatingResponse.ok) {
                    throw new Error(`Failed to submit PO rating. Status: ${poRatingResponse.status}`);
                }
            }
    
            for (const hunter of taskDetails.hunters) {
                if (hunter.rating > 0) {
                    const hunterUrl = `http://localhost:8080/api/rating/hunter/${localUserId}/rate/${hunter.id}/task/${taskId}`;
                    console.log("Avaliar Hunter - URL:", hunterUrl);
                    console.log("Payload:", { rating: hunter.rating });
    
                    const hunterRatingResponse = await fetch(hunterUrl, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${accessToken}`,
                        },
                        body: JSON.stringify({ rating: hunter.rating }),
                    });
    
                    if (!hunterRatingResponse.ok) {
                        throw new Error(
                            `Failed to submit hunter rating for ${hunter.name}. Status: ${hunterRatingResponse.status}`
                        );
                    }
                }
            }
    
            toast({
                title: "Sucesso",
                description: "Avaliações enviadas com sucesso!",
            });
            onClose();
        } catch (err) {
            console.error("Error submitting ratings:", err);
            toast({
                title: "Erro",
                description: "Falha ao enviar as avaliações. Por favor, tente novamente.",
                variant: "destructive",
            });
        }
    };
    

    useEffect(() => {
        if (isOpen) {
            fetchTaskDetails();
        }
    }, [isOpen]);

    return (
        <AlertDialog open={isOpen} onOpenChange={onClose}>
            <AlertDialogContent>
                <AlertDialogHeader>
                    <AlertDialogTitle>Avaliar Tarefa: {taskDetails?.title}</AlertDialogTitle>
                </AlertDialogHeader>
                <div>
                    {taskDetails?.hunters.length ? (
                        taskDetails.hunters.map((hunter) => (
                            <div key={hunter.id} className="flex items-center justify-between">
                                <span>{hunter.name}</span>
                                <div className="flex">
                                    {[1, 2, 3, 4, 5].map((star) => (
                                        <Star
                                            key={star}
                                            onClick={() => handleStarClick(hunter.id, star, "hunter")}
                                            className={`cursor-pointer ${
                                                hunter.rating >= star ? "text-yellow-400" : "text-gray-400"
                                            }`}
                                        />
                                    ))}
                                </div>
                            </div>
                        ))
                    ) : (
                        <p className="text-gray-500">Nenhum outro hunter para avaliar.</p>
                    )}
                    <div className="mt-4">
                        <h4 className="text-lg font-bold">Avaliar PO</h4>
                        <div className="flex items-center justify-between">
                            <span>{taskDetails?.po.name}</span>
                            <div className="flex">
                                {[1, 2, 3, 4, 5].map((star) => (
                                    <Star
                                        key={star}
                                        onClick={() => taskDetails && handleStarClick(taskDetails.po.id, star, "po")}
                                        className={`cursor-pointer ${
                                            (taskDetails?.po.rating ?? 0) >= star ? "text-yellow-400" : "text-gray-400"
                                        }`}
                                    />
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
                <AlertDialogFooter>
                    <Button onClick={onClose} variant="outline">
                        Cancelar
                    </Button>
                    <Button onClick={handleSubmit}>Enviar Avaliações</Button>
                </AlertDialogFooter>
            </AlertDialogContent>
        </AlertDialog>
    );
}
