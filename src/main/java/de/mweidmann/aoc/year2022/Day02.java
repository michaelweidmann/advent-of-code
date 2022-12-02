package de.mweidmann.aoc.year2022;

import java.util.Map;

/**
 * Contains the solutions to the tasks from 02.12.2022.
 *
 * @author mweidmann
 */
public class Day02 extends AbstractDay2022 {

    /**
     * Default constructor for Day02.
     */
    public Day02() {
        super(2);
    }

    @Override
    protected Object partOne() {
        var allPossibleResults = Map.of(
                "A", Map.of("X", 3L, "Y", 6L, "Z", 0L),
                "B", Map.of("X", 0L, "Y", 3L, "Z", 6L),
                "C", Map.of("X", 6L, "Y", 0L, "Z", 3L)
        );

        var sum = 0L;
        for (var rockPaperScissorsRound : this.INPUT) {
            var shapes = rockPaperScissorsRound.split(" ");
            sum += getRatingFromShape(shapes[1]);
            sum += allPossibleResults.get(shapes[0]).get(shapes[1]);
        }
        return sum;
    }

    private Long getRatingFromShape(String shape) {
        return switch (shape) {
            case "X" -> 1L;
            case "Y" -> 2L;
            case "Z" -> 3L;
            default -> throw new RuntimeException("Invalid value for shape.");
        };
    }

    @Override
    protected Object partTwo() {
        var opponentShapeToNeededResultToRatingFromShape = Map.of(
                "A", Map.of("X", 3L, "Y", 1L, "Z", 2L),
                "B", Map.of("X", 1L, "Y", 2L, "Z", 3L),
                "C", Map.of("X", 2L, "Y", 3L, "Z", 1L)
        );

        var sum = 0L;
        for (var rockPaperScissorsRound : this.INPUT) {
            var shapeAndNeededResult = rockPaperScissorsRound.split(" ");
            sum += getRatingFromNeededResult(shapeAndNeededResult[1]);
            sum += opponentShapeToNeededResultToRatingFromShape.get(shapeAndNeededResult[0]).get(shapeAndNeededResult[1]);
        }

        return sum;
    }

    private Long getRatingFromNeededResult(String neededResult) {
        return switch (neededResult) {
            case "X" -> 0L;
            case "Y" -> 3L;
            case "Z" -> 6L;
            default -> throw new RuntimeException("Invalid value for needed result.");
        };
    }
}
