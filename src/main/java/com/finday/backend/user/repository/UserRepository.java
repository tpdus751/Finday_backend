package com.finday.backend.user.repository;

import com.finday.backend.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT user_no FROM users WHERE user_specific_no = :userSpecificNo", nativeQuery = true)
    Long findByUserNoByUserSpecificNo(@Param("userSpecificNo") String userSpecificNo);

    Optional<UserEntity> findByUserNo(Long userNo);
}