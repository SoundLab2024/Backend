package com.soundlab.domain.properties;

public enum FasciaOrariaType {

    MATTINA {
        @Override
        public String toString() {
            return "MATTINA";
        }
    },

    POMERIGGIO {
        @Override
        public String toString() {
            return "POMERIGGIO";
        }
    },

    SERA {
        @Override
        public String toString() {
            return "SERA";
        }
    },

    NOTTE {
        @Override
        public String toString() {
            return "NOTTE";
        }
    },
}
