import React, { useEffect, useState } from "react";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { ScrollArea } from "@/components/ui/scroll-area";
import { useToast } from "@/hooks/use-toast";
import { User } from "lucide-react";

interface Hunter {
  id: string;
  name: string;
}

interface RawHunter {
  id: {
    id: string;
  };
  name: string;
}

interface TaskHuntersAppliedPopupProps {
  taskId: string;
  isOpen: boolean;
  onClose: () => void;
}

export function TaskHuntersAppliedPopup({ taskId, isOpen, onClose }: TaskHuntersAppliedPopupProps) {
  const [hunters, setHunters] = useState<Hunter[]>([]);
  const { toast } = useToast();

  const fetchHunters = async () => {
    try {
      if (!taskId) {
        throw new Error("Task ID is missing.");
      }

      const accessToken = localStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token not found.");
      }

      const res = await fetch(`http://localhost:8080/api/task/${taskId}/hunters`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (res.status === 204 || res.status === 404 || !res.ok) {
        setHunters([]);
        return;
      }

      const rawData: RawHunter[] = await res.json();
      const processedData: Hunter[] = rawData.map((hunter) => ({
        id: hunter.id.id,
        name: hunter.name,
      }));

      setHunters(processedData);
    } catch (err) {
      console.error("Error fetching hunters:", err);
      toast({
        title: "Error",
        description: "Failed to load hunters. Please try again.",
        variant: "destructive",
      });
    }
  };

  const handleApproveHunter = async (hunterId: string) => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token not found.");
      }

      const res = await fetch(`http://localhost:8080/api/task/${taskId}/accept/${hunterId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (!res.ok) {
        throw new Error(`Failed to approve hunter. Status: ${res.status}`);
      }

      toast({
        title: "Approved",
        description: "Hunter approved successfully.",
      });
      fetchHunters();
    } catch (err) {
      console.error("Error approving hunter:", err);
      toast({
        title: "Error",
        description: "Failed to approve hunter. Please try again.",
        variant: "destructive",
      });
    }
  };

  const handleRejectHunter = async (hunterId: string) => {
    try {
      const accessToken = localStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token not found.");
      }

      const res = await fetch(`http://localhost:8080/api/task/${taskId}/decline/${hunterId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${accessToken}`,
        },
      });

      if (!res.ok) {
        throw new Error(`Failed to reject hunter. Status: ${res.status}`);
      }

      toast({
        title: "Rejected",
        description: "Hunter rejected successfully.",
      });
      fetchHunters(); // Atualiza a lista
    } catch (err) {
      console.error("Error rejecting hunter:", err);
      toast({
        title: "Error",
        description: "Failed to reject hunter. Please try again.",
        variant: "destructive",
      });
    }
  };

  useEffect(() => {
    if (isOpen) {
      fetchHunters();
    }
  }, [taskId, isOpen]);

  return (
    <>
      <Dialog open={isOpen} onOpenChange={onClose}>
        <DialogContent className="sm:max-w-[600px]" aria-describedby="task-hunters-description">
          <DialogHeader>
            <DialogTitle>Task´s applicants</DialogTitle>
          </DialogHeader>
          <ScrollArea className="h-[300px] w-full rounded-md border p-4" id="task-hunters-description">
            {hunters.length > 0 ? (
              <div className="grid grid-cols-2 gap-4">
                {hunters.map((hunter, index) => (
                  <div
                    key={`${hunter.id}-${index}`}
                    className="border rounded-md p-4 flex flex-col gap-2"
                  >
                    <div className="flex items-center gap-4">
                      <div className="w-10 h-10 rounded-full flex items-center justify-center border border-blue-400 text-white bg-gray-950">
                        <User className="w-6 h-6" />
                      </div>
                      <p className="font-semibold">{hunter.name}</p>
                    </div>
                    <div className="flex justify-end gap-2 mt-2">
                      <button
                        onClick={() => handleApproveHunter(hunter.id)}
                        className="text-green-500 hover:text-green-700"
                      >
                        ✅
                      </button>
                      <button
                        onClick={() => handleRejectHunter(hunter.id)}
                        className="text-red-500 hover:text-red-700"
                      >
                        ❌
                      </button>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-center text-gray-500">There are no hunters applied for this task</p>
            )}
          </ScrollArea>
        </DialogContent>
      </Dialog>
    </>
  );
}
