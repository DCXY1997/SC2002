package src.Helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * A utility class that provides Helper methods and utilities for common
 * operations such as input validation, date-time processing, and user
 * interaction. The class includes methods for reading and validating user
 * input, generating unique IDs, validating date and time formats, and managing
 * console-based UI operations.
 *
 * @author Jasmine Tye, Keng Jia Chi
 * @version 1.0
 * @since 2024-11-17
 */
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
     * read. Keeps catching the exception {@link InputMismatch} when invalid
     * characters are entered
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

    /**
     * Function to read a float value from terminal.
     * <p>
     *
     * Repeatedly tries to read a float until a float is actually being read.
     * Keeps catching the exception {@link InputMismatchException} when invalid
     * characters are entered
     *
     * @return returns the read float entered in the terminal.
     */
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

    public static <K, V> int generateUniqueStaffId(HashMap<K, V> database) {
        int maxId = 0; // Initialize to 0 for the base case when the database is empty

        for (K key : database.keySet()) {
            if (key instanceof String) {
                String currentKey = (String) key;
                // Extract the numeric portion from the key
                String numericPart = currentKey.replaceAll("\\D", ""); // Remove non-digit characters
                if (!numericPart.isEmpty()) {
                    int currentId = Integer.parseInt(numericPart); // Convert to integer
                    maxId = Math.max(maxId, currentId); // Update max ID
                }
            }
        }

        return maxId + 1; // Return the next unique ID
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
     * Validates a date-time value against a set of specified validation rules.
     *
     * @param dateTime The LocalDateTime to validate.
     * @param rules The validation rules to apply.
     * @return A list of validation error messages.
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
     * Prompts the user to enter and validate a time range. Continues prompting
     * until a valid range is provided.
     *
     * @param startPrompt The message to display when asking for the start time.
     * @param endPrompt The message to display when asking for the end time.
     * @param validationRules Rules to apply when validating the time range.
     * @return An array of two {@link LocalDateTime} objects representing the
     * start and end times.
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
     * Validates if a given time range is valid based on the provided rules.
     *
     * @param start The start time of the range.
     * @param end The end time of the range.
     * @param rules The validation rules to apply.
     * @return {@code true} if the time range is valid, otherwise {@code false}.
     *
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
     * Clears the terminal screen for a neat interface experience.
     * <p>
     * Uses the system's command to clear the screen.
     * </p>
     */
    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception err) {

        }

    }
}
