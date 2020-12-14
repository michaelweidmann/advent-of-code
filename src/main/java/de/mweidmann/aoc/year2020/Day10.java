package de.mweidmann.aoc.year2020;

/**
 * Contains the solutions to the tasks from 10.12.2020.
 *
 * @author mweidmann
 */
public class Day10 extends AbstractDay2020 {

    /**
     * Default constructor for Day10.
     */
    public Day10() {
        super(10);
    }

    @Override
    protected Number partOne() {
        int numberOfOneJoltDiff = 0;
        int numberOfThreeJoltDiff = 0;

        this.INPUT_AS_INTEGER.sort(Integer::compareTo);

        for (int i = 0; i < this.INPUT_AS_INTEGER.size() - 1; i++) {
            if (this.INPUT_AS_INTEGER.get(i + 1) - this.INPUT_AS_INTEGER.get(i) == 1) {
                numberOfOneJoltDiff++;
            } else if (this.INPUT_AS_INTEGER.get(i + 1) - this.INPUT_AS_INTEGER.get(i) == 3) {
                numberOfThreeJoltDiff++;
            }
        }

        return numberOfOneJoltDiff * ++numberOfThreeJoltDiff;
    }

    /**
     * Divides the whole sorted input into sequences.
     * For each size of a sequence, there are a certain number of ways the Jolt connectors can be arranged.
     * If the sequence is 3 long --> 2x1 Jolt or 1x2 Jolt connector is possible needed.
     * If the sequence is 4 long --> 4x1 Jolt or 2x2 Jolt connector or 1x3 and 1x1 connector ist needed.
     * And so on.
     * Afterwards the possibilities of every sequence are multiplicated with each other to get every possible arrangement.
     *
     * @return The number of possible arrangements of Jolt connectors.
     */
    @Override
    protected Number partTwo() {
        // Maybe the first jolt connector is part of a sequence.
        this.INPUT_AS_INTEGER.add(0);
        this.INPUT_AS_INTEGER.sort(Integer::compareTo);

        int[] amountOfSequences = new int[6];
        int sizeOfSequence = 1;

        for (int i = 0; i < this.INPUT_AS_INTEGER.size() - 2; i++) {
            // If the difference of two elements is 1, then the size of sequence is increased.
            if (this.INPUT_AS_INTEGER.get(i + 1) - this.INPUT_AS_INTEGER.get(i) == 1) {
                sizeOfSequence++;
            // Otherwise the sizeOfSequence is resetted and the count for the sequence in the specific length is increased.
            } else {
                amountOfSequences[sizeOfSequence]++;
                sizeOfSequence = 1;
            }
        }

        // Add the last sequence to the calculation.
        amountOfSequences[sizeOfSequence]++;

        long answer = 1;
        answer *= Math.pow(2, amountOfSequences[3]);
        answer *= Math.pow(4, amountOfSequences[4]);
        answer *= Math.pow(7, amountOfSequences[5]);

        return answer;
    }
}
