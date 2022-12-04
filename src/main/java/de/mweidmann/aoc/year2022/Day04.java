package de.mweidmann.aoc.year2022;

import java.util.function.BiPredicate;

/**
 * Contains the solutions to the tasks from 04.12.2022.
 *
 * @author mweidmann
 */
public class Day04 extends AbstractDay2022 {

    /**
     * Default constructor for Day04.
     */
    public Day04() {
        super(4);
    }

    @Override
    protected Object partOne() {
        return taskRunner((firstAssignment, secondAssignment) -> firstAssignment.fullyContains(secondAssignment) || secondAssignment.fullyContains(firstAssignment));
    }

    @Override
    protected Object partTwo() {
        return taskRunner(SectionAssignment::overlaps);
    }

    private long taskRunner(BiPredicate<SectionAssignment, SectionAssignment> predicate) {
        var sum = 0L;

        for (var sectionAssignmentPair : this.INPUT) {
            // Split from "2-4,6-8" to ["2-4","6-8"]
            var sectionAssignments = sectionAssignmentPair.split(",");

            var firstAssignment = parseSectionAssignment(sectionAssignments[0]);
            var secondAssignment = parseSectionAssignment(sectionAssignments[1]);

            if (predicate.test(firstAssignment, secondAssignment)) {
                sum++;
            }
        }

        return sum;
    }

    /**
     * Parse "2-4" to a SectionAssignment(minSection=2, maxSection=4)
     *
     * @param sectionAssignment The string to be parsed.
     * @return A SectionAssignment object filled by the parsed String.
     */
    private SectionAssignment parseSectionAssignment(String sectionAssignment) {
        var splitSectionAssignment = sectionAssignment.split("-");
        return new SectionAssignment(Long.parseLong(splitSectionAssignment[0]), Long.parseLong(splitSectionAssignment[1]));
    }

    private record SectionAssignment(long minSection, long maxSection) {

        private static boolean overlaps(SectionAssignment assignment1, SectionAssignment assignment2) {
            return assignment1.minSection() <= assignment2.maxSection() && assignment1.maxSection() >= assignment2.minSection();
        }

        private boolean fullyContains(SectionAssignment other) {
            return minSection <= other.minSection() && maxSection >= other.maxSection();
        }
    }
}
