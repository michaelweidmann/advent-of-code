package de.mweidmann.aoc.utils;

import java.util.List;

/**
 * The abstract representation of a day in any year.
 *
 * @author mweidmann
 */
public abstract class AbstractDay {

    protected final List<String> input;
    protected final List<Integer> inputAsNumbers;

    private final int year;
    private final int day;

    /**
     * A constructor for an abstract day in any year.
     *
     * @param year The year to be represented.
     * @param day  The day to be represented.
     */
    public AbstractDay(int year, int day) {
        this.year = year;
        this.day = day;
        this.input = Utils.readFile(year, day);
        this.inputAsNumbers = Utils.convertListToInteger(this.input);
    }

    /**
     * Contains the solution for the first part of the task of a day.
     */
    protected abstract Integer partOne();

    /**
     * Contains the solution for the second part of the task of a day.
     */
    protected abstract Integer partTwo();

    /**
     * Runs all two parts of the day, benchmarks them and prints the results.
     */
    public void runAll() {
        long startTimePartOne = System.nanoTime();
        Integer resultPartOne = partOne();

        long startTimePartTwo = System.nanoTime();
        Integer resultPartTwo = partTwo();

        long endTime = System.nanoTime();

        System.out.println("Day %s:".formatted(day));
        System.out.println("Result Part 1: " + resultPartOne);
        System.out.println("Result Part 2: " + resultPartTwo);

        System.out.println("\nPart 1 took " + (startTimePartTwo - startTimePartOne) / 1000000.0 + "ms");
        System.out.println("Part 2 took " + (endTime - startTimePartTwo) / 1000000.0 + "ms");
        System.out.println("Together it took " + (endTime - startTimePartOne) / 1000000.0 + "ms");
    }
}
