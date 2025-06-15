package com.finday.backend.user.service;

import com.finday.backend.user.dto.LoginRequestDTO;
import com.finday.backend.user.entity.UserEntity;

public interface UserService {

    UserEntity tryLogin(LoginRequestDTO loginRequestDTO);
}
