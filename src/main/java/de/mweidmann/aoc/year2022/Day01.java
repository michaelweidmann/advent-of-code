package de.mweidmann.aoc.year2022;

import java.util.Arrays;
import java.util.Collections;

/**
 * Contains the solutions to the tasks from 01.12.2022.
 *
 * @author mweidmann
 */
public class Day01 extends AbstractDay2022 {

    /**
     * Default constructor for Day01.
     */
    public Day01() {
        super(1);
    }

    @Override
    protected Object partOne() {
        var max = Long.MIN_VALUE;
        var sum = 0L;

        for (var inputLine : this.INPUT) {
            if (!inputLine.isBlank()) {
                sum += Long.parseLong(inputLine);
                continue;
            }

            if (sum > max) {
                max = sum;
            }
            sum = 0L;
        }

        return max;
    }

    @Override
    protected Object partTwo() {
        var maximumList = Arrays.asList(Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE);
        var sum = 0L;

        for (var inputLine : this.INPUT) {
            if (!inputLine.isBlank()) {
                sum += Long.parseLong(inputLine);
                continue;
            }

            var smallestMaximumElement = Collections.min(maximumList);
            var indexOfSmallestMaximumElement = maximumList.indexOf(smallestMaximumElement);
            if (sum > smallestMaximumElement) {
                maximumList.set(indexOfSmallestMaximumElement, sum);
            }
            sum = 0L;
        }

        return maximumList.stream().mapToLong(Long::longValue).sum();
    }
}
