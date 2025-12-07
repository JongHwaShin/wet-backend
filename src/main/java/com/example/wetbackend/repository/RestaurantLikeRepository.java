package com.example.wetbackend.repository;

import com.example.wetbackend.model.User;
import com.example.wetbackend.model.entity.Restaurant;
import com.example.wetbackend.model.entity.RestaurantLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantLikeRepository extends JpaRepository<RestaurantLike, Long> {
    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);

    Optional<RestaurantLike> findByUserAndRestaurant(User user, Restaurant restaurant);

    void deleteByUserAndRestaurant(User user, Restaurant restaurant);
}
