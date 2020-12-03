package de.mweidmann.aoc.year2020;

import java.util.Map;

/**
 * Contains the solutions to the tasks from 03.12.2020.
 *
 * @author mweidmann
 */
public class Day03 extends AbstractDay2020 {

    /**
     * The width of the input grid to get to a field using modulo when crossing the right border.
     */
    private final Integer gridWidth;

    /**
     * Default constructor for Day03.
     */
    public Day03() {
        super(3);
        gridWidth = input.get(0).length();
    }

    @Override
    protected int partOne() {
        return numberOfCrashesOnPath(3, 1);
    }

    @Override
    protected int partTwo() {
        return numberOfCrashesOnPath(1, 1) * numberOfCrashesOnPath(3, 1)
                * numberOfCrashesOnPath(5, 1) * numberOfCrashesOnPath(7, 1)
                * numberOfCrashesOnPath(1, 2);
    }

    /**
     * Calculates the number of crashes with a tree on a path to the bottom with a given slope.
     *
     * @param xSlope The slope in the right direction for every step.
     * @param ySlope The slope in the bottom direction for every step.
     * @return The number of crashes with a tree
     */
    private int numberOfCrashesOnPath(Integer xSlope, Integer ySlope) {
        int xPosition = 0;
        int treeCrashCounter = 0;

        for (int yPosition = 0; yPosition < input.size(); yPosition += ySlope) {
            if (hasTreeOnField(xPosition, yPosition)) {
                treeCrashCounter++;
            }

            xPosition += xSlope;
        }
        return treeCrashCounter;
    }

    /**
     * Checks whether on a position (x, y) is a tree.
     *
     * @param xPosition The x position which should be checked.
     * @param yPosition The y position which should be checked.
     * @return true if on the field is a tree otherwise false.
     */
    private boolean hasTreeOnField(Integer xPosition, Integer yPosition) {
        char[] arr = input.get(yPosition).toCharArray();
        return arr[xPosition % gridWidth] == '#';
    }
}
