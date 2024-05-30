package com.project.reelRadar.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "favorites")
public class Favorite implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private List<String> tvShows;
    private List<String> movies;
    private List<String> people;
}