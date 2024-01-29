package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingRequest {
    private GuestInfo guestInfo;
    private BookingInfo bookingInfo;

}
