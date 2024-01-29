package com.example.demo.controller;

import com.example.demo.DTO.BookingInfo;
import com.example.demo.DTO.BookingRequest;
import com.example.demo.DTO.GuestInfo;
import com.example.demo.model.*;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.MainGuestRepository;
import com.example.demo.services.DataForGraphService;
import com.example.demo.services.ModelService;
import com.example.demo.services.PredictionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.example.demo.services.BookingService.*;
import static java.lang.Math.round;

@RestController
@RequestMapping("/api")
public class BookingController {
    private static Double selectedThreshold = 0.5;
    private final MainGuestRepository mainGuestRepository;
    private final BookingRepository bookingRepository;


    public BookingController(MainGuestRepository mainGuestRepository, BookingRepository bookingRepository) {
        this.mainGuestRepository = mainGuestRepository;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/seasonal-prediction-accuracy")
    public ResponseEntity<List<double[]>> getSeasonalPredictionAccuracy() {
        List<double[]> seasonalPredictionAccuracyData = DataForGraphService.calculateSeasonalPredictionAccuracy();
        return ResponseEntity.ok(seasonalPredictionAccuracyData);
    }

    @GetMapping("/prediction-accuracy-per-threshold")
    public ResponseEntity<double[][]> getPredictionAccuracyPerThreshold() {
            double[][] predictionAccuracyPerThresholdData = DataForGraphService.calculatePredictionAccuracyPerThreshold();
            return ResponseEntity.ok(predictionAccuracyPerThresholdData);

    }
    @GetMapping("/model-data")
    public ResponseEntity<double[]> getModelData() {
        double[] modelResult = ModelService.createModel();
        return ResponseEntity.ok(modelResult);
    }

    @CrossOrigin
    @PostMapping("/set-selected-threshold")
    public ResponseEntity<String> setSelectedThreshold(@RequestBody Map<String, Double> requestBody) {
        Double threshold = requestBody.get("threshold");
        selectedThreshold = threshold;
        // Save the threshold value as a global variable (e.g., selectedThreshold)
        // You may want to store it in a static variable or a singleton bean
        // For demonstration purposes, let's print it here
        System.out.println("Selected threshold: " + threshold);
        return ResponseEntity.ok("Selected threshold set successfully: " + threshold);
    }



    @CrossOrigin
    @PostMapping("/hotelbooking")
    public ResponseEntity<String> saveBooking(@RequestBody BookingRequest bookingRequest) {
        // Extract main guest information from the BookingRequest
        GuestInfo guestInfo = bookingRequest.getGuestInfo();

        // Create a MainGuest entity from the extracted information
        MainGuest mainGuest = new MainGuest();
        mainGuest.setFirstName(guestInfo.getFirstName());
        mainGuest.setLastName(guestInfo.getLastName());
        mainGuest.setMobileNumber(guestInfo.getMobileNumber());
        mainGuest.setEmailAddress(guestInfo.getEmailAddress());

        // Save the MainGuest entity to the repository
        mainGuestRepository.save(mainGuest);

        // Extract booking information from the BookingRequest
        BookingInfo bookingInfo = bookingRequest.getBookingInfo();

        // Create a Booking entity from the extracted information
        Booking booking = new Booking();
        booking.setNumberOfGuests(bookingInfo.getNumberOfGuests());
        booking.setCheckInDate(bookingInfo.getCheckInDate());
        booking.setCheckOutDate(bookingInfo.getCheckOutDate());
        booking.setRoomType(ReservedRoomType.fromLabel(bookingInfo.getRoomType()));
        booking.setMealOptions(Meal.fromLabel(bookingInfo.getMealOption()));
        booking.setDepositType(DepositType.fromLabel(bookingInfo.getDepositOption()));
        booking.setMainGuest(mainGuest);
        booking.setConfirmationNumber(String.valueOf(UUID.randomUUID()));

        booking.setGuestType(fromGuestCount(bookingInfo.getNumberOfGuests()));
        booking.setBreakPeriod(MonthCalculator(bookingInfo.getCheckInDate(), booking.getCheckOutDate()));
        booking.setWeekendNights(weekendCalculator(bookingInfo.getCheckInDate(), booking.getCheckOutDate()));
        booking.setWeekNights(weekdayCalculator(bookingInfo.getCheckInDate(), booking.getCheckOutDate()));


        // Save the Booking entity to the repository
        bookingRepository.save(booking);

        // Call the prediction service after saving the booking
        double[] predictionArray = PredictionService.predictionService(booking);
        System.out.println("Predicted Likelihood of Cancellation is " + predictionArray[1] + "%" + " for booking for " + mainGuest.getFirstName());
        String predictionText = "\n\nThe model predicts this booking will NOT be cancelled based on prediction % chance of " + round(predictionArray[1] * 100) + "% exceeds the set threshold of "+ round( selectedThreshold* 100) +"%";
        if (predictionArray[1] > selectedThreshold) {
            predictionText = "\n\nThe model predicts this booking will be CANCELLED since the prediction chance of " + round(predictionArray[1] * 100) + "% exceeds the set threshold of "+ round( selectedThreshold* 100) +"%";
        }
        // Implement your logic to save the booking data to the database or perform other actions
        // You can access guestInfo and bookingInfo from hotelBookingRequest object
        String responseMessage = "Booking saved successfully!\n\n " + predictionText + "%\n\nThe Booking Confirmation Number is " + booking.getConfirmationNumber();
        return ResponseEntity.ok(responseMessage);
    }
}
