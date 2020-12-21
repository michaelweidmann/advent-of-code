package de.mweidmann.aoc.year2020;

import lombok.Getter;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Contains the solutions to the tasks from 07.12.2020.
 *
 * @author mweidmann
 */
public class Day07 extends AbstractDay2020 {

    /**
     * The corresponding pattern to the suitcase regex.
     */
    private final Pattern SUITCASE_PATTERN;

    /**
     * The corresponding pattern to the contents regex.
     */
    private final Pattern CONTENTS_PATTERN;

    /**
     * A list containing all possible trades corresponding to the rules.
     */
    private final List<Trade> trades;

    /**
     * Default constructor for Day07.
     */
    public Day07() {
        super(7);
        this.SUITCASE_PATTERN = Pattern.compile("([a-z]+ [a-z]+) bags contain");
        this.CONTENTS_PATTERN = Pattern.compile("([\\d]+) ([a-z]+ [a-z]+) bags?");
        this.trades = INPUT.stream()
                .map(fileLine -> new Trade(fileLine, SUITCASE_PATTERN, CONTENTS_PATTERN))
                .collect(Collectors.toList());
    }

    @Override
    protected Object partOne() {
        return getNumberOfBagColors("shiny gold", new HashSet<>()).size() - 1;
    }

    /**
     * Recursive method which returns to an inputColor how many bag colors can eventually contain at least one bag in the inputColor.
     *
     * @param inputColor    The color of the bag in the input.
     * @param visitedColors All already visited colors.
     * @return The number of bag colors which can eventually contain at least one bag in the inputColor.
     */
    private Set<String> getNumberOfBagColors(String inputColor, Set<String> visitedColors) {
        visitedColors.add(inputColor);

        // Gets all trades where the inputColor is in the output.
        List<Trade> possibleTrades = trades.stream()
                .filter(trade -> trade.getOutput().keySet().stream()
                        .anyMatch(output -> output.equals(inputColor)))
                .collect(Collectors.toList());

        // For every possible trade (to get the current color) calculate the number of bag colors which can contain the input color.
        possibleTrades.forEach(possibleTrade -> getNumberOfBagColors(possibleTrade.getInput(), visitedColors));
        return visitedColors;
    }

    @Override
    protected Object partTwo() {
        return solve("shiny gold", 1) - 1;
    }

    /**
     * Recursive method which calculates how many bags a bag can maximum contain.
     *
     * @param inputBag The bag to calculate the maximum amount of bags for.
     * @param numberOfBags The current number of bags.
     * @return How many bags the inputBag can contain.
     */
    private int solve(String inputBag, int numberOfBags) {
        return this.trades.stream()
                // Get all trades which can be made from the input bag.
                .filter(trade -> trade.getInput().equals(inputBag))
                // Get the output of the trades.
                .map(Trade::getOutput)
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                // For every trade solve it recursively with the current number of bags.
                // Take the return value of the recursive function and multiplicate it with how many bags are possible to carry in the current bag.
                .mapToInt(entry -> solve(entry.getKey(), numberOfBags) * entry.getValue())
                .sum() + numberOfBags;
    }

    /**
     * Represents what for bag colors a bag can take.
     */
    private static class Trade {

        /**
         * The outer bag color.
         */
        @Getter
        private final String input;

        /**
         * A map containing the bag color and how many times it fits into the {@link Trade#input}
         */
        @Getter
        private final Map<String, Integer> output;

        private Trade(String rule, Pattern suitcasePattern, Pattern contentsPattern) {
            this.input = getSuitcase(rule, suitcasePattern);
            this.output = getContents(rule, contentsPattern);
        }

        /**
         * Gets with a regular expression the input color of the trade.
         *
         * @param rule The complete rule.
         * @param suitcasePattern The compiled regex pattern.
         * @return The input color.
         */
        private String getSuitcase(String rule, Pattern suitcasePattern) {
            Matcher matcher = suitcasePattern.matcher(rule);

            if (!matcher.find()) {
                throw new RuntimeException("Wrong rule, cannot find any matches.");
            }

            return matcher.group(1);
        }

        /**
         * Gets with a regular expression all trades that can be done with the color.
         *
         * @param rule The complete rule.
         * @param contentsPattern The compiled regex pattern.
         * @return A map containing all possible trades of the input bag color.0
         */
        private Map<String, Integer> getContents(String rule, Pattern contentsPattern) {
            Map<String, Integer> suitcaseToNumber = new HashMap<>();
            Matcher matcher = contentsPattern.matcher(rule);

            while (matcher.find()) {
                suitcaseToNumber.put(matcher.group(2), Integer.parseInt(matcher.group(1)));
            }
            return suitcaseToNumber;
        }
    }
}
