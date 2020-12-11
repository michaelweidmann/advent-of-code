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
     * TODO: document this mess.
     */
    @Override
    protected Number partTwo() {
        this.INPUT_AS_INTEGER.add(0);
        this.INPUT_AS_INTEGER.sort(Integer::compareTo);

        int[] amountOfSequences = new int[6];
        int sizeOfSequence = 1;

        for (int i = 0; i < this.INPUT_AS_INTEGER.size() - 2; i++) {
            if (this.INPUT_AS_INTEGER.get(i + 1) - this.INPUT_AS_INTEGER.get(i) == 1) {
                sizeOfSequence++;
            } else {
                amountOfSequences[sizeOfSequence]++;
                sizeOfSequence = 1;
            }
        }

        amountOfSequences[sizeOfSequence]++;

        long answer = 1;
        answer *= Math.pow(2, amountOfSequences[3]);
        answer *= Math.pow(4, amountOfSequences[4]);
        answer *= Math.pow(7, amountOfSequences[5]);

        return answer;
    }
}
