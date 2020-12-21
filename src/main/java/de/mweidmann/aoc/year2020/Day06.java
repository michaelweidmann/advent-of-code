package de.mweidmann.aoc.year2020;

import com.google.common.primitives.Chars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Contains the solutions to the tasks from 06.12.2020.
 *
 * @author mweidmann
 */
public class Day06 extends AbstractDay2020 {

    /**
     * Default constructor for Day06.
     */
    public Day06() {
        super(6);
    }

    /**
     * Calculates how many distinct yes-answers of a group exist.
     * First of all the String is splitted into a list of Strings.
     * Every String in this list contains every answer of all members of one group.
     * Then a distinct count of characters is done.
     *
     * @return The sum of yes answers of a group.
     */
    @Override
    protected Object partOne() {
        return Arrays.stream(INPUT_AS_STRING.split("\n\n"))
                // Get every yes answer into one line without line breaks.
                .map(answers -> answers.replace("\n", ""))
                // Count the distinct answers.
                .mapToLong(answers -> answers.chars().distinct().count())
                .sum();
    }

    /**
     * Calculates how many answers of a group exist, where every member voted yes.
     * First the input String is prepared. Afterwards there is a list of Strings.
     * Every String in this List contain all answers which are by a space separated.
     * Now there is a for each iteration over these lists:
     * - Create a list of Strings where every String represents the answer of a user.
     * - Get the characters of the first answer and save them into a list.
     * - Iterate over the answers of a user and retain all characters which are also in the character list.
     * - Return the size.
     *
     * @return Return how many questions are answered with yes by all group members.
     */
    @Override
    protected Object partTwo() {
        return Arrays.stream(INPUT_AS_STRING.split("\n\n"))
                // Get every yes answer into one line without line breaks.
                .map(answers -> answers.replace("\n", " "))
                // Get the answers to those question where everyone answered yes.
                .mapToInt(answers -> {
                    // This list contains from every user every answer.
                    List<String> userAnswers = Arrays.asList(answers.split(" "));
                    // This list contains the answers to which every user answered yes.
                    List<Character> answersYesFromEveryone = new ArrayList<>(Chars.asList(userAnswers.get(0).toCharArray()));

                    userAnswers.stream()
                            .map(String::toCharArray)
                            .map(Chars::asList)
                            .forEach(answersYesFromEveryone::retainAll);
                    return answersYesFromEveryone.size();
                })
                .sum();
    }
}
