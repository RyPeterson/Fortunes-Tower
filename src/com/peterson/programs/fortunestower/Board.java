package com.peterson.programs.fortunestower;

/**
 * A Fortune's Tower Board.
 * A Fortune's Tower board consists
 * of 8 rows, with each row containing the number
 * of cards in that row (ex. row 1 has 1 card, row 2 has 2 cards, ... row 8 has
 * 8 cards).
 * The card at the top of the board is called the Gate Card,
 * which will remain face down. It is to save the player from
 * a game condition called "Misfortune", which occurs when
 * a card on n-th row has a card on (n-1)-th that is equal to it, with
 * no knight cards to prevent this; that is, if there is a knight card
 * on row n, the row is automatically immune to a Misfortune. If a Misfortune
 * occurs, the card on top of the tower is then flipped and replaced with the card on
 * n-th row that causes the Misfortune, and then a Misfortune condition is re-tested, and if cleared
 * the game continues until another is found or all 8 rows are filled. If all
 * 8 rows are filled and the Gate card is not used, the Jackpot condition is met and
 * the total value of the rows are added together for the grand total.
 * @author Peterson, Ryan
 *         Created 8/6/2014
 */
public interface Board
{
    /**
     * Adds cards to the next row of the board.
     * The number of cards added is equal to the row
     * (ex. row 2 has two cards, row three has three cards, etc).
     */
    void nextRow();

    /**
     * Determines if the board is complete.
     * A board is complete once all 8 rows of the board are filled
     * @return true if all the rows are filled with cards
     */
    boolean isComplete();

    /**
     * Flips the card at the top of the board.
     * This reveals the gate card.
     */
    void flipGateCard();
    /**
     * Gets the value of the Row.
     * The value is equal to the face sum of every
     * card in the row, with knights getting a value of 7.
     * @param rowNum the row number, greater than 0 and less than 8
     * @return the value of the row, or -1 if the row hasn't been
     * dealt or if rowNum is out bounds
     */
    int rowValue(int rowNum);

    /**
     * Gets the value of the last dealt row.
     * @see int rowValue(int rowNum) for specifics.
     * @return the value of the last dealt row.
     */
    int lastRowValue();

    /**
     * Determines if a misfortune has happened.
     * This occurs if the row above the last dealt row
     * has a value right above equal to it, without
     * any knight cards to prevent this.
     * This can be thought of in terms of a tree.
     * If a node's child's value equals the value of the parent,
     * the condition for a misfortune is met.
     * @return true if a misfortune occurs.
     */
    boolean misFortune();

    /**
     * Try to save the board from a misfortune.
     * This will reveal the value of the gate card.
     * It will be placed where the first occurance of a misfortune
     * occurs, changing the value of that misfortune, potentially saving the board.
     */
    void trySave();


    /**
     * Determines if the board has hit a jackpot.
     * A jackpot condition occurs if the board is complete and no
     * misfortune occurs.
     * @return true if the jackpot condition mentioned is met
     */
    boolean hitJackpot();

    /**
     * Gets the value of a Jackpot.
     * A Jackpot value is the sum of ALL cards on the board,
     * including the gate card.
     * @return 0 if the game isn't complete and no jackpot can be found,
     * or the sum of all cards on the board if the jackpot is met.
     */
    int jackpotValue();

    /**
     * Gets a row of Cards from the Board.
     * Care must be taken to not change the state of
     * the row, but this sort of operation is allowed.
     * @param rowNumber the row of cards to get
     * @return null, if the row is out of bounds, an array of
     * null if the row hasn't been dealt, or an array of Cards from the row
     */
    Card[] getCardsInRow(int rowNumber);

    /**
     * Gets the last row dealt.
     * @return the last row of Cards dealt.
     */
    Card[] getLastRow();
}
