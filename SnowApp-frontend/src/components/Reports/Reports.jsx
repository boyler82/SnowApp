import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Reports from "../Reports/Reports.jsx";

export default function Dashboard() {
  const [reports, setReports] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("jwt");

    axios
      .get("http://localhost:8080/api/reports", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => {
        setReports(res.data);
      })
      .catch((err) => {
        console.error("Błąd podczas pobierania zleceń:", err);
      });
  }, []);

  const handleAddReport = () => {
    navigate("/add-report");
  };

  const handleMyReports = () => {
    navigate("/my-reports");
  };

  const handleProfile = () => {
    navigate("/profile");
  };

  return (
    <div>
      <h1>📋 Dostępne zlecenia</h1>
      <div style={{ marginBottom: "1rem" }}>
        <button onClick={handleAddReport}>➕ Dodaj zlecenie</button>{" "}
        <button onClick={handleMyReports}>📄 Moje zlecenia</button>{" "}
        <button onClick={handleProfile}>👤 Mój profil</button>
      </div>
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
      <Reports />
    </div>
  );
}