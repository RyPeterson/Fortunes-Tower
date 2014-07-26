package com.peterson.programs.fortunestower;

/**
 * A concrete subclass of Deck.
 * All the card will have a face and a value.
 * @author Peterson, Ryan
 *         Created 7/22/2014
 */
public class TestDeck extends Deck
{

    public TestDeck(int numCards)
    {
        super(numCards);
    }

    @Override
    protected Card makeKnight()
    {
        return new Card(0, Face.KNIGHT);
    }

    @Override
    protected Card makeNumber(int value)
    {
        return new Card(value, Face.NORMAL);
    }
}
