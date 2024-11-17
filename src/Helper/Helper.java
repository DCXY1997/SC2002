package src.Helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.InputMismatchException;
// import java.util.List;
// import java.util.Scanner;

//Helper class to provide support functions for other classes
public class Helper {

    /**
     * Scanner object for taking user input
     */
    public static final Scanner sc = new Scanner(System.in);

    /**
     * Default constructor for initializing Scanner object
     */
    public Helper() {

    }

    /**
     * Enumeration for different time validation rules that can be combined
     */
    public static enum TimeValidationRule {
        FUTURE_DATE, // Date must be in the future
        PAST_DATE, // Date must be in the past
        WORKING_HOURS, // Time must be within working hours (e.g., 9 AM to 5 PM)
        NO_WEEKENDS, // Date cannot fall on weekends
        MAX_DURATION_24H, // Duration cannot exceed 24 hours
        MAX_DURATION_48H, // Duration cannot exceed 48 hours
        BUSINESS_HOURS        // Must be during business hours (e.g., 8 AM to 6 PM)
    }

    /**
     * Function to read an integer value from terminal
     * <p>
     *
     * Repeatedly tries to read an integer until an integer is actually being
     * read. Keeps catching the exception {@link InputMismatchException} when
     * invalid characters are entered
     *
     * @return The read integer entered in the terminal.
     */
    public static int readInt() {
        while (true) {
            try {
                int userInput = -1;
                userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input. Enter an integer!");
            }
        }
    }

