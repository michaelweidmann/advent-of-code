package de.mweidmann.aoc.year2020;

import java.util.Optional;

/**
 * Contains the solutions to the tasks from 01.12.2020.
 *
 * @author mweidmann
 */
public class Day01 extends AbstractDay2020 {

    /**
     * Default constructor for Day01.
     */
    public Day01() {
        super(1);
    }

    @Override
    protected Number partOne() {
        for (Integer firstNumber : this.INPUT_AS_INTEGER) {
            Optional<Integer> secondNumber = this.INPUT_AS_INTEGER.stream()
                    .filter(number -> firstNumber + number == 2020)
                    .findFirst();

            if (secondNumber.isPresent()) {
                return firstNumber * secondNumber.get();
            }
        }

        return -1;
    }

    @Override
    protected Number partTwo() {
        for (Integer firstNumber : this.INPUT_AS_INTEGER) {
            for (Integer secondNumber : this.INPUT_AS_INTEGER) {
                for (Integer thirdNumber : this.INPUT_AS_INTEGER) {
                    if (!firstNumber.equals(secondNumber) && !secondNumber.equals(thirdNumber) && firstNumber + secondNumber + thirdNumber == 2020) {
                        return firstNumber * secondNumber * thirdNumber;
                    }
                }
            }
        }

        return -1;
    }
}
