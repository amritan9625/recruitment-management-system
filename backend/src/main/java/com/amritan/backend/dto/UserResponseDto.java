package com.amritan.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

	private Long id;
    private String name;
    private String email;
    private String phone;
    private Long roleId;
}
