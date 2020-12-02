package de.mweidmann.aoc.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * A utility class with methods needed across the project.
 *
 * @author mweidmann
 */
public class Utils {

    /**
     * Reads the input file into a list of strings.
     *
     * @param year The year of the current task to get the right resource.
     * @param day The day of the current task to get the right resource.
     * @return A list of strings. Each string describes one line of the input file.
     */
    public static List<String> readFile(int year, int day) {
        File file = new File("src/main/resources/%s/%02d-day/input".formatted(year, day));

        try {
            return FileUtils.readLines(file, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Converts a given list of strings to a list of integers if possible.
     *
     * @param stringList A list of strings to be converted.
     * @return A list with successfully converted integers or an empty list if the conversion failed.
     */
    public static List<Integer> convertListToInteger(List<String> stringList) {
        List<Integer> integerList = new ArrayList<>();

        for (String string : stringList) {
            try {
                Integer parseInt = Integer.parseInt(string);
                integerList.add(parseInt);
            } catch (NumberFormatException e) {
                return new ArrayList<>();
            }
        }

        return integerList;
    }
}
