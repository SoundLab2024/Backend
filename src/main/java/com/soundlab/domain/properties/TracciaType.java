package com.soundlab.domain.properties;

public enum TracciaType {

    ORIGINAL {
        @Override
        public String toString() {
            return "ORIGINAL";
        }
    },

    COVER {
        @Override
        public String toString() {
            return "COVER";
        }
    },

    REMASTER {
        @Override
        public String toString() {
            return "REMASTER";
        }
    },
}
