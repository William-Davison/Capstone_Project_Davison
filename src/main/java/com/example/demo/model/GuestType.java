package com.example.demo.model;

import lombok.Getter;


@Getter
public enum GuestType {
        ONE_GUEST("1_Guest"),
        TWO_GUESTS("2_Guests"),
        THREE_GUESTS("3_Guests"),
        FOUR_OR_MORE_GUESTS("4_or_more_Guests");

        private final String label;

        GuestType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }

        public static GuestType fromLabel(String label) {
            for (GuestType gt : GuestType.values()) {
                if (gt.getLabel().equals(label)) {
                    return gt;
                }
            }
            return null;
        }
    }

