import { GoogleLogin } from "@react-oauth/google";
import axios from "axios";
import { useNavigate } from "react-router-dom";

export default function GoogleLoginButton() {
  const navigate = useNavigate();

  const handleGoogleLogin = async (response) => {
    const credential = response.credential;
    console.log("✅ Otrzymano credential:", credential);

    try {
      const res = await axios.post("http://localhost:8080/api/auth/auth/google", {
        credential: credential
      });

      console.log("🎉 JWT z backendu:", res.data.token);
      localStorage.setItem("jwt", res.data.token);
      navigate("/dashboard");
    } catch (err) {
      console.error("❌ Błąd logowania przez Google:", err);
    }
  };

  return (
    <GoogleLogin
      onSuccess={handleGoogleLogin}
      onError={() => {
        console.log("❌ Błąd logowania Google");
      }}
      useOneTap
    />
  );
}