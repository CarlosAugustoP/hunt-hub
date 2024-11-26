import React, { useState } from "react";
import { CheckIcon } from "@radix-ui/react-icons";

const MultiSelect = ({
  options,
  value,
  onChange,
}: {
  options: string[];
  value: string[];
  onChange: (selected: string[]) => void;
}) => {
  const [isOpen, setIsOpen] = useState(false);

  const handleToggle = (option: string) => {
    const selected = value.includes(option)
      ? value.filter((v) => v !== option)
      : [...value, option];
    onChange(selected);
  };

  return (
    <div className="relative w-full">
      <button
        type="button"
        onClick={() => setIsOpen(!isOpen)}
        className="flex h-9 w-full items-center justify-between rounded-md border px-3 py-2 text-sm shadow-sm focus:outline-none"
      >
        <span className="truncate">
          {value.length > 0 ? value.join(", ") : "Select tags"}
        </span>
        <span className="ml-2">&#9662;</span>
      </button>
      {isOpen && (
        <div className="absolute z-10 mt-1 w-full max-h-48 overflow-auto rounded-md border bg-white shadow-lg">
          {options.map((option) => (
            <div
              key={option}
              onClick={() => handleToggle(option)}
              className={`cursor-pointer flex items-center justify-between px-3 py-2 text-sm ${value.includes(option) ? "bg-blue-500 text-white" : "hover:bg-gray-100"
                }`}
            >
              <span>{option}</span>
              {value.includes(option) && <CheckIcon />}
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default MultiSelect;
