package com.project.reelRadar.repositories;

import com.project.reelRadar.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {

    @Query(value = "SELECT movies, people, tv_shows FROM favorites WHERE user_id = ?1", nativeQuery = true)
    List<Object[]> getFavoritesByUserId(UUID userId);
}