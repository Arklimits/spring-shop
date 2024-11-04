# Spring Shop Project

**Spring Shop**은 최신 웹 기술을 활용하여 전자 상거래 플랫폼의 백엔드 로직을 구현한 학습용 프로젝트로, 실제 상용 환경을 고려한 설계와 다양한 기능을 갖춘 웹
애플리케이션입니다. 이 프로젝트는 상품 관리, 주문 처리, 사용자 인증, 게시글 작성 등의 전자 상거래 핵심 기능을 포괄하며, 클라우드 서비스를 연동하여 실제 배포 환경을 가정한
데이터 관리 및 보안 기능을 구현했습니다.

---

### 📅 개발 기간

개발 기간 정보가 필요하다면 여기에 추가하세요 (예: 2024.01.01 ~ 2024.02.01).

### 🔧 서비스 구조도

---

### 📚 기술 스택

- **백엔드**: `Spring Boot`, `JPA`, `Azure MySQL`
- **프론트엔드**: `Thymeleaf`
- **보안**: `JWT (JSON Web Token)`
- **클라우드 통합**: `AWS S3`

---

### 📝 프로젝트 목표

- **API Layer와 Web Layer 분리**: RESTful API를 제공하는 `@RestController`와 웹 페이지 렌더링을 담당하는 `@Controller`를
  분리하여, API 로직과 웹 뷰 로직의 명확한 역할 분리를 이루었습니다. 이를 통해 클라이언트 특성에 따른 유연한 대응이 가능하며, 유지보수 및 테스트의 효율성이 크게
  향상됩니다.
- **RESTful API 구현**: 클린 코드와 테스트 가능한 API 개발을 지향하여, 가독성 높은 구조와 함께 손쉽게 유지보수할 수 있는 백엔드 시스템을 구축했습니다.
- **클라우드 서비스 통합**: **Azure MySQL** 데이터베이스와 **AWS S3** 스토리지를 연동하여, 실제 배포 환경과 동일한 조건을 가정한 데이터 관리와 이미지
  파일 업로드 기능을 구현했습니다. 이를 통해 데이터의 영속성과 파일 관리의 신뢰성을 높였습니다.
- **JWT 인증 강화**: JSON Web Token을 활용해 사용자 인증을 수행하며, HTTP-Only 쿠키에 토큰을 저장해 클라이언트와 서버 간 안전한 인증 상태를
  유지합니다. 보안성이 강화된 인증 시스템을 통해 사용자 데이터를 안전하게 보호합니다.
- **에러 처리**: 예외 상황에 대한 철저한 처리 로직을 통해, 사용자에게는 직관적인 오류 메시지를 제공하고, 서버 측에서는 로깅을 통해 에러의 효율적인 추적 및 디버깅이
  가능하도록 설계되었습니다.

---

### 🌐 프로젝트 링크

* [Spring Shop 프로젝트 배포 링크](http://ark-test.ap-northeast-2.elasticbeanstalk.com/)

---

### 주요 도메인 설명

- **Member (회원 관리)**: 사용자 등록, 로그인 및 JWT를 통한 인증 기능을 담당하며, 강력한 보안 로직으로 안전한 인증 환경을 제공합니다.
- **Order (주문 관리)**: 사용자 주문 및 관리 로직을 구현하며, 복잡한 주문 처리 및 상태 관리를 위한 백엔드 기능을 포함하고 있습니다.
- **Item (상품 관리)**: 상품 정보의 저장 및 조회를 지원하며, AWS S3의 Presigned URL을 사용해 상품 이미지 파일의 안전한 업로드 및 조회 기능을
  제공합니다.
- **Comment (상품 후기)**: 사용자 상품 리뷰 기능을 담당하여, 쇼핑 경험에 있어 필수적인 후기 작성 및 관리 기능을 구현합니다.
- **Post (게시글 작성)**: 주로 공지사항 등의 게시글을 작성하는 기능을 제공하여, 사용자에게 중요한 정보를 전달할 수 있는 웹사이트의 기본 커뮤니케이션 기능을
  담당합니다.

---
