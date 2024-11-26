import { Bebas_Neue } from "next/font/google";
import "./globals.css";

const bebasNeue = Bebas_Neue({
  subsets: ["latin"],
  weight: "400",
});

type Metadata = {
  title: string;
  description: string;
};

export const metadata: Metadata = {
  title: "Hunt Hub",
  description: "Aqui seu trabalho vale Ouro",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className={bebasNeue.className}>
      <body>{children}</body>
    </html>
  );
}
