# Spring Shop Project

Spring Shop은 Spring Boot를 활용한 전자 상거래 플랫폼의 연습 프로젝트로, 핵심 도메인은 주문, 상품, 회원, 게시글 등 으로 구성되어 있습니다. 이 프로젝트는
실전처럼 백엔드 로직을 구현하고, 사용자 인증과 파일 업로드 같은 기능을 연습하기 위해 만들었습니다.

### 프로젝트 목표

- **RESTful API 구현**: 클린 코드와 테스트 가능한 API 개발을 목표로 하고 있습니다.
- **클라우드 통합**: Azure MySQL과 AWS S3를 활용하여 실제 배포 환경을 가정한 데이터 관리 및 파일 업로드 기능을 포함합니다.
- **JWT 인증**: JWT를 활용하여 사용자 인증을 수행하며, 보안성을 위해 JWT 토큰을 HTTP-Only 쿠키에 저장합니다. 이를 통해 클라이언트와 서버 간의 통신에서 토큰을 안전하게 관리하며, 쿠키에 저장된 JWT는 서버 측에서 검증되어 사용자의 인증 상태를 유지합니다.
### Link

* [http://ark-test.ap-northeast-2.elasticbeanstalk.com/](http://ark-test.ap-northeast-2.elasticbeanstalk.com/)

### 기술 스택

- **Backend**: `Spring Boot`, `JPA`, `Azure MySQL`
- **Frontend**: `Thymeleaf`
- **Security**: `JWT (JSON Web Token)`
- **Cloud Integration**: `AWS S3`

### 주요 도메인 설명

- **Member**: 회원가입 및 사용자 인증 기능을 담당하며, JWT 인증을 통한 보안 로직을 포함.
- **Order**: 사용자가 상품을 주문하고 관리하는 시스템으로, 복잡한 주문 처리 로직을 구현.
- **Item**: 상품 정보를 저장 및 조회하고, AWS S3와 연동하여 Presigned URL을 사용한 이미지 파일 저장 및 조회 기능 제공.
- **Comment**: 상품에 대한 후기를 작성하는 기능을 담당.
- **Post**: 게시글 작성 기능.
