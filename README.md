# 🏦 Finday Backend

> **Finday**는 안면인증 기반의 통합 핀테크 시뮬레이션 플랫폼입니다.  
본 레포는 사용자 인증, 계좌 연동, 카드 조회, 거래 생성 및 소비 분석 기능을 담당하는 **백엔드 서버**입니다.

---

## 🔍 프로젝트 소개

- 안면인증 기반 로그인 시스템
- 사용자가 직접 은행을 선택하여 계좌 및 카드를 연동
- 거래 내역 생성 및 통합 조회
- 소비 분석 차트 및 이상 거래 탐지 기능 포함
- **MSA 구조 기반**으로, KFTC Gateway 및 은행 API와 REST 통신

---

## 🧩 역할 및 아키텍처

Finday 백엔드는 MSA 아키텍처 내에서 **중앙 사용자 서비스 서버** 역할을 하며, 다음과 같은 흐름을 가집니다:

사용자
↓
[Finday Backend]
↓
[KFTC Gateway 서버]
↓
[각 은행 서버들 (국민, 신한, 토스 등)]

---

## 🛠️ Tech Stack

| 분류 | 기술 | 설명 |
|------|------|------|
| **Language** | Java 17 | 안정적인 JVM 기반 언어 |
| **Framework** | Spring Boot 3 | RESTful API 백엔드 프레임워크 |
| **Build Tool** | Gradle | 프로젝트 빌드/의존성 관리 |
| **ORM** | Spring Data JPA | DB 테이블과 객체 매핑 |
| **Database** | MySQL | 주요 데이터 저장소 |
| **WebClient** | API 연동용 비동기 HTTP 클라이언트 |
| **API 문서화** | SpringDoc OpenAPI (Swagger UI) |
| **Validation** | Bean Validation | 사용자 입력 검증 |
| **JWT** | 인증 및 세션 관리 |
| **AWS S3** | 안면 이미지 저장소 |
| **Test** | Spring Boot Test, JUnit |

---

## 📌 주요 기능

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

## 📂 프로젝트 구조src

├── controller # API 엔드포인트
├── service # 핵심 비즈니스 로직
├── repository # DB 접근 (JPA)
├── entity # DB 테이블 매핑 엔티티
├── dto # 요청/응답 데이터 구조
├── client # WebClient 기반 외부 API 연동
└── util # JWT, S3 등 유틸성 클래스

---

## ⚙️ 설정 방법

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

## 🔗 관련 레포지토리

| 서비스 | 레포지토리 |
|--------|-------------|
| 🌐 Finday 프론트엔드 | [`finday-frontend`](https://github.com/your-username/finday-frontend) |
| 🧠 Finday 백엔드 | [`finday-backend`](https://github.com/your-username/finday-backend) |
| 💳 국민은행 서버 | [`finday-bank-kb`](https://github.com/your-username/finday-bank-kb) |
| 💳 신한은행 서버 | [`finday-bank-shinhan`](https://github.com/your-username/finday-bank-shinhan) |

