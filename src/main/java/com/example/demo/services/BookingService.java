package com.example.demo.services;

import com.example.demo.model.ArrivalDateMonth;
import com.example.demo.model.GuestType;
import com.example.demo.model.WeekNights;
import com.example.demo.model.WeekendNights;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import java.util.List;


import static com.example.demo.model.GuestType.*;
import static com.example.demo.model.GuestType.FOUR_OR_MORE_GUESTS;
import static com.example.demo.model.WeekNights.*;
import static com.example.demo.model.WeekendNights.*;

@Service
public class BookingService {



    // Weekday Calculator
    public static WeekNights weekdayCalculator(String first, String last) {
        LocalDate beginning = LocalDate.parse(first);
        LocalDate ending = LocalDate.parse(last);

        long weekdays = beginning.datesUntil(ending)
                .filter(d -> !(d.getDayOfWeek().getValue() == 6 || d.getDayOfWeek().getValue() == 7))
                .count();

        return switch ((int) weekdays) {
            case 0 -> ZERO_NIGHTS;
            case 1 -> ONE_NIGHT;
            case 2 -> TWO_NIGHTS;
            case 3 -> THREE_NIGHTS;
            case 4 -> FOUR_NIGHTS;
            case 5 -> FIVE_NIGHTS;
            default -> SIX_OR_MORE_NIGHTS;
        };
    }


    //Weekend days Calculator
    public static WeekendNights weekendCalculator(String first, String last) {
        LocalDate beginning = LocalDate.parse(first);
        LocalDate ending = LocalDate.parse(last);

        long weekends = beginning.datesUntil(ending)
                .filter(d -> (d.getDayOfWeek().getValue() == 6 || d.getDayOfWeek().getValue() == 7))
                .count();

        return switch ((int) weekends) {
            case 0 -> WEEKEND_ZERO_NIGHTS;
            case 1 -> WEEKEND_ONE_NIGHTS;
            case 2 -> WEEKEND_TWO_NIGHTS;
            case 3 -> WEEKEND_THREE_NIGHTS;
            case 4 -> WEEKEND_FOUR_NIGHTS;
            default -> WEEKEND_FIVE_OR_MORE_NIGHTS;


        };
    }

    //Month Calculator
    public static ArrivalDateMonth MonthCalculator(String startDate2String, String endDate2String) {

        LocalDate beginningDate = LocalDate.parse(startDate2String);
        LocalDate endingDate = LocalDate.parse(endDate2String);

        List<ArrivalDateMonth> months = new ArrayList<>();
        while (!beginningDate.isAfter(endingDate)) {
            switch (Month.of(beginningDate.getMonthValue())) {
                case JUNE:
                case JULY:
                case AUGUST:
                    months.add(ArrivalDateMonth.SUMMER_BREAK);
                    break;
                case DECEMBER:
                case JANUARY:
                    months.add(ArrivalDateMonth.WINTER_BREAK);
                    break;
                default:
                    months.add(ArrivalDateMonth.REGULAR);
            }
            beginningDate = beginningDate.plusMonths(1);
        }
        System.out.println(months);
        return months.getFirst();
    }

    //Guest Calculator
    public static GuestType fromGuestCount(int count) {
        return switch (count) {
            case 1 -> ONE_GUEST;
            case 2 -> TWO_GUESTS;
            case 3 -> THREE_GUESTS;
            default -> FOUR_OR_MORE_GUESTS;
        };
    }

   //Guests entered in valid (whole number)
private static boolean validateNumberOfGuests(int numberOfGuests) {

        return numberOfGuests > 0;
    }


    //Start Date and End date validation
    public static boolean isValidBookingDates(String startDate3, String endDate3) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate start = LocalDate.parse(startDate3, formatter);
            LocalDate end = LocalDate.parse(endDate3, formatter);

            if (start.isBefore(end)) {
                return true;
            }

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use 'yyyy-MM-dd'.");

        }

        return false;




    }
}
