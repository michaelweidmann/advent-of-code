package de.mweidmann.aoc.year2020;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Contains the solutions to the tasks from 05.12.2020.
 *
 * @author mweidmann
 */
public class Day05 extends AbstractDay2020 {

    /**
     * Default constructor for Day05.
     */
    public Day05() {
        super(5);
    }

    @Override
    protected int partOne() {
        Optional<Integer> opt = INPUT.stream()
                .map(String::toCharArray)
                .map(this::calculateSeatId)
                .max(Comparator.naturalOrder());

        return opt.orElse(-1);
    }

    @Override
    protected int partTwo() {
        List<Integer> seatIds = INPUT.stream()
                .map(String::toCharArray)
                .map(this::calculateSeatId)
                .collect(Collectors.toList());

        Optional<Integer> opt = seatIds.stream()
                .filter(seatId -> !seatIds.contains(seatId + 1))
                .filter(seatId -> seatIds.contains(seatId + 2))
                .findFirst();

        return opt.orElseThrow();
    }

    /**
     * Calculates the seatId of a boarding pass by finding the row and the column of the seat.
     * It works like a binary search. Afterwards the seatId is calculated with the row and column.
     *
     * @param boardingPass The boarding pass in form of a character array.
     * @return The calculated seatId.
     */
    private int calculateSeatId(char[] boardingPass) {
        double rowLow = 0;
        double rowHigh = 127;
        double rowMid;

        double columLow = 0;
        double columnHigh = 7;
        double columnMid;

        for (char c : boardingPass) {
            rowMid = Math.floor((rowLow + rowHigh) / 2);
            columnMid = Math.floor((columLow + columnHigh) / 2);

            switch (c) {
                case 'F' -> rowHigh = rowMid;
                case 'B' -> rowLow = rowMid + 1;
                case 'L' -> columnHigh = columnMid;
                case 'R' -> columLow = columnMid + 1;
                default -> throw new RuntimeException("Invalid boarding pass code.");
            }
        }

        return (int) (rowHigh * 8 + columnHigh);
    }
}
