package com.example.demo.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    //System_Calculated Data
    @Id
    @GeneratedValue
    private Long id;

    private String confirmationNumber;
    @OneToOne
    private MainGuest mainGuest;
    private Integer weekdayStays;
    private Integer weekendStays;
    private ArrivalDateMonth breakPeriod;
    private GuestType guestType;
    private WeekendNights weekendNights;
    private WeekNights weekNights;
    private String CancelorNot;

    //User_Entered Data
    private Integer numberOfGuests;
    private String checkInDate;
    private String checkOutDate;
    private ReservedRoomType roomType;
    private Meal mealOptions;
    private DepositType depositType;

}
