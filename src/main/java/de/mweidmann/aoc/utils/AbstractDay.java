package de.mweidmann.aoc.utils;

import java.util.List;

/**
 * The abstract representation of a day in any year.
 *
 * @author mweidmann
 */
public abstract class AbstractDay {

    protected final List<String> INPUT;
    protected final List<Integer> INPUT_AS_INTEGER;
    protected final List<Long> INPUT_AS_LONG;
    protected final String INPUT_AS_STRING;

    private final int DAY;

    /**
     * A constructor for an abstract day in any year.
     *
     * @param year The year to be represented.
     * @param day  The day to be represented.
     */
    protected AbstractDay(int year, int day) {
        this.DAY = day;
        this.INPUT = Utils.readFile(year, day);
        this.INPUT_AS_INTEGER = Utils.convertListToInteger(this.INPUT);
        this.INPUT_AS_LONG = Utils.convertListToLong(this.INPUT);
        this.INPUT_AS_STRING = Utils.convertListOfStringsToString(this.INPUT);
    }

    /**
     * Contains the solution for the first part of the task of a day.
     *
     * @return The solution.
     */
    protected abstract Object partOne();

    /**
     * Contains the solution for the second part of the task of a day.
     *
     * @return The solution.
     */
    protected abstract Object partTwo();

    /**
     * Runs all two parts of the day, benchmarks them and prints the results.
     */
    public void runAll() {
        long startTimePartOne = System.nanoTime();
        Object resultPartOne = partOne();

        long startTimePartTwo = System.nanoTime();
        Object resultPartTwo = partTwo();

        long endTime = System.nanoTime();

        System.out.println("Day " + DAY + ":");
        System.out.println("Result Part 1: " + resultPartOne);
        System.out.println("Result Part 2: " + resultPartTwo);

        System.out.println("\nPart 1 took " + (startTimePartTwo - startTimePartOne) / 1000000.0 + "ms");
        System.out.println("Part 2 took " + (endTime - startTimePartTwo) / 1000000.0 + "ms");
        System.out.println("Together it took " + (endTime - startTimePartOne) / 1000000.0 + "ms");
    }
}
