package de.mweidmann.aoc.year2020;

import lombok.Data;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Contains the solutions to the tasks from 21.12.2020.
 *
 * @author mweidmann
 */
public class Day21 extends AbstractDay2020 {

    private final List<Receipt> receipts;
    private final Set<String> ingredients;
    private final Set<String> allergens;

    /**
     * Default constructor for Day21.
     */
    public Day21() {
        super(21);
        this.receipts = initReceipts();
        this.ingredients = initSet(Receipt::getIngredients);
        this.allergens = initSet(Receipt::getAllergens);
    }

    /**
     * Parses the input and creates a list of all receipts.
     *
     * @return A list of all receipts.
     */
    private List<Receipt> initReceipts() {
        List<Receipt> receipts = new ArrayList<>();

        for (String inputLine : this.INPUT) {
            int endIndexIngredients = inputLine.indexOf("(");
            int startIndexAllergens = endIndexIngredients + "contains  ".length();

            String[] ingredients = inputLine.substring(0, endIndexIngredients).split(" ");
            String[] allergens = inputLine.substring(startIndexAllergens, inputLine.length() - 1).split(", ");

            receipts.add(new Receipt(ingredients, allergens));
        }

        return receipts;
    }

    /**
     * Returns a flat mapped list of a property of the {@link Receipt} object.
     * The property to be flat mapped can be specified with the mapper parameter.
     *
     * @param mapper The mapper which is mapped on a receipt object.
     * @return A set containing all flat mapped elements of a property of a {@link Receipt}
     */
    private Set<String> initSet(Function<Receipt, List<String>> mapper) {
        return receipts.stream()
                .map(mapper)
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Gets all possible combinations of ingredients to allergens.
     * Using the list of all available ingredients, the ingredients that are not a possible combination are filtered out.
     *
     * @return The number of ingredients which are not a possible combination in terms of allergens.
     */
    @Override
    protected Object partOne() {
        Map<String, List<String>> map = getPossibleCombinations();

        return this.receipts.stream()
                .map(Receipt::getIngredients)
                .flatMap(List::stream)
                .filter(ingredient -> !map.containsKey(ingredient))
                .count();
    }

    @Override
    protected Object partTwo() {
        Map<String, List<String>> possibleCombinations = getPossibleCombinations();
        Map<String, String> ingredientToAllergen = new HashMap<>();

        rec(possibleCombinations, ingredientToAllergen);

        return ingredientToAllergen.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(","));
    }

    /**
     * Gets a list of all possible combinations between an ingredient and allergens.
     * It works by iterating over all ingredients and retaining all allergens used in a receipt where the ingredient is used.
     *
     * @return A list of all possible combinations between an ingredient and allergens.
     */
    private Map<String, List<String>> getPossibleCombinations() {
        Map<String, List<String>> possibleCombinations = new HashMap<>();

        for (String ingredient : this.ingredients) {
            List<String> possibleAllergensForIngredient = new ArrayList<>(this.allergens);

            this.receipts.stream()
                    .filter(receipt -> !receipt.getIngredients().contains(ingredient))
                    .map(Receipt::getAllergens)
                    .forEach(possibleAllergensForIngredient::removeAll);

            if (!possibleAllergensForIngredient.isEmpty()) {
                possibleCombinations.put(ingredient, possibleAllergensForIngredient);
            }
        }
        return possibleCombinations;
    }

    /**
     * Gets a unique assignment of an ingredient to an allergen. It works with a recursive method.
     * Important: It works with the assumption that in the list of possible combinations a unique assignment exist.
     *
     * @param possibleCombinations The list of all currently available possible combinations between an ingredient and allergens.
     * @param ingredientToAllergen The unique mapping between an ingredient and allergen which is filled in this function.
     */
    private void rec(Map<String, List<String>> possibleCombinations, Map<String, String> ingredientToAllergen) {
        // If the possible combinations map is empty, stop the recursion.
        if (possibleCombinations.isEmpty()) {
            return;
        }

        // Gets the possible combination where a unique identification is possible to do.
        Map.Entry<String, List<String>> entry = possibleCombinations.entrySet().stream()
                .min(Comparator.comparingInt(a -> a.getValue().size()))
                .get();

        // Some clean up of the maps and the recursive call.
        String allergenToBeRemoved = entry.getValue().get(0);
        ingredientToAllergen.put(entry.getKey(), allergenToBeRemoved);

        possibleCombinations.remove(entry.getKey());
        possibleCombinations.forEach((key, value) -> value.remove(allergenToBeRemoved));

        rec(possibleCombinations, ingredientToAllergen);
    }

    @Data
    private static class Receipt {
        List<String> ingredients;
        List<String> allergens;

        private Receipt(String[] ingredients, String[] allergens) {
            this.ingredients = Arrays.stream(ingredients).collect(Collectors.toList());
            this.allergens = Arrays.stream(allergens).collect(Collectors.toList());
        }
    }
}
