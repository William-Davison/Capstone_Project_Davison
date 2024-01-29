package com.example.demo.services;

import com.example.demo.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.example.demo.services.ModelService.createModel;

public class PredictionService {
    public static double[] predictionService(Booking booking) {
        return predictionService(
                "0",
                booking.getGuestType().getLabel(),
                booking.getWeekendNights().getLabel(),
                booking.getWeekNights().getLabel(),
                booking.getRoomType().getLabel(),
                booking.getMealOptions().getLabel(),
                booking.getBreakPeriod().getLabel(),
                booking.getDepositType().getLabel()
        );
    }

    public static double[] predictionService(
            String likelyToCancel,
            String totalGuests,
            String staysInWeekendNights,
            String staysInWeekNights,
            String reservedRoomType,
            String mealType,
            String breakPeriod,
            String depositType
    ) {
        // double[] resultArray = createModel();
        String inputFile = "output.csv";


        // Retrieve the percentage_cancelled values for the specified types
        double percentageCancelledTotalGuests = getPercentageCancelled(inputFile, totalGuests);
        double percentageCancelledStaysInWeekendNights = getPercentageCancelled(inputFile, staysInWeekendNights);
        double percentageCancelledStaysInWeekNights = getPercentageCancelled(inputFile, staysInWeekNights);
        double percentageCancelledReservedRoomType = getPercentageCancelled(inputFile, reservedRoomType);
        double percentageCancelledMealType = getPercentageCancelled(inputFile, mealType);
        double percentageCancelledBreakPeriod = getPercentageCancelled(inputFile, breakPeriod);
        double percentageCancelledDepositeType = getPercentageCancelled(inputFile, depositType);
        // Add more variables for each type

        // Print the results
        System.out.println("Percentage Cancelled for Type: " + totalGuests + " is " + percentageCancelledTotalGuests + "%");
        System.out.println("Percentage Cancelled for Type: " + staysInWeekNights + " is " + percentageCancelledStaysInWeekendNights + "%");
        System.out.println("Percentage Cancelled for Type: " + staysInWeekendNights + " is " + percentageCancelledStaysInWeekNights + "%");
        System.out.println("Percentage Cancelled for Type: " + reservedRoomType + " is " + percentageCancelledReservedRoomType + "%");
        System.out.println("Percentage Cancelled for Type: " + mealType + " is " + percentageCancelledMealType + "%");
        System.out.println("Percentage Cancelled for Type: " + breakPeriod + " is " + percentageCancelledBreakPeriod + "%");
        System.out.println("Percentage Cancelled for Type: " + depositType + " is " + percentageCancelledDepositeType + "%");

        double guestWeightedAverage = 0.14016396920498844;
        //resultArray[0];
        double weekendNightsWeightedAverage = 0.15053700924104077;//resultArray[1];
        double weekNightsWeightedAverage = 0.13985050359087958;//resultArray[2];
        double reservedRoomTypeWeightedAverage = 0.1393554734395815;// resultArray[3];
        double mealWeightedAverage = 0.14519815778191378;//resultArray[4];
        double arrivalDateMonthWeightedAverage = 0.09565333847694858;// resultArray[5];
        double depositTypeWeightedAverage = 0.18924154826464734;//resultArray[6];
        double maxCancelScore = 49.8174996607572;//resultArray[7];
        double minCancelScore = 28.6614977029315;//resultArray[8];

        Double weightedLikelihoodOfCancellation = percentageCancelledTotalGuests * guestWeightedAverage +
                percentageCancelledStaysInWeekendNights * weekendNightsWeightedAverage +
                percentageCancelledStaysInWeekNights * weekNightsWeightedAverage +
                percentageCancelledReservedRoomType * reservedRoomTypeWeightedAverage +
                percentageCancelledMealType * mealWeightedAverage +
                percentageCancelledBreakPeriod * arrivalDateMonthWeightedAverage +
                percentageCancelledDepositeType * depositTypeWeightedAverage;
        System.out.println("The WeightedLikelihoodOfCancellation is: " + weightedLikelihoodOfCancellation + "%");

        Double predictedLikelihoodOfCancellation = (weightedLikelihoodOfCancellation - minCancelScore) / (maxCancelScore - minCancelScore);
        System.out.println("Predicated LikelihoodOfCancellation is: " + predictedLikelihoodOfCancellation + "%");

        double[] result = new double[3];
        result[0] = weightedLikelihoodOfCancellation;
        result[1] = predictedLikelihoodOfCancellation;
        return result;
    }

    private static int findIndex(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    private static double getPercentageCancelled(String inputFile, String type) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String headerLine = reader.readLine(); // Read the header

            // Find the indices of required columns
            int typeIndex = findIndex(headerLine.split(","), "Type");
            int percentageCancelledIndex = findIndex(headerLine.split(","), "Percentage_Cancelled");

            // Read the lines and search for the required type
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > typeIndex && values.length > percentageCancelledIndex) {
                    if (values[typeIndex].trim().equals(type)) {
                        return Double.parseDouble(values[percentageCancelledIndex]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if not found
    }
}
