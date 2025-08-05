package com.finday.backend.user.controller;

import com.finday.backend.user.dto.LoginRequestDTO;
import com.finday.backend.user.dto.UserSignupRequest;
import com.finday.backend.user.entity.UserEntity;
import com.finday.backend.user.service.UserService;
import com.finday.backend.user.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // ✅ 추가
public class UserAPIController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            UserEntity user = userService.tryLogin(loginRequestDTO);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/auth/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> signup(
            @RequestPart("data") UserSignupRequest request,
            @RequestPart(value = "faceImage", required = false) MultipartFile faceImage) {

        request.setFaceImage(faceImage);  // 수동 설정
        userService.registerUser(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/auth/final-login")
    public ResponseEntity<String> finalLogin(@RequestBody LoginRequestDTO dto) {
        try {
            // 1. 이메일로 유저 조회
            UserEntity user = userService.findByEmail(dto.getEmail());

            if (user == null) {
                return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
            }

            // 2. JWT 토큰 발급
            String token = jwtUtil.generateToken(user.getUserNo());

            // 3. 토큰 반환
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("로그인 처리 중 오류 발생");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpServletRequest request) {
        Long userNo = (Long) request.getAttribute("userNo");
        System.out.println("userNo in controller : " + userNo);
        if (userNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        UserEntity user = userService.findByNo(userNo);
        return ResponseEntity.ok(user);
    }

}