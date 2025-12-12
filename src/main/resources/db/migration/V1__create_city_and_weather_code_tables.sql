CREATE TABLE city (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    korean_name VARCHAR(50) NOT NULL,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL
);

CREATE TABLE weather_code (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code INT NOT NULL UNIQUE,
    description VARCHAR(50) NOT NULL
);

INSERT INTO city (name, korean_name, latitude, longitude) VALUES
('Seoul', '서울', 37.5665, 126.9780),
('Tokyo', '도쿄', 35.6762, 139.6503),
('NewYork', '뉴욕', 40.7128, -74.0060),
('Paris', '파리', 48.8566, 2.3522),
('London', '런던', 51.5074, -0.1278);

INSERT INTO weather_code (code, description) VALUES
(0, '맑음'),
(1, '대체로 맑음'),
(2, '구름 조금'),
(3, '흐림'),
(45, '안개'),
(48, '안개'),
(51, '이슬비'),
(53, '이슬비'),
(55, '이슬비'),
(56, '이슬비'),
(57, '이슬비'),
(61, '비'),
(63, '비'),
(65, '비'),
(66, '비'),
(67, '비'),
(71, '눈'),
(73, '눈'),
(75, '눈'),
(77, '눈'),
(80, '소나기'),
(81, '소나기'),
(82, '소나기'),
(85, '눈 소나기'),
(86, '눈 소나기'),
(95, '뇌우'),
(96, '뇌우'),
(99, '뇌우');
