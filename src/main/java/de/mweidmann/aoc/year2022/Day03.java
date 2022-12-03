package de.mweidmann.aoc.year2022;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains the solutions to the tasks from 03.12.2022.
 *
 * @author mweidmann
 */
public class Day03 extends AbstractDay2022 {

    /**
     * Default constructor for Day03.
     */
    public Day03() {
        super(3);
    }

    @Override
    protected Object partOne() {
        var sum = 0L;
        for (var rucksack : this.INPUT) {
            var mid = rucksack.length() / 2;

            var firstRucksackCompartment = convertToSet(rucksack.substring(0, mid).toCharArray());
            var secondRucksackCompartment = convertToSet(rucksack.substring(mid).toCharArray());

            var duplicate = findDuplicate(firstRucksackCompartment, secondRucksackCompartment);
            sum += getPriority(duplicate);
        }
        return sum;
    }

    @SafeVarargs
    private char findDuplicate(Set<Character>... sets) {
        var firstSet = sets[0];

        for (int i = 1; i < sets.length; i++) {
            firstSet.retainAll(sets[i]);
        }
        return firstSet.stream().findFirst().orElseThrow();
    }

    private Set<Character> convertToSet(char[] arr) {
        var set = new HashSet<Character>();
        for (var element : arr) {
            set.add(element);
        }
        return set;
    }

    private long getPriority(char rucksackElement) {
        // ASCII magic: https://www.w3resource.com/w3r_images/java-basic-image-exercise-41.png
        if (rucksackElement >= 97 && rucksackElement <= 122) {
            return rucksackElement % 97 + 1;
        } else if (rucksackElement >= 65 && rucksackElement <= 90) {
            return rucksackElement % 65 + 27;
        } else {
            throw new RuntimeException("Invalid rucksack element.");
        }
    }

    @Override
    protected Object partTwo() {
        var sum = 0L;
        for (int i = 0; i < this.INPUT.size(); i += 3) {
            var firstRucksack = convertToSet(this.INPUT.get(i).toCharArray());
            var secondRucksack = convertToSet(this.INPUT.get(i + 1).toCharArray());
            var thirdRucksack = convertToSet(this.INPUT.get(i + 2).toCharArray());

            var duplicate = findDuplicate(firstRucksack, secondRucksack, thirdRucksack);
            sum += getPriority(duplicate);
        }
        return sum;
    }
}
