"use client";

import { useEffect, useState } from "react";
import {
  Bell,
  CheckCircle,
  AlertTriangle,
  UserPlus,
  ClipboardCheck,
} from "lucide-react"; 

interface Notification {
  id: number;
  message: string;
  type: string;
  theme: string; 
  createdAt: string;
}

export default function Notifications() {
  const [data, setData] = useState<Notification[]>([]);

const getNotifications = async () => {
    const userRole = localStorage.getItem("role");
    const userId = localStorage.getItem("userId");
    const accessToken = localStorage.getItem("accessToken");
    let url: string;

    if (userRole === "ROLE_HUNTER") {
        url = `http://localhost:8080/api/notifications/hunter/${userId}`;
    } else {
        url = `http://localhost:8080/api/notifications/po/${userId}`;
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
            throw new Error("Error searching notifications");
        }

        const notifications: Notification[] = await response.json();
        console.log("Notificações recebidas:", notifications);

        setData(notifications);
    } catch (err) {
        console.error("Erro ao buscar notificações:", err);
    }
};

  useEffect(() => {
    getNotifications();
  }, []);

  const renderIcon = (theme: string) => {
    switch (theme) {
      case "New task":
        return <ClipboardCheck className="text-blue-500 w-6 h-6" />;
      case "Task applied":
        return <UserPlus className="text-green-500 w-6 h-6" />;
      case "Task accepted":
        return <CheckCircle className="text-yellow-500 w-6 h-6" />;
      case "Task completed":
        return <Bell className="text-purple-500 w-6 h-6" />;
      case "Hunter applied":
        return <AlertTriangle className="text-red-500 w-6 h-6" />;
      case "Hunter accepted":
        return <CheckCircle className="text-green-500 w-6 h-6" />;
      default:
        return <Bell className="text-gray-500 w-6 h-6" />;
    }
  };

  return (
    <div className="p-6 bg-gray-900 text-white min-h-screen">
      <h1 className="text-3xl font-bold mb-6">Notifications</h1>
      {data && data.length > 0 ? (
        <ul className="space-y-4">
          {data.map((notification) => (
            <li
              key={notification.id}
              className="p-4 bg-gray-800 rounded-lg shadow flex items-center gap-4"
            >
              {renderIcon(notification.theme)}
              <div>
                <p className="text-lg">{notification.message}</p>
                <p className="text-sm text-gray-400">
                  {new Date(notification.createdAt).toLocaleString()}
                </p>
              </div>
            </li>
          ))}
        </ul>
      ) : (
        <p className="text-gray-400">No notifications available.</p>
      )}
    </div>
  );
}
