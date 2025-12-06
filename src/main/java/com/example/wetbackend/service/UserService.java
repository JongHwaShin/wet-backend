package com.example.wetbackend.service;

import com.example.wetbackend.model.User;
import com.example.wetbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 사용자(User) 서비스 클래스
 * 사용자 관련 비즈니스 로직을 처리합니다.
 * 
 * Controller와 Repository 사이의 중간 계층으로,
 * 실제 비즈니스 로직과 데이터 처리를 담당합니다.
 * 
 * @Service: 이 클래스가 서비스 계층(비즈니스 로직)임을 나타냄
 */
@Service
public class UserService {

    /**
     * UserRepository 의존성 주입
     * 
     * @Autowired: Spring이 자동으로 UserRepository 인스턴스를 주입해줍니다.
     *             데이터베이스 접근을 위해 사용됩니다.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * 모든 사용자 목록을 조회합니다.
     * 
     * @return 모든 사용자 리스트
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * ID로 특정 사용자를 조회합니다.
     * 
     * @param id 조회할 사용자의 ID
     * @return Optional로 감싸진 사용자 객체 (존재하지 않을 수 있음)
     */
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 새로운 사용자를 생성합니다.
     * 
     * @param user 생성할 사용자 정보
     * @return 생성된 사용자 객체 (ID 포함)
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * 기존 사용자의 정보를 수정합니다.
     * 
     * @param id          수정할 사용자의 ID
     * @param userDetails 수정할 사용자 정보
     * @return 수정된 사용자 객체
     * @throws RuntimeException 해당 ID의 사용자가 존재하지 않을 경우
     */
    public User updateUser(Long id, User userDetails) {
        // ID로 사용자를 찾고, 없으면 예외를 발생시킵니다
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 사용자 정보를 업데이트합니다
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        // 변경된 사용자 정보를 저장하고 반환합니다
        return userRepository.save(user);
    }

    /**
     * 사용자를 삭제합니다.
     * 
     * @param id 삭제할 사용자의 ID
     * @throws RuntimeException 해당 ID의 사용자가 존재하지 않을 경우
     */
    public void deleteUser(Long id) {
        // ID로 사용자를 찾고, 없으면 예외를 발생시킵니다
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // 사용자를 삭제합니다
        userRepository.delete(user);
    }
}
