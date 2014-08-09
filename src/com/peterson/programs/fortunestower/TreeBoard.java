package com.peterson.programs.fortunestower;

/**
 * A "Tree Like" implementation of Board.
 * In order to provide quick access to
 * rows, a Board has to be list/array based,
 * so a real tree board is not recommended.
 * The nodes of this tree store references
 * to other nodes, but still follow the ragged
 * 2-D array of Board2D.
 *
 * @author Peterson, Ryan
 *         Created 8/9/2014
 * @deprecated Use Board2D, since this isn't much of a change
 */
public class TreeBoard implements Board
{
    private static final int NUM_ROWS = 8;

    private BoardNode[][] board;
    private Deck deck;
    private int nextRow;

    private int misfortuneRow;
    private int misfortuneCol;

    public TreeBoard(Deck ref)
    {
        deck = ref;

        board = new BoardNode[NUM_ROWS][];
        for (int i = 0; i < NUM_ROWS; i++)
            board[i] = new BoardNode[i + 1];

        board[0][0] = new BoardNode(deck.draw());
        board[1][0] = new BoardNode(deck.draw(), true);
        board[1][1] = new BoardNode(deck.draw(), true);
        nextRow = 2;
        misfortuneRow = -1;
        misfortuneCol = -1;
    }


    @Override
    public void nextRow()
    {
        for (int i = 0; i < board[nextRow].length; i++)
        {
            board[nextRow][i] = new BoardNode(deck.draw(), true);
            if (i < board[nextRow - 1].length)
                board[nextRow - 1][i].left = board[nextRow][i];
            if (i + 1 < board[nextRow].length)
                board[nextRow - 1][i].right = board[nextRow][i + 1];
        }

        nextRow++;
    }

    @Override
    public boolean isComplete()
    {
        return nextRow == NUM_ROWS;
    }

    @Override
    public void flipGateCard()
    {
        if (board[0][0] != null)
            board[0][0].card.flip();
    }

    @Override
    public int rowValue(int rowNum)
    {
        int value = 0;
        for (int i = 0; i < board[rowNum].length; i++)
            value += board[rowNum][i].card.getValue();
        return value;
    }

    @Override
    public int lastRowValue()
    {
        return rowValue(nextRow - 1);
    }

    @Override
    public boolean misFortune()
    {
        if (nextRow - 1 <= 2)
            return false;

        int rowCurr = nextRow - 1;

        if (rowContainsKnight(rowCurr))
            return false;

        int rowLast = rowCurr - 1;

        for (int i = 0; i < board[rowLast].length; i++)
        {
            if (board[rowLast][i].card.equals(board[rowLast][i].left.card))
            {
                misfortuneRow = rowCurr;
                misfortuneCol = i;
                return true;
            }
            else if (board[rowLast][i].right != null && board[rowLast][i].card.equals(board[rowLast][i].right.card))
            {
                misfortuneRow = rowCurr;
                misfortuneCol = i + 1;
                return true;
            }
        }

        return false;
    }

    @Override
    public void trySave()
    {
        //dont do anytrhing if the value hasn't been set
        if (misfortuneCol < 0)
            return;
        //if the gate card hasn't been used
        if (board[0][0] != null)
        {
            Card temp = board[0][0].card;
            temp.flip();
            board[0][0] = null;
            board[misfortuneRow][misfortuneCol].card = temp;
        }
    }

    @Override
    public boolean hitJackpot()
    {
        return isComplete() && board[0][0] != null;
    }

    @Override
    public int jackpotValue()
    {
        if (!hitJackpot())
            return 0;

        int value = 0;
        for (int i = 0; i < board.length; i++)
            value += rowValue(i + 1);

        return value;
    }

    @Override
    public Card[] getCardsInRow(int rowNumber)
    {
        Card[] cards = new Card[board[rowNumber].length];
        for (int i = 0; i < cards.length; i++)
            cards[i] = board[rowNumber][i].card;
        return cards;
    }

    public boolean rowContainsKnight(int rowNum)
    {
        for (int i = 0; i < board[rowNum].length; i++)
            if (board[rowNum][i].card.isKnight())
                return true;

        return false;
    }

    @Override
    public Card[] getLastRow()
    {
        return getCardsInRow(nextRow - 1);
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j] == null)
                    b.append("*");
                else if (!board[i][j].card.isFaceUp())
                    b.append("?");
                else
                    b.append(board[i][j]).append(" ");
            }
            b.append("\n");
        }
        return b.toString();
    }

    private class BoardNode
    {
        private Card card;
        private BoardNode left;
        private BoardNode right;

        public BoardNode(Card c)
        {
            card = c;
        }

        public BoardNode(Card c, boolean flip)
        {
            this(c);
            if (flip)
                card.flip();
        }

        public String toString()
        {
            return card.toString();
        }
    }
}
