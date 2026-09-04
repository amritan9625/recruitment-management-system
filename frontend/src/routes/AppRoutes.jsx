import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        {/* public routes */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<h1>Login Page</h1>} />

        {/* Protected Application Routes - temporary */}
        <Route path="/dashboard" element={<MainLayout><h1>Dashboard</h1></MainLayout>} />

        {/* Fallback Route (url doesn't exist)*/}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
