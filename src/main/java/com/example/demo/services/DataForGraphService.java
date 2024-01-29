package com.example.demo.services;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataForGraphService {

    private static double total_cancellations_complete = 0;
    public static String[] thresholdValues = {"0.1", "0.11", "0.12", "0.13", "0.14", "0.15", "0.16", "0.17", "0.18", "0.19", "0.2",
            "0.21", "0.22", "0.23", "0.24", "0.25", "0.26", "0.27", "0.28", "0.29", "0.3",
            "0.31", "0.32", "0.33", "0.34", "0.35", "0.36", "0.37", "0.38", "0.39", "0.4",
            "0.41", "0.42", "0.43", "0.44", "0.45", "0.46", "0.47", "0.48", "0.49", "0.5",
            "0.51", "0.52", "0.53", "0.54", "0.55", "0.56", "0.57", "0.58", "0.59", "0.6",
            "0.61", "0.62", "0.63", "0.64", "0.65", "0.66", "0.67", "0.68", "0.69", "0.7",
            "0.71", "0.72", "0.73", "0.74", "0.75", "0.76", "0.77", "0.78", "0.79", "0.8",
            "0.81", "0.82", "0.83", "0.84", "0.85", "0.86", "0.87", "0.88", "0.89", "0.9"};

    public static void InsertThresholdValuesColumnToCSV() {

        String inputFilePath = "input_data_for_graph.csv";
        String outputFilePath = "output.csv";

        List<String> newColumnValues = Arrays.asList(
                "0.1", "0.11", "0.12", "0.13", "0.14", "0.15", "0.16", "0.17", "0.18", "0.19", "0.2",
                "0.21", "0.22", "0.23", "0.24", "0.25", "0.26", "0.27", "0.28", "0.29", "0.3",
                "0.31", "0.32", "0.33", "0.34", "0.35", "0.36", "0.37", "0.38", "0.39", "0.4",
                "0.41", "0.42", "0.43", "0.44", "0.45", "0.46", "0.47", "0.48", "0.49", "0.5",
                "0.51", "0.52", "0.53", "0.54", "0.55", "0.56", "0.57", "0.58", "0.59", "0.6",
                "0.61", "0.62", "0.63", "0.64", "0.65", "0.66", "0.67", "0.68", "0.69", "0.7",
                "0.71", "0.72", "0.73", "0.74", "0.75", "0.76", "0.77", "0.78", "0.79", "0.8",
                "0.81", "0.82", "0.83", "0.84", "0.85", "0.86", "0.87", "0.88", "0.89", "0.9"
        );

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int index = -1;

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < columns.length; i++) {
                    sb.append(columns[i]);
                    if (i == 3) {
                        sb.append(",");
                        if (index == -1) {
                            sb.append("Threshold");
                        } else if (index < newColumnValues.size()) {
                            sb.append(newColumnValues.get(index));
                        }
                    }
                    sb.append(",");
                }

                bw.write(sb.toString());
                bw.newLine();
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double[][] calculatePredictionAccuracyPerThreshold() {
        double[] total_cancellations_within_threshold = new double[thresholdValues.length];
        double[] total_bookings_within_threshold = new double[thresholdValues.length];
        double[] prediction_accuracy = new double[thresholdValues.length];
        double[] percentage_of_bookings_within_threshold = new double[thresholdValues.length];
        double total_cancellations_complete = 0; // Initialize total_cancellations_complete
        String csvFile = "input_data_for_graph.csv";
        String cvsSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); // Skip header line
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                total_cancellations_complete += Double.parseDouble(values[0]);

                for (int i = 0; i < thresholdValues.length; i++) {
                    if (Double.parseDouble(values[3]) > Double.parseDouble(thresholdValues[i])) {
                        total_cancellations_within_threshold[i] += Double.parseDouble(values[0]);
                        total_bookings_within_threshold[i]++;
                    }
                }
            }

            for (int i = 0; i < total_cancellations_within_threshold.length; i++) {
                if (total_bookings_within_threshold[i] != 0) {
                    prediction_accuracy[i] = total_cancellations_within_threshold[i] / total_bookings_within_threshold[i];
                } else {
                    prediction_accuracy[i] = 0;
                }

                // Calculate percentage_of_bookings_within_threshold outside the loop
                if (total_cancellations_complete != 0) {
                    percentage_of_bookings_within_threshold[i] = total_cancellations_within_threshold[i] / total_cancellations_complete;
                } else {
                    percentage_of_bookings_within_threshold[i] = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new double[][] { prediction_accuracy, percentage_of_bookings_within_threshold };
    }


    public static List<double[]> calculateSeasonalPredictionAccuracy() {
        String csvFile = "input_data_for_graph.csv";
        String cvsSplitBy = ",";
        List<double[]> predictionAccuracyResultsList = new ArrayList<>();

        for (String thresholdValue : thresholdValues) {
            double threshold = Double.parseDouble(thresholdValue);

            double[] totalCancellationsWithinThresholdPerMonth = new double[12];
            double[] totalCancelledBookingsPerMonth = new double[12];

            try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                br.readLine(); // Skip header line
                String line;

                while ((line = br.readLine()) != null) {
                    String[] values = line.split(cvsSplitBy);
                    int cancellation = Integer.parseInt(values[0]);
                    int month = Integer.parseInt(values[2]);
                    double value = Double.parseDouble(values[3]);

                    if (value > threshold && month >= 1 && month <= 12) {
                        totalCancellationsWithinThresholdPerMonth[month - 1] += cancellation;
                    }

                    if (value > threshold && month >= 1 && month <= 12) {
                        totalCancelledBookingsPerMonth[month - 1] = totalCancelledBookingsPerMonth[month - 1] + 1; // Summing cancellations for bookings
                    }
                }

                double[] prediction_accuracy = new double[12];
                for (int i = 0; i < 12; i++) {
                    if (totalCancelledBookingsPerMonth[i] != 0) {
                        prediction_accuracy[i] = totalCancellationsWithinThresholdPerMonth[i] / totalCancelledBookingsPerMonth[i];
                    } else {
                        prediction_accuracy[i] = 0;
                    }
                }

                // Store the prediction accuracy for this threshold
                predictionAccuracyResultsList.add(prediction_accuracy);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeSeasonalGraphDataToCSVFile(predictionAccuracyResultsList);
        return predictionAccuracyResultsList;
    }

    public static void writeThresholdDataToCSVFile(double[] prediction_accuracy, double[] percentage_of_bookings_within_threshold) {
        String csvFile = "input_data_for_graph.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            List<String[]> dataLines = new ArrayList<>();
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                dataLines.add(values);
            }

            System.out.println("Length of dataLines: " + dataLines.size());
            System.out.println("Length of prediction_accuracy: " + prediction_accuracy.length);
            System.out.println("Length of percentage_of_bookings_within_threshold: " + percentage_of_bookings_within_threshold.length);

            try (PrintWriter pw = new PrintWriter(new File("updated_" + csvFile))) {
                StringBuilder sb = new StringBuilder();
                sb.append("threshold,prediction_accuracy,percentage_of_bookings_within_threshold\n");

                int minSize = Math.min(dataLines.size(), Math.min(prediction_accuracy.length, percentage_of_bookings_within_threshold.length));
                for (int i = 0; i < minSize; i++) {
                    // Append values from columns 6 and 7 only
                    sb.append(dataLines.get(i)[4]); // Column 5
                    sb.append(",");
                    sb.append(prediction_accuracy[i]); // prediction_accuracy
                    sb.append(",");
                    sb.append(percentage_of_bookings_within_threshold[i]); // percentage_of_bookings_within_threshold
                    sb.append("\n");
                }

                pw.write(sb.toString());

            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeSeasonalGraphDataToCSVFile(List<double[]> predictionAccuracyResultsList) {
        String csvFile = "input_data_for_graph.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            List<String[]> dataLines = new ArrayList<>();
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                dataLines.add(values);
            }

            try (PrintWriter pw = new PrintWriter(new FileWriter("seasonal_cancellation_data.csv"))) {
                StringBuilder sb = new StringBuilder();

                // Append titles for prediction accuracy columns
                for (String threshold : thresholdValues) {
                    sb.append("Prediction Accuracy (Threshold ").append(threshold).append("),");
                }
                sb.deleteCharAt(sb.length() - 1); // Remove the extra comma at the end
                sb.append("\n");

                // Append data lines with prediction accuracy values
                int numRows = Math.min(dataLines.size(), predictionAccuracyResultsList.get(0).length); // Number of rows to iterate over
                for (int i = 0; i < numRows; i++) {
                    // Append prediction accuracy results for each threshold
                    for (double[] predictionAccuracyResults : predictionAccuracyResultsList) {
                        if (i < predictionAccuracyResults.length) {
                            sb.append(predictionAccuracyResults[i]); // Append prediction accuracy for this row
                        }
                        sb.append(",");
                    }

                    // Append existing columns after prediction accuracy columns
                    if (i < dataLines.size()) {
                        sb.append(String.join(",", dataLines.get(i)));
                    }
                    sb.append("\n");
                }

                pw.write(sb.toString());

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

