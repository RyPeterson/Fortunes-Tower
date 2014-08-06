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
 *         Created 7/22/2014
 */
public class Board
{
    protected Card [][] board;
    private Deck deck;

    //Pointer to the next row to deal into
    private int nextRow;

    /*
        Keep track of the row and column where the
        misfortune occurs to be able to save the game if
        possible
     */
    private int potentialMisfortuneRow;
    private int potentialMisfortuneCol;

    /**
     * The number of levels in the tower.
     */
    public static final int LAST_LEVEL = 8;

    /**
     * Constructs the Game board.
     * The game board will use the reference of
     * the deck passed in to draw cards from.
     * @param deckRef the reference to the deck to use in the game
     */
    public Board(Deck deckRef)
    {
        board = new Card[LAST_LEVEL][];
        for(int i = 0; i < board.length; i++)
        {
            board[i] = new Card[i + 1];
        }

        /*
            The board starts out with 3 cards.
            The one on the first row is face down,
            and the other two on the second row is face up.
         */
        deck = deckRef;
        board[0][0] = deck.draw();
        board[1][0] = deck.draw();
        board[1][1] = deck.draw();
        board[1][0].flip();
        board[1][1].flip();
        nextRow = 2;

        potentialMisfortuneCol = -1;
        potentialMisfortuneRow = -1;
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                if(board[i][j] == null)
                    b.append("*");
                else if(i == 0 && j == 0)
                {
                    b.append("?");
                }
                else
                    b.append(board[i][j]);
                b.append(" ");
            }
            b.append("\n");
        }
        return b.toString();
    }

    /**
     * Adds cards to the next row of the board.
     * The number of cards added is equal to the row
     * (ex. row 2 has two cards, row three has three cards, etc).
     */
    public void nextRow()
    {
        for(int i = 0; i < board[nextRow].length; i++)
        {
            board[nextRow][i] = deck.draw();
            board[nextRow][i].flip();
        }

        nextRow++;
    }

    /**
     * Determines if the board is complete.
     * A board is complete once all 8 rows of the board are filled
     * @return true if all the rows are filled with cards
     */
    public boolean isComplete()
    {
        /*
            Note, array indices.
            Last row will be 7, therefore the next
            row is 8.
         */
        return nextRow == LAST_LEVEL;
    }

    /**
     * Flips the card at the top of the board.
     * This reveals the gate card.
     */
    public void flipGateCard()
    {
        //cant flip nothing...
        if(board[0][0] != null)
            board[0][0].flip();
    }

    /**
     * Gets the value of the Row.
     * The value is equal to the face sum of every
     * card in the row, with knights getting a value of 7.
     * @param rowNum the row number, greater than 0 and less than 8
     * @return the value of the row, or -1 if the row hasn't been
     * dealt or if rowNum is out bounds
     */
    public int rowValue(int rowNum)
    {
        if(rowNum >= 0 && rowNum < LAST_LEVEL)
        {
            if(board[rowNum][0] != null)
            {
                int value = 0;
                for(int i = 0; i < board[rowNum].length; i++)
                    value += board[rowNum][i].getValue();
                return value;
            }
            else
                return -1;
        }
        else
            return -1;
    }

    /**
     * Gets the value of the last dealt row.
     * @see int rowValue(int rowNum) for specifics.
     * @return the value of the last dealt row.
     */
    public int rowValue()
    {
        return rowValue(nextRow - 1);
    }

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
    public boolean misFortune()
    {
        //if its the first two rows
        //then a misfortune CANNOT happen
        if(nextRow - 1 <= 2)
            return false;

        //last dealt row
        int rowCurr = nextRow - 1;

        //if the row has a knight, it is immune to misfortune
        if(rowContainsKnight(rowCurr))
            return false;


        //the row before the last dealt row
        int rowLast = rowCurr - 1;

        for(int i = 0; i < board[rowLast].length; i++)
        {
            /*
                check rowLast's left child
             */
            if(board[rowLast][i].equals(board[rowCurr][i]))
            {
                potentialMisfortuneCol = i;
                potentialMisfortuneRow = rowCurr;
                return true;
            }
            /*
                Chceck rowLast's right child.
             */
            if(board[rowLast][i].equals(board[rowCurr][i + 1]))
            {
                potentialMisfortuneCol = i + 1;
                potentialMisfortuneRow = rowCurr;
                return true;
            }
        }

        return false;
    }

    /**
     * Try to save the board from a misfortune.
     * This will reveal the value of the gate card.
     * It will be placed where the first occurance of a misfortune
     * occurs, changing the value of that misfortune, potentially saving the board.
     */
    public void trySave()
    {
        //dont do anytrhing if the value hasn't been set
        if(potentialMisfortuneCol < 0)
            return;
        //if the gate card hasn't been used
        if(board[0][0] != null)
        {
            Card temp = board[0][0];
            temp.flip();
            board[0][0] = null;
            board[potentialMisfortuneRow][potentialMisfortuneCol] = temp;
        }
    }

    /*
        Helper to determine if the row contains a knight.
        A misfortune cannot occur if the row has a
        knight on it.
     */
    private boolean rowContainsKnight(int row)
    {
        for(int i = 0; i < board[row].length; i++)
        {
            if(board[row][i].isKnight())
                return true;
        }

        return false;
    }

    /**
     * Determines if the board has hit a jackpot.
     * A jackpot condition occurs if the board is complete and no
     * misfortune occurs.
     * @return true if the jackpot condition mentioned is met
     */
    public boolean hitJackpot()
    {
        return (isComplete() && (board[0][0] !=null));
    }

    /**
     * Gets the value of a Jackpot.
     * A Jackpot value is the sum of ALL cards on the board,
     * including the gate card.
     * @return 0 if the game isn't complete and no jackpot can be found,
     * or the sum of all cards on the board if the jackpot is met.
     */
    public int jackpotValue()
    {
        if(!hitJackpot())
            return 0;

        int value = 0;
        for(int i = 0; i < board.length; i++)
        {
            value += rowValue(i + 1);
        }

        return value;
    }
    
    /**
     * Gets a row of Cards from the Board.
     * Care must be taken to not change the state of
     * the row, but this sort of operation is allowed.
     * @param rowNumber the row of cards to get
     * @return null, if the row is out of bounds, an array of
     * null if the row hasn't been dealt, or an array of Cards from the row
     */
    public Card []getCardsInRow(int rowNumber)
    {
        if(rowNumber >= 0 && rowNumber < LAST_LEVEL)
            return board[rowNumber];
        else
            return null;
    }
    
    /**
     * Gets the last row dealt.
     * @return the last row of Cards dealt.
     */
    public Card []getLastRow()
    {
        return getCardsInRow(nextRow - 1);
    }
}
