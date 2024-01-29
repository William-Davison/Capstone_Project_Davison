package com.example.demo.model;

import lombok.Getter;

@Getter
public enum DepositType {
    NO_DEPOSIT("No_Deposit"),
    NONREFUNDABLE("Nonrefundable"),
    REFUNDABLE("Refundable");

    private final String label;

    DepositType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static DepositType fromLabel(String label) {
        for (DepositType dt : DepositType.values()) {
            if (dt.getLabel().equals(label)) {
                return dt;
            }
        }
        return null;
    }
}
