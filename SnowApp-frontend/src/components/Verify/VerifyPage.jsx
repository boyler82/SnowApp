import { useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import axios from "axios";

export default function VerifyPage() {
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  useEffect(() => {
    const verifyToken = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/api/auth/verify?token=${token}`);
        alert("✅ Konto zostało aktywowane!");
      } catch (err) {
        alert("❌ Błąd aktywacji: " + err.response?.data || "Nieznany błąd");
      }
    };

    if (token) verifyToken();
  }, [token]);

  return <p>🔄 Trwa weryfikacja konta...</p>;
}