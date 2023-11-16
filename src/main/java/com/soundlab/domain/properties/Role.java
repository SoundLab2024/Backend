package com.soundlab.domain.properties;

public enum Role {
  ADMIN {
    @Override
    public String toString() {
      return "ADMIN";
    }
  },

  USER {
    @Override
    public String toString() {
      return "USER";
    }
  },
}