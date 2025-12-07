-- 데이터베이스 생성
-- IF NOT EXISTS: 이미 존재하면 생성하지 않음
-- CHARACTER SET utf8mb4: 한글, 이모지 등 모든 유니코드 문자 지원
-- COLLATE utf8mb4_unicode_ci: 대소문자 구분 없이 정렬 (ci = case insensitive)
CREATE DATABASE IF NOT EXISTS wetdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 생성한 데이터베이스 사용 (이후 모든 명령은 이 DB에서 실행됨)
USE wetdb;

-- 사용자(users) 테이블 생성
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 사용자 고유 ID (자동 증가, 기본 키)
    name VARCHAR(255) NOT NULL,            -- 사용자 이름 (최대 255자, 필수)
    email VARCHAR(255) NOT NULL UNIQUE,    -- 이메일 주소 (최대 255자, 필수, 중복 불가)
    created_at DATETIME(6),                -- 생성 일시 (마이크로초 단위까지 저장)
    INDEX idx_email (email)                -- 이메일 컬럼에 인덱스 생성 (검색 속도 향상)
) ENGINE=InnoDB                            -- InnoDB 스토리지 엔진 사용 (트랜잭션 지원)
  DEFAULT CHARSET=utf8mb4                  -- 기본 문자셋: utf8mb4
  COLLATE=utf8mb4_unicode_ci;              -- 정렬 규칙: 대소문자 구분 없음


-- 식당(restaurants) 테이블 생성
CREATE TABLE IF NOT EXISTS restaurants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,          -- 우리 서비스 내부 ID
    kakao_id VARCHAR(50) NOT NULL UNIQUE,          -- 카카오 맵 API의 장소 ID (유니크)
    name VARCHAR(255) NOT NULL,                    -- 식당 이름
    category VARCHAR(255),                         -- 카테고리
    phone VARCHAR(50),                             -- 전화번호
    address VARCHAR(255),                          -- 지번 주소
    road_address VARCHAR(255),                     -- 도로명 주소
    x_coordinate VARCHAR(50),                      -- 경도 (X)
    y_coordinate VARCHAR(50),                      -- 위도 (Y)
    place_url VARCHAR(500),                        -- 카카오 플레이스 URL
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6), -- 생성일
    updated_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6), -- 수정일
    INDEX idx_kakao_id (kakao_id)                  -- 카카오 ID로 빠른 조회
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;


-- 식당 좋아요(restaurant_likes) 테이블 생성
CREATE TABLE IF NOT EXISTS restaurant_likes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,          -- 좋아요 고유 ID
    user_id BIGINT NOT NULL,                       -- 사용자 ID (FK)
    restaurant_id BIGINT NOT NULL,                 -- 식당 ID (FK)
    created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6), -- 생성일
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_restaurant (user_id, restaurant_id) -- 유저는 같은 식당을 중복해서 찜할 수 없음
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_unicode_ci;

-- 현재 데이터베이스의 모든 테이블 목록 표시
SHOW TABLES;

-- users 테이블의 구조(컬럼, 타입, 제약조건 등) 표시
DESCRIBE users;
DESCRIBE restaurants;
DESCRIBE restaurant_likes;
