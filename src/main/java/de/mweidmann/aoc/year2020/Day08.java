package de.mweidmann.aoc.year2020;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

/**
 * Contains the solutions to the tasks from 08.12.2020.
 *
 * @author mweidmann
 */
public class Day08 extends AbstractDay2020 {

    private final Map<Integer, Instruction> instructions;

    /**
     * Default constructor for Day08.
     */
    public Day08() {
        super(8);
        this.instructions = initInstructions();
    }

    /**
     * Parses the instructions and put them into map.
     * The key is the instruction whereby the value is the parsed integer after the instruction keyword.
     *
     * @return A map with all instructions of the program.
     */
    private Map<Integer, Instruction> initInstructions() {
        Map<Integer, Instruction> instructions = new HashMap<>();

        for (int i = 0; i < this.INPUT.size(); i++) {
            String instructionInput = this.INPUT.get(i);

            int number = Integer.parseInt(instructionInput.substring(4));
            Instruction instruction = new Instruction(instructionInput.substring(0, 3), number);

            instructions.put(i, instruction);
        }
        return instructions;
    }

    @Override
    protected Object partOne() {
        return calculate().getAccumulator();
    }

    /**
     * Calculates the complete instruction set.
     *
     * @return A calculation result object which contains the information whether the script was successful and the calculated number.
     */
    private CalculationResult calculate() {
        Set<Integer> alreadyVisited = new HashSet<>();

        int currentInstruction = 0;
        int accumulator = 0;

        while(!alreadyVisited.contains(currentInstruction)) {
            alreadyVisited.add(currentInstruction);

            Instruction instruction = this.instructions.get(currentInstruction);

            if (instruction == null) {
                // Script ran until the end so the result can be returned.
                return new CalculationResult(true, accumulator);
            }

            switch (instruction.getInstruction()) {
                case "acc" -> {
                    accumulator += instruction.getNumber();
                    currentInstruction++;
                }
                case "jmp" -> currentInstruction += instruction.getNumber();
                case "nop" -> currentInstruction++;
                default -> throw new RuntimeException("Invalid instruction.");
            }
        }

        return new CalculationResult(false, accumulator);
    }

    /**
     * Iterates over the instruction and makes little changes to the instruction script to get a successful script running.
     *
     * @return The result.
     */
    @Override
    protected Object partTwo() {
        for (Map.Entry<Integer, Instruction> entry : this.instructions.entrySet()) {
            Instruction instruction = entry.getValue();
            CalculationResult calculationResult = null;

            if (instruction.getInstruction().equals("nop")) {
                calculationResult = swap(instruction, "nop", "jmp");
            } else if (instruction.getInstruction().equals("jmp")) {
                calculationResult = swap(instruction, "jmp", "nop");
            }

            if (calculationResult != null && calculationResult.isRunUntilEnd()) {
                return calculationResult.getAccumulator();
            }
        }

        return 0;
    }

    /**
     * Swaps the old instruction with the new instruction and calculate the script.
     * If the script was not successful then the change is taken back.
     *
     * @param instruction The instruction which should be swapped.
     * @param oldInstruction The old instruction.
     * @param newInstruction The new instruction.
     * @return The calculation result from the script.
     */
    private CalculationResult swap(Instruction instruction, String oldInstruction, String newInstruction) {
        instruction.setInstruction(newInstruction);
        CalculationResult calculationResult = calculate();

        if (!calculationResult.isRunUntilEnd()) {
            instruction.setInstruction(oldInstruction);
        }
        return calculationResult;
    }

    @Data
    @AllArgsConstructor
    private static class Instruction {
        private String instruction;
        private Integer number;
    }

    @Data
    @AllArgsConstructor
    private static class CalculationResult {
        private boolean runUntilEnd;
        private int accumulator;
    }
}
