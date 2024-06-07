package com.project.reelRadar.repositories;
/**
import com.project.reelRadar.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Favorite findByUserId(String userId);
} */