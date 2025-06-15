package com.finday.backend.account.service;

import com.finday.backend.account.dto.AccountCreateRequestDTO;
import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.entity.AccountEntity;
import com.finday.backend.account.entity.UserCardEntity;
import com.finday.backend.account.repository.AccountRepository;
import com.finday.backend.account.repository.UserCardRepository;
import com.finday.backend.account.util.AccountNumberGenerator;
import com.finday.backend.card.entity.CardInfoEntity;
import com.finday.backend.user.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserCardRepository userCardRepository;

    public List<AccountDTO> findAllAccountsByUserNo(Long userNo) {
        List<AccountEntity> accounts = accountRepository.findByUserNo(userNo);
        return accounts.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createAccount(AccountCreateRequestDTO accountCreateRequestDTO) {

        AccountEntity account = new AccountEntity();
        account.setUserNo(accountCreateRequestDTO.getUserNo());
        account.setBankName(accountCreateRequestDTO.getBankName());
        account.setAlias(accountCreateRequestDTO.getAlias());
        account.setLimitAmount(accountCreateRequestDTO.getAccountLimit());
        account.setBalance(0L);
        account.setStatus("ACTIVE");
        account.setCreatedAt(LocalDateTime.now());

        int retry = 0;
        AccountEntity returnedAccount = null;

        while (retry < 5) {
            String accountNumber = AccountNumberGenerator.generateAccountNumber(account.getBankName());
            account.setAccountNumber(accountNumber);

            try {
                returnedAccount = accountRepository.save(account); // ✅ 저장 성공하면 반환값 받기
                break;
            } catch (DataIntegrityViolationException e) {
                retry++;
                if (retry == 5) throw new RuntimeException("계좌번호 생성 실패 (중복)");
            }
        }

        if (accountCreateRequestDTO.isCardRequested()) {
            UserCardEntity userCardEntity = UserCardEntity.builder()
                    .userNo(accountCreateRequestDTO.getUserNo())
                    .accountNo(returnedAccount.getAccountNo())
                    .cardNo(accountCreateRequestDTO.getCardNo())
                    .issuedDate(LocalDateTime.now())
                    .build();

            userCardRepository.save(userCardEntity);
        }
    }

    private AccountDTO toDto(AccountEntity entity) {
        return AccountDTO.builder()
                .accountNo(entity.getAccountNo())
                .bankName(entity.getBankName())
                .accountNumber(entity.getAccountNumber())
                .alias(entity.getAlias())
                .balance(entity.getBalance())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
