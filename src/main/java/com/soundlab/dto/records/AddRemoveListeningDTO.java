package com.soundlab.dto.records;

import lombok.Builder;

@Builder
public record AddRemoveListeningDTO(String userId, Long songId) { }
