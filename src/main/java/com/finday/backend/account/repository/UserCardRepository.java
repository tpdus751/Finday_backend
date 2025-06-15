package com.finday.backend.account.repository;

import com.finday.backend.account.entity.UserCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCardRepository extends JpaRepository<UserCardEntity, Long> {
}
