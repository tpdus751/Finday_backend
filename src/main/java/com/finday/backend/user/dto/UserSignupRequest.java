package com.finday.backend.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@Data
public class UserSignupRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    private String gender;

    private LocalDate birthDate;

    @NotBlank
    private String phoneNumber;

    private String address;

    @JsonIgnore
    private MultipartFile faceImage;
}
