package com.example.demo.model;

import lombok.Getter;

@Getter
public enum ArrivalDateMonth {
    REGULAR("Regular"),
    SUMMER_BREAK("Summer_Break"),
    WINTER_BREAK("Winter_Break");

    private final String label;

    ArrivalDateMonth(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static ArrivalDateMonth fromLabel(String label) {
        for (ArrivalDateMonth adm : ArrivalDateMonth.values()) {
            if (adm.getLabel().equals(label)) {
                return adm;
            }
        }
        return null;
    }
}
