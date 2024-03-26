package com.soundlab.domain.properties;

public enum Gender {

    Male {
        @Override
        public String toString() {
            return "Uomo";
        }
    },

    Women {
        @Override
        public String toString() {
            return "Donna";
        }
    },

    Other {
        @Override
        public String toString() {
            return "Altro";
        }
    };

    public static Gender getGender(String value){

        return switch (value) {
            case "Uomo" -> Male;
            case "Donna" -> Women;
            default -> Other;
        };

    }


}
