package com.project.reelRadar.dto;

import java.util.UUID;

public record AuthenticationResponseDTO(String username, UUID Id, String token) {}