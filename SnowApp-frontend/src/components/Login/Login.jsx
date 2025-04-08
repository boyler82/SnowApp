import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email,
        password,
      });

      console.log("✅ Odpowiedź z logowania:", response.data);
      const token = response.data;
      localStorage.setItem("jwt", token);
      console.log("💾 Token zapisany:", token);

      navigate("/dashboard");
    } catch (err) {
      console.error("❌ Błąd logowania:", err);
    }
  };

  return (
    <form onSubmit={handleLogin}>
      <h2>Logowanie</h2>
      <input
        type="email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        placeholder="Email"
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Hasło"
      />
      <button type="submit">Zaloguj</button>
    </form>
  );
}