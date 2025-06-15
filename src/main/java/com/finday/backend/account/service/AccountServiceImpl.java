package com.finday.backend.account.service;

import com.finday.backend.account.dto.AccountCreateRequestDTO;
import com.finday.backend.account.dto.AccountDTO;
import com.finday.backend.account.dto.TransferRequestDTO;
import com.finday.backend.account.entity.AccountEntity;
import com.finday.backend.account.entity.UserCardEntity;
import com.finday.backend.account.repository.AccountRepository;
import com.finday.backend.account.repository.UserCardRepository;
import com.finday.backend.account.util.AccountNumberGenerator;
import com.finday.backend.card.entity.CardInfoEntity;
import com.finday.backend.user.entity.UserEntity;
import com.finday.backend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserCardRepository userCardRepository;
    private final UserRepository userRepository;

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

    @Override
    public AccountDTO getAccountByNo(Long accountNo) {
        AccountEntity account = accountRepository.findById(accountNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 계좌를 찾을 수 없습니다: " + accountNo));

        return AccountDTO.builder()
                .accountNo(account.getAccountNo())
                .bankName(account.getBankName())
                .accountNumber(account.getAccountNumber())
                .alias(account.getAlias())
                .balance(account.getBalance())
                .status(account.getStatus())
                .createdAt(account.getCreatedAt())
                .build();
    }

    @Override
    public String findAccountOwner(String bankName, String accountNumber) {
        AccountEntity presentAccount = accountRepository
                .findAccountByBankAndAccountNumber(bankName, accountNumber)
                .orElseThrow(() -> new NoSuchElementException("해당 계좌 정보를 찾을 수 없습니다."));

        Long userNo = presentAccount.getUserNo();

        UserEntity owner = userRepository.findById(userNo)
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다."));

        return owner.getName();
    }

    @Override
    public void transfer(TransferRequestDTO dto) {
        // 출금 계좌 조회
        AccountEntity from = accountRepository.findById(dto.getFromAccountNo())
                .orElseThrow(() -> new NoSuchElementException("출금 계좌를 찾을 수 없습니다"));

        // 수취 계좌 조회
        AccountEntity to = accountRepository.findAccountByBankAndAccountNumber(dto.getToBank(), dto.getToAccountNumber())
                .orElseThrow(() -> new NoSuchElementException("수취 계좌를 찾을 수 없습니다"));

        // 출금 가능 여부 확인
        if (from.getBalance() < dto.getAmount()) {
            throw new IllegalStateException("잔액이 부족합니다");
        }

        // 잔액 변경
        from.setBalance(from.getBalance() - dto.getAmount());
        to.setBalance(to.getBalance() + dto.getAmount());

        // 저장
        accountRepository.save(from);
        accountRepository.save(to);
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
