package com.example.demo.model;

import lombok.Getter;

@Getter
public enum CategoryType {
    GUESTS("Guests"),
    WEEKEND_NIGHTS("Weekend_Nights"),
    WEEK_NIGHTS("Week_Nights"),
    RESERVED_ROOM_TYPE("Reserved_Room_Type"),
    MEAL("Meal"),
    ARRIVAL_DATE_MONTH("Arrival_Date_Month"),
    DEPOSIT_TYPE("Deposit_Type");

    private final String label;

    CategoryType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}

