package de.mweidmann.aoc.year2020;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Contains the solutions to the tasks from 11.12.2020.
 *
 * @author mweidmann
 */
public class Day11 extends AbstractDay2020 {

    private final Map<Coordinate, Character> grid;

    /**
     * Default constructor for Day11.
     */
    public Day11() {
        super(11);
        this.grid = this.init();
    }

    private Map<Coordinate, Character> init() {
        Map<Coordinate, Character> grid = new HashMap<>();

        // Iteration over every row (y-coordinate) of the grid.
        for (int y = 0; y < this.INPUT.size(); y++) {
            char[] row = this.INPUT.get(y).toCharArray();

            for (int x = 0; x < row.length; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                grid.put(coordinate, row[x]);
            }
        }
        return grid;
    }

    @Override
    protected Number partOne() {
        Map<Coordinate, Character> oldGrid = this.grid;
        Map<Coordinate, Character> newGrid = new HashMap<>();

        while (true) {
            simulateRound(oldGrid, newGrid);

            if (oldGrid.equals(newGrid)) {
                break;
            }
            oldGrid = newGrid;
            newGrid = new HashMap<>();
        }

        return oldGrid.values().stream()
                .filter(seat -> seat == '#')
                .count();
    }

    private void simulateRound(Map<Coordinate, Character> oldMap, Map<Coordinate, Character> newMap) {
        for (Map.Entry<Coordinate, Character> entry : oldMap.entrySet()) {
            int occupiedSeats = occupiedSeats(oldMap, entry.getKey());

            if (entry.getValue() == 'L' && occupiedSeats == 0) {
                newMap.put(entry.getKey(), '#');
            } else if (entry.getValue() == '#' && occupiedSeats >= 4) {
                newMap.put(entry.getKey(), 'L');
            } else {
                newMap.put(entry.getKey(), entry.getValue());
            }
        }
    }

    private int occupiedSeats(Map<Coordinate, Character> oldMap, Coordinate coordinate) {
        return (int) coordinate.getNeighbors().stream()
                .map(oldMap::get)
                .filter(Objects::nonNull)
                .filter(character -> character == '#')
                .count();
    }

    @Override
    protected Number partTwo() {
        return 0;
    }

    @Data
    @NoArgsConstructor
    private static class Coordinate {

        private int xPosition;
        private int yPosition;

        @EqualsAndHashCode.Exclude
        private List<Coordinate> neighbors;

        public Coordinate(int xPosition, int yPosition) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.neighbors = initNeighbors();
        }

        private List<Coordinate> initNeighbors() {
            List<Coordinate> neighbors = new ArrayList<>();

            for (int x = this.getXPosition() - 1; x <= this.getXPosition() + 1; x++) {
                for (int y = this.getYPosition() - 1; y <= this.getYPosition() + 1; y++) {
                    // To avoid an infinite loop, the constuctor with all arguments is not called.
                    Coordinate coordinate = new Coordinate();

                    coordinate.setXPosition(x);
                    coordinate.setYPosition(y);

                    neighbors.add(coordinate);
                }
            }
            // Remove this object.
            neighbors.remove(this);

            return neighbors;
        }
    }
}
