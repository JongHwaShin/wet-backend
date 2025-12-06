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

-- 현재 데이터베이스의 모든 테이블 목록 표시
SHOW TABLES;

-- users 테이블의 구조(컬럼, 타입, 제약조건 등) 표시
DESCRIBE users;
