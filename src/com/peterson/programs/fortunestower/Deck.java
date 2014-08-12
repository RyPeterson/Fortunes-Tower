package com.peterson.programs.fortunestower;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * An Abstract Deck.
 * Provides minimal implementation to handle
 * adding cards to the deck as well as
 * getting the top card in the deck.
 * Children of this class must implement two methods,
 * makeKnight() and makeNumber(int value).
 * This is done so that Cards can have specific implementations,
 * adding things such as Images to the card, which would be
 * hard with a base Deck class.
 * @author Peterson, Ryan
 *         Created 7/22/2014
 */
public abstract class Deck
{
    protected List<Card> deck;

    /**
     * A Standard Diamond deck has 56 cards.
     */
    public static final int DIAMOND_DECK = 56;

    /**
     * A Standard Emerald Deck has 70 cards
     */
    public static final int EMERALD_DECK = 70;

    /**
     * A Standard Ruby deck has 63 cards
     */
    public static final int RUBY_DECK = 63;

    /**
     * Creates a Deck.
     * The Deck will have numCards cards,
     * four of which will be knight cards while the remaining
     * numCards - 4 will be number cards between 1 and 7.
     * @param numCards the number of cards in the deck.
     */
    public Deck(int numCards)
    {
        deck = new LinkedList<>();
        createDeck(numCards);
        Collections.shuffle(deck);
    }

    /*
        Constructs the deck.
        The first four cards are knight cards, while
        (numberCards - 4) are number cards with
        values between 1 and 7.
        The abstract protected methods of this class are called,
        and upon implementation of child classes must construct
        the card to be added properly; the base Card class will
        have the cards just be a face and value, while a potential
        Swing class will have an image associated with the card as well.
     */
    private void createDeck(int numberCards)
    {
        for(int i = 0; i < 4; i++)
        {
            deck.add(makeKnight());
        }

        numberCards -= 4;

        for(int i = 0; i < numberCards; i++)
        {
            deck.add(makeNumber((i % Card.MAX_VALUE) + 1));
        }
    }

    /**
     * Abstract method to fulfill the contract of making
     * a Knight card. Subclasses must implement this, and
     * will handle the details of creating the Card
     * @return card a single Knight card
     */
    protected abstract Card makeKnight();

    /**
     * Abstract method to fulfill the contract of making a
     * number card. Subclasses will implement this, which
     * will handle the specifics of making a single
     * number card, which may have more fields, such
     * as an image
     * @param value the value of the card
     * @return card a single Number card
     */
    protected abstract Card makeNumber(int value);

    /**
     * Removes the card on top of the Deck.
     * The Card instance is fully removed, reducing the
     * size of the total Deck. This may cause exceptions
     * to be thrown if the deck size is 0 and a card is drawn.
     * Use in conjunction with hasMoreCards().
     * @return the card on top of the deck.
     * @throws java.util.NoSuchElementException if the deck is empty
     */
    public Card draw()
    {
        return deck.remove(deck.size() - 1);
    }

    /**
     * Tests to see if the deck has more cards.
     * @return true if the deck is not empty, false otherwise.
     */
    public boolean hasMoreCards()
    {
        return deck.size() != 0;
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for(Card c : deck)
        {
            b.append(c);
            b.append("\n");
        }

        return b.toString();
    }
}
