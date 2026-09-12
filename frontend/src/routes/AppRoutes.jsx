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
import UserList from "../pages/admin/UserList";
import RoleList from "../pages/admin/RoleList";
import Register from "../pages/auth/Register";
import MyProfile from "../pages/candidate/MyProfile";

function AppRoutes() {
  return (
    <BrowserRouter>
      <Routes>
        {/* public routes */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* Authentication Protected Routes */}
        <Route element={<ProtectedRoute />}>

          {/* My Profile */}
          <Route element={<RoleProtectedRoute allowedRoles={[ "CANDIDATE" ]} />} >
            <Route path="/my-profile" element={<MainLayout><MyProfile /></MainLayout>} />
          </Route>

          {/* Dashboard */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.DASHBOARD} />} >
            <Route path="/dashboard" element={<MainLayout><Dashboard /></MainLayout>} />
          </Route>

          {/* Users */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.USERS} />} >
            <Route path="/users" element={<MainLayout><UserList /></MainLayout>} />
          </Route>

          {/* Roles */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.ROLES} />} >
            <Route path="/roles" element={<MainLayout><RoleList /></MainLayout>} />
          </Route>

          {/* Candidates */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.CANDIDATES_VIEW} />                       }>
            <Route path="/candidates" element={<MainLayout><CandidateList /></MainLayout>} />
          </Route>

          {/* Jobs */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.JOBS_VIEW} />} >
            <Route path="/jobs" element={<MainLayout><JobList /></MainLayout>} />
          </Route>

          {/* Applications */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.APPLICATIONS_VIEW} />} >
            <Route path="/applications" element={<MainLayout><ApplicationList /></MainLayout>} />
          </Route>

          {/* Interviews */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.INTERVIEWS_VIEW} />} >
            <Route path="/interviews" element={<MainLayout><InterviewList /></MainLayout>} />
          </Route>

          {/* Offers */}
          <Route element={<RoleProtectedRoute allowedRoles={PERMISSIONS.OFFERS_VIEW}/>} >
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
