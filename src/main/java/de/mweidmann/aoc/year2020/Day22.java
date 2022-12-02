package de.mweidmann.aoc.year2020;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Contains the solutions to the tasks from 22.12.2020.
 *
 * @author mweidmann
 */
public class Day22 extends AbstractDay2020 {

    private final LinkedList<Integer> stackPlayer1;
    private final LinkedList<Integer> stackPlayer2;

    /**
     * Default constructor for Day21.
     */
    public Day22() {
        super(22);
        this.stackPlayer1 = this.getInitialStacks(1);
        this.stackPlayer2 = this.getInitialStacks(2);
    }

    /**
     * Parses the input for a specific player passed by the parameter.
     *
     * @param playerNumber The player number from which the deck should be parsed.
     * @return A list containing the parsed cards of a player.
     */
    private LinkedList<Integer> getInitialStacks(int playerNumber) {
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
        LinkedList<Integer> stackPlayer1 = new LinkedList<>(this.stackPlayer1);
        LinkedList<Integer> stackPlayer2 = new LinkedList<>(this.stackPlayer2);

        while (!stackPlayer1.isEmpty() && !stackPlayer2.isEmpty()) {
            int cardPlayer1 = stackPlayer1.removeFirst();
            int cardPlayer2 = stackPlayer2.removeFirst();

            processRound(stackPlayer1, stackPlayer2, cardPlayer1, cardPlayer2);
        }

        return calculateScore(stackPlayer1.isEmpty() ? stackPlayer2 : stackPlayer1);
    }

    /**
     * Processes a round by checking which of the top card of both players is higher.
     * Based on this decision the cards are added to the winning stack of this round.
     *
     * @param stackPlayer1 The card stack of player 1.
     * @param stackPlayer2 The card stack of player 2.
     * @param cardPlayer1  The top card of player 1.
     * @param cardPlayer2  The top card of player 2.
     */
    private void processRound(LinkedList<Integer> stackPlayer1, LinkedList<Integer> stackPlayer2, int cardPlayer1, int cardPlayer2) {
        if (cardPlayer1 > cardPlayer2) {
            stackPlayer1.addLast(cardPlayer1);
            stackPlayer1.addLast(cardPlayer2);
        } else {
            stackPlayer2.addLast(cardPlayer2);
            stackPlayer2.addLast(cardPlayer1);
        }
    }

    /**
     * Processes a round by checking the winner of the round passed by a parameter.
     * Based on the winner the cards are added to the winning stack of this round.
     *
     * @param stackPlayer1 The card stack of player 1.
     * @param stackPlayer2 The card stack of player 2.
     * @param cardPlayer1  The top card of player 1.
     * @param cardPlayer2  The top card of player 2.
     * @param winner       The winner of this round.
     */
    private void processRound(LinkedList<Integer> stackPlayer1, LinkedList<Integer> stackPlayer2, int cardPlayer1, int cardPlayer2, Winner winner) {
        if (winner == Winner.PLAYER_1) {
            stackPlayer1.addLast(cardPlayer1);
            stackPlayer1.addLast(cardPlayer2);
        } else {
            stackPlayer2.addLast(cardPlayer2);
            stackPlayer2.addLast(cardPlayer1);
        }
    }

    /**
     * Calculates the end score of the game.
     * The bottom card in their deck is worth the value of the card multiplied by 1, the second-from-the-bottom card is worth the value of the card multiplied by 2, and so on.
     *
     * @param winnerStack The stack of the winner.
     * @return The calculated score.
     */
    private int calculateScore(LinkedList<Integer> winnerStack) {
        return IntStream.range(0, winnerStack.size())
                .map(index -> winnerStack.get(index) * (winnerStack.size() - index))
                .sum();
    }

    @Override
    protected Object partTwo() {
        recurseCombat(stackPlayer1, stackPlayer2);
        return calculateScore(stackPlayer1.isEmpty() ? stackPlayer2 : stackPlayer1);
    }

    /**
     * This method plays a round of recursive combat. Rules can be found in the description of this task.
     * Important points will be documented in the code lines.
     *
     * @param stackPlayer1 The card stack of player 1.
     * @param stackPlayer2 The card stack of player 2.
     * @return The winner of the game.
     */
    private Winner recurseCombat(LinkedList<Integer> stackPlayer1, LinkedList<Integer> stackPlayer2) {
        Set<List<Integer>> previousConstellations = new HashSet<>();

        while (!stackPlayer1.isEmpty() && !stackPlayer2.isEmpty()) {
            if (!previousConstellations.add(new ArrayList<>(stackPlayer1))) {
                return Winner.PLAYER_1;
            }

            int cardPlayer1 = stackPlayer1.removeFirst();
            int cardPlayer2 = stackPlayer2.removeFirst();

            // If both players have at least as many cards remaining in their deck as the value of the card, then a another "Sub-Game" is started.
            if (cardPlayer1 <= stackPlayer1.size() && cardPlayer2 <= stackPlayer2.size()) {
                Winner winner = recurseCombat(new LinkedList<>(stackPlayer1.subList(0, cardPlayer1)), new LinkedList<>(stackPlayer2.subList(0, cardPlayer2)));
                processRound(stackPlayer1, stackPlayer2, cardPlayer1, cardPlayer2, winner);
            } else {
                processRound(stackPlayer1, stackPlayer2, cardPlayer1, cardPlayer2);
            }
        }

        return stackPlayer1.isEmpty() ? Winner.PLAYER_2 : Winner.PLAYER_1;
    }

    private enum Winner {
        PLAYER_1,
        PLAYER_2
    }
}
