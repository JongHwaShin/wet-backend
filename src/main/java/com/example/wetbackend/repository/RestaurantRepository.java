package com.example.wetbackend.repository;

import com.example.wetbackend.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByKakaoId(String kakaoId);

    boolean existsByKakaoId(String kakaoId);
}
