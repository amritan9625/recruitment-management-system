import { apiRequest } from "./api";

export const createRole = async (roleData) => {
  return apiRequest("/api/roles", {
    method: "POST",
    body: JSON.stringify(roleData),
  });
};

export const getAllRoles = async (
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

  return apiRequest(`/api/roles?${params}`);
};

export const getRoleById = async (id) => {
  return apiRequest(`/api/roles/${id}`);
};

export const updateRole = async (id, roleData) => {
  return apiRequest(`/api/roles/${id}`, {
    method: "PUT",
    body: JSON.stringify(roleData),
  });
};

export const deleteRole = async (id) => {
  return apiRequest(`/api/roles/${id}`, {
    method: "DELETE",
  });
};

export const getRolesByName = async (roleName, pageNo = 0, pageSize = 10) => {
  const params = new URLSearchParams({
    pageNo,
    pageSize,
  });

  return apiRequest(
    `/api/roles/roleName/${encodeURIComponent(roleName)}?${params}`,
  );
};
