package de.mweidmann.aoc.year2021;

import lombok.Data;
import lombok.Getter;
import lombok.Value;

import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Contains the solutions to the tasks from 02.12.2021.
 *
 * @author mweidmann
 */
public class Day02 extends AbstractDay2021 {

    /**
     * Default constructor for Day02.
     */
    public Day02() {
        super(2);
    }

    /**
     * Defines with a BiConsumer the logic of Part 1 and then calculates the result.
     */
    @Override
    protected Object partOne() {
        BiConsumer<Command, Position> positionUpdater = (command, position) -> {
            switch (command.direction) {
                case UP -> position.setDepth(position.getDepth() - command.value);
                case DOWN -> position.setDepth(position.getDepth() + command.value);
                case FORWARD -> position.setHorizontal(position.getHorizontal() + command.value);
            }
        };

        return calculateResult(positionUpdater);
    }

    /**
     * Defines with a BiConsumer the logic of Part 2 and then calculates the result.
     */
    @Override
    protected Object partTwo() {
        BiConsumer<Command, Position> positionUpdater = (command, position) -> {
            switch (command.direction) {
                case UP -> position.setAim(position.getAim() - command.value);
                case DOWN -> position.setAim(position.getAim() + command.value);
                case FORWARD -> {
                    position.setHorizontal(position.getHorizontal() + command.value);
                    position.setDepth(position.getDepth() + position.getAim() * command.value);
                }
            }
        };

        return calculateResult(positionUpdater);
    }

    /**
     * Iterates through the input, parses each line, updates the position and returns the result of the task.
     */
    private Integer calculateResult(BiConsumer<Command, Position> positionUpdater) {
        var position = new Position();

        for (var commandString : this.INPUT) {
            var command = parseLine(commandString);
            positionUpdater.accept(command, position);
        }
        return position.getDepth() * position.getHorizontal();
    }

    /**
     * Each line of the input represents a command. So a line gets parsed here and mapped into a Command object.
     */
    private Command parseLine(String line) {
        var splittedStrings = line.split(" ");

        var directionOptional = Arrays.stream(Direction.values())
                .filter(direction -> splittedStrings[0].equals(direction.getDirectionName()))
                .findFirst();
        var value = Integer.valueOf(splittedStrings[1]);

        return new Command(directionOptional.orElseThrow(), value);
    }

    /**
     * Represents the direction used in the command.
     */
    @Getter
    private enum Direction {
        FORWARD("forward"), DOWN("down"), UP("up");

        private final String directionName;

        Direction(String directionName) {
            this.directionName = directionName;
        }
    }

    /**
     * Represents the current position of the submarine. (Part 1 and Part 2 are represented here with the same object)
     */
    @Data
    private static class Position {
        private Integer horizontal = 0;
        private Integer depth = 0;
        private Integer aim = 0;
    }

    /**
     * Represents a command with the direction and the corresponding value.
     */
    @Value
    private static class Command {
        Direction direction;
        Integer value;
    }
}
