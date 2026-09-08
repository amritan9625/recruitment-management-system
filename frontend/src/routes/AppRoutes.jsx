import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";
import Login from "../pages/auth/Login";
import ProtectedRoute from "./ProtectedRoute";
import RoleProtectedRoute from "./RoleProtectedRoute";

import { PERMISSIONS } from "../utils/permissions";
import Dashboard from "../pages/Dashboard";
import CandidateList from "../pages/candidate/CandidateList";
import JobList from "../pages/recruiter/JobList";
import ApplicationList from "../pages/recruiter/ApplicationList";
import InterviewList from "../pages/interviewer/InterviewList";
import OfferList from "../pages/recruiter/OfferList";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        {/* public routes */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />

        {/* Authentication Protected Routes */}
        <Route element={<ProtectedRoute />}>
          {/* Dashboard */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.DASHBOARD} />} >
            <Route path="/dashboard" element={<MainLayout><Dashboard /></MainLayout>} />
          </Route>

          {/* Users */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.USERS} />} >
            <Route path="/users" element={<MainLayout><h1>Users</h1></MainLayout>} />
          </Route>

          {/* Roles */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.ROLES} />} >
            <Route path="/roles" element={<MainLayout><h1>Roles</h1></MainLayout>} />
          </Route>

          {/* Candidates */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.CANDIDATES} />                       }>
            <Route path="/candidates" element={<MainLayout><CandidateList /></MainLayout>} />
          </Route>

          {/* Jobs */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.JOBS} />} >
            <Route path="/jobs" element={<MainLayout><JobList /></MainLayout>} />
          </Route>

          {/* Applications */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.APPLICATIONS} />} >
            <Route path="/applications" element={<MainLayout><ApplicationList /></MainLayout>} />
          </Route>

          {/* Interviews */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.INTERVIEWS} />} >
            <Route path="/interviews" element={<MainLayout><InterviewList /></MainLayout>} />
          </Route>

          {/* Offers */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.OFFERS}/>} >
            <Route path="/offers" element={<MainLayout><OfferList /></MainLayout>}/>
          </Route>

        </Route>

        {/* Fallback Route (url doesn't exist)*/}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRoutes;
