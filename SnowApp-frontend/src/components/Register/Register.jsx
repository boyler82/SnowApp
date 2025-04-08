import { useState } from "react";
import axios from "axios";

export default function Register() {
  const [form, setForm] = useState({ username: "", email: "", password: "" });

  const handleChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/api/auth/register", form);
      alert("Zarejestrowano!");
    } catch (err) {
      alert("Błąd rejestracji");
    }
  };

  return (
    <div className="p-4">
      <h1 className="text-xl mb-4">Rejestracja</h1>
      <form onSubmit={handleSubmit} className="space-y-2">
        <input name="username" placeholder="Nazwa użytkownika" onChange={handleChange} className="border p-2 w-full" />
        <input type="email" name="email" placeholder="Email" onChange={handleChange} className="border p-2 w-full" />
        <input type="password" name="password" placeholder="Hasło" onChange={handleChange} className="border p-2 w-full" />
        <button type="submit" className="bg-green-500 text-white px-4 py-2">Zarejestruj</button>
      </form>
    </div>
  );
}