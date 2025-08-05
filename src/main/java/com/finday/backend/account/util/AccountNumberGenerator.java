package com.finday.backend.account.util;

import java.util.Random;

public class AccountNumberGenerator {

    private static final Random random = new Random();

    public static String generateAccountNumber(String bankName) {
        switch (bankName) {

            case "국민은행":
                // XXXXYY-ZZ-ZZZZZC (14자리)
                return String.format("%06d-%02d-%06d", rand(1000000), rand(100), rand(1000000));

            case "신한은행":
                // YYY-ZZ-ZZZZZZ-C (12자리)
                return String.format("%03d-%02d-%06d-%01d", rand(1000), rand(100), rand(1000000), rand(10));

            case "하나은행":
                // XXX-BBBBBB-YY-ZZC (14자리)
                return String.format("%03d-%06d-%02d-%03d", rand(1000), rand(1000000), rand(100), rand(1000));

            case "우리은행":
                // SYYY-CZZ-ZZZZZZ (13자리)
                return String.format("%04d-%03d-%06d", rand(10000), rand(1000), rand(1000000));

            case "농협은행":
                // YYY-ZZZZ-ZZZZ-CC (13자리)
                return String.format("%03d-%04d-%04d-%02d", rand(1000), rand(10000), rand(10000), rand(100));

            case "SC제일은행":
                // XXX-YY-ZZZZZ-C (11자리)
                return String.format("%03d-%02d-%05d-%01d", rand(1000), rand(100), rand(100000), rand(10));

            case "카카오뱅크":
                // 16자리 또는 15자리 (3333-YYYY-ZZZZ-WWWW)
                return String.format("3333-%04d-%04d-%04d", rand(10000), rand(10000), rand(10000));

            case "케이뱅크":
                // YYY-NNN-ZZZZZZ (12자리)
                return String.format("%03d-%03d-%06d", rand(1000), rand(1000), rand(1000000));

            case "토스뱅크":
                // 4-4-4
                return String.format("%04d-%04d-%04d", rand(10000), rand(10000), rand(10000));

            case "부산은행":
                // YYY-ZZZZ-ZZZZ-ZC (13자리)
                return String.format("%03d-%04d-%04d-%02d", rand(1000), rand(10000), rand(10000), rand(100));

            case "대구은행":
                // YYY-ZZ-ZZZZZZ-C (12자리)
                return String.format("%03d-%02d-%06d-%01d", rand(1000), rand(100), rand(1000000), rand(10));

            case "광주은행":
                // YYY-ZZZZ-ZZZZ-CT (13자리)
                return String.format("%03d-%04d-%04d-%02d", rand(1000), rand(10000), rand(10000), rand(100));

            case "전북은행":
                // YYY-ZZZZ-ZZZZ-ZC (13자리)
                return String.format("%03d-%04d-%04d-%02d", rand(1000), rand(10000), rand(10000), rand(100));

            case "제주은행":
                // XX-YY-ZZZZZZ (10자리)
                return String.format("%02d-%02d-%06d", rand(100), rand(100), rand(1000000));

            default:
                // 기본형: 3-8 (예: 012-12345678)
                return String.format("%03d-%08d", rand(1000), rand(100000000));
        }
    }

    private static int rand(int bound) {
        return random.nextInt(bound);
    }
}
