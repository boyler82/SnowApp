import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { BrowserRouter } from "react-router-dom";
import { GoogleOAuthProvider } from "@react-oauth/google";
import "./index.css";

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <GoogleOAuthProvider clientId="846183773185-91tjsglthdn2spo516qf9d5n4re542fq.apps.googleusercontent.com">
      <BrowserRouter>
        <App /> 
      </BrowserRouter>
    </GoogleOAuthProvider>
  </React.StrictMode>
);
