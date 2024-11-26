"use client";

import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { TaskExpanded } from "@/components/Tasks/task-component-hunter";
import { Star, User, Tag, Calendar, Users, Clock, CheckCircle, AlertCircle, HelpCircle } from 'lucide-react';
import { Button } from "@/components/ui/button";
import { handleApply } from "@/components/Tasks/tasks";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { UUID } from "crypto";

interface Hunter {
  name: string;
  rating: number;
  levels: number;
  bio: string;
  profilePicture: string;
}

interface Po {
  name: string;
  rating: number;
  levels: number;
  profilePicture: string;
  bio: string;
}

interface TaskDetailsProps extends TaskExpanded {
  po: Po;
  hunters: Hunter[];
}

export default function TaskDetails() {
  const pathName = useParams().id;
  const id = pathName;
  const [taskDetails, setTaskDetails] = useState<TaskDetailsProps | null>(null);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [role, setRole] = useState<string | null>(null);

  useEffect(() => {
    if (typeof window !== 'undefined') {
      const roleFromStorage = localStorage.getItem("role");
      setRole(roleFromStorage);
    }
  }, []);

  const fetchTaskDetailsProps = async () => {
    if (!id) return;

    setIsLoading(true);
    try {
      const token = localStorage.getItem("accessToken");
      if (!token) {
        throw new Error("Missing access token.");
      }

      const res = await fetch(`http://localhost:8080/api/task/${id}`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!res.ok) {
        throw new Error(`Failed to fetch task details: ${res.statusText}`);
      }

      const data: TaskDetailsProps = await res.json();

      const parsedData = {
        ...data,
        deadline: new Date(data.deadline),
      };

      setTaskDetails(parsedData);
    } catch (err) {
      console.error("Error fetching task details:", err);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    if (id) {
      fetchTaskDetailsProps();
    }
  }, [id]);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-900">
        <p className="text-white text-2xl">Loading task details...</p>
      </div>
    );
  }

  if (!taskDetails) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-900">
        <p className="text-white text-2xl">Task details not found</p>
      </div>
    );
  }

  return (
    <div className="bg-gray-900 min-h-screen text-white p-4 sm:p-8">
      <div className="grid grid-cols-1 lg:grid-cols-[1fr,400px] gap-8">
        <div className="space-y-8">
          <h1 className="text-3xl sm:text-4xl font-bold">{taskDetails.title}</h1>
          <div className="bg-gray-800 rounded-lg p-4 sm:p-6 shadow-lg">
            <p className="text-lg mb-6">{taskDetails.description}</p>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 mb-6">
              <div className="flex items-center bg-gray-700 rounded-lg px-4 py-3">
                <Star className="w-5 h-5 mr-3 text-yellow-400" />
                <span className="font-medium">Bounty: {taskDetails.reward} Gold</span>
              </div>
              <div className="flex items-center bg-gray-700 rounded-lg px-4 py-3">
                <User className="w-5 h-5 mr-3 text-blue-400" />
                <span className="font-medium">Rating: {taskDetails.ratingRequired}</span>
              </div>
              <div className="flex items-center bg-gray-700 rounded-lg px-4 py-3">
                <Users className="w-5 h-5 mr-3 text-green-400" />
                <span className="font-medium">Hunters: {taskDetails.numberOfHuntersRequired}</span>
              </div>
              <div className="flex items-center bg-gray-700 rounded-lg px-4 py-3">
                <Calendar className="w-5 h-5 mr-3 text-purple-400" />
                <span className="font-medium">Deadline: {taskDetails.deadline.toLocaleDateString()}</span>
              </div>
              <div className="flex items-center bg-gray-700 rounded-lg px-4 py-3">
                <Clock className="w-5 h-5 mr-3 text-red-400" />
                <span className="font-medium">Meetings: {taskDetails.numberOfMeetings}</span>
              </div>
            </div>
            <div className="flex flex-wrap gap-2 mb-6">
              {taskDetails.tags.map((tag, index) => (
                <span key={index} className="bg-gray-700 rounded-full px-3 py-1 text-sm flex items-center">
                  <Tag className="w-4 h-4 mr-1" />
                  {tag}
                </span>
              ))}
            </div>
            {role === "ROLE_HUNTER" && (
              <Button
                className="w-full bg-blue-700 hover:bg-blue-800 text-white font-bold py-3 rounded-lg shadow-lg transform transition duration-300 hover:scale-105"
                onClick={() => id && handleApply(id as UUID)}>
                Apply for this Task
              </Button>
            )}
          </div>

          <div className="grid md:grid-cols-2 gap-8">
            <div className="bg-gray-800 rounded-lg p-6 shadow-lg">
              <h2 className="text-2xl font-semibold mb-4">PO Details</h2>
              <div className="flex items-center mb-4">
                <User className="w-12 h-12 mr-4 text-blue-400" />
                <div>
                  <p className="font-semibold text-lg">{taskDetails.po.name}</p>
                  <div className="flex gap-4">
                    <p className="text-gray-400">Rating: {taskDetails.po.rating}</p>
                    <p className="text-gray-400">Level: {taskDetails.po.levels}</p>
                  </div>
                </div>
              </div>
              <p className="text-gray-300">{taskDetails.po.bio}</p>
            </div>

            <div className="bg-gray-800 rounded-lg p-6 shadow-lg">
              <h2 className="text-2xl font-semibold mb-4">Hunters</h2>
              <div className="space-y-4 max-h-96 overflow-y-auto">
                {taskDetails.hunters.map((hunter, index) => (
                  <div key={index} className="flex items-center border-b border-gray-700 pb-4 last:border-b-0 last:pb-0">
                    <User className="w-12 h-12 mr-4 text-blue-400" />
                    <div>
                      <p className="font-semibold">{hunter.name}</p>
                      <p className="text-gray-400">Rating: {hunter.rating}</p>
                      <p className="text-sm text-gray-500 mt-1">{hunter.bio}</p>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>

        <div className="space-y-6">
          <Card className="bg-gray-800 text-white border-gray-700">
            <CardHeader>
              <CardTitle>Quest Requirements</CardTitle>
              <CardDescription>Make sure you meet these requirements before applying</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="flex items-center gap-2 text-green-400">
                <CheckCircle className="w-5 h-5" />
                <span>Minimum rating of {taskDetails.ratingRequired}</span>
              </div>
              <div className="flex items-center gap-2 text-yellow-400">
                <AlertCircle className="w-5 h-5" />
                <span>Must attend {taskDetails.numberOfMeetings} meetings</span>
              </div>
              <div className="flex items-center gap-2 text-blue-400">
                <HelpCircle className="w-5 h-5" />
                <span>Experience with required technologies</span>
              </div>
            </CardContent>
          </Card>

          <Card className="bg-gray-800 border-gray-700 text-white">
            <CardHeader>
              <CardTitle>Quest Timeline</CardTitle>
              <CardDescription>Important dates and milestones</CardDescription>
            </CardHeader>
            <CardContent className="space-y-4">
              <div className="space-y-2">
                <p className="text-sm text-gray-400">Start Date</p>
                <p>Immediate upon selection</p>
              </div>
              <div className="space-y-2">
                <p className="text-sm text-gray-400">Deadline</p>
                <p>{taskDetails.deadline.toLocaleDateString()}</p>
              </div>
              <div className="space-y-2">
                <p className="text-sm text-gray-400">Expected Duration</p>
                <p>Until deadline ({Math.ceil((taskDetails.deadline.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24))} days)</p>
              </div>
            </CardContent>
          </Card>

          <Card className="bg-gray-800 border-gray-700 text-white">
            <CardHeader>
              <CardTitle>Required Skills</CardTitle>
              <CardDescription>Technologies and expertise needed</CardDescription>
            </CardHeader>
            <CardContent>
              <div className="flex flex-wrap gap-2">
                {taskDetails.tags.map((tag, index) => (
                  <span key={index} className="bg-gray-700 px-3 py-1 rounded-full text-sm">
                    {tag}
                  </span>
                ))}
              </div>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
}