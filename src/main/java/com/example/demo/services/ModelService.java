package com.example.demo.services;

import com.example.demo.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class ModelService {

    public static double[] createModel() {
        double[] result = new double[9];
        try {
            Scanner scanner = new Scanner(new File("hotel_bookings_data.csv"));
            Map<GuestType, Integer> countGuestCancelled = new EnumMap<>(GuestType.class);
            Map<GuestType, Integer> countGuestNotCancelled = new EnumMap<>(GuestType.class);
            Map<WeekendNights, Integer> countWeekendNightsCancelled = new EnumMap<>(WeekendNights.class);
            Map<WeekendNights, Integer> countWeekendNightsNotCancelled = new EnumMap<>(WeekendNights.class);
            Map<WeekNights, Integer> countWeekNightsCancelled = new EnumMap<>(WeekNights.class);
            Map<WeekNights, Integer> countWeekNightsNotCancelled = new EnumMap<>(WeekNights.class);
            Map<ReservedRoomType, Integer> countReservedRoomTypeCancelled = new EnumMap<>(ReservedRoomType.class);
            Map<ReservedRoomType, Integer> countReservedRoomTypeNotCancelled = new EnumMap<>(ReservedRoomType.class);
            Map<Meal, Integer> countMealCancelled = new EnumMap<>(Meal.class);
            Map<Meal, Integer> countMealNotCancelled = new EnumMap<>(Meal.class);
            Map<ArrivalDateMonth, Integer> countArrivalDateMonthCancelled = new EnumMap<>(ArrivalDateMonth.class);
            Map<ArrivalDateMonth, Integer> countArrivalDateMonthNotCancelled = new EnumMap<>(ArrivalDateMonth.class);
            Map<DepositType, Integer> countDepositTypeCancelled = new EnumMap<>(DepositType.class);
            Map<DepositType, Integer> countDepositTypeNotCancelled = new EnumMap<>(DepositType.class);
            Map<CategoryType, Double> sumPercentageCancelled = new EnumMap<>(CategoryType.class);
            Map<CategoryType, Integer> countTypes = new EnumMap<>(CategoryType.class);


            // Initialize counts
            for (GuestType gt : GuestType.values()) {
                countGuestCancelled.put(gt, 0);
                countGuestNotCancelled.put(gt, 0);
            }
            for (WeekendNights wn : WeekendNights.values()) {
                countWeekendNightsCancelled.put(wn, 0);
                countWeekendNightsNotCancelled.put(wn, 0);
            }
            for (WeekNights wn : WeekNights.values()) {
                countWeekNightsCancelled.put(wn, 0);
                countWeekNightsNotCancelled.put(wn, 0);
            }
            for (ReservedRoomType rrt : ReservedRoomType.values()) {
                countReservedRoomTypeCancelled.put(rrt, 0);
                countReservedRoomTypeNotCancelled.put(rrt, 0);
            }
            for (Meal m : Meal.values()) {
                countMealCancelled.put(m, 0);
                countMealNotCancelled.put(m, 0);
            }
            for (ArrivalDateMonth adm : ArrivalDateMonth.values()) {
                countArrivalDateMonthCancelled.put(adm, 0);
                countArrivalDateMonthNotCancelled.put(adm, 0);
            }
            for (DepositType dt : DepositType.values()) {
                countDepositTypeCancelled.put(dt, 0);
                countDepositTypeNotCancelled.put(dt, 0);
            }


            // Skip header line
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String[] columns = scanner.nextLine().split(",");
                int isCanceled = Integer.parseInt(columns[0]);
                GuestType totalGuests = GuestType.fromLabel(columns[1]);
                WeekendNights weekendNights = WeekendNights.fromLabel(columns[2]);
                WeekNights weekNights = WeekNights.fromLabel(columns[3]);
                ReservedRoomType reservedRoomType = ReservedRoomType.fromLabel(columns[4]);
                Meal meal = Meal.fromLabel(columns[5]);
                ArrivalDateMonth arrivalDateMonth = ArrivalDateMonth.fromLabel(columns[6]);
                DepositType depositType = DepositType.fromLabel(columns[7]);

                if (totalGuests != null) {
                    if (isCanceled == 1) {
                        countGuestCancelled.put(totalGuests, countGuestCancelled.get(totalGuests) + 1);
                    } else if (isCanceled == 0) {
                        countGuestNotCancelled.put(totalGuests, countGuestNotCancelled.get(totalGuests) + 1);
                    }
                }


                if (weekendNights != null) {
                    if (isCanceled == 1) {
                        countWeekendNightsCancelled.put(weekendNights, countWeekendNightsCancelled.get(weekendNights) + 1);
                    } else if (isCanceled == 0) {
                        countWeekendNightsNotCancelled.put(weekendNights, countWeekendNightsNotCancelled.get(weekendNights) + 1);
                    }
                }

                if (weekNights != null) {
                    if (isCanceled == 1) {
                        countWeekNightsCancelled.put(weekNights, countWeekNightsCancelled.get(weekNights) + 1);
                    } else if (isCanceled == 0) {
                        countWeekNightsNotCancelled.put(weekNights, countWeekNightsNotCancelled.get(weekNights) + 1);
                    }
                }

                if (reservedRoomType != null) {
                    if (isCanceled == 1) {
                        countReservedRoomTypeCancelled.put(reservedRoomType, countReservedRoomTypeCancelled.get(reservedRoomType) + 1);
                    } else if (isCanceled == 0) {
                        countReservedRoomTypeNotCancelled.put(reservedRoomType, countReservedRoomTypeNotCancelled.get(reservedRoomType) + 1);
                    }
                }

                if (meal != null) {
                    if (isCanceled == 1) {
                        countMealCancelled.put(meal, countMealCancelled.get(meal) + 1);
                    } else if (isCanceled == 0) {
                        countMealNotCancelled.put(meal, countMealNotCancelled.get(meal) + 1);
                    }
                }

                if (arrivalDateMonth != null) {
                    if (isCanceled == 1) {
                        countArrivalDateMonthCancelled.put(arrivalDateMonth, countArrivalDateMonthCancelled.get(arrivalDateMonth) + 1);
                    } else if (isCanceled == 0) {
                        countArrivalDateMonthNotCancelled.put(arrivalDateMonth, countArrivalDateMonthNotCancelled.get(arrivalDateMonth) + 1);
                    }
                }

                if (depositType != null) {
                    if (isCanceled == 1) {
                        countDepositTypeCancelled.put(depositType, countDepositTypeCancelled.get(depositType) + 1);
                    } else if (isCanceled == 0) {
                        countDepositTypeNotCancelled.put(depositType, countDepositTypeNotCancelled.get(depositType) + 1);
                    }
                }

            }

            PrintWriter writer = new PrintWriter(new File("output.csv"));
            writer.println("Category,Type,Count_Cancelled,Count_Not_Cancelled,Percentage_Cancelled,Category_Average");

            double sumPercentageCancelledGuest = 0;
            double sumPercentageCancelledWeekendNights = 0;
            double sumPercentageCancelledWeekNights = 0;
            double sumPercentageCancelledReservedRoomType = 0;
            double sumPercentageCancelledMeal = 0;
            double sumPercentageCancelledArrivalDateMonth = 0;
            double sumPercentageCancelledDepositType = 0;
            double minPercentageCancelledGuest = 100;
            double maxPercentageCancelledGuest = 0;
            double minPercentageCancelledWeekendNights = 100;
            double maxPercentageCancelledWeekendNights = 0;
            double minPercentageCancelledWeekNights = 100;
            double maxPercentageCancelledWeekNights = 0;
            double minPercentageCancelledReservedRoomType = 100;
            double maxPercentageCancelledReservedRoomType = 0;
            double minPercentageCancelledMeal = 100;
            double maxPercentageCancelledMeal = 0;
            double minPercentageCancelledArrivalDateMonth = 100;
            double maxPercentageCancelledArrivalDateMonth = 0;
            double minPercentageCancelledDepositType = 100;
            double maxPercentageCancelledDepositType = 0;



            for (GuestType gt : GuestType.values()) {
                int total = countGuestCancelled.get(gt) + countGuestNotCancelled.get(gt);
                double percentageCancelled = total > 0 ? (double) countGuestCancelled.get(gt) * 100 / total : 0;
                sumPercentageCancelledGuest = sumPercentageCancelledGuest + percentageCancelled;
                if (percentageCancelled < minPercentageCancelledGuest) {
                    minPercentageCancelledGuest = percentageCancelled;
                }
                if (percentageCancelled > maxPercentageCancelledGuest) {
                    maxPercentageCancelledGuest = percentageCancelled;
                }
                writer.println(CategoryType.GUESTS.getLabel() + "," + gt.getLabel() + "," + countGuestCancelled.get(gt) + "," + countGuestNotCancelled.get(gt) + "," + percentageCancelled);
            }
            double categoryAverageGuest = sumPercentageCancelledGuest / GuestType.values().length;

            for (WeekendNights wn : WeekendNights.values()) {
                int total = countWeekendNightsCancelled.get(wn) + countWeekendNightsNotCancelled.get(wn);
                double percentageCancelled = total > 0 ? (double) countWeekendNightsCancelled.get(wn) * 100 / total : 0;
                sumPercentageCancelledWeekendNights = sumPercentageCancelledWeekendNights + percentageCancelled;
                if (percentageCancelled < minPercentageCancelledWeekendNights) {
                    minPercentageCancelledWeekendNights = percentageCancelled;
                }
                if (percentageCancelled > maxPercentageCancelledWeekendNights) {
                    maxPercentageCancelledWeekendNights = percentageCancelled;
                }
                writer.println(CategoryType.WEEKEND_NIGHTS.getLabel() + "," + wn.getLabel() + "," + countWeekendNightsCancelled.get(wn) + "," + countWeekendNightsNotCancelled.get(wn) + "," + percentageCancelled);
            }
            double categoryAverageWeekendNights = sumPercentageCancelledWeekendNights / WeekendNights.values().length;

            for (WeekNights wn : WeekNights.values()) {
                int total = countWeekNightsCancelled.get(wn) + countWeekNightsNotCancelled.get(wn);
                double percentageCancelled = total > 0 ? (double) countWeekNightsCancelled.get(wn) * 100 / total : 0;
                sumPercentageCancelledWeekNights = sumPercentageCancelledWeekNights + percentageCancelled;
                if (percentageCancelled < minPercentageCancelledWeekNights) {
                    minPercentageCancelledWeekNights = percentageCancelled;
                }
                if (percentageCancelled > maxPercentageCancelledWeekNights) {
                    maxPercentageCancelledWeekNights = percentageCancelled;
                }

                writer.println(CategoryType.WEEK_NIGHTS.getLabel() + "," + wn.getLabel() + "," + countWeekNightsCancelled.get(wn) + "," + countWeekNightsNotCancelled.get(wn) + "," + percentageCancelled);
            }

            double categoryAverageWeekNights = sumPercentageCancelledWeekNights / WeekNights.values().length;

            for (ReservedRoomType rrt : ReservedRoomType.values()) {
                int total = countReservedRoomTypeCancelled.get(rrt) + countReservedRoomTypeNotCancelled.get(rrt);
                double percentageCancelled = total > 0 ? (double) countReservedRoomTypeCancelled.get(rrt) * 100 / total : 0;
                sumPercentageCancelledReservedRoomType = sumPercentageCancelledReservedRoomType + percentageCancelled;
                if (percentageCancelled < minPercentageCancelledReservedRoomType) {
                    minPercentageCancelledReservedRoomType = percentageCancelled;
                }
                if (percentageCancelled > maxPercentageCancelledReservedRoomType) {
                    maxPercentageCancelledReservedRoomType = percentageCancelled;
                }
                writer.println(CategoryType.RESERVED_ROOM_TYPE.getLabel() + "," + rrt.getLabel() + "," + countReservedRoomTypeCancelled.get(rrt) + "," + countReservedRoomTypeNotCancelled.get(rrt) + "," + percentageCancelled);
            }
            double categoryAverageReservedRoomType = sumPercentageCancelledReservedRoomType / ReservedRoomType.values().length;


            for (Meal m : Meal.values()) {
                int total = countMealCancelled.get(m) + countMealNotCancelled.get(m);
                double percentageCancelled = total > 0 ? (double) countMealCancelled.get(m) * 100 / total : 0;
                sumPercentageCancelledMeal = sumPercentageCancelledMeal + percentageCancelled;
                if (percentageCancelled < minPercentageCancelledMeal) {
                    minPercentageCancelledMeal = percentageCancelled;
                }
                if (percentageCancelled > maxPercentageCancelledMeal) {
                    maxPercentageCancelledMeal = percentageCancelled;
                }
                writer.println(CategoryType.MEAL.getLabel() + "," + m.getLabel() + "," + countMealCancelled.get(m) + "," + countMealNotCancelled.get(m) + "," + percentageCancelled);
            }
            double categoryAverageMeal = sumPercentageCancelledMeal / Meal.values().length;

            for (ArrivalDateMonth adm : ArrivalDateMonth.values()) {
                int total = countArrivalDateMonthCancelled.get(adm) + countArrivalDateMonthNotCancelled.get(adm);
                double percentageCancelled = total > 0 ? (double) countArrivalDateMonthCancelled.get(adm) * 100 / total : 0;
                sumPercentageCancelledArrivalDateMonth = sumPercentageCancelledArrivalDateMonth + percentageCancelled;
                if (percentageCancelled < minPercentageCancelledArrivalDateMonth) {
                    minPercentageCancelledArrivalDateMonth = percentageCancelled;
                }
                if (percentageCancelled > maxPercentageCancelledArrivalDateMonth) {
                    maxPercentageCancelledArrivalDateMonth = percentageCancelled;
                }
                writer.println(CategoryType.ARRIVAL_DATE_MONTH.getLabel() + "," + adm.getLabel() + "," + countArrivalDateMonthCancelled.get(adm) + "," + countArrivalDateMonthNotCancelled.get(adm) + "," + percentageCancelled);
            }
            double categoryAverageArrivalDateMonth = sumPercentageCancelledArrivalDateMonth / ArrivalDateMonth.values().length;


            for (DepositType dt : DepositType.values()) {
                int total = countDepositTypeCancelled.get(dt) + countDepositTypeNotCancelled.get(dt);
                double percentageCancelled = total > 0 ? (double) countDepositTypeCancelled.get(dt) * 100 / total : 0;
                if (percentageCancelled < minPercentageCancelledDepositType) {
                    minPercentageCancelledDepositType = percentageCancelled;
                }
                if (percentageCancelled > maxPercentageCancelledDepositType) {
                    maxPercentageCancelledDepositType = percentageCancelled;
                }
                sumPercentageCancelledDepositType = sumPercentageCancelledDepositType + percentageCancelled;
                writer.println(CategoryType.DEPOSIT_TYPE.getLabel() + "," + dt.getLabel() + "," + countDepositTypeCancelled.get(dt) + "," + countDepositTypeNotCancelled.get(dt) + "," + percentageCancelled);
            }
            double categoryAverageDepositType = sumPercentageCancelledDepositType / DepositType.values().length;


            writer.close();

            System.out.println(categoryAverageGuest);
            System.out.println(categoryAverageWeekendNights);
            System.out.println(categoryAverageWeekNights);
            System.out.println(categoryAverageReservedRoomType);
            System.out.println(categoryAverageMeal);
            System.out.println(categoryAverageArrivalDateMonth);
            System.out.println(categoryAverageDepositType);

            double total_weight = (categoryAverageGuest + categoryAverageWeekendNights + categoryAverageWeekNights + categoryAverageReservedRoomType + categoryAverageMeal + categoryAverageArrivalDateMonth + categoryAverageDepositType);
            double Guest_Weighted_Average = categoryAverageGuest / total_weight;
            double WeekendNights_Weighted_Average = categoryAverageWeekendNights / total_weight;
            double WeekNights_Weighted_Average = categoryAverageWeekNights / total_weight;
            double ReservedRoomType_Weighted_Average = categoryAverageReservedRoomType / total_weight;
            double Meal_Weighted_Average = categoryAverageMeal / total_weight;
            double ArrivalDateMonth_Weighted_Average = categoryAverageArrivalDateMonth / total_weight;
            double DepositType_Weighted_Average = categoryAverageDepositType / total_weight;

            double maxCancelScore = Guest_Weighted_Average * maxPercentageCancelledGuest + WeekendNights_Weighted_Average * maxPercentageCancelledWeekendNights + WeekNights_Weighted_Average * maxPercentageCancelledWeekNights + ReservedRoomType_Weighted_Average * maxPercentageCancelledReservedRoomType + Meal_Weighted_Average * maxPercentageCancelledMeal + ArrivalDateMonth_Weighted_Average * maxPercentageCancelledArrivalDateMonth + DepositType_Weighted_Average * maxPercentageCancelledDepositType;
            double minCancelScore = Guest_Weighted_Average * minPercentageCancelledGuest + WeekendNights_Weighted_Average * minPercentageCancelledWeekendNights + WeekNights_Weighted_Average * minPercentageCancelledWeekNights + ReservedRoomType_Weighted_Average * minPercentageCancelledReservedRoomType + Meal_Weighted_Average * minPercentageCancelledMeal + ArrivalDateMonth_Weighted_Average * minPercentageCancelledArrivalDateMonth + DepositType_Weighted_Average * minPercentageCancelledDepositType;

            System.out.println("Weighted Average for Guest: " + Guest_Weighted_Average);
            System.out.println("Weighted Average for WeekendNights: " + WeekendNights_Weighted_Average);
            System.out.println("Weighted Average for WeekNights: " + WeekNights_Weighted_Average);
            System.out.println("Weighted Average for ReservedRoomType: " + ReservedRoomType_Weighted_Average);
            System.out.println("Weighted Average for Meal: " + Meal_Weighted_Average);
            System.out.println("Weighted Average for ArrivalDateMonth: " + ArrivalDateMonth_Weighted_Average);
            System.out.println("Weighted Average for DepositType: " + DepositType_Weighted_Average);
            System.out.println(maxCancelScore);
            System.out.println(minCancelScore);

            double calculatedValue = 35;

            double predictedValue = (calculatedValue - minCancelScore) / (maxCancelScore - minCancelScore);
            System.out.println(predictedValue);


            result[0] = Guest_Weighted_Average;
            result[1] = WeekendNights_Weighted_Average;
            result[2] = WeekNights_Weighted_Average;
            result[3] = ReservedRoomType_Weighted_Average;
            result[4] = Meal_Weighted_Average;
            result[5] = ArrivalDateMonth_Weighted_Average;
            result[6] = DepositType_Weighted_Average;
            result[7] = maxCancelScore;
            result[8] = minCancelScore;




        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }




}
