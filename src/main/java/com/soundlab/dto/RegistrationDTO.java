package com.soundlab.dto;

import lombok.Builder;

/**
 * Record representing the credentials to enable the user for future requests
 *
 * @param email       email of the client (user)
 * @param oldPassword the old password of the client (user)
 * @param newPassword the new requested password of the client (user)
 */
@Builder
public record RegistrationDTO(String email, String oldPassword, String newPassword) {

}