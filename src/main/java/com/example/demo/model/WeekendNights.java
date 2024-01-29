package com.example.demo.model;

import lombok.Getter;

@Getter
public enum WeekendNights {
    WEEKEND_ZERO_NIGHTS("0_Weekend_Nights"),
    WEEKEND_ONE_NIGHTS("1_Weekend_Night"),
    WEEKEND_TWO_NIGHTS("2_Weekend_Nights"),
    WEEKEND_THREE_NIGHTS("3_Weekend_Nights"),
    WEEKEND_FOUR_NIGHTS("4_Weekend_Nights"),
    WEEKEND_FIVE_OR_MORE_NIGHTS("5_or_more_Weekend_Nights");

    private final String label;

    WeekendNights(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
    public static WeekendNights fromLabel(String label) {
        for (WeekendNights wn : WeekendNights.values()) {
            if (wn.getLabel().equals(label)) {
                return wn;
            }
        }
        return null;
    }
}

