package Isep.webtechno.utils;

import java.time.LocalDate;
import java.util.Arrays;

public class BrowserService {

    public static boolean checkDatePattern(String date) { // Checks if a given date respects the pattern YYYY-MM-DD
        return date.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
    }

    public static LocalDate stringToLocalDate(String date) { // Convert a String of type YYYY-MM-DD to LocalDate object
        String[] dateStrings = date.split("-");
        int[] dateIntegers = new int[3];
        try {
            dateIntegers = Arrays.stream(dateStrings).mapToInt(Integer::parseInt).toArray();
        } catch (Exception e) {
            System.out.println("Got a problem converting the string date to LocalDate object !");
            e.printStackTrace();
        }
        return LocalDate.of(dateIntegers[0], dateIntegers[1], dateIntegers[2]);
    }

    public static boolean isDateAfter(LocalDate secondDate, LocalDate firstDate) { // Checks the order of two given dates
        return secondDate.isAfter(firstDate);
    }
}
