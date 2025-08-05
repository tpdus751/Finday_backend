package com.finday.backend.user.service;

import com.finday.backend.user.dto.LoginRequestDTO;
import com.finday.backend.user.dto.UserSignupRequest;
import com.finday.backend.user.entity.UserEntity;
import com.finday.backend.user.repository.UserRepository;
import com.finday.backend.user.util.HashUtil;
import com.finday.backend.user.util.S3Uploader;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public UserServiceImpl(UserRepository userRepository, S3Uploader s3Uploader) {
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;
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

    @Override
    public Long getUserNoByUserSpecificNo(String userSpecificNo) {
        return userRepository.findByUserNoByUserSpecificNo(userSpecificNo);
    }

    @Override
    public void registerUser(UserSignupRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        UserEntity user = new UserEntity();
        user.setEmail(req.getEmail());
        user.setPasswordHash(HashUtil.sha256(req.getPassword()));
        user.setName(req.getName());
        user.setGender(UserEntity.Gender.valueOf(req.getGender()));
        user.setBirthDate(req.getBirthDate());
        user.setPhoneNumber(req.getPhoneNumber());

        // 생년월일-성별 암호화
        LocalDate birthDate = req.getBirthDate(); // 예: 1999-06-05
        String birthPart = birthDate.format(DateTimeFormatter.ofPattern("yyMMdd")); // → "990605"

        String genderDigit;
        if (birthDate.getYear() < 2000) {
            genderDigit = req.getGender().equals("M") ? "1" : "2";
        } else {
            genderDigit = req.getGender().equals("M") ? "3" : "4";
        }

        String rawSpecific = birthPart + "-" + genderDigit;
        String hashedSpecific = HashUtil.sha256(rawSpecific);

        user.setUserSpecificNo(hashedSpecific);
        // 여기까지

        user.setAddress(req.getAddress());

        if (req.getFaceImage() != null && !req.getFaceImage().isEmpty()) {
            String imgUrl = s3Uploader.uploadFile(req.getFaceImage(), "faces");
            user.setFaceImgUrl(imgUrl);
        }

        user.setStatus(UserEntity.Status.ACTIVE);

        userRepository.save(user);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public UserEntity findByNo(Long userNo) {
        return userRepository.findByUserNo(userNo)
                .orElse(null);
    }
}