package com.example.wetbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 애플리케이션 설정 클래스
 * CORS(Cross-Origin Resource Sharing) 정책을 설정합니다.
 * 
 * CORS란? 다른 도메인(예: Flutter 앱, React 앱)에서 이 API를 호출할 수 있도록 허용하는 보안 정책입니다.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 매핑을 추가하는 메서드
     * 어떤 도메인에서 어떤 방식으로 API를 호출할 수 있는지 설정합니다.
     * 
     * @param registry CORS 설정을 등록하는 레지스트리
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // /api/로 시작하는 모든 경로에 CORS 적용
                .allowedOrigins("*") // 모든 도메인에서의 요청 허용 (개발 환경용, 배포 시 특정 도메인으로 제한 필요)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메서드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(false); // 쿠키/인증 정보 전송 비활성화 (allowedOrigins("*")와 함께 사용 시 필수)
    }
}
