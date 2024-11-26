'use client';

import React, { useEffect, useState } from 'react';
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { User } from 'lucide-react';
import { useToast } from "@/hooks/use-toast";
import EvaluateTaskHunter from "@/components/Tasks/evaluateTaskHunter";

interface Achievement {
  id: number;
  name: string;
  description: string;
}

interface HunterProfile {
  id: string;
  name: string;
  email: string;
  points: number;
  bio: string;
  profilePicture: string;
  achievements: Achievement[];
  certifications: string[];
  levels: number;
  linkPortfolio: string;
  rating: number;
}

interface CompletedTask {
  id: string;
  title: string;
  description: string;
  deadline: string;
  reward: number;
  numberOfMeetings: number;
  numberOfHuntersRequired: number;
  ratingRequired: number;
  taskStatus: string;
  tags: string[];
  hunters: {
    id: string;
    name: string;
    email: string;
    points: number;
    bio: string;
    profilePicture: string;
    totalRating: number;
    levels: number;
    rating: number;
  }[];
  po: {
    id: string;
    name: string;
    email: string;
    points: number;
    levels: number;
    totalRating: number;
    profilePicture: string;
    bio: string;
    rating: number;
  };
}

const fetchProfile = async (): Promise<HunterProfile | null> => {
  const hunterId = localStorage.getItem('userId');
  if (!hunterId) return null;

  const token = localStorage.getItem('accessToken');
  if (!token) throw new Error('Missing access token.');

  const response = await fetch(`http://localhost:8080/api/hunters/${hunterId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch profile');
  }

  return response.json();
};

const fetchCompletedTasks = async (hunterId: string): Promise<CompletedTask[]> => {
  const token = localStorage.getItem('accessToken');
  if (!token) throw new Error('Missing access token.');

  const response = await fetch(`http://localhost:8080/api/task/hunter/${hunterId}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(`Failed to fetch completed tasks: ${response.status} ${response.statusText}. ${errorText}`);
  }

  const text = await response.text();
  if (!text) {
    return []; // Return an empty array if the response is empty
  }

  try {
    return JSON.parse(text);
  } catch (error) {
    console.error('Error parsing JSON:', error);
    throw new Error('Invalid JSON in response');
  }
};

export default function HunterProfilePage() {
  const [profile, setProfile] = useState<HunterProfile | null>(null);
  const [completedTasks, setCompletedTasks] = useState<CompletedTask[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const { toast } = useToast();
  const [isEvaluateDialogOpen, setIsEvaluateDialogOpen] = useState(false);
  const [selectedTaskId, setSelectedTaskId] = useState<string | null>(null);

  useEffect(() => {
    const loadProfileAndTasks = async () => {
      try {
        const profileData = await fetchProfile();
        setProfile(profileData);

        if (profileData) {
          const tasksData = await fetchCompletedTasks(profileData.id);
          setCompletedTasks(tasksData);
        }
      } catch (err) {
        console.error(err);
        setError(err instanceof Error ? err.message : 'An unknown error occurred');
        toast({
          title: "Error",
          description: "Failed to load profile or tasks. Please try again later.",
          variant: "destructive",
        });
      } finally {
        setLoading(false);
      }
    };

    loadProfileAndTasks();
  }, [toast]);

  const requestPayment = async (taskId: string) => {
    const token = localStorage.getItem('accessToken');
    const userId = localStorage.getItem('userId');
    if (!token || !userId) {
      toast({
        title: "Error",
        description: "Missing authentication information",
        variant: "destructive",
      });
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/task/hunter/${userId}/request-payment/${taskId}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error("Failed to request payment");
      }
      toast({
        title: "Success",
        description: "Payment requested successfully",
      });
    } catch (error) {
      console.error(error);
      toast({
        title: "Error",
        description: "Failed to request payment. Please try again.",
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

  if (loading) {
    return <div className="flex justify-center items-center min-h-screen bg-gray-900 text-gray-200">Loading...</div>;
  }

  if (error || !profile) {
    return (
      <div className="flex flex-col justify-center items-center min-h-screen bg-gray-900 text-gray-200">
        <p>{error || 'Profile not found.'}</p>
        <Button onClick={() => window.location.reload()} className="mt-4">
          Try Again
        </Button>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-900 text-gray-200 p-6">
      <div className="mx-auto max-w-6xl grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* Main Content */}
        <div className="md:col-span-2 space-y-6">
          {/* Profile Header */}
          <div className="p-6 bg-gray-800 rounded-lg shadow">
            <div className="flex items-start gap-6">
              <User className="h-24 w-24 text-gray-200" />
              <div className="space-y-2">
                <h1 className="text-2xl font-bold">{profile.name}</h1>
                <p className="text-gray-400">{profile.bio || "No bio available"}</p>
                <div className="flex gap-2">
                  {profile.certifications.map((cert, index) => (
                    <Badge key={index} variant="secondary">{cert}</Badge>
                  ))}
                </div>
              </div>
            </div>
          </div>

          {/* Completed Tasks */}
          <div className="p-6 bg-gray-800 rounded-lg shadow">
            <h2 className="text-xl font-semibold mb-6">Completed Tasks ({completedTasks.filter((task) => task.taskStatus === "DONE").length})</h2>
            {completedTasks.length > 0 ? (
              <ul className="space-y-4">
                {completedTasks.filter((task) => task.taskStatus === "DONE").map((task) => (
                  <li
                    key={task.id}
                    className="p-4 bg-gray-700 rounded-lg shadow hover:shadow-lg transition-shadow"
                  >
                    <h3 className="text-xl font-bold">{task.title}</h3>
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
                        <Badge key={index} variant="outline">{tag}</Badge>
                      ))}
                    </div>
                    <div className="mt-4 flex gap-4">
                      <Button
                        onClick={() => navigateToTask(task.id)}
                        variant="link"
                        className="text-blue-400 hover:text-blue-300 transition-colors"
                      >
                        View Details
                      </Button>
                      <Button
                        onClick={() => openEvaluateDialog(task.id)}
                        variant="secondary"
                        className="bg-yellow-500 hover:bg-yellow-400 text-black transition-colors"
                      >
                        Rate
                      </Button>
                      <Button
                        onClick={() => requestPayment(task.id)}
                        variant="secondary"
                        className="bg-purple-500 hover:bg-purple-400 text-black transition-colors"
                      >
                        Request Payment
                      </Button>
                    </div>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-gray-400">You haven not completed any tasks yet.</p>
            )}
          </div>
        </div>

        {/* Sidebar */}
        <div className="space-y-6">
          <div className="p-6 bg-gray-800 rounded-lg shadow">
            <div className="text-center">
              <div className="inline-block px-4 py-2 rounded-lg bg-gray-700 mb-4">
                <span className="text-3xl font-bold text-gray-200">Lvl {profile.levels}</span>
              </div>
              <h2 className="text-sm text-gray-400">Legend</h2>
              <div className="flex justify-center mt-2">
                {Array.from({ length: 5 }).map((_, i) => (
                  <span key={i} className={i < Math.round(profile.rating) ? "text-yellow-500" : "text-gray-500"}>
                    ★
                  </span>
                ))}
              </div>
            </div>
          </div>

          <div className="p-6 bg-gray-800 rounded-lg shadow">
            <h2 className="text-xl font-semibold mb-4">Achievements</h2>
            <div className="grid grid-cols-3 gap-4">
              {profile.achievements.slice(0, 9).map((achievement) => (
                <div key={achievement.id} className="aspect-square rounded-full bg-gray-600 flex items-center justify-center">
                  <span className="text-xs text-center p-1 text-gray-200">{achievement.name}</span>
                </div>
              ))}
            </div>
            <Button variant="ghost" className="w-full mt-4">
              View More
            </Button>
          </div>
        </div>
      </div>

      {selectedTaskId && (
        <EvaluateTaskHunter
          taskId={selectedTaskId}
          isOpen={isEvaluateDialogOpen}
          onClose={() => setIsEvaluateDialogOpen(false)}
        />
      )}
    </div>
  );
}

