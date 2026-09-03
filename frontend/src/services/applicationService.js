import { apiRequest } from "./api";

export const createApplication = async (applicationData) => {
  return apiRequest("/api/applications", {
    method: "POST",
    body: JSON.stringify(applicationData),
  });
};

export const getAllApplications = async (
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

  return apiRequest(`/api/applications?${params}`);
};

export const getApplicationById = async (id) => {
  return apiRequest(`/api/applications/${id}`);
};

export const updateApplication = async (id, applicationData) => {
  return apiRequest(`/api/applications/${id}`, {
    method: "PUT",
    body: JSON.stringify(applicationData),
  });
};

export const deleteApplication = async (id) => {
  return apiRequest(`/api/applications/${id}`, {
    method: "DELETE",
  });
};

export const updateApplicationStatus = async (id, status) => {
  return apiRequest(`/api/applications/${id}/${encodeURIComponent(status)}`, {
    method: "PUT",
  });
};

export const getApplicationsByCandidateId = async (
  candidateId,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(`/api/applications/candidate/${candidateId}?${params}`);
};

export const getApplicationsByJobId = async (
  jobId,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(`/api/applications/job/${jobId}?${params}`);
};

export const getApplicationsByStatus = async (
  status,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/applications/status/${encodeURIComponent(status)}?${params}`,
  );
};

export const getApplicationsByCandidateName = async (
  candidateName,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/applications/candidateName/${encodeURIComponent(candidateName)}?${params}`,
  );
};

export const getApplicationsByJobTitle = async (
  jobTitle,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/applications/jobTitle/${encodeURIComponent(jobTitle)}?${params}`,
  );
};
