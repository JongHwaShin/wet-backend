package com.example.wetbackend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * 사용자(User) 엔티티 클래스
 * 데이터베이스의 'users' 테이블과 매핑되는 JPA 엔티티입니다.
 * 
 * @Entity: 이 클래스가 JPA 엔티티임을 나타냄 (데이터베이스 테이블과 매핑)
 * @Table: 매핑될 테이블 이름을 지정 (기본값은 클래스명의 소문자)
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * 사용자 고유 ID (Primary Key)
     * 
     * @Id: 이 필드가 기본 키(Primary Key)임을 나타냄
     * @GeneratedValue: 값이 자동으로 생성됨을 나타냄
     *                  IDENTITY 전략: 데이터베이스의 AUTO_INCREMENT를 사용하여 자동 증가
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사용자 이름
     * 
     * @Column(nullable = false): 이 필드는 NULL 값을 허용하지 않음 (필수 입력)
     */
    @Column(nullable = false)
    private String name;

    /**
     * 사용자 이메일 주소
     * 
     * @Column(nullable = false, unique = true):
     *                  - NULL 값 불가 (필수 입력)
     *                  - 중복 값 불가 (유니크 제약조건)
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 사용자 생성 일시
     * 
     * @Column(name = "created_at"): 데이터베이스 컬럼명을 'created_at'으로 지정
     *              (Java의 camelCase를 DB의 snake_case로 매핑)
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 엔티티가 데이터베이스에 저장되기 전에 자동으로 실행되는 메서드
     * 
     * @PrePersist: JPA 라이프사이클 콜백 어노테이션
     *              새로운 엔티티가 persist(저장)되기 직전에 실행됩니다.
     *              여기서는 생성 시간을 자동으로 설정합니다.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 현재 시간을 생성 일시로 설정
    }

    // ========== 생성자 (Constructors) ==========

    /**
     * 기본 생성자
     * JPA는 리플렉션을 사용하여 엔티티를 생성하므로 기본 생성자가 필수입니다.
     */
    public User() {
    }

    /**
     * 사용자 생성을 위한 생성자
     * 
     * @param name  사용자 이름
     * @param email 사용자 이메일
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // ========== Getters and Setters ==========
    // 필드에 접근하고 수정하기 위한 메서드들

    /**
     * 사용자 ID를 반환합니다.
     * 
     * @return 사용자 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 사용자 ID를 설정합니다.
     * 
     * @param id 설정할 사용자 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 사용자 이름을 반환합니다.
     * 
     * @return 사용자 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 사용자 이름을 설정합니다.
     * 
     * @param name 설정할 사용자 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 사용자 이메일을 반환합니다.
     * 
     * @return 사용자 이메일
     */
    public String getEmail() {
        return email;
    }

    /**
     * 사용자 이메일을 설정합니다.
     * 
     * @param email 설정할 사용자 이메일
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 사용자 생성 일시를 반환합니다.
     * 
     * @return 생성 일시
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 사용자 생성 일시를 설정합니다.
     * 
     * @param createdAt 설정할 생성 일시
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
