package de.mweidmann.aoc.year2020;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains the solutions to the tasks from 13.12.2020.
 *
 * @author mweidmann
 */
public class Day13 extends AbstractDay2020 {

    /**
     * Default constructor for Day13.
     */
    public Day13() {
        super(13);
    }

    @Override
    protected Number partOne() {
        int arrivalTimestamp = Integer.parseInt(this.INPUT.get(0));

        List<Integer> busIds = Arrays.stream(this.INPUT.get(1).split(","))
                .filter(busId -> !busId.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (int timestamp = arrivalTimestamp; true; timestamp++) {
            for (int busId : busIds) {
                if (timestamp % busId == 0) {
                    return busId * (timestamp - arrivalTimestamp);
                }
            }
        }
    }

    @Override
    protected Number partTwo() {
        return 0;
    }
}
