import { apiRequest } from "./api";

export const createInterview = async (interviewData) => {
  return apiRequest("/api/interviews", {
    method: "POST",
    body: JSON.stringify(interviewData),
  });
};

export const getAllInterviews = async (
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

  return apiRequest(`/api/interviews?${params}`);
};

export const getInterviewById = async (id) => {
  return apiRequest(`/api/interviews/${id}`);
};

export const updateInterview = async (id, interviewData) => {
  return apiRequest(`/api/interviews/${id}`, {
    method: "PUT",
    body: JSON.stringify(interviewData),
  });
};

export const deleteInterview = async (id) => {
  return apiRequest(`/api/interviews/${id}`, {
    method: "DELETE",
  });
};

export const getInterviewsByStatus = async (
  status,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/interviews/status/${encodeURIComponent(status)}?${params}`,
  );
};

export const searchInterviewsByInterviewer = async (
  keyword,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/interviews/interviewer/${encodeURIComponent(keyword)}?${params}`,
  );
};
