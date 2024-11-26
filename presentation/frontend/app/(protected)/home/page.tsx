'use client'
import Tasks from "@/components/Tasks/tasks"
import { CreateTaskButton } from '@/components/Tasks/create-task-button'
import { useEffect, useState } from "react"

export default function Home() {
  const [userRole, setUserRole] = useState('')
  const [userid, setUserid] = useState('')
  useEffect(() => {
    const role = localStorage.getItem('role') || ''
    const userid = localStorage.getItem('userId') || ''
    setUserRole(role)
    setUserid(userid)
  }, [])

  return (
    <div className="p-6 bg-gray-900 text-white min-h-screen">
      <div className="flex justify-between items-center mb-8 text-white">
        <h1 className="text-3xl font-bold mb-6">Tasks</h1>
        {userRole === 'ROLE_PO' && (
          <CreateTaskButton poid={userid} userRole={userRole} />
        )}
      </div>
      <Tasks />
    </div>
  )
}

