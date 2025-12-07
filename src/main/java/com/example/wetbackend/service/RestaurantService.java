package com.example.wetbackend.service;

import com.example.wetbackend.model.RestaurantDto;
import com.example.wetbackend.model.User;
import com.example.wetbackend.model.entity.Restaurant;
import com.example.wetbackend.model.entity.RestaurantLike;
import com.example.wetbackend.repository.RestaurantLikeRepository;
import com.example.wetbackend.repository.RestaurantRepository;
import com.example.wetbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantLikeRepository restaurantLikeRepository;
    private final UserRepository userRepository;

    /**
     * 식당 좋아요 토글 (찜하기/찜취소)
     * 1. 식당이 DB에 없으면 저장
     * 2. 이미 좋아요 상태면 취소 (삭제)
     * 3. 아니면 좋아요 추가
     */
    @Transactional
    public boolean toggleLike(Long userId, RestaurantDto restaurantDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // 1. 식당 조회 및 저장 (On-Demand Save)
        Restaurant restaurant = restaurantRepository.findByKakaoId(restaurantDto.getId())
                .orElseGet(() -> saveRestaurant(restaurantDto));

        // 2. 좋아요 여부 확인
        if (restaurantLikeRepository.existsByUserAndRestaurant(user, restaurant)) {
            // 이미 찜한 상태면 삭제 (찜 취소)
            restaurantLikeRepository.deleteByUserAndRestaurant(user, restaurant);
            return false;
        } else {
            // 찜하지 않은 상태면 추가
            restaurantLikeRepository.save(RestaurantLike.builder()
                    .user(user)
                    .restaurant(restaurant)
                    .build());
            return true;
        }
    }

    private Restaurant saveRestaurant(RestaurantDto dto) {
        Restaurant restaurant = Restaurant.builder()
                .kakaoId(dto.getId())
                .name(dto.getName())
                .category(dto.getCategory())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .roadAddress(dto.getRoadAddress())
                .x(dto.getX())
                .y(dto.getY())
                .placeUrl(dto.getPlaceUrl())
                .build();

        return restaurantRepository.save(restaurant);
    }

    /**
     * 사용자가 찜한 식당 목록 조회
     */
    @Transactional(readOnly = true)
    public List<RestaurantDto> getLikedRestaurants(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        return restaurantLikeRepository.findAll().stream()
                .filter(like -> like.getUser().getId().equals(userId))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RestaurantDto convertToDto(RestaurantLike like) {
        Restaurant r = like.getRestaurant();
        return RestaurantDto.builder()
                .id(r.getKakaoId())
                .name(r.getName())
                .category(r.getCategory())
                .phone(r.getPhone())
                .address(r.getAddress())
                .roadAddress(r.getRoadAddress())
                .x(r.getX())
                .y(r.getY())
                .placeUrl(r.getPlaceUrl())
                // .isLiked(true) // 나중에 DTO에 필드 추가 가능
                .build();
    }
}
