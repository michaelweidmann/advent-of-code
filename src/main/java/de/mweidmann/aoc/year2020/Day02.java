package de.mweidmann.aoc.year2020;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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
    private final String REGEX = "(\\d+)-(\\d+) ([a-z]): (.+)";

    /**
     * The pattern which compiles the regular expression defined in {@link Day02#REGEX}.
     */
    private final Pattern PATTERN;

    /**
     * A list containing the input of the task in form of {@link PasswordWithPolicy} objects.
     */
    private final List<PasswordWithPolicy> INPUT_PASSWORDS_WITH_POLICIES;

    /**
     * Default constructor for Day02.
     */
    public Day02() {
        super(2);
        this.PATTERN = Pattern.compile(REGEX);
        this.INPUT_PASSWORDS_WITH_POLICIES = initializeInputPasswordWithPolicies();
    }

    @Override
    protected Number partOne() {
        return countValidPasswords(PasswordWithPolicy::isValidPartOne);
    }

    @Override
    protected Number partTwo() {
        return countValidPasswords(PasswordWithPolicy::isValidPartTwo);
    }

    /**
     * Counts valid passwords according to their policy.
     *
     * @param validator The policy of password in form of a Predicate.
     * @return The number of valid passwords.
     */
    private long countValidPasswords(Predicate<PasswordWithPolicy> validator) {
        return INPUT_PASSWORDS_WITH_POLICIES.stream()
                .filter(validator)
                .count();
    }

    /**
     * Associates all {@link de.mweidmann.aoc.utils.AbstractDay#INPUT} strings (according to the input scheme)
     * with one {@link PasswordWithPolicy} object each by analyzing and mapping the string.
     *
     * @return A list of {@link PasswordWithPolicy} objects where each object corresponds to a line in the input file.
     */
    private List<PasswordWithPolicy> initializeInputPasswordWithPolicies() {
        List<PasswordWithPolicy> list = new ArrayList<>();

        // For every line in the input create a password policy.
        INPUT.forEach(lineOfInput -> {
            Matcher matcher = PATTERN.matcher(lineOfInput);

            if (!matcher.matches()) {
                throw new RuntimeException("Failed to match the input string with the given pattern.");
            }
            list.add(new PasswordWithPolicy(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), matcher.group(3).charAt(0), matcher.group(4)));
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
        protected boolean isValidPartOne() {
            int frequencyOfCharacter = password.length() - password.replaceAll(character.toString(), "").length();
            return frequencyOfCharacter >= min && frequencyOfCharacter <= max;
        }

        /**
         * Checks whether the {@link PasswordWithPolicy#password} corresponds to the following policy:
         * The {@link PasswordWithPolicy#character} must occur at the position {@link PasswordWithPolicy#min} or {@link PasswordWithPolicy#max}.
         *
         * @return true if the password is valid, otherwise false.
         */
        protected boolean isValidPartTwo() {
            return (password.charAt(min - 1) == character && password.charAt(max - 1) != character)
                    || (password.charAt(min - 1) != character && password.charAt(max - 1) == character);
        }
    }
}