    /**
     * Function to read an integer value from terminal that within the specified
     * minimum and maximum arguments.
     * <p>
     *
     * Repeatedly tries to read an integer until an integer within the specified
     * range is actually being read.
     * <p>
     * Keeps catching the exception {@link InputMismatchException} when invalid
     * characters are entered.
     * <p>
     * Keeps catching the exception {@link OutOfRange} when an integer entered
     * is lesser than the minimum or greater than the maximum value specified as
     * arguments.
     *
     * @param min minimum valid value that will be read and returned.
     * @param max maximum valid value that will be read and returned.
     * @return The read integer entered in the terminal.
     */
    public static int readInt(int min, int max) {

        while (true) {
            try {
                int userInput = -1;
                userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                if (userInput < min || userInput > max) {
                    throw new OutOfRange();
                } else {
                    return userInput;
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input. Enter an integer!");
            } catch (OutOfRange e) {
                System.out.println("Input is out of allowed range");
            }
        }
    }

    /**
     * Function to read a double value from terminal.
     * <p>
     *
     * Repeatedly tries to read a double until a double is actually being read.
     * Keeps catching the exception {@link InputMismatchException} when invalid
     * characters are entered
     *
     * @return returns the read double entered in the terminal.
     */
    public static double readDouble() {

        while (true) {
            try {
                double userInput = -1;
                userInput = sc.nextDouble();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input, Enter an double!!");
            }
        }
    }

    public static double readFloat() {

        while (true) {
            try {
                float userInput = -1;
                userInput = sc.nextFloat();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input, Enter an float!!");
            }
        }
    }

    /**
     * Reads a new line of string
     *
     * @return user input as string
     */
    public static String readString() {

        String userInput = sc.nextLine();
        return userInput;
    }

    /**
     * Method to prompt confirmation from the user. Usually for confirmation of
     * removing data
     *
     * @param message Message for confirmation prompt.
     * @return {@code true} if user input 'yes'. Otherwise, {@code false}.
     */
    public static boolean promptConfirmation(String message) {

        System.out.println(String.format("Are you sure you want to %s? (yes/no)", message));
        String userInput = sc.nextLine();
        return userInput.equals("yes");
    }

    /**
     * Method to generate unique id for hashMap key
     *
     * @param <K> Generic type for the key of the HashMap
     * @param <V> Generic type for the value of the HashMap
     * @param database Hashmap object to reference
     * @return A unique id for the database
     */
    public static <K, V> int generateUniqueId(HashMap<K, V> database) {
        if (database.size() == 0) {
            return 1;
        }
        String currentMax = "";
        for (K key : database.keySet()) {
            if (key instanceof String) {
                String currentKey = (String) key;
                if (currentKey.compareTo(currentMax) > 0) {
                    currentMax = currentKey;
                }
            }
        }
        String maxId = currentMax.replaceAll("\\D", "");
        return Integer.parseInt(maxId) + 1;
    }

    /**
     * Method to set the date for either current date or user input date
     *
     * @param now {@code true} to return the current time. Otherwise,
     * {@code false} to prompt user for new time.
     * @return String object for the date in the format "yyyy-MM-dd HH:mm"
     */
    public static String setDate(boolean now) {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (now) {
            return getTimeNow();
        }
        System.out.println("Please enter the date in this format: 'yyyy-MM-dd HH:mm'");
        String date = sc.nextLine();
        try {
            LocalDateTime Date = LocalDateTime.parse(date, format);
            date = format.format(Date);
            if (validateDate(date, format)) {
                return date;

            } else {
                System.out.println("Invalid Date");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Date format");
        }
        return "";
    }

    /**
     * Method to parse a string date in a format
     *
     * @param date Date in string
     * @param format {@link DateTimeFormatter} object for formatting of dates
     * @return {@link LocalDateTime} object after parsing the string date with
     * the formatter
     */
    public static LocalDateTime getDate(String date, DateTimeFormatter format) {
        return LocalDateTime.parse(date, format);
    }

    /**
     * Method to get current date and time
     *
     * @return String object for the date in the format "yyyy-MM-dd HH:mm"
     */
    public static String getTimeNow() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.now();
        return date.format(format);
    }

    /**
     * Method to validate date
     *
     * @param date Date in string
     * @param format {@link DateTimeFormatter} object for formatting of dates
     * @return {@code true} if date is valid. Otherwise, {@code false} if the
     * date is invalid (date is in the past)
     */
    public static boolean validateDate(String date, DateTimeFormatter format) {
        LocalDateTime Date = getDate(date, format);
        LocalDateTime now = LocalDateTime.now();
        return (Date.compareTo(now) >= 0 ? true : false);
    }

    /**
     * Function to validate a date string in the format "yyyy-MM-dd".
     *
     * @param dateString the date string
     * @return true if the date is valid, false otherwise
     */
    public static boolean isValidDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            // Try to parse the string as a LocalDate directly
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.\n");
            return false;
        }
    }

    /**
     * Function to validate time input in the format "HH:mm".
     *
     * @param timeString the time string
     * @return true if the time is valid, false otherwise
     */
    public static boolean isValidTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(timeString, formatter); // Changed to use LocalTime directly
            return true;
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:mm (e.g., 09:30)");
            return false;
        }
    }

    /**
     * Validates a single date-time value against specified validation rules
     * Returns a list of failed validation messages
     */
    public static List<String> validateDateTime(LocalDateTime dateTime, TimeValidationRule... rules) {
        List<String> failedValidations = new ArrayList<>();
        if (dateTime == null || rules == null) {
            return failedValidations;
        }

        for (TimeValidationRule rule : rules) {
            switch (rule) {
                case FUTURE_DATE:
                    if (!dateTime.isAfter(LocalDateTime.now())) {
                        failedValidations.add("The date and time must be in the future (after " + LocalDate.now() + ")");
                    }
                    break;

                case PAST_DATE:
                    if (!dateTime.isBefore(LocalDateTime.now())) {
                        failedValidations.add("The date and time must be in the past");
                    }
                    break;

                case WORKING_HOURS:
                    int hour = dateTime.getHour();
                    if (hour < 9 || hour >= 17) {
                        failedValidations.add("Time must be between 9 AM and 5 PM");
                    }
                    break;

                case NO_WEEKENDS:
                    DayOfWeek day = dateTime.getDayOfWeek();
                    if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
                        failedValidations.add("Date cannot fall on weekends");
                    }
                    break;

                case BUSINESS_HOURS:
                    int businessHour = dateTime.getHour();
                    int minute = dateTime.getMinute();
                    // Allow time between 8:00 AM (08:00) and 10:00 PM (22:00) inclusive
                    if (businessHour < 8 || (businessHour == 22 && minute > 0) || businessHour > 22) {
                        failedValidations.add("Time must be between 8 AM and 10 PM, inclusive.");
                    }
                    break;
            }
        }
        return failedValidations;
    }

    /**
     * Method to validate and prompt for time period input with customizable
     * validation rules.
     */
    public static LocalDateTime[] promptAndValidateTimeRange(
            String startPrompt,
            String endPrompt,
            TimeValidationRule... validationRules) {

        LocalDateTime start = null;
        LocalDateTime end = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (start == null || end == null || !isValidTimeRange(start, end, validationRules)) {
            // Get and validate start date-time
            System.out.println(startPrompt);
            System.out.println("Enter date (yyyy-MM-dd):");
            String startDate = readString().trim();
            if (!isValidDate(startDate)) {
                continue;
            }

            System.out.println("Enter time (HH:mm):");
            String startTime = readString().trim();
            if (!isValidTime(startTime)) {
                continue;
            }

            try {
                start = LocalDateTime.parse(startDate + " " + startTime, formatter);
                List<String> startValidationErrors = validateDateTime(start, validationRules);
                if (!startValidationErrors.isEmpty()) {
                    System.out.println("Invalid start time:");
                    startValidationErrors.forEach(error -> System.out.println("- " + error));
                    start = null;
                    continue;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date-time combination. Please try again.");
                continue;
            }

            // Get and validate end date-time
            System.out.println(endPrompt);
            System.out.println("Enter date (yyyy-MM-dd):");
            String endDate = readString().trim();
            if (!isValidDate(endDate)) {
                continue;
            }

            System.out.println("Enter time (HH:mm):");
            String endTime = readString().trim();
            if (!isValidTime(endTime)) {
                continue;
            }

            try {
                end = LocalDateTime.parse(endDate + " " + endTime, formatter);
                List<String> endValidationErrors = validateDateTime(end, validationRules);
                if (!endValidationErrors.isEmpty()) {
                    System.out.println("\nInvalid end time:");
                    endValidationErrors.forEach(error -> System.out.println("- " + error));
                    end = null;
                    continue;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date-time combination. Please try again.");
                continue;
            }

            // Check if start and end are on the same day
            if (!start.toLocalDate().isEqual(end.toLocalDate())) {
                System.out.println("Start and end date must be on the same day.\n");
                start = null;
                end = null;
                continue;
            }

            // Validate the time range
            if (!end.isAfter(start)) {
                System.out.println("End time must be after start time.");
                start = null;
                end = null;
                continue;
            }

            // Validate duration constraints
            for (TimeValidationRule rule : validationRules) {
                switch (rule) {
                    case MAX_DURATION_24H:
                        if (ChronoUnit.HOURS.between(start, end) > 24) {
                            System.out.println("Duration cannot exceed 24 hours.");
                            start = null;
                            end = null;
                        }
                        break;
                    case MAX_DURATION_48H:
                        if (ChronoUnit.HOURS.between(start, end) > 48) {
                            System.out.println("Duration cannot exceed 48 hours.");
                            start = null;
                            end = null;
                        }
                        break;
                }
            }
        }

        return new LocalDateTime[]{start, end};
    }

    /**
     * Validates if a time range is valid according to all rules
     */
    public static boolean isValidTimeRange(LocalDateTime start, LocalDateTime end, TimeValidationRule... rules) {
        if (start == null || end == null || !end.isAfter(start)) {
            return false;
        }

        List<String> startValidationErrors = validateDateTime(start, rules);
        List<String> endValidationErrors = validateDateTime(end, rules);

        // Check duration-specific rules
        for (TimeValidationRule rule : rules) {
            switch (rule) {
                case MAX_DURATION_24H:
                    if (ChronoUnit.HOURS.between(start, end) > 24) {
                        return false;
                    }
                    break;
                case MAX_DURATION_48H:
                    if (ChronoUnit.HOURS.between(start, end) > 48) {
                        return false;
                    }
                    break;
            }
        }

        return startValidationErrors.isEmpty() && endValidationErrors.isEmpty();
    }

    /**
     * Prints appropriate error messages based on the validation rules
     *
     * @param rules Array of validation rules
     */
    private static void printValidationErrors(TimeValidationRule... rules) {
        if (rules == null) {
            return;
        }

        System.out.println("Please ensure the following conditions are met:");
        for (TimeValidationRule rule : rules) {
            switch (rule) {
                case FUTURE_DATE:
                    System.out.println("- Date must be in the future");
                    break;
                case PAST_DATE:
                    System.out.println("- Date must be in the past");
                    break;
                case WORKING_HOURS:
                    System.out.println("- Time must be between 9 AM and 5 PM");
                    break;
                case NO_WEEKENDS:
                    System.out.println("- Date cannot fall on weekends");
                    break;
                case MAX_DURATION_24H:
                    System.out.println("- Duration cannot exceed 24 hours");
                    break;
                case MAX_DURATION_48H:
                    System.out.println("- Duration cannot exceed 48 hours");
                    break;
                case BUSINESS_HOURS:
                    System.out.println("- Time must be between 8 AM and 6 PM");
                    break;
            }
            System.out.println("\n");
        }
    }

    /**
     * Helper method to combine date and time into a LocalDateTime object.
     *
     * @param date the date string in format yyyy-MM-dd
     * @param time the time string in format HH:mm
     * @return the combined LocalDateTime object
     */
    public static LocalDateTime parseDateTime(String date, String time) {
        // Combine the date and time parts and parse as LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date + " " + time, formatter);
    }

    /**
     * Method to validate start and end times for an appointment. Keeps
     * prompting the user until valid times are entered (end time must be after
     * start time).
     *
     * @param scheduleDate The date of the schedule (e.g., "2024-11-05").
     * @param startPrompt The prompt message for start time input.
     * @param endPrompt The prompt message for end time input.
     * @return An array of LocalDateTime, where [0] is the start time and [1] is
     * the end time.
     */
    public static LocalDateTime[] validateAppointmentTime(String scheduleDate, String startPrompt, String endPrompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Validate start time
        LocalDateTime startTime = null;
        while (startTime == null) {
            startTime = validateTimeInput(scheduleDate, startPrompt);
        }

        // Validate end time
        LocalDateTime endTime = null;
        while (endTime == null || endTime.isBefore(startTime)) {
            endTime = validateTimeInput(scheduleDate, endPrompt);
            if (endTime != null && endTime.isBefore(startTime)) {
                System.out.println("The end time must be after the start time. Please enter a valid end time.\n");
            }
        }

        return new LocalDateTime[]{startTime, endTime};
    }

    /**
     * Method to validate a time input in HH:mm format. Repeatedly asks the user
     * to enter the time until it is valid.
     *
     * @param scheduleDate The date of the schedule to combine with the time
     * input.
     * @param prompt The prompt message to ask for the time.
     * @return The validated LocalDateTime object, or null if the input is
     * invalid.
     */
    public static LocalDateTime validateTimeInput(String scheduleDate, String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (true) {
            System.out.print(prompt);
            String timeStr = sc.nextLine();
            String fullDateTimeStr = scheduleDate + " " + timeStr;

            try {
                LocalDateTime dateTime = LocalDateTime.parse(fullDateTimeStr, formatter);
                return dateTime;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please enter the time in HH:mm format.\n");
            }
        }
    }

    /**
     * Method to check if the time difference of the input date and current time
     * exceeds 1 hour (Hotel check in / check out checking)
     *
     * @param date Date in string
     * @return {@code true} if the date does not exceed 1 hour. Otherwise,
     * {@code false}.
     */
    public static boolean LocalDateTimediff(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = getDate(date, format);
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime fromTemp = LocalDateTime.from(from);

        long hours = fromTemp.until(to, ChronoUnit.HOURS);
        fromTemp = fromTemp.plusHours(hours);

        long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
        fromTemp = fromTemp.plusMinutes(minutes);

        if (hours > 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method to calculate the days elapsed between two dates.
     *
     * @param fromDate From date in string.
     * @param toDate To date in string.
     * @return Days difference of the two dates.
     */
    public static long calculateDaysElapsed(String fromDate, String toDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = getDate(fromDate, format);
        LocalDateTime to = getDate(toDate, format);
        long daysBetween = from.until(to, ChronoUnit.DAYS);
        return daysBetween + 1;
    }

    /**
     * Method to check if fromDate is earlier than toDate
     *
     * @param fromDate From date in string.
     * @param toDate To date in string.
     * @return {@code true} if the fromDate is earlier than toDate. Otherwise,
     * {@code false}
     */
    public static boolean validateTwoDates(String fromDate, String toDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = getDate(fromDate, format);
        LocalDateTime to = getDate(toDate, format);
        return (to.compareTo(from) >= 0 ? true : false);
    }

    /**
     * Method to check if the date is weekend
     *
     * @param dateToCheck Date to check in String
     * @return {@code true} if the date to check is weekend. Otherwise,
     * {@code false}.
     */
    public static boolean checkIsDateWeekend(String dateToCheck) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = getDate(dateToCheck, format);
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    /**
     * Method to pause the application and prompt user to press the ENTER key to
     * continue using the app.
     */
    public static void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

    /**
     * Method to clear the screen of the terminal for user experience and neat
     * interface.
     */
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception err) {

        }

    }
}
