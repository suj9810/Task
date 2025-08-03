# Security 개발 과제

## 프로젝트 개요
Spring Boot와 Spring Security를 활용한 사용자 인증 및 권한 관리 시스템입니다. JWT 토큰 기반 인증과 역할 기반 접근 제어를 구현한 프로젝트입니다.

## 주요 기능

### 사용자 관리
- **회원가입**: 새로운 사용자 계정 생성
- **로그인**: JWT 토큰을 통한 사용자 인증
- **권한 관리**: USER/ADMIN 역할 기반 접근 제어

### 관리자 기능
- **사용자 권한 변경**: 관리자만 다른 사용자의 권한을 수정할 수 있음

### 보안 기능
- JWT 토큰 기반 인증
- BCrypt를 활용한 비밀번호 암호화
- Spring Security를 통한 인가 처리
- 통합된 예외 처리 시스템

## 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **Spring Security**
- **JWT (JJWT 0.12.3)**

### 데이터베이스
- **H2 Database** (개발/테스트용 인메모리 DB)

### 보안
- **BCrypt** (비밀번호 암호화)
- **JWT** (인증 토큰)

### 문서화
- **Swagger/OpenAPI 3** (API 문서화)

### 빌드 도구
- **Gradle**

### 테스트
- **JUnit 5**
- **Spring Boot Test**
- **Spring Security Test**

## 실행 방법

### 사전 요구사항
- Java 17 이상
- Gradle

### 실행 단계

1. **프로젝트 클론**
   ```bash
   git clone <repository-url>
   cd task
   ```

2. **의존성 설치 및 빌드**
   ```bash
   ./gradlew build
   ```

3. **애플리케이션 실행**
   ```bash
   ./gradlew bootRun
   ```

## 설계 구조

### 패키지 구조
```
com.example.task
├── common/                 # 공통 컴포넌트
│   ├── code/              # 응답 코드 정의
│   ├── config/            # 설정 클래스
│   ├── exception/         # 예외 처리
│   ├── jwt/              # JWT 관련 클래스
│   ├── response/         # 공통 응답 형식
│   └── util/             # 유틸리티
├── domain/
│   ├── user/             # 사용자 도메인
│   │   ├── controller/   # 사용자 컨트롤러
│   │   ├── dto/         # 데이터 전송 객체
│   │   ├── entity/      # 사용자 엔티티
│   │   ├── repository/  # 데이터 접근 계층
│   │   └── service/     # 비즈니스 로직
│   └── admin/           # 관리자 도메인
│       ├── controller/  # 관리자 컨트롤러
│       ├── dto/        # 데이터 전송 객체
│       └── service/    # 비즈니스 로직
└── TaskApplication.java # 메인 애플리케이션
```

### 아키텍처 특징
- **계층형 아키텍처**: Controller → Service → Repository
- **도메인 중심 설계**: 기능별 패키지 분리
- **공통 컴포넌트 분리**: 재사용 가능한 컴포넌트들을 common 패키지로 관리

## 주요 특징

### 보안
- JWT 토큰 기반 Stateless 인증
- 액세스 토큰: 1시간 만료
- 리프레시 토큰: 14일 만료
- 역할 기반 접근 제어 (USER/ADMIN)

### API 설계
- RESTful API 설계 원칙 준수
- 일관된 응답 형식 (`CommonResponse`)
- 상세한 에러 메시지와 코드 체계

### 코드 품질
- Lombok을 활용한 보일러플레이트 코드 제거
- 계층별 역할 분리로 유지보수성 향상
- 포괄적인 단위 테스트

## API 문서

### Swagger UI 접속
- **배포 환경**: http://15.164.134.153:8080/swagger-ui.html

### 사용자 API
- `POST /users/signup` - 회원가입
- `POST /users/login` - 로그인

### 관리자 API
- `PATCH /admin/users/{userId}/roles` - 사용자 권한 변경 (ADMIN 권한 필요)

### 인증
모든 보호된 엔드포인트는 Authorization 헤더에 Bearer 토큰이 필요
```
Authorization: Bearer <JWT_TOKEN>
```

### API 테스트 방법
1. Swagger UI에서 `/users/signup`으로 회원가입
2. `/users/login`으로 로그인하여 JWT 토큰 획득
3. Swagger UI 상단의 "Authorize" 버튼 클릭
4. `Bearer <JWT_TOKEN>` 형식으로 토큰 입력
5. 인증이 필요한 API 테스트 가능

## 개발 도구

### IDE 설정
- IntelliJ IDEA 또는 Eclipse 권장
- Lombok 플러그인 설치 필요

### 테스트 실행
```bash
./gradlew test
```

### 코드 스타일
- Java 17 기준
- Spring Boot Best Practices 준수
- 패키지별 책임 분리 원칙

### 환경 설정
- H2 데이터베이스 설정
- JWT secretKey 및 토큰 만료 시간
- 로깅 레벨 설정