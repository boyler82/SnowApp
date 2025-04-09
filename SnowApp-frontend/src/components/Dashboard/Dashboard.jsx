import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Dashboard() {
  const [reports, setReports] = useState([]);
  const navigate = useNavigate(); // 👈 DODANE!

  const handleLogout = () => {
    localStorage.removeItem("jwt");
    navigate("/login");
  };

  console.log("✅ Dashboard został załadowany");
  const token = localStorage.getItem("jwt");
  console.log("🔐 Token JWT:", token);

  useEffect(() => {
    axios
      .get("http://localhost:8080/api/reports", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        console.log("📦 Otrzymane zlecenia:", res.data);
        setReports(res.data);
      })
      .catch((err) => {
        console.error("❌ Błąd pobierania zleceń:", err);
      });
  }, []);

  return (
    <div>
      <button onClick={handleLogout}>🔓 Wyloguj</button>
      <h1>📋 Dostępne zlecenia</h1>
      {reports.length === 0 ? (
        <p>Brak dostępnych zleceń.</p>
      ) : (
        <ul>
          {reports.map((report) => (
            <li key={report.id}>
              <strong>{report.title}</strong> – {report.description}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}