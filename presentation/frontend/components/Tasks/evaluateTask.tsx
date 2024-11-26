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

interface TaskDetails {
    id: string;
    title: string;
    hunters: Hunter[];
}

interface EvaluateTaskProps {
    taskId: string;
    isOpen: boolean;
    onClose: () => void;
}

export function EvaluateTask({ taskId, isOpen, onClose }: EvaluateTaskProps) {
    const [taskDetails, setTaskDetails] = useState<TaskDetails | null>(null);
    const { toast } = useToast();

    const fetchTaskDetails = async () => {
        try {
            const accessToken = localStorage.getItem("accessToken");
            if (!accessToken) {
                throw new Error("Access token not found.");
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
            setTaskDetails({
                id: data.id,
                title: data.title,
                hunters: data.hunters.map((hunter: Hunter) => ({
                    id: hunter.id,
                    name: hunter.name,
                    rating: 0, // Inicia com avaliação 0
                })),
            });
        } catch (err) {
            console.error("Error fetching task details:", err);
            toast({
                title: "Error",
                description: "Failed to load task details. Please try again.",
                variant: "destructive",
            });
        }
    };

    const handleStarClick = (hunterId: string, rating: number) => {
        if (taskDetails) {
            const updatedHunters = taskDetails.hunters.map((hunter) =>
                hunter.id === hunterId ? { ...hunter, rating } : hunter
            );
            setTaskDetails({ ...taskDetails, hunters: updatedHunters });
        }
    };

    const handleSubmit = async () => {
        try {
            const accessToken = localStorage.getItem("accessToken");
            const poId = localStorage.getItem("userId");
            if (!accessToken || !poId) {
                throw new Error("Access token or user ID not found.");
            }

            if (!taskDetails || !taskDetails.hunters.length) {
                throw new Error("No hunters to evaluate.");
            }

            const evaluationPromises = taskDetails.hunters.map((hunter) =>
                fetch(`http://localhost:8080/api/rating/po/${poId}/hunter/${hunter.id}`, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${accessToken}`,
                    },
                    body: JSON.stringify({ rating: hunter.rating }),
                })
            );

            const results = await Promise.all(evaluationPromises);

            const failed = results.filter((res) => !res.ok);
            if (failed.length > 0) {
                throw new Error(`Failed to submit evaluations for ${failed.length} hunters.`);
            }

            toast({
                title: "Success",
                description: "Evaluations submitted successfully.",
            });
            onClose();
        } catch (err) {
            console.error("Error submitting evaluations:", err);
            toast({
                title: "Error",
                description: "Failed to submit evaluations. Please try again.",
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
            <AlertDialogContent className="max-w-lg">
                <AlertDialogHeader>
                    <AlertDialogTitle>Evaluate Hunters</AlertDialogTitle>
                </AlertDialogHeader>
                {taskDetails ? (
                    <div className="space-y-4">
                        <p className="text-gray-600">
                            Task: <span className="font-bold">{taskDetails.title}</span>
                        </p>
                        <div className="space-y-4">
                            {taskDetails.hunters.map((hunter) => (
                                <div key={hunter.id} className="flex items-center justify-between">
                                    <div>
                                        <p className="font-semibold">{hunter.name}</p>
                                    </div>
                                    <div className="flex gap-1">
                                        {[1, 2, 3, 4, 5].map((star) => (
                                            <button
                                                key={star}
                                                onClick={() => handleStarClick(hunter.id, star)}
                                                className={`p-1 ${hunter.rating >= star ? "text-yellow-400" : "text-gray-400"
                                                    }`}
                                            >
                                                <Star className="w-5 h-5" />
                                            </button>
                                        ))}
                                    </div>
                                </div>
                            ))}
                        </div>
                        <AlertDialogFooter>
                            <Button variant="destructive" onClick={onClose}>
                                Cancel
                            </Button>
                            <Button variant="default" onClick={handleSubmit}>
                                Submit Evaluations
                            </Button>
                        </AlertDialogFooter>
                    </div>
                ) : (
                    <p className="text-center text-gray-500">Loading task details...</p>
                )}
            </AlertDialogContent>
        </AlertDialog>
    );
}
