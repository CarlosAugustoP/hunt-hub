"use client";

import React, { useEffect, useState } from "react";
import Task from "./task-component-hunter";
import { TaskSummary } from "./task-component-hunter";
import TaskPO from "./task-component-po";
import { UUID } from "crypto";
import { toast } from "@/hooks/use-toast";
import MultiSelect from "@/components/ui/select";
import { Button } from "../ui/button";
import { Filter, X } from 'lucide-react';
import { Input } from "../ui/input";

export const TagsEnum = [
  "KOTLIN", "NODE", "TYPESCRIPT", "CRIMINAL_DATA", "FIREBASE", "SQLITE",
  "SPRING", "STATISTICAL_DATA", "GOOGLE_CLOUD", "WEB_DEVELOPMENT",
  "DEEP_LEARNING", "FRONTEND", "CLOUD", "XAMARIN", "ELASTICSEARCH",
  "JAVA", "NATURAL_LANGUAGE_PROCESSING", "SCALA", "SYNCHRONOUS",
  "ACTIVEMQ", "HTML", "SQL", "DATA_SCIENCE", "NO_SQL", "MACHINE_LEARNING",
  "MEDICAL_DATA", "FULLSTACK", "RABBITMQ", "SECURITY", "ANGULAR", "C",
  "ASYNCHRONOUS", "GRAPHQL", "ANDROID", "EXPRESS", "MONGODB", "HASKELL",
  "APACHE_KAFKA", "PHP", "RUBY", "REDIS", "PYTHON", "DATABASE_MODELING",
  "REACT", "CSS", "RUST", "APACHE_CAMEL", "VUE", "GEOGRAPHICAL_DATA",
  "PRIVACY", "JAVASCRIPT", "AZURE", "OTHER", "SWIFT", "SQL_SERVER",
  "CPLUSPLUS", "API", "DATABASE", "FLUTTER", "DEVOPS", "MOBILE_DEVELOPMENT",
  "HISTORICAL_DATA", "MYSQL", "KAFKA", "REINFORCEMENT_LEARNING",
  "ARTIFICIAL_INTELLIGENCE", "BIG_DATA", "POSTGRESQL", "AWS", "CSHARP",
  "GO", "IOS", "BACKEND", "REACT_NATIVE", "APACHE_ACTIVEMQ", "ORACLE",
  "COMPUTER_VISION", "MICROSERVICES", "REST",
];

