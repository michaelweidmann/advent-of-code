package de.mweidmann.aoc.year2020;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Contains the solutions to the tasks from 22.12.2020.
 *
 * @author mweidmann
 */
public class Day22 extends AbstractDay2020 {

    private final LinkedList<Integer> initialCardsPlayer1;
    private final LinkedList<Integer> initialCardsPlayer2;

    /**
     * Default constructor for Day21.
     */
    public Day22() {
        super(22);
        this.initialCardsPlayer1 = this.getInitialCards(1);
        this.initialCardsPlayer2 = this.getInitialCards(2);
    }

    /**
     * Parses the input for a specific player passed by the parameter.
     *
     * @param playerNumber The player number from which the deck should be parsed.
     * @return A list containing the parsed cards of a player.
     */
    private LinkedList<Integer> getInitialCards(int playerNumber) {
        LinkedList<Integer> cards = new LinkedList<>();

        String[] cardsSplittedByPlayer = this.INPUT_AS_STRING.split("\n\n");
        String[] cardsToBeParsed = cardsSplittedByPlayer[playerNumber - 1].split("\n");

        Arrays.stream(cardsToBeParsed, 1, cardsToBeParsed.length)
                .map(Integer::parseInt)
                .forEach(cards::add);

        return cards;
    }

    @Override
    protected Object partOne() {
        LinkedList<Integer> stackPlayer1 = new LinkedList<>(initialCardsPlayer1);
        LinkedList<Integer> stackPlayer2 = new LinkedList<>(initialCardsPlayer2);

        while(!stackPlayer1.isEmpty() && !stackPlayer2.isEmpty()) {
            processRound(stackPlayer1, stackPlayer2, null);
        }

        return calculateEndResult(stackPlayer1.isEmpty() ? stackPlayer2 : stackPlayer1);
    }

    private void processRound(LinkedList<Integer> stackPlayer1, LinkedList<Integer> stackPlayer2, @Nullable RoundWinner roundWinner) {
        int cardPlayer1 = stackPlayer1.removeFirst();
        int cardPlayer2 = stackPlayer2.removeFirst();

        if (roundWinner != null) {
            if (roundWinner.equals(RoundWinner.PLAYER_1)) {
                stackPlayer1.addLast(cardPlayer1);
                stackPlayer1.addLast(cardPlayer2);
            } else if (roundWinner.equals(RoundWinner.PLAYER_2)) {
                stackPlayer2.addLast(cardPlayer2);
                stackPlayer2.addLast(cardPlayer1);
            }
        } else {
            if (cardPlayer1 > cardPlayer2) {
                stackPlayer1.addLast(cardPlayer1);
                stackPlayer1.addLast(cardPlayer2);
            } else {
                stackPlayer2.addLast(cardPlayer2);
                stackPlayer2.addLast(cardPlayer1);
            }
        }
    }

    private long calculateEndResult(LinkedList<Integer> winnerStack) {
        return IntStream.range(0, winnerStack.size())
                .map(index -> winnerStack.get(index) * (winnerStack.size() - index))
                .sum();
    }

    @Override
    protected Object partTwo() {
        rec(initialCardsPlayer1, initialCardsPlayer2);
        return calculateEndResult(initialCardsPlayer1.isEmpty() ? initialCardsPlayer2 : initialCardsPlayer1);
    }

    private RoundWinner rec(LinkedList<Integer> stackPlayer1, LinkedList<Integer> stackPlayer2) {
        Set<List<Integer>> previousConstellations = new HashSet<>();

        while(!stackPlayer1.isEmpty() && !stackPlayer2.isEmpty()) {
            if (!previousConstellations.add(new ArrayList<>(stackPlayer1))) {
                return RoundWinner.PLAYER_1;
            }

            int cardPlayer1 = stackPlayer1.getFirst();
            int cardPlayer2 = stackPlayer2.getFirst();

            RoundWinner roundWinner = null;

            if (cardPlayer1 <= stackPlayer1.size() - 1 && cardPlayer2 <= stackPlayer2.size() - 1) {
                roundWinner = rec(new LinkedList<>(stackPlayer1.subList(1, cardPlayer1 + 1)), new LinkedList<>(stackPlayer2.subList(1, cardPlayer2 + 1)));
            }

            processRound(stackPlayer1, stackPlayer2, roundWinner);
        }

        return stackPlayer1.isEmpty() ? RoundWinner.PLAYER_2 : RoundWinner.PLAYER_1;
    }

    private enum RoundWinner {
        PLAYER_1,
        PLAYER_2
    }
}
