import { apiRequest } from "./api";

export const getDashboard = async () => {
  return apiRequest("/api/dashboard");
};

export const getRecentCandidates = async () => {
  return apiRequest("/api/dashboard/recent-candidates");
};

export const getRecentJobs = async () => {
  return apiRequest("/api/dashboard/recent-jobs");
};

export const getRecentApplications = async () => {
  return apiRequest("/api/dashboard/recent-applications");
};
