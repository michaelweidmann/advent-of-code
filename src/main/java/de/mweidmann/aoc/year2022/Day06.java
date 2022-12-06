package de.mweidmann.aoc.year2022;

import java.util.HashSet;

/**
 * Contains the solutions to the tasks from 06.12.2022.
 *
 * @author mweidmann
 */
public class Day06 extends AbstractDay2022 {

    /**
     * Default constructor for Day06.
     */
    public Day06() {
        super(6);
    }

    @Override
    protected Object partOne() {
        return taskRunner(4);
    }

    @Override
    protected Object partTwo() {
        return taskRunner(14);
    }

    private long taskRunner(int packetSize) {
        var signal = this.INPUT_AS_STRING.toCharArray();

        for (int i = 0; i <= signal.length - packetSize; i++) {
            if (containsDuplicates(signal, packetSize, i)) {
                continue;
            }

            // need to return the index plus the packetSize for correct counting (index 0 --> counted 1) and to get the position of the last signal in this packet
            return i + packetSize;
        }

        throw new RuntimeException("No packet marker found in the data stream");
    }

    private boolean containsDuplicates(char[] signal, int packetSize, int offset) {
        var set = new HashSet<Character>();

        for (int i = offset; i < offset + packetSize; i++) {
            set.add(signal[i]);
        }

        return set.size() != packetSize;
    }
}
