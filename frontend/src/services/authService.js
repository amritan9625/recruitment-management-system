import { apiRequest } from "./api";

export const login = async (credentials) => {
    return apiRequest("/api/auth/login", {
        method: "POST",
        body: JSON.stringify(credentials),
    });
};

export const register = async (userData) => {
    return apiRequest("/api/auth/register", {
        method: "POST",
        body: JSON.stringify(userData),
    });
};