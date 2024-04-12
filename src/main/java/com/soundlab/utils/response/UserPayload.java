package com.soundlab.utils.response;

import lombok.Builder;

@Builder
public record UserPayload(String email, String username, String role, Long libraryId, String token) {
}
