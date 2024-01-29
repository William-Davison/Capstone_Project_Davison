package com.example.demo;

import com.example.demo.model.ArrivalDateMonth;
import com.example.demo.model.GuestType;
import com.example.demo.services.DataForGraphService;
import com.example.demo.services.PredictionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.model.GuestType.*;
import static com.example.demo.model.GuestType.FOUR_OR_MORE_GUESTS;

import static com.example.demo.services.DataForGraphService.calculatePredictionAccuracyPerThreshold;
import static com.example.demo.services.DataForGraphService.calculateSeasonalPredictionAccuracy;
import static com.example.demo.services.ModelService.createModel;
import static com.example.demo.services.PredictionService.predictionService;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {



        SpringApplication.run(DemoApplication.class, args);
        // Testing calculateSeasonalPredictionAccuracy
        System.out.println("Testing calculateSeasonalPredictionAccuracy:");
        List<double[]> seasonalPredictionAccuracy = DataForGraphService.calculateSeasonalPredictionAccuracy();
        for (int i = 0; i < seasonalPredictionAccuracy.size(); i++) {
            System.out.println("Threshold " + DataForGraphService.thresholdValues[i]);
            double[] accuracy = seasonalPredictionAccuracy.get(i);
            for (int j = 0; j < accuracy.length; j++) {
                System.out.println("Month " + (j + 1) + ": " + accuracy[j]);
            }
        }

        // Testing calculatePredictionAccuracyPerThreshold
        System.out.println("\nTesting calculatePredictionAccuracyPerThreshold:");
        double[][] predictionAccuracyResults = DataForGraphService.calculatePredictionAccuracyPerThreshold();
        double[] predictionAccuracy = predictionAccuracyResults[0];
        double[] percentageOfBookingsWithinThreshold = predictionAccuracyResults[1];
        for (int i = 0; i < predictionAccuracy.length; i++) {
            System.out.println("Threshold " + DataForGraphService.thresholdValues[i]);
            System.out.println("Prediction Accuracy: " + predictionAccuracy[i]);

        }
    }
}