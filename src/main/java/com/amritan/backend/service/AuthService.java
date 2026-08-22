package com.amritan.backend.service;

import com.amritan.backend.dto.LoginRequest;
import com.amritan.backend.dto.LoginResponse;

public interface AuthService {

	LoginResponse login(LoginRequest request);
}
