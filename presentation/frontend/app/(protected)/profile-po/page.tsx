'use client';

import { useEffect, useState } from "react";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { User } from "lucide-react";

interface POProfile {
  id: string;
  name: string;
  level: number;
  experience: string;
}

interface TaskSummary {
  id: string;
  title: string;
  description: string;
  reward: number;
  tags: string[];
}

export default function PoPage() {
  const [profile, setProfile] = useState<POProfile | null>(null);
  const [tasks, setTasks] = useState<TaskSummary[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchProfileAndTasks = async () => {
      const poId = localStorage.getItem('userId');
      if (!poId) {
        setLoading(false);
        return;
      }
      setLoading(true);

      try {
        const token = localStorage.getItem('accessToken');
        if (!token) {
          throw new Error('Missing access token.');
        }

        // Fetch profile
        const profileResponse = await fetch(`http://localhost:8080/api/po/${poId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        });

        if (!profileResponse.ok) {
          throw new Error('Failed to fetch profile');
        }

        const profileData: POProfile = await profileResponse.json();
        setProfile(profileData);

        // Fetch tasks
        const tasksResponse = await fetch(`http://localhost:8080/api/task/po/${poId}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
          },
        });

        if (!tasksResponse.ok) {
          throw new Error('Failed to fetch tasks');
        }

        const tasksData: TaskSummary[] = await tasksResponse.json();
        setTasks(tasksData);
      } catch (error) {
        console.error(error);
      } finally {
        setLoading(false);
      }
    };

    fetchProfileAndTasks();
  }, []);

  if (loading) {
    return <div className="flex justify-center items-center min-h-screen bg-gray-900 text-gray-200">Loading...</div>;
  }

  if (!profile) {
    return <div className="flex justify-center items-center min-h-screen bg-gray-900 text-gray-200">Profile not found.</div>;
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
                <p className="text-muted-foreground">
                  {profile.experience || "No experience provided"}
                </p>
                <div className="flex gap-2">
                  <Badge variant="secondary">Product Owner</Badge>
                </div>
              </div>
            </div>
          </div>

          {/* Tasks Section */}
          <div className="p-6 bg-gray-800 rounded-lg shadow">
            <h2 className="text-xl font-semibold mb-4">
              Open Tasks ({tasks.length})
            </h2>
            {tasks.length > 0 ? (
              <div className="space-y-4">
                {tasks.map((task, index) => (
                  <div key={task.id} className="p-4 border rounded-lg shadow-sm bg-gray-700 border-gray-600">
                    <div>
                      <h3 className="font-medium text-gray-200 mb-2">Task {index + 1}: {task.title}</h3>
                      <p className="text-gray-400 mb-4">{task.description}</p>
                      <div className="flex flex-wrap gap-2 mb-4">
                        {task.tags.map((tag, i) => (
                          <span key={i} className="bg-gray-600 px-3 py-1 rounded-full text-sm text-white">{tag}</span>
                        ))}
                      </div>
                      <div className="flex items-center justify-between mb-4">
                        <span className="text-yellow-400 font-medium">Reward: R$ {task.reward} Gold</span>
                      </div>
                      <div className="flex justify-end space-x-4">
                        <button className="px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-500">View More</button>
                        <button className="px-4 py-2 bg-gray-600 text-white rounded hover:bg-gray-500">View Applied</button>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-muted-foreground">No tasks available yet.</p>
            )}
          </div>
        </div>

        {/* Sidebar */}
        <div className="space-y-6">
          {/* Level Card */}
<div className="p-6 bg-gray-800 rounded-lg shadow">
  <div className="text-center">
    <div className="inline-block px-4 py-2 rounded-lg bg-gray-700 mb-4">
      <span className="text-3xl font-bold text-gray-200">
        Lvl {Math.floor((tasks.length || 0) / 10)} {/* Calcula o nível baseado em 10 tasks por nível */}
      </span>
            </div>
            <h2 className="text-sm text-gray-400">Legend</h2>
            <div className="flex justify-center mt-2">
              {Array.from({ length: 5 }).map((_, i) => (
                <span key={i} className={i < Math.min(5, Math.floor(tasks.length / 2)) ? "text-yellow-500" : "text-gray-500"}>
                  ★
                </span>
              ))}
            </div>
        </div>
      </div>


          {/* Achievements Card */}
          <div className="p-6 bg-gray-800 rounded-lg shadow">
            <h2 className="text-xl font-semibold mb-4">Achievements</h2>
            <div className="grid grid-cols-3 gap-4">
              {Array.from({ length: 9 }).map((_, i) => (
                <div key={i} className="aspect-square rounded-full bg-gray-100" />
              ))}
            </div>
            <Button variant="ghost" className="w-full mt-4">
              View More
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}
