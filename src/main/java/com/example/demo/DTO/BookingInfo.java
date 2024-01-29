package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingInfo {
    private Integer numberOfGuests;
    private String checkInDate;
    private String checkOutDate;
    private String roomType;
    private String mealOption;
    private String depositOption;
    private String cancelOrNot;
}
