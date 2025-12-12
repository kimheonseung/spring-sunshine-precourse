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

1. `City` enum으로 도시 이름과 좌표를 매핑
2. `WeatherService`에서 Open-Meteo API 호출 및 응답 파싱
3. `WeatherCodeTranslator`로 WMO 코드를 한국어로 변환
4. `WeatherSummaryGenerator`로 요약 문장 생성
5. `WeatherController`에서 REST API 제공

---

## AI 활용 내역

- Claude Code를 활용하여 코드 구현
- Open-Meteo API 문서 분석 및 WMO weather code 매핑 정보 조사에 활용
