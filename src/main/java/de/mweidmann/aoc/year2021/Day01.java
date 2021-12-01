package de.mweidmann.aoc.year2021;

/**
 * Contains the solutions to the tasks from 01.12.2021.
 *
 * @author mweidmann
 */
public class Day01 extends AbstractDay2021 {

    /**
     * Default constructor for Day01.
     */
    public Day01() {
        super(1);
    }

    @Override
    protected Object partOne() {
        var counter = 0;

        for (int i = 1; i < this.INPUT_AS_INTEGER.size(); i++) {
            if (this.INPUT_AS_INTEGER.get(i - 1) < this.INPUT_AS_INTEGER.get(i)) {
                counter++;
            }
        }

        return counter;
    }

    @Override
    protected Object partTwo() {
        var counter = 0;

        var currentThreeMeasurement = this.INPUT_AS_INTEGER.get(0) + this.INPUT_AS_INTEGER.get(1) + this.INPUT_AS_INTEGER.get(2);
        var nextThreeMeasurement = 0;

        for (int i = 2; i < this.INPUT_AS_INTEGER.size() - 1; i++) {
            nextThreeMeasurement = currentThreeMeasurement - this.INPUT_AS_INTEGER.get(i - 2) + this.INPUT_AS_INTEGER.get(i + 1);

            if (currentThreeMeasurement < nextThreeMeasurement) {
                counter++;
            }
            currentThreeMeasurement = nextThreeMeasurement;
        }

        return counter;
    }
}
