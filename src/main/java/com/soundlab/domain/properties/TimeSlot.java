package com.soundlab.domain.properties;

public enum TimeSlot {

    Morning {
        @Override
        public String toString() {
            return "Mattina";
        }
    },

    Afternoon {
        @Override
        public String toString() {
            return "Pomeriggio";
        }
    },

    Evening {
        @Override
        public String toString() {
            return "Sera";
        }
    },

    Night {
        @Override
        public String toString() {
            return "Notte";
        }
    };

    public static TimeSlot getTimeSlot(String value) {

        return switch (value) {
            case "Mattina" -> Morning;
            case "Pomeriggio" -> Afternoon;
            case "Sera" -> Evening;
            case "Notte" -> Night;
            default -> null;
        };

    }


}