export const handleApply = async (taskId: UUID) => {
  try {
    const token = localStorage.getItem("accessToken");
    const hunterId = localStorage.getItem("userId");

    if (!token || !hunterId) {
      throw new Error("Missing access token or hunter ID.");
    }

    const response = await fetch(`http://localhost:8080/api/task/${taskId}/applying/${hunterId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error(`Failed to apply: ${response.statusText}`);
    }

    const result = await response.text();
    toast({
      title: "Aplicado com sucesso!",
      description: result,
      duration: 5000,
      variant: "default",
    });
  } catch (error) {
    console.error("Error applying to task:", error);
    toast({
      title: "Erro ao aplicar",
      description: "Erro ao aplicar para a tarefa. Tente novamente mais tarde.",
      duration: 5000,
      variant: "destructive",
    });
  }
};

export default function Tasks() {
  const [taskSummaries, setTaskSummaries] = useState<TaskSummary[]>([]);
  const [role, setRole] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  // Estados para cada filtro
  const [reward, setReward] = useState<number | null>(null);
  const [numberOfMeetings, setNumberOfMeetings] = useState<number | null>(null);
  const [ratingRequired, setRatingRequired] = useState<number | null>(null);
  const [poRating, setPoRating] = useState<number | null>(null);
  const [numberOfHuntersRequired, setNumberOfHuntersRequired] = useState<number | null>(null);
  const [tags, setTags] = useState<string[]>([]);

  const fetchTasks = async () => {
    setIsLoading(true);
    try {
      const roleFromStorage = localStorage.getItem("role");
      setRole(roleFromStorage);
  
      let url = "http://localhost:8080/api/task";
      if (roleFromStorage === "ROLE_PO") {
        const userId = localStorage.getItem("userId");
        if (!userId) {
          console.warn("User ID is missing in localStorage.");
          return;
        }
        url = `http://localhost:8080/api/task/po/${userId}`;
      }
  
      const response = await fetch(url, {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
        },
      });
  
      let data: TaskSummary[] = [];
  
      // Verificar se a resposta foi bem-sucedida
      if (response.ok) {
        data = await response.json();
        
        // Se os dados estiverem vazios, não lança erro
        if (data.length === 0) {
          console.log("No tasks found.");
        }
  
      } else if (response.status === 400) {
        console.log("No tasks found (404).");
      } else {
        throw new Error(`Request failed: ${response.statusText}`);
      }
  
      setTaskSummaries(data);
  
    } catch (error) {
      console.error("Error fetching tasks:", error);
    } finally {
      setIsLoading(false);
    }
  };
  

  const applyFilters = async () => {
    try {
      setIsLoading(true);
      const params = new URLSearchParams();

      if (reward !== null) {
        params.append("reward", reward.toString());
      }
      if (numberOfMeetings !== null) {
        params.append("numberOfMeetings", numberOfMeetings.toString());
      }
      if (ratingRequired !== null) {
        params.append("ratingRequired", ratingRequired.toString());
      }
      if (poRating !== null) {
        params.append("PORating", poRating.toString());
      }
      if (numberOfHuntersRequired !== null) {
        params.append("numberOfHuntersRequired", numberOfHuntersRequired.toString());
      }
      if (tags.length > 0) {
        params.append("tags", tags.join(","));
      }

      const queryString = params.toString();

      const response = await fetch(
        `http://localhost:8080/api/task/filter?${queryString}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
          },
        }
      );

      if (!response.ok) {
        throw new Error(`Failed to apply filters: ${response.statusText}`);
      }

      const filteredTasks: TaskSummary[] = await response.json();
      setTaskSummaries(filteredTasks);
    } catch (error) {
      console.error("Error applying filters:", error);
      toast({
        title: "Erro ao aplicar filtros",
        description: "Não foi possível filtrar as tarefas. Tente novamente mais tarde.",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  return (
    <div className="w-full">
      <div className="mb-4">
      {role !== "ROLE_PO" && (
          <div className=" p-4 rounded-lg shadow-md">
            <div className="flex flex-col gap-4">
              <div className="flex items-center gap-2 mb-2">
                <Filter className="w-4 h-4" />
                <h2 className="font-semibold">Filter Tasks</h2>
              </div>
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                <Input
                  type="number"
                  placeholder="Reward"
                  value={reward !== null ? reward : ""}
                  onChange={(e) => setReward(e.target.value ? parseFloat(e.target.value) : null)}
                  className="w-full"
                />
                <Input
                  type="number"
                  placeholder="Number of Meetings"
                  value={numberOfMeetings !== null ? numberOfMeetings : ""}
                  onChange={(e) =>
                    setNumberOfMeetings(e.target.value ? parseInt(e.target.value, 10) : null)
                  }
                  className="w-full"
                />
                <Input
                  type="number"
                  placeholder="Rating Required"
                  value={ratingRequired !== null ? ratingRequired : ""}
                  onChange={(e) =>
                    setRatingRequired(e.target.value ? parseFloat(e.target.value) : null)
                  }
                  className="w-full"
                />
                <Input
                  type="number"
                  placeholder="PO Rating"
                  value={poRating !== null ? poRating : ""}
                  onChange={(e) => setPoRating(e.target.value ? parseFloat(e.target.value) : null)}
                  className="w-full"
                />
                <Input
                  type="number"
                  placeholder="Number of Hunters Required"
                  value={numberOfHuntersRequired !== null ? numberOfHuntersRequired : ""}
                  onChange={(e) =>
                    setNumberOfHuntersRequired(e.target.value ? parseInt(e.target.value, 10) : null)
                  }
                  className="w-full"
                />
                <div className="w-full bg-white text-black ">
                  <MultiSelect
                    options={TagsEnum}
                    value={tags}
                    onChange={(value) => setTags(value)}
                  />
                </div>
              </div>
              <div className="flex justify-end gap-2 mt-2">
                <Button
                  variant="outline"
                  onClick={() => {
                    setReward(null);
                    setNumberOfMeetings(null);
                    setRatingRequired(null);
                    setPoRating(null);
                    setNumberOfHuntersRequired(null);
                    setTags([]);
                    fetchTasks();
                  }}
                  className="gap-2 bg-red-600"
                >
                  <X className="w-4 h-4" />
                  Reset
                </Button>
                <Button
                  onClick={applyFilters}
                  className="gap-2"
                >
                  <Filter className="w-4 h-4" />
                  Apply Filters
                </Button>
              </div>
            </div>
          </div>
        )}
      </div>

      {isLoading ? (
        <p className="text-center text-gray-500">Carregando tarefas...</p>
      ) : taskSummaries.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {taskSummaries.map((taskSummary, index) =>
            role === "ROLE_HUNTER" ? (
              <Task
                key={`${taskSummary.id}-${index}`}
                {...taskSummary}
                onApply={handleApply}
              />
            ) : (
              <TaskPO key={`${taskSummary.id}-${index}`} {...taskSummary} />
            )
          )}
        </div>
      ) : (
        <p className="text-center text-gray-500">Nenhuma tarefa encontrada.</p>
      )}
    </div>
  );
}
