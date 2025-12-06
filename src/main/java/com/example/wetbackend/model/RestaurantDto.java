package com.example.wetbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    private String id; // 장소 ID
    private String name; // 장소명 (place_name)
    private String category; // 카테고리 (category_name)
    private String phone; // 전화번호
    private String address; // 지번 주소 (address_name)
    private String roadAddress; // 도로명 주소 (road_address_name)
    private String x; // X 좌표 (경도)
    private String y; // Y 좌표 (위도)
    private String placeUrl; // 장소 상세 페이지 URL
}
