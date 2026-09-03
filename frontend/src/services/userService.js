import { apiRequest } from "./api";

export const createUser = async (userData) => {
  return apiRequest("/api/users", {
    method: "POST",
    body: JSON.stringify(userData),
  });
};

export const getAllUsers = async (
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

  return apiRequest(`/api/users?${params}`);
};

export const getUserById = async (id) => {
  return apiRequest(`/api/users/${id}`);
};

export const updateUser = async (id, userData) => {
  return apiRequest(`/api/users/${id}`, {
    method: "PUT",
    body: JSON.stringify(userData),
  });
};

export const deleteUser = async (id) => {
  return apiRequest(`/api/users/${id}`, {
    method: "DELETE",
  });
};

export const getUsersByName = async (name, pageNo = 0, pageSize = 10) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(`/api/users/name/${encodeURIComponent(name)}?${params}`);
};

export const getUsersByEmail = async (email, pageNo = 0, pageSize = 10) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(`/api/users/email/${encodeURIComponent(email)}?${params}`);
};
