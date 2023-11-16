package com.soundlab.utils.response;
import lombok.Builder;

/**
 * Record used to represent the status of an operation
 *
 * @param statusCode HTTP Status code (as value)
 * @param msg        Additional message
 */
@Builder
public record Payload(int statusCode, String msg) { }