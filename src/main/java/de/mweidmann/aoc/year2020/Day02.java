package de.mweidmann.aoc.year2020;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the solutions to the tasks from 02.12.2020.
 *
 * @author mweidmann
 */
public class Day02 extends AbstractDay2020 {

    /**
     * A regular expression matching the following string: (number)-(number) (character): (any string)
     */
    private final String regex = "(\\d+)-(\\d+) ([a-z]): (.+)";

    /**
     * The pattern which compiles the regular expression defined in {@link Day02#regex}.
     */
    private final Pattern pattern;

    /**
     * A list containing the input of the task in form of {@link PasswordWithPolicy} objects.
     */
    private final List<PasswordWithPolicy> inputPasswordWithPolicies;

    /**
     * Default constructor for Day02.
     */
    public Day02() {
        super(2);
        this.pattern = Pattern.compile(regex);
        inputPasswordWithPolicies = initializeInputPasswordWithPolicies();
    }

    @Override
    protected int partOne() {
        long validPasswords = inputPasswordWithPolicies.stream()
                .filter(PasswordWithPolicy::isValidPartOne)
                .count();

        return Math.toIntExact(validPasswords);
    }

    @Override
    protected int partTwo() {
        long validPasswords = inputPasswordWithPolicies.stream()
                .filter(PasswordWithPolicy::isValidPartTwo)
                .count();

        return Math.toIntExact(validPasswords);
    }

    /**
     * Associates all {@link de.mweidmann.aoc.utils.AbstractDay#input} strings (according to the input scheme)
     * with one {@link PasswordWithPolicy} object each by analyzing and mapping the string.
     *
     * @return A list of {@link PasswordWithPolicy} objects where each object corresponds to a line in the input file.
     */
    private List<PasswordWithPolicy> initializeInputPasswordWithPolicies() {
        List<PasswordWithPolicy> list = new ArrayList<>();

        input.forEach(lineOfInput -> {
            Matcher matcher = pattern.matcher(lineOfInput);

            if (matcher.matches()) {
                list.add(
                        new PasswordWithPolicy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3).charAt(0), matcher.group(4))
                );
            } else {
                throw new RuntimeException("Failed to match the input string with the given pattern.");
            }
        });

        return list;
    }

    /**
     * Represents a line of the input file. That means the password policy with the password.
     */
    @Value
    private static class PasswordWithPolicy {

        /**
         * Describes how often the {@link PasswordWithPolicy#character} must be represented in the
         * {@link PasswordWithPolicy#password} minimum.
         */
        int min;

        /**
         * Describes how often the {@link PasswordWithPolicy#character} can be represented in the
         * {@link PasswordWithPolicy#password} maximum.
         */
        int max;

        /**
         * The character the policy is about.
         */
        Character character;

        /**
         * The password.
         */
        String password;

        /**
         * Checks whether the {@link PasswordWithPolicy#password} corresponds to the following policy:
         * The frequency of the {@link PasswordWithPolicy#character} in the password has to be between the
         * {@link PasswordWithPolicy#min} and {@link PasswordWithPolicy#max} value.
         *
         * @return true if the password is valid, otherwise false.
         */
        protected Boolean isValidPartOne() {
            int frequencyOfCharacter = password.length() - password.replaceAll(character.toString(), "").length();
            return frequencyOfCharacter >= min && frequencyOfCharacter <= max;
        }

        /**
         * Checks whether the {@link PasswordWithPolicy#password} corresponds to the following policy:
         * The {@link PasswordWithPolicy#character} must occur at the position {@link PasswordWithPolicy#min} or {@link PasswordWithPolicy#max}.
         *
         * @return true if the password is valid, otherwise false.
         */
        protected Boolean isValidPartTwo() {
            return (password.charAt(min - 1) == character && password.charAt(max - 1) != character)
                    || (password.charAt(min - 1) != character && password.charAt(max - 1) == character);
        }
    }
}
