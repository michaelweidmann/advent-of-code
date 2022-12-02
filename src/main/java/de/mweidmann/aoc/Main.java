package de.mweidmann.aoc;

import de.mweidmann.aoc.utils.AbstractDay;
import de.mweidmann.aoc.year2022.Day01;

/**
 * The starting point of this program.
 *
 * @author mweidmann
 */
public class Main {

    /**
     * The main method.
     *
     * @param args Arguments passed to the program.
     */
    public static void main(String[] args) {
        AbstractDay day = new Day01();

        day.runAll();
    }
}
