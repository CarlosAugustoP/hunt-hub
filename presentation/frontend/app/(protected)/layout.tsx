"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";
import {jwtDecode,  JwtPayload } from "jwt-decode";
import TopNavbar from "@/components/base/top-navbar";
import LeftNavbar from "@/components/base/left-navbar";
import { Toaster } from "@/components/ui/toaster";

interface ProtectedLayoutProps {
  children: React.ReactNode;
}

export default function ProtectedLayout({ children }: ProtectedLayoutProps) {
  const router = useRouter();

  const validateToken = () => {
    const accessToken = localStorage.getItem("accessToken");

    if (!accessToken) {
      console.error("Access token is missing.");
      router.push("/"); 
      return;
    }

    try {
      const decodedToken = jwtDecode<JwtPayload>(accessToken);
      const currentTime = Date.now() / 1000; 

      if (!decodedToken.exp || decodedToken.exp < currentTime) {
        console.error("Access token is expired.");
        router.push("/"); 
        return;
      }
    } catch (err) {
      console.error("Invalid access token:", err);
      router.push("/"); 
    }
  };

  useEffect(() => {
    validateToken();
  }, []);

  return (
    <div className="flex h-screen overflow-hidden">
      <LeftNavbar />
      <div className="flex flex-col w-full">
        <TopNavbar />
        <main className="flex-1 p-6 bg-slate-700 overflow-y-scroll">
          {children}
          <Toaster />
        </main>
      </div>
    </div>
  );
}
