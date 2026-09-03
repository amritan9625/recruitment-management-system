import { BrowserRouter, Routes, Route } from 'react-router-dom';

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<h1>Recruitment Management System</h1>} />
        <Route path="/login" element={<h1>Login Page</h1>} />
        <Route path="/dashboard" element={<h1>Dashboard</h1>} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;