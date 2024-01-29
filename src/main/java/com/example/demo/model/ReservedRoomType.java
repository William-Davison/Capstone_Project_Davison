package com.example.demo.model;

import lombok.Getter;

@Getter
public enum ReservedRoomType {
        DELUXE("Deluxe"),
        STANDARD("Standard"),
        SUITE("Suite");

        private final String label;

        ReservedRoomType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }

        public static ReservedRoomType fromLabel(String label) {
            for (ReservedRoomType rrt : ReservedRoomType.values()) {
                if (rrt.getLabel().equals(label)) {
                    return rrt;
                }
            }
            return null;
        }
    }

