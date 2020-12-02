package de.mweidmann.aoc.year2020;

import lombok.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains the solutions to the tasks from 02.12.2020.
 *
 * @author michael
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
     * Default constructor for Day02.
     */
    public Day02() {
        super(2);
        this.pattern = Pattern.compile(regex);
    }

    @Override
    protected Integer partOne() {
        long validPasswords = input.stream()
                .map(this::parseString)
                .filter(PasswordWithPolicy::isValidPartOne)
                .count();

        return Math.toIntExact(validPasswords);
    }

    @Override
    protected Integer partTwo() {
        long validPasswords = input.stream()
                .map(this::parseString)
                .filter(PasswordWithPolicy::isValidPartTwo)
                .count();

        return Math.toIntExact(validPasswords);
    }

    /**
     * Maps a string (following the scheme of the input) to a {@link PasswordWithPolicy} object by parsing the string.
     *
     * @param lineOfInput A line of the input.
     */
    private PasswordWithPolicy parseString(String lineOfInput) {
        Matcher matcher = pattern.matcher(lineOfInput);

        if (matcher.matches()) {
            return new PasswordWithPolicy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3).charAt(0), matcher.group(4));
        } else {
            throw new RuntimeException("Failed to match the input string with the given pattern.");
        }
    }

    /**
     * Represents a line of the input file. That means the password policy with the password.
     */
    @Value
    private static class PasswordWithPolicy {

        /**
         * Describes how often the {@link PasswordWithPolicy#character} must be represented in the {@link PasswordWithPolicy#password} minimum.
         */
        Integer min;

        /**
         * Describes how often the {@link PasswordWithPolicy#character} can be represented in the {@link PasswordWithPolicy#password} maximum.
         */
        Integer max;

        /**
         * The letter the policy is about.
         */
        Character character;

        /**
         * The password.
         */
        String password;

        /**
         * Checks whether the password corresponds to the following policy:
         * The frequency of the {@link PasswordWithPolicy#character} in the password has to be between the
         * {@link PasswordWithPolicy#min} and {@link PasswordWithPolicy#max} value.
         *
         * @return true if the password is valid, otherwise false.
         */
        protected Boolean isValidPartOne() {
            Integer frequencyOfCharacter = password.length() - password.replaceAll(character.toString(), "").length();
            return frequencyOfCharacter >= min && frequencyOfCharacter <= max;
        }

        /**
         * Checks whether the password corresponds to the following policy:
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
