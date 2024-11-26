import React from "react";

interface FormErrorProps {
    error?: string;
}

export const FormError: React.FC<FormErrorProps> = ({ error }) => {
    if (!error) return null;

    return (
        <div className="rounded-md bg-red-100 p-4 text-red-700 border border-red-300">
            <p className="text-sm font-medium">{error}</p>
        </div>
    );
};
