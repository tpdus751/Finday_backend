# Finday 백엔드 서버

> **Finday**는 안면인증 기반의 통합 핀테크 시뮬레이션 플랫폼입니다.  
본 레포는 사용자 인증, 계좌 연동, 카드 조회, 계좌 이체, 거래 생성(결제) 및 소비 분석 기능(거래 비율)을 담당하는 **백엔드 서버**입니다.

---

## 아키텍처

Finday 백엔드는 MSA 아키텍처 내에서 **중앙 사용자 서비스 서버** 역할을 하며, 다음과 같은 흐름을 가집니다:

<img width="1320" height="651" alt="image" src="https://github.com/user-attachments/assets/577b5f99-fbdc-4f23-b6f6-770c2b73138c" />

```
사용자
↓
[Finday Backend]
↓
[KFTC Gateway 서버]
↓
[각 은행 서버들 (국민, 신한, 토스 등)]
```

---

## 패키지 구조

Finday 백엔드 서버는 도메인 기반의 패키지 구조를 따르며, 각 도메인이 명확한 역할을 담당합니다.

| 패키지 | 설명 | 비고 |
|--------|------|------|
| `user` | 회원가입, 로그인, 얼굴 인증 등 사용자 계정 관련 요청/응답 처리 | |
| `account` | 계좌 목록 조회, 이체 요청 등 사용자 계좌 기능 처리 | |
| `bank` | Finday와 협약된 전체 은행 목록 및 사용자 연동 은행 정보 처리 | |
| `card` | 사용자가 연동한 카드 목록 조회 기능 처리 | |
| `item` | 거래 생성을 위한 소비 항목(아이템) 목록 조회 기능 처리 | |
| `transaction` | 거래 생성, 거래 내역 조회, 소비 분석 등 거래 관련 기능 처리 | |
| `client` | `WebClient`를 사용하여 KFTC Gateway 서버와의 API 통신 처리 (계좌/이체/카드 등) | 
| `config` | 전역 설정 관련 패키지. `SecurityConfig`를 통해 다음을 설정: | - CORS 정책 (localhost:3000 허용)  
- JWT 인증 필터(`JwtAuthenticationFilter`) 등록  
- 인증 없이 접근 가능한 경로: `/api/user/auth/**`, `/img/**`  
- 그 외 모든 요청은 인증 필요  
- 세션은 `STATELESS`로 설정 (JWT 기반) |
| `security` | 보안 관련 필터 정의 |
  - `JwtAuthenticationFilter`: 모든 요청에 대해 JWT 유효성을 검사하고, 유효한 경우 `userNo`를 인증 객체로 등록  
  - `Authorization` 헤더가 없거나 잘못된 경우 필터를 통과시키되 인증 없이 처리  
  - 유효한 토큰이 존재하면 `SecurityContextHolder`에 인증 정보 저장하여 이후 컨트롤러에서 인증 정보 활용 가능 |

---

## 패키지 내부 구조

├── controller # API 엔드포인트
├── service # 핵심 비즈니스 로직
├── repository # DB 접근 (JPA)
├── entity # DB 테이블 매핑 엔티티
├── dto # 요청/응답 데이터 구조
└── util # Hash 생성, JWT, S3 등 유틸성 클래스

---

##  Tech Stack

| 분류 | 기술 | 설명 |
|------|------|------|
| **Language** | Java 17 | 안정성과 최신 문법을 모두 갖춘 JVM 기반 언어 |
| **Framework** | Spring Boot 3.4.4 | REST API 개발에 최적화된 백엔드 프레임워크 |
| **Build Tool** | Gradle | 의존성 관리 및 빌드 자동화를 위한 도구 |
| **Web** | Spring Web / WebFlux | 동기 + 비동기 웹 애플리케이션 지원 |
| **Security** | Spring Security | 로그인, 인증, JWT 필터링 처리 |
| **JWT** | JJWT (io.jsonwebtoken) | JWT 발급 및 검증을 위한 경량 라이브러리 |
| **API 문서화** | SpringDoc OpenAPI 3 (Swagger UI) | Swagger 기반 API 명세 자동 생성 |
| **ORM** | Spring Data JPA | 객체-관계 매핑 및 쿼리 메서드 지원 |
| **Database** | MySQL 8.x | 주요 데이터 저장소 |
| **AWS** | AWS SDK v2 (S3, Auth, Regions) | 안면 이미지 저장 및 AWS 리소스 연동 |
| **Lombok** | Lombok 1.18.30 | Getter/Setter/Builder 등 반복 코드 제거 |
| **Test** | JUnit 5 + Spring Security Test | 단위/인증 테스트 지원 |

---

## 주요 기능

| 기능 | 설명 |
|------|------|
| 회원가입 / 로그인 | 이메일+비밀번호, 얼굴인증 포함 |
| 계좌 연동 | 각 은행 계좌 선택 후 연동 |
| 카드 조회 및 신청 | 연동된 은행 카드 목록 조회 |
| 거래 생성 | 핀데이 페이 기반 수동 거래 생성 |
| 계좌 이체 | 중계서버 통해 출금 → 입금 처리 |
| 거래 내역 조회 | 월별 / 30일 기준 통합 거래 조회 |
| 소비 분석 | 카테고리별 지출 비율 시각화 |
| 자산 통합 조회 | 사용자 총 자산 정보 계산 |
| 이상 거래 탐지 | 비정상 패턴 탐지 및 알림 제공 |

---

## ⚙설정 방법

- 민감 정보는 `.gitignore`로 보호되어 있으며, 아래 예시 파일을 참고해 직접 구성해야 합니다.

```yaml
# src/main/resources/application-example.yaml

spring:
  datasource:
    url: jdbc:mysql://your-db-url
    username: your-username
    password: your-password

kftc:
  api:
    url: http://localhost:8085

cloud:
  aws:
    credentials:
      access-key: your-access-key
      secret-key: your-secret-key
    region:
      static: ap-northeast-2
    s3:
      bucket: your-bucket

jwt:
  secret: your-jwt-secret
```

## 관련 레포지토리

| 서비스 | 레포지토리 |
|--------|-------------|
| 🌐 Finday 프론트엔드 | [`finday-frontend`](https://github.com/your-username/finday-frontend) |
| 🧠 Finday 백엔드 | [`finday-backend`](https://github.com/your-username/finday-backend) |
| 💳 국민은행 서버 | [`finday-bank-kb`](https://github.com/your-username/finday-bank-kb) |
| 💳 신한은행 서버 | [`finday-bank-shinhan`](https://github.com/your-username/finday-bank-shinhan) |

