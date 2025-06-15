package com.finday.backend.user.service;

import com.finday.backend.user.dto.LoginRequestDTO;
import com.finday.backend.user.entity.UserEntity;
import com.finday.backend.user.repository.UserRepository;
import com.finday.backend.user.util.HashUtil;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity tryLogin(LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.getEmail();
        String plainPassword = loginRequestDTO.getPassword();
        String hashed = HashUtil.sha256(plainPassword);

        UserEntity user = (UserEntity) userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        System.out.println("변환된 해시 : " + hashed);
        System.out.println("DB에 저장된 해시 : " + user.getPasswordHash());

        if (!user.getPasswordHash().equals(hashed)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user; // 로그인 성공
    }
}
