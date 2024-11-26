'use client'
import { Bell, LogOut, Mail, Target, User, BookCheck } from "lucide-react";
import { cn } from "@/lib/utils";
import { useRouter } from "next/navigation"

function logout() {
  localStorage.removeItem('accessToken');
  localStorage.removeItem('role');
  localStorage.removeItem('userId');
  window.location.href = '/';
}

export default function LeftNavbar() {

  const router = useRouter();

  return (
    <nav className="group h-full w-28 transition-[width] duration-300 hover:w-60">
      <div className="flex h-full flex-col gap-4 bg-gray-900 p-6 shadow-xl">
        <NavItem icon={Target} onClick={() => router.push('/home')} label="Tasks" />
        <NavItem icon={Bell} onClick={() => router.push('/notifications')} label="Notifications" />
        <NavItem
          icon={User}
          onClick={() => {
            const role = localStorage.getItem('role');
            if (role === 'ROLE_PO') {
              router.push('/profile-po');
            } else {
              router.push('/profile');
            }
          }}
          label="Profile"
        />
        <NavItem icon={BookCheck} onClick={() => router.push('/mytasks')} label="My Tasks" />
        <NavItem
          icon={LogOut}
          label="Logout"
          className="mt-auto"
          onClick={logout}
        />
      </div>
    </nav>
  );
}

function NavItem({
  icon: Icon,
  label,
  className,
  onClick,
}: {
  icon: React.ElementType;
  label: string;
  className?: string;
  onClick?: () => void;
}) {
  return (
    <button
      className={cn(
        "flex w-full items-center gap-4 rounded-lg border border-blue-400 bg-gray-950 p-5 text-white transition-colors hover:bg-gray-800",
        className
      )}
      onClick={onClick}
    >
      <Icon className="h-5 w-5 shrink-0" />
      <span className="truncate opacity-0 transition-opacity group-hover:opacity-100">{label}</span>
    </button>
  );
}
