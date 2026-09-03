import { apiRequest } from "./api";

export const createJob = async (jobData) => {
  return apiRequest("/api/jobs", {
    method: "POST",
    body: JSON.stringify(jobData),
  });
};

export const getAllJobs = async (
  pageNo = 0,
  pageSize = 10,
  sortBy = "id",
  sortDir = "asc",
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
    sortBy,
    sortDir,
  });

  return apiRequest(`/api/jobs?${params}`);
};

export const getJobById = async (id) => {
  return apiRequest(`/api/jobs/${id}`);
};

export const updateJob = async (id, jobData) => {
  return apiRequest(`/api/jobs/${id}`, {
    method: "PUT",
    body: JSON.stringify(jobData),
  });
};

export const deleteJob = async (id) => {
  return apiRequest(`/api/jobs/${id}`, {
    method: "DELETE",
  });
};

export const getJobsByStatus = async (status, pageNo = 0, pageSize = 10) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(`/api/jobs/status/${encodeURIComponent(status)}?${params}`);
};

export const getJobsByLocation = async (
  location,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/jobs/location/${encodeURIComponent(location)}?${params}`,
  );
};
