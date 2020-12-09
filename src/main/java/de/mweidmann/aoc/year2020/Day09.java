package de.mweidmann.aoc.year2020;

import java.util.HashSet;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Contains the solutions to the tasks from 09.12.2020.
 *
 * @author mweidmann
 */
public class Day09 extends AbstractDay2020 {

    /**
     * Default constructor for Day09.
     */
    public Day09() {
        super(9);
    }

    /**
     * Iterates over the list and 
     *
     * @return
     */
    @Override
    protected Number partOne() {
        OptionalInt firstInvalidId = IntStream.range(25, this.INPUT_AS_LONG.size())
                .filter(currentId -> !isValidNumber(currentId))
                .findFirst();

        if (firstInvalidId.isEmpty()) {
            return 0;
        }
        return this.INPUT_AS_LONG.get(firstInvalidId.getAsInt());
    }

    /**
     * Checks whether number behind the currentId which is passed is a valid number according the encryption.
     *
     * @param currentId The currentId from the number in the list can be fetched.
     * @return True if the number is valid, otherwise false.
     */
    private boolean isValidNumber(int currentId) {
        return IntStream.range(currentId - 25, currentId)
                .mapToLong(this.INPUT_AS_LONG::get)
                .filter(firstNumber -> IntStream.range(currentId - 25, currentId)
                        .mapToLong(this.INPUT_AS_LONG::get)
                        .filter(secondNumber -> firstNumber != secondNumber)
                        .filter(secondNumber -> firstNumber + secondNumber == this.INPUT_AS_LONG.get(currentId))
                        .count() > 0)
                .count() > 0;
    }

    @Override
    protected Number partTwo() {
        final long invalidNumber = 1212510616L;
        final Set<Long> numbers = new HashSet<>();

        boolean found = false;
        int currentId = 0;

        while (!found) {
            long sum = 0;

            for (int i = currentId; i < this.INPUT_AS_LONG.size(); i++) {
                long currentNumber = this.INPUT_AS_LONG.get(i);
                numbers.add(currentNumber);
                sum += currentNumber;

                if (sum > invalidNumber) {
                    currentId++;
                    numbers.clear();
                    break;
                } else if (sum == invalidNumber) {
                    found = true;
                    break;
                }
            }
        }

        OptionalLong lowestNumber = numbers.stream().mapToLong(e -> e).min();
        OptionalLong highestNumber = numbers.stream().mapToLong(e -> e).max();

        if (lowestNumber.isEmpty()) {
            return 0;
        }

        return lowestNumber.getAsLong() + highestNumber.getAsLong();
    }
}
