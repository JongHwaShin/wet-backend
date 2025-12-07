package com.example.wetbackend.controller;

import com.example.wetbackend.model.RestaurantDto;
import com.example.wetbackend.model.RestaurantLikeRequest;
import com.example.wetbackend.service.KakaoMapService;
import com.example.wetbackend.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 식당 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantController.class);

    private final KakaoMapService kakaoMapService;
    private final RestaurantService restaurantService;

    /**
     * 주소 기반 주변 식당 검색
     * 
     * @param address 사용자가 선택한 주소 (예: "서울시 강남구 역삼동")
     * @return 검색된 식당 목록
     */
    @GetMapping("/search")
    public ResponseEntity<List<RestaurantDto>> searchRestaurants(@RequestParam String address) {
        // 검색 요청 로깅
        logger.info("========== 식당 검색 요청 ==========");
        logger.info("요청 주소: {}", address);

        // 검색어 생성: 주소 + " 맛집"
        String query = address + " 맛집";

        List<RestaurantDto> result = kakaoMapService.searchRestaurants(query);

        // 검색 결과 로깅
        logger.info("검색 완료. 결과 데이터: {}", result);
        logger.info("==================================");

        return ResponseEntity.ok(result);
    }

    /**
     * 식당 찜하기 (토글)
     * DB에 없는 식당이면 자동 저장 후 찜 처리
     */
    @PostMapping("/like")
    public ResponseEntity<String> toggleLike(@RequestBody RestaurantLikeRequest request) {
        boolean isLiked = restaurantService.toggleLike(request.getUserId(), request.getRestaurant());
        return ResponseEntity.ok(isLiked ? "Liked" : "Unliked");
    }

    /**
     * 내가 찜한 식당 목록 조회
     */
    @GetMapping("/likes")
    public ResponseEntity<List<RestaurantDto>> getMyLikes(@RequestParam Long userId) {
        List<RestaurantDto> likes = restaurantService.getLikedRestaurants(userId);
        return ResponseEntity.ok(likes);
    }
}
