import Link from "next/link"
import { Button } from "@/components/ui/button"

export default function PublicNavbar() {
    return (
        <header className="border-b">
            <div className="container flex h-16 items-center justify-between px-4">
                <Link href="/" className="text-2xl font-bold">
                    Hunt Hub
                </Link>
                <nav className="flex items-center gap-6">
                    <Link
                        href="/"
                        className="text-sm font-medium transition-colors hover:text-primary"
                    >
                        Início
                    </Link>
                    <Link
                        href="/auth/hunter-signup"
                        className="text-sm font-medium transition-colors hover:text-primary"
                    >
                        Cadastrar Hunter
                    </Link>
                    <Link
                        href="/auth/po-signup"
                        className="text-sm font-medium transition-colors hover:text-primary"
                    >
                        Cadastrar PO
                    </Link>
                    <Button variant="default">
                        <Link href="/auth/signin">Entrar</Link>
                    </Button>
                </nav>
            </div>
        </header>
    )
}

