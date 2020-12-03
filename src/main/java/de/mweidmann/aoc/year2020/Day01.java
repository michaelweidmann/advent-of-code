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
    protected int partOne() {
        for (Integer firstNumber : this.inputAsNumbers) {
            Optional<Integer> secondNumber = this.inputAsNumbers.stream()
                    .filter(number -> firstNumber + number == 2020)
                    .findFirst();

            if (secondNumber.isPresent()) {
                return firstNumber * secondNumber.get();
            }
        }

        return -1;
    }

    @Override
    protected int partTwo() {
        for (Integer firstNumber : this.inputAsNumbers) {
            for (Integer secondNumber : this.inputAsNumbers) {
                for (Integer thirdNumber : this.inputAsNumbers) {
                    if (!firstNumber.equals(secondNumber) && !secondNumber.equals(thirdNumber) && firstNumber + secondNumber + thirdNumber == 2020) {
                        return firstNumber * secondNumber * thirdNumber;
                    }
                }
            }
        }

        return -1;
    }
}
