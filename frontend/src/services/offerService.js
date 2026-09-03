import { apiRequest } from "./api";

export const createOffer = async (offerData) => {
  return apiRequest("/api/offers", {
    method: "POST",
    body: JSON.stringify(offerData),
  });
};

export const getAllOffers = async (
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

  return apiRequest(`/api/offers?${params}`);
};

export const getOfferById = async (id) => {
  return apiRequest(`/api/offers/${id}`);
};

export const updateOffer = async (id, offerData) => {
  return apiRequest(`/api/offers/${id}`, {
    method: "PUT",
    body: JSON.stringify(offerData),
  });
};

export const deleteOffer = async (id) => {
  return apiRequest(`/api/offers/${id}`, {
    method: "DELETE",
  });
};

export const getOffersBySalary = async (salary, pageNo = 0, pageSize = 10) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(`/api/offers/salary/${salary}?${params}`);
};

export const getOffersByCandidateName = async (
  candidateName,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/offers/candidateName/${encodeURIComponent(candidateName)}?${params}`,
  );
};

export const getOffersByJobTitle = async (
  jobTitle,
  pageNo = 0,
  pageSize = 10,
) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/offers/jobTitle/${encodeURIComponent(jobTitle)}?${params}`,
  );
};

export const getOffersByStatus = async (status, pageNo = 0, pageSize = 10) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/offers/status/${encodeURIComponent(status)}?${params}`,
  );
};
