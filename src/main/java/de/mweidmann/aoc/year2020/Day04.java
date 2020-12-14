package de.mweidmann.aoc.year2020;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Contains the solutions to the tasks from 04.12.2020.
 *
 * @author mweidmann
 */
public class Day04 extends AbstractDay2020 {

    /**
     * Map containing for every passport field the corresponding Regex Pattern.
     */
    private final Map<String, Pattern> FIELD_TO_PATTERN = Map.of("byr", Pattern.compile("200[0-2]|19[2-9][0-9]"),
            "iyr", Pattern.compile("201[0-9]|2020"),
            "eyr", Pattern.compile("202[0-9]|2030"),
            "hgt", Pattern.compile("1([5-8][0-9]|9[0-3])cm|(59|6[0-9]|7[0-6])in"),
            "hcl", Pattern.compile("#[0-9a-f]{6}"),
            "ecl", Pattern.compile("amb|blu|brn|gry|grn|hzl|oth"),
            "pid", Pattern.compile("[0-9]{9}"));

    /**
     * Default constructor for Day04.
     */
    public Day04() {
        super(4);
    }

    @Override
    protected Number partOne() {
        return countValidPassports(this::isValidPartOne);
    }

    @Override
    protected Number partTwo() {
        return countValidPassports(this::isValidPartTwo);
    }

    /**
     * This method counts valid passwords according to some rules provided by the validator predicate.
     * Here is each step explained:
     * - split input String by "\n\n" to get for each passport a separate String.
     * - replace "\n" by " " to eliminate all new lines in a passport String and to get a passport in one line.
     * - split by " " to get every passport field in a separate String.
     *
     * After these steps the validator validates each passport.
     *
     * @param validator The rules in form of a predicate.
     * @return The number of valid passports.
     */
    private long countValidPassports(Predicate<String[]> validator) {
        return Arrays.stream(INPUT_AS_STRING.split("\n\n"))
                .map(string -> string.replace("\n", " "))
                .map(string -> string.split(" "))
                .filter(validator)
                .count();
    }

    /**
     * Checks if all required passport fields are given.
     *
     * @param passportFields The passport fields in form of a String array. (One entry contains one passport field "key:value")
     * @return True if the passport is valid otherwise false.
     */
    private boolean isValidPartOne(String[] passportFields) {
        List<String> usedPassportFields = Arrays.stream(passportFields)
                .map(passportField -> passportField.substring(0, 3))
                .collect(Collectors.toList());

        return usedPassportFields.containsAll(FIELD_TO_PATTERN.keySet());
    }

    /**
     * Checks if all required passport fields are given and if the values match the required pattern.
     *
     * @param passportFields The passport fields in form of a String array. (One entry contains one passport field "key:value")
     * @return True if the passport is valid otherwise false.
     */
    private boolean isValidPartTwo(String[] passportFields) {
        boolean matchesPattern = Arrays.stream(passportFields)
                .filter(passportField -> !passportField.startsWith("cid"))
                .map(passportField -> passportField.split(":"))
                .allMatch(this::matchesPattern);

        return matchesPattern && isValidPartOne(passportFields);
    }

    /**
     * Checks if a passport field matches the required pattern.
     *
     * @param passportField The passport field split into key and value.
     * @return True if the passport field matches the pattern otherwise false.
     */
    private boolean matchesPattern(String[] passportField) {
        Matcher matcher = FIELD_TO_PATTERN.get(passportField[0]).matcher(passportField[1]);
        return matcher.matches();
    }
}
