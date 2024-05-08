package com.soundlab.dto.records;

import lombok.Builder;

import java.sql.Date;

/**
 * Record representing the credentials to make authentication requests
 *
 * @param email    email of the client (user)
 * @param password password of the client (user)
 */
@Builder
public record CredentialsDTO(String email, String password, String username, String gender, Date birth) { }