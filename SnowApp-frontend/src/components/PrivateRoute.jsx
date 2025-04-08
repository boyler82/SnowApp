import { Navigate } from "react-router-dom";

export default function PrivateRoute({ children }) {
  const token = localStorage.getItem("jwt");

  if (!token) {
    return <Navigate to="/login" />;
  }

  return children;
}