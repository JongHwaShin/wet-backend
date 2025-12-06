package com.example.wetbackend.repository;

import com.example.wetbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 사용자(User) 리포지토리 인터페이스
 * 데이터베이스의 users 테이블에 접근하기 위한 인터페이스입니다.
 * 
 * JpaRepository<User, Long>를 상속받아 기본적인 CRUD 기능을 자동으로 제공받습니다:
 * - save(User user): 사용자 저장/수정
 * - findById(Long id): ID로 사용자 조회
 * - findAll(): 모든 사용자 조회
 * - delete(User user): 사용자 삭제
 * - count(): 사용자 수 조회
 * 등등...
 * 
 * @Repository: 이 인터페이스가 데이터 접근 계층(Repository)임을 나타냄
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA가 자동으로 기본 CRUD 작업을 제공합니다.
    // 필요한 경우 여기에 커스텀 쿼리 메서드를 추가할 수 있습니다.

    // 예시: 이메일로 사용자 찾기
    // Optional<User> findByEmail(String email);

    // 예시: 이름으로 사용자 검색
    // List<User> findByNameContaining(String name);
}
