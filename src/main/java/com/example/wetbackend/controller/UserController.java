package com.example.wetbackend.controller;

import com.example.wetbackend.model.User;
import com.example.wetbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 사용자(User) 컨트롤러 클래스
 * 사용자 관련 REST API 엔드포인트를 제공합니다.
 * 
 * 클라이언트(Flutter, React 등)의 HTTP 요청을 받아 처리하고,
 * JSON 형식으로 응답을 반환합니다.
 * 
 * @RestController: 이 클래스가 REST API 컨트롤러임을 나타냄
 *                  모든 메서드의 반환값이 자동으로 JSON으로 변환됩니다.
 *                  @RequestMapping("/api"): 이 컨트롤러의 모든 엔드포인트는 /api로 시작합니다.
 */
@RestController
@RequestMapping("/api")
public class UserController {

    /**
     * UserService 의존성 주입
     * 
     * @Autowired: Spring이 자동으로 UserService 인스턴스를 주입해줍니다.
     *             비즈니스 로직 처리를 위해 사용됩니다.
     */
    @Autowired
    private UserService userService;

    /**
     * 서버 상태 확인 엔드포인트 (Health Check)
     * 
     * HTTP Method: GET
     * URL: /api/health
     * 
     * 서버가 정상적으로 실행 중인지 확인하는 용도로 사용됩니다.
     * 
     * @return 서버 상태 정보 (상태, 시간, 메시지)
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Wet Backend API is running");
        return ResponseEntity.ok(response);
    }

    /**
     * 모든 사용자 목록 조회 엔드포인트
     * 
     * HTTP Method: GET
     * URL: /api/users
     * 
     * 데이터베이스에 저장된 모든 사용자를 조회합니다.
     * 
     * @return 모든 사용자 리스트 (JSON 배열)
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * 특정 사용자 조회 엔드포인트
     * 
     * HTTP Method: GET
     * URL: /api/users/{id}
     * 
     * ID를 사용하여 특정 사용자의 정보를 조회합니다.
     * 
     * @param id 조회할 사용자의 ID (URL 경로에서 추출)
     * @return 사용자 정보 (존재하면 200 OK, 없으면 404 Not Found)
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok) // 사용자가 존재하면 200 OK와 함께 반환
                .orElse(ResponseEntity.notFound().build()); // 없으면 404 Not Found
    }

    /**
     * 새로운 사용자 생성 엔드포인트
     * 
     * HTTP Method: POST
     * URL: /api/users
     * Request Body: {"name": "홍길동", "email": "hong@example.com"}
     * 
     * 새로운 사용자를 데이터베이스에 저장합니다.
     * 
     * @param user 생성할 사용자 정보 (JSON 요청 본문에서 자동 변환)
     * @return 생성된 사용자 정보 (ID 포함, 201 Created 상태 코드)
     */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    /**
     * 사용자 정보 수정 엔드포인트
     * 
     * HTTP Method: PUT
     * URL: /api/users/{id}
     * Request Body: {"name": "김철수", "email": "kim@example.com"}
     * 
     * 기존 사용자의 정보를 수정합니다.
     * 
     * @param id          수정할 사용자의 ID (URL 경로에서 추출)
     * @param userDetails 수정할 사용자 정보 (JSON 요청 본문에서 자동 변환)
     * @return 수정된 사용자 정보 (성공하면 200 OK, 사용자가 없으면 404 Not Found)
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            // 사용자를 찾을 수 없는 경우 404 반환
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 사용자 삭제 엔드포인트
     * 
     * HTTP Method: DELETE
     * URL: /api/users/{id}
     * 
     * 특정 사용자를 데이터베이스에서 삭제합니다.
     * 
     * @param id 삭제할 사용자의 ID (URL 경로에서 추출)
     * @return 삭제 성공 메시지 (성공하면 200 OK, 사용자가 없으면 404 Not Found)
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // 사용자를 찾을 수 없는 경우 404 반환
            return ResponseEntity.notFound().build();
        }
    }
}
