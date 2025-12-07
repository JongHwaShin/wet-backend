package com.example.wetbackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RestaurantLikeRequest {
    private Long userId;
    private RestaurantDto restaurant;
}
