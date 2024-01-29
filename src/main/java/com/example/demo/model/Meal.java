package com.example.demo.model;

import lombok.Getter;

@Getter
public enum Meal {
    SELF_CATERING("Self_Catering"),
    BED_AND_BREAKFAST("Bed_And_Breakfast"),
    HALF_BOARD("Half_Board"),
    FULL_BOARD("Full_Board");

    private final String label;

    Meal(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static Meal fromLabel(String label) {
        for (Meal m : Meal.values()) {
            if (m.getLabel().equals(label)) {
                return m;
            }
        }
        return null;
    }
}

