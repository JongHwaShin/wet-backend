package com.example.wetbackend.service;

import com.example.wetbackend.model.KakaoSearchResponse;
import com.example.wetbackend.model.RestaurantDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoMapService {

    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Value("${kakao.api.url}")
    private String kakaoApiUrl;

    public List<RestaurantDto> searchRestaurants(String query) {
        if (query == null || query.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // 카카오 API 호출을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // URL 생성 (검색어 + 카테고리 그룹 코드 FD6: 음식점)
        URI uri = UriComponentsBuilder.fromHttpUrl(kakaoApiUrl)
                .queryParam("query", query)
                .queryParam("category_group_code", "FD6") // 음식점 카테고리 필터링
                .build()
                .encode()
                .toUri();

        log.info("Kakao API Request: {}", uri);

        try {
            ResponseEntity<KakaoSearchResponse> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    entity,
                    KakaoSearchResponse.class);

            KakaoSearchResponse body = response.getBody();
            if (body == null || body.getDocuments() == null) {
                return Collections.emptyList();
            }

            return body.getDocuments().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Kakao API 호출 중 오류 발생: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private RestaurantDto convertToDto(KakaoSearchResponse.Document document) {
        return RestaurantDto.builder()
                .id(document.getId())
                .name(document.getPlaceName())
                .category(document.getCategoryName())
                .phone(document.getPhone())
                .address(document.getAddressName())
                .roadAddress(document.getRoadAddressName())
                .x(document.getX())
                .y(document.getY())
                .placeUrl(document.getPlaceUrl())
                .build();
    }
}
