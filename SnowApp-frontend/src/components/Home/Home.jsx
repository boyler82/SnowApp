import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import GoogleLoginButton from "../Login/GoogleLoginButton";

export default function Home() {
  const [reports, setReports] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/reports")
      .then((res) => {
        setReports(res.data.slice(0, 3)); // tylko kilka przykładów
      })
      .catch((err) => {
        console.error("❌ Błąd pobierania zleceń:", err);
      });
  }, []);

  return (
    <div style={{ padding: "2rem" }}>
      <h1>❄️ SnowTask</h1>
      <p>Zgłoś lub podejmij zlecenie odśnieżania</p>

      <div style={{ margin: "2rem 0" }}>
        <button onClick={() => navigate("/login")}>🔐 Zaloguj się</button>{" "}
        <button onClick={() => navigate("/register")}>🆕 Zarejestruj się</button>{" "}
        <button disabled>🔴 Google (soon)</button>{" "}
        <GoogleLoginButton />
        <button disabled>🔵 Facebook (soon)</button>
      </div>

      <h2>📋 Przykładowe zlecenia:</h2>
      <ul>
        {reports.map((r) => (
          <li key={r.id}>
            <strong>{r.title}</strong> – {r.description}
          </li>
        ))}
      </ul>
    </div>
  );
}