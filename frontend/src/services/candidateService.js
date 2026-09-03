import { apiRequest } from "./api";

export const createCandidate = async (candidateData) => {
  return apiRequest("/api/candidates", {
    method: "POST",
    body: JSON.stringify(candidateData),
  });
};

export const getAllCandidates = async (
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

  return apiRequest(`/api/candidates?${params}`);
};

export const getCandidateById = async (id) => {
  return apiRequest(`/api/candidates/${id}`);
};

export const updateCandidate = async (id, candidateData) => {
  return apiRequest(`/api/candidates/${id}`, {
    method: "PUT",
    body: JSON.stringify(candidateData),
  });
};

export const deleteCandidate = async (id) => {
  return apiRequest(`/api/candidates/${id}`, {
    method: "DELETE",
  });
};

export const searchCandidates = async (
  candidateName,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/candidates/candidateName/${encodeURIComponent(candidateName)}?${params}`,
  );
};

export const getCandidatesByStatus = async (
  status,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/candidates/status/${encodeURIComponent(status)}?${params}`,
  );
};
