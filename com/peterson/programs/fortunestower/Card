package com.peterson.programs.fortunestower;

/**
 * A card for a game of Fortune's Tower.
 * Every card has a Face, which is either normal
 * or a knight, as well as a value between 1 and 7, or
 * 0 for a knight.
 * A card can also alturnate between flipped and face down.
 * @author Peterson, Ryan
 *         Created 7/22/2014
 */
public class Card implements Comparable<Card>
{
    private Face face;
    private int number;
    private boolean faceUp;

    /**
     * The minimum value of a card, which is a Knight
     */
    public static final int MIN_VALUE = 0;

    /**
     * The max vaulue of a card
     */
    public static final int MAX_VALUE = 7;

    /**
     * Creates a Card.
     * The value of the face as well as
     * a face must be passed.
     * @param value the value of the card
     * @param f the face of the Card
     */
    public Card(int value, Face f)
    {
        face = f;
        number = value;
        faceUp = false;
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        if(face == Face.KNIGHT)
            b.append("Knight");
        else
            b.append(number);
        return b.toString();
    }

    /**
     * Gets the face value of the Card.
     * If the card is a normal card, the value
     * 1-7 is returned.
     * If its a knight, 0 is returned.
     * @return the value of the card
     */
    public int getValue()
    {
        return number;
    }

    /**
     * Determines if the card is a knight.
     * @return true if the card is a Knight card, false if not
     */
    public boolean isKnight()
    {
        return face == Face.KNIGHT;
    }

    /**
     * Alternates the card between
     * face up and face down.
     */
    public void flip()
    {
        faceUp = !faceUp;
    }

    /**
     * Determines if the card is face up
     * @return true if the card is face up.
     */
    public boolean isFaceUp()
    {
        return faceUp;
    }

    /**
     * Compares two Cards.
     * This will compare based on the face value.
     * If one of the cards is a Knight, -1 is returned.
     * @param other the Card to compare to
     * @return -1, 0, or 1 based on the comparision.
     */
    public int compareTo(Card other)
    {
        if(other == null)
            return Integer.MIN_VALUE;

        if(this.face == Face.KNIGHT
            || other.face == Face.KNIGHT)
            return -1;

        if(this.number == other.number)
            return 0;

        if(this.number < other.number)
            return -1;

        return 1;
    }


    /**
     * Determines if the cards have the same value.
     * @param other the Card to compare to
     * @return true if this card equals other card
     */
    public boolean equals(Card other)
    {
        if(this.face == Face.KNIGHT)
        {
            if(other.face == Face.KNIGHT)
                return true;
            else
                return false;
        }
        return compareTo(other) == 0;
    }
}
