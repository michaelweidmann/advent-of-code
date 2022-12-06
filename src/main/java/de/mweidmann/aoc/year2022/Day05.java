package de.mweidmann.aoc.year2022;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Contains the solutions to the tasks from 05.12.2022.
 *
 * @author mweidmann
 */
public class Day05 extends AbstractDay2022 {

    /**
     * Default constructor for Day05.
     */
    public Day05() {
        super(5);
    }

    @Override
    protected Object partOne() {
        // split the supply stack from the instructions
        var split = this.INPUT_AS_STRING.split("\n\n");

        var supplyStack = parseSupplyStack(split[0]);
        var rearrangementProcedureSteps = parseRearrangementProcedureSteps(split[1]);
        rearrangementProcedureSteps.forEach(rearrangementProcedureStep -> rearrangementProcedureStep.applyStepPart1(supplyStack));

        return supplyStack.getResult();
    }

    @Override
    protected Object partTwo() {
        // split the supply stack from the instructions
        var split = this.INPUT_AS_STRING.split("\n\n");

        var supplyStack = parseSupplyStack(split[0]);
        var rearrangementProcedureSteps = parseRearrangementProcedureSteps(split[1]);
        rearrangementProcedureSteps.forEach(rearrangementProcedureStep -> rearrangementProcedureStep.applyStepPart2(supplyStack));

        return supplyStack.getResult();
    }

    private SupplyStack parseSupplyStack(String supplyStackToBeParsed) {
        // split every line in the stack picture
        var split = supplyStackToBeParsed.split("\n");

        // parse the stack numbers (last line in the stack picture)
        var lastLine = split[split.length - 1];
        var supplyStack = new SupplyStack(lastLine.split("\s\s\s").length);

        for (int i = split.length - 2; i >= 0; i--) {
            // remove brackets [] and replace every empty stack field with a question mark
            var stackRow = split[i].replaceAll("\\[", "")
                    .replaceAll("]", "")
                    // workaround for the first element of the stack (if it is empty)
                    .replaceFirst("^\s\s\s", "?")
                    .replaceAll("\s\s\s\s", " ?");

            var stackRowElement = stackRow.split(" ");
            for (int j = 0; j < stackRowElement.length; j++) {
                if (stackRowElement[j].equals("?")) {
                    continue;
                }
                supplyStack.add(j, stackRowElement[j]);
            }
        }

        return supplyStack;
    }

    private List<RearrangementProcedureStep> parseRearrangementProcedureSteps(String rearrangementProcedureSteps) {
        // split the instructions (which are in separate lines) and parse them into an object
        return Arrays.stream(rearrangementProcedureSteps.split("\n"))
                .map(RearrangementProcedureStep::new)
                .toList();
    }

    private static class SupplyStack {

        private final List<List<String>> stacks = new ArrayList<>();

        private SupplyStack(int numberOfStacks) {
            for (int i = 0; i < numberOfStacks; i++) {
                stacks.add(new ArrayList<>());
            }
        }

        private void add(int stackNumber, String element) {
            stacks.get(stackNumber).add(element);
        }

        private void addAll(int stackNumber, List<String> elements) {
            stacks.get(stackNumber).addAll(elements);
        }

        private String removeElement(int stackNumber) {
            var length = stacks.get(stackNumber).size();
            return stacks.get(stackNumber).remove(length - 1);
        }

        private String getResult() {
            var stringBuilder = new StringBuilder();
            for (var stack : stacks) {
                var length = stack.size();
                stringBuilder.append(stack.get(length - 1));
            }
            return stringBuilder.toString();
        }
    }

    private static class RearrangementProcedureStep {

        private static final Pattern PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

        private final int amount;
        private final int sourceStackNumber;
        private final int destinationStackNumber;

        private RearrangementProcedureStep(String rearrangementProcedureStep) {
            var matcher = PATTERN.matcher(rearrangementProcedureStep);
            if (matcher.matches()) {
                this.amount = Integer.parseInt(matcher.group(1));
                this.sourceStackNumber = Integer.parseInt(matcher.group(2));
                this.destinationStackNumber = Integer.parseInt(matcher.group(3));
            } else {
                throw new RuntimeException("Something went wrong when parsing RearrangementProcedureStep");
            }
        }

        private void applyStepPart1(SupplyStack supplyStack) {
            for (int i = 0; i < amount; i++) {
                // remove one here to get the correct index
                var element = supplyStack.removeElement(sourceStackNumber - 1);
                // remove one here to get the correct indexs
                supplyStack.add(destinationStackNumber - 1, element);
            }
        }

        private void applyStepPart2(SupplyStack supplyStack) {
            var list = new LinkedList<String>();
            for (int i = 0; i < amount; i++) {
                // remove one here to get the correct index
                list.addFirst(supplyStack.removeElement(sourceStackNumber - 1));
            }
            // remove one here to get the correct index
            supplyStack.addAll(destinationStackNumber - 1, list);
        }
    }
}
