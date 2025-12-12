# spring-sunshine-precourse

## 기능 목록

### 1. 도시 좌표 매핑
- 도시 이름을 위도/경도 좌표로 매핑하는 기능
- 지원 도시: Seoul, Tokyo, NewYork, Paris, London (최소 5개)

### 2. Open-Meteo API 연동
- Open-Meteo API를 호출하여 날씨 정보 조회
- 조회 항목: 현재 온도, 체감 온도, 하늘 상태(weather code), 습도

### 3. 하늘 상태 변환
- WMO weather code를 한국어 하늘 상태로 변환
- 예: 0 → 맑음, 3 → 흐림, 61 → 비

### 4. 날씨 요약 문장 생성
- 조회한 데이터를 기반으로 한 줄 요약 문장 생성
- 예: "현재 서울의 기온은 3.4°C이며, 체감 온도는 1.2°C입니다. 날씨는 흐림입니다."

### 5. REST API 엔드포인트
- GET /api/weather?city={도시명} 형태로 날씨 정보 조회 API 제공

---

## 구현 전략

### 1차 구현
1. `City` enum으로 도시 이름과 좌표를 매핑
2. `WeatherCodeTranslator`로 WMO 코드를 한국어로 변환
3. `WeatherService`에서 Open-Meteo API 호출 및 응답 파싱
4. `WeatherSummaryGenerator`로 요약 문장 생성
5. `WeatherController`에서 REST API 제공

### 2차 구현
1. 도시 좌표 매핑을 DB 테이블로 관리 (추가/수정/삭제 용이)
2. WMO weather code 매핑을 DB 테이블로 관리
3. Docker Compose로 MySQL 환경 구성
4. Flyway로 DB 마이그레이션 관리

---

## AI 활용 내역

Claude Code를 활용하여 대화형으로 프로젝트를 진행했습니다.

---

### 1차 작업

#### 진행 흐름

1. **과제 분석 및 계획 수립**
   - 과제 PDF 문서를 읽고 요구사항 파악
   - README.md에 기능 목록 및 구현 전략 작성

2. **외부 API 조사**
   - Open-Meteo API 문서를 웹에서 조회하여 필요한 파라미터 파악
   - WMO weather code 매핑 정보 조사

3. **기능 단위 구현**
   - 기능 목록 순서대로 구현 및 커밋
   - AngularJS 커밋 컨벤션에 맞춰 커밋 메시지 작성

4. **요구사항 검증 및 리팩토링**
   - 프로그래밍 요구사항 준수 여부 검증 요청
   - 위반 사항 발견 시 즉시 수정

5. **테스트 보완 및 구조 개선**
   - 부족한 테스트 케이스 추가
   - 코드 구조 개선 (예외 처리 분리 등)

#### AI 활용으로 수정한 내용

**프로그래밍 요구사항 준수**
- `WeatherService.buildWeatherResponse` 메서드가 15줄을 초과하여 `createSummary`, `createWeatherResponse`로 분리

**브라우저 호환성 이슈 해결**
- Safari에서 JSON 응답의 한글이 깨지는 문제 발생
- 원인: Spring Boot 3.x는 RFC 7159에 따라 JSON 응답에 charset을 명시하지 않음
- 해결: `application.yml`에 `server.servlet.encoding.force-response: true` 설정 추가

**테스트 코드 보완**
- 도메인 클래스 단위 테스트만 있던 상태에서 `WeatherControllerTest` 추가
- MockMvc를 활용한 컨트롤러 레이어 테스트

**예외 처리 구조 개선**
- `WeatherController`에 있던 `@ExceptionHandler`를 `GlobalExceptionHandler`로 분리
- `ErrorResponse` DTO로 공통 에러 응답 규격 정의

**프로젝트 지침 문서화**
- `CLAUDE.md` 파일 생성하여 프로그래밍 요구사항 및 커밋 컨벤션 정리
- 다음 세션에서도 동일한 규칙 적용 가능하도록 문서화

---

### 2차 작업

#### 진행 흐름

1. **DB 환경 구성**
   - Docker Compose로 MySQL 컨테이너 설정
   - Flyway 마이그레이션 스크립트 작성

2. **엔티티 및 Repository 구현**
   - City 엔티티와 CityRepository 구현
   - WeatherCode 엔티티와 WeatherCodeRepository 구현

3. **기존 코드 리팩토링**
   - WeatherController가 CityRepository를 사용하도록 변경
   - WeatherCodeTranslator가 WeatherCodeRepository를 사용하도록 변경
   - 기존 City enum 삭제

4. **테스트 환경 구성**
   - 테스트용 H2 인메모리 DB 설정
   - data.sql로 테스트 데이터 초기화
   - 테스트 코드를 DB 기반으로 수정

#### AI 활용으로 수정한 내용

**Docker 환경 구성**
- `environment/docker-compose.yml`로 MySQL 8.0 컨테이너 설정
- username/password를 `weather`로 설정

**DB 스키마 설계**
- `city` 테이블: id, name, korean_name, latitude, longitude
- `weather_code` 테이블: id, code, description
- Flyway 마이그레이션으로 테이블 생성 및 초기 데이터 삽입

**테스트 환경 분리**
- 운영: MySQL, 테스트: H2 인메모리 DB
- `spring.jpa.defer-datasource-initialization`으로 data.sql 실행 순서 조정
