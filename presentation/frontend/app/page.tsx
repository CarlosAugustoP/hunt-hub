import { RainbowButton } from "@/components/ui/rainbow-button";
import Link from "next/link";
import { cn } from "@/lib/utils";
import AnimatedGridPattern from "@/components/ui/grid-pattern";
import FeaturesSection from "@/components/home/features-section";
import PublicNavbar from "@/components/base/public-navbar";

export default function Home() {
  return (
    <div className="relative flex flex-col h-screen w-full">
      <PublicNavbar />
      <AnimatedGridPattern
        numSquares={30}
        maxOpacity={0.1}
        duration={3}
        repeatDelay={1}
        className={cn(
          "absolute inset-0",
          "[mask-image:radial-gradient(500px_circle_at_center,white,transparent)]",
          "h-full w-full skew-y-12"
        )}
      />
      <div className="relative flex flex-col justify-center items-center gap-8 text-center mx-10 md:mx-40 mt-10">
        <h1 className="font-bold text-6xl">
          Aqui seu trabalho vale <span className="uppercase text-yellow-400">Ouro</span>
        </h1>
        <p className="text-xl text-black/70">
          Junte-se a uma comunidade de desenvolvedores comprometidos em publicar seus melhores trabalhos em todo o
          mundo. Entregue projetos fantásticos e ganhe uma compensação justa, ou tenha suas ideias ganhando vida em
          prazos curtos.
        </p>
        <div className="flex items-center justify-around w-2/3 font-bold">
          <Link href={'/auth/hunter-signup'}>
            <RainbowButton className="bg-white">Encontre um Trabalho</RainbowButton>
          </Link>
          <Link href={'/auth/po-signup'}>
            <RainbowButton className="bg-white">Procure um Talento</RainbowButton>
          </Link>
        </div>
      </div>
      <FeaturesSection />
    </div>
  );
}
