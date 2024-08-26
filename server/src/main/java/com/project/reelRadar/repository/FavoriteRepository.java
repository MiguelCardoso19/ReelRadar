package com.project.reelRadar.repository;

import com.project.reelRadar.dto.FavoriteDTO;
import com.project.reelRadar.model.Favorite;
import com.project.reelRadar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, UUID> {
    Optional<Favorite> findByUser(User user);

    @Query("SELECT new com.project.reelRadar.dto.FavoriteDTO(f.movies, f.people, f.tvShows) FROM Favorite f WHERE f.user.id = ?1")
    List<FavoriteDTO> getFavoritesByUserId(UUID userId);
}