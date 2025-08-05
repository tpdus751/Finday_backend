package com.finday.backend.user.service;

import com.finday.backend.user.dto.LoginRequestDTO;
import com.finday.backend.user.dto.UserSignupRequest;
import com.finday.backend.user.entity.UserEntity;

public interface UserService {

    UserEntity tryLogin(LoginRequestDTO loginRequestDTO);

    Long getUserNoByUserSpecificNo(String userSpecificNo);

    void registerUser(UserSignupRequest request);

    UserEntity findByEmail(String email);

    UserEntity findByNo(Long userNo);
}