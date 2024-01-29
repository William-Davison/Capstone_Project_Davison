package com.example.demo.model;

import lombok.Getter;

@Getter
public enum WeekNights {
    ZERO_NIGHTS("0_Week_Nights"),
    ONE_NIGHT("1_Week_Night"),
    TWO_NIGHTS("2_Week_Nights"),
    THREE_NIGHTS("3_Week_Nights"),
    FOUR_NIGHTS("4_Week_Nights"),
    FIVE_NIGHTS("5_Week_Nights"),
    SIX_OR_MORE_NIGHTS("6_or_more_Week_Nights");

    private final String label;

    WeekNights(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static WeekNights fromLabel(String label) {
        for (WeekNights wn : WeekNights.values()) {
            if (wn.getLabel().equals(label)) {
                return wn;
            }
        }
        return null;
    }
}

