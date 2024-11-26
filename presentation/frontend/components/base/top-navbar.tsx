'use client';
import { User } from "lucide-react";
import { useEffect, useState } from "react";
import Image from 'next/image';

export default function TopNavbar() {
    const [userData, setUserData] = useState({
        name: "",
        points: 0,
        levels: 0,
    });
    const [role, setRole] = useState("");

    useEffect(() => {
        const userRole = localStorage.getItem("role");
        const id = localStorage.getItem("userId");
        const token = localStorage.getItem("accessToken");
        setRole(userRole || "");

        const url = userRole === "ROLE_PO"
            ? `http://localhost:8080/api/po/${id}`
            : `http://localhost:8080/api/hunters/${id}`;

        fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Erro ao buscar dados do usuário");
                }
                return response.json();
            })
            .then((data) => {
                setUserData({
                    name: data.name,
                    points: data.points,
                    levels: data.levels,
                });
            })
            .catch((error) => console.error("Erro ao buscar dados:", error));
    }, []);

    const handleGetMorePoints = async () => {
        const id = localStorage.getItem("userId");
        const token = localStorage.getItem("accessToken");
        const pointsToAdd = 100;

        try {
            const response = await fetch(`http://localhost:8080/api/po/points/${id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${token}`,
                },
                body: JSON.stringify({ points: pointsToAdd }),
            });

            if (!response.ok) {
                throw new Error("Erro ao atualizar pontos");
            }

            const message = await response.text();
            console.log(message);

            // Atualiza os pontos localmente
            setUserData((prev) => ({
                ...prev,
                points: prev.points + pointsToAdd,
            }));
        } catch (error) {
            console.error("Erro ao atualizar pontos:", error);
        }
    };

    return (
        <div className="w-full bg-gray-900 h-20 flex items-center justify-end gap-4 pr-6">
            <div className="w-80 h-3/5 rounded-xl bg-black flex items-center justify-between border border-blue-400">
                <div className="w-8 h-8 rounded-full ml-4 flex items-center justify-center border border-blue-400 text-white">
                    <User />
                </div>
                <p className="text-white text-sm">{userData.name}</p>
                <div className="h-full w-1/4 rounded-r-xl flex items-center justify-center border-l border-blue-400">
                    <p className="text-white">lvl.{userData.levels}</p>
                </div>
            </div>
            <div className="w-28 h-3/5 rounded-xl bg-black flex items-center px-3 justify-between border border-blue-400">
                <Image src="/img/gold.svg" width={6} height={6} className="w-6 h-6" alt="gold" />
                <p className="text-white text-sm">{userData.points}</p>

            </div>
            {role === "ROLE_PO" && (
                <button
                    className="ml-2 px-2 py-1 text-sm bg-blue-500 text-white rounded hover:bg-blue-600"
                    onClick={handleGetMorePoints}
                >
                    Get More!
                </button>
            )}
        </div>
    );
}
