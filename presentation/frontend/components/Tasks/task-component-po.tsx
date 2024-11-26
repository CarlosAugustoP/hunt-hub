"use client"

import { UUID } from "crypto"
import { Star } from 'lucide-react'
import Image from 'next/image'
import { useState } from "react"
import { Button } from "@/components/ui/button"
import { TaskHuntersAppliedPopup } from "./task-hunters-applied"
import Link from "next/link"

export interface TaskSummary {
  title: string;
  description: string;
  reward: number;
  tags: string[];
  ratingRequired: number;
  id: UUID;
}

export default function TaskPO({ title, description, reward, tags, ratingRequired, id }: TaskSummary) {
  const [isPopupOpen, setIsPopupOpen] = useState(false)
  const displayedTags = tags.slice(0, 3)
  const hasMoreTags = tags.length > 3

  const handleViewHunters = () => {
    setIsPopupOpen(true)
  }

  return (
    <div className="w-full h-56 bg-gray-800 rounded-xl p-6 flex flex-col justify-between text-white">
      <div>
        <div className="flex justify-between items-start">
        <div className="flex items-center gap-1 mb-4">
            {Array.from({ length: ratingRequired }).map((_, index) => (
              <Star key={index} className="h-4 w-4 text-yellow-400" />
            ))}
          </div>
          <div className="flex gap-2">
            {displayedTags.map((tag) => (
              <span key={tag} className="px-2 py-1 bg-blue-600 text-[0.5rem] rounded-lg">
                {tag}
              </span>
            ))}
            {hasMoreTags && (
              <span className="py-1 text-[0.5rem] rounded-lg">+{tags.length - 3}</span>
            )}
          </div>
        </div>
        <div className="flex justify-between items-start">
          <h2 className="text-lg font-bold truncate w-1/2">{title}</h2>
        </div>
        <div>
          <p className="text-gray-400 text-sm truncate mt-2">{description}</p>
        </div>
      </div>
      <div className="flex justify-between items-center flex-row-reverse">
        <div className="flex items-center gap-4">
          <Link href={`/task/${id}`}>
            <Button className="text-blue-400 hover:text-blue-300 text-sm transition-colors">
              View More
            </Button>
          </Link>
          <Button className="text-blue-400 hover:text-blue-300 text-sm transition-colors" onClick={handleViewHunters}>
            View Applied
          </Button>
        </div>
        <div className="flex items-center gap-2">
          <Image src="/img/gold.svg" width={5} height={5} className="h-5 w-5" alt="" />
          <span className="text-lg font-semibold">R$ {reward / 10}</span>
          <span className="text-sm text-gray-400">({reward} gold)</span>
        </div>
      </div>
      <TaskHuntersAppliedPopup
        taskId={id.toString()}
        isOpen={isPopupOpen}
        onClose={() => setIsPopupOpen(false)}
      />
    </div>
  )
}