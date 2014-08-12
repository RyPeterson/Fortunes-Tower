package com.peterson.programs.fortunestowergame;

import com.peterson.programs.fortunestower.Card;
import com.peterson.programs.fortunestower.Deck;
import com.peterson.programs.fortunestower.Face;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Peterson, Ryan
 *         Created 8/12/2014
 */
public class GameDeck extends Deck
{
    private List<ImageIcon> images;

    /**
     * Creates a Deck.
     * The Deck will have numCards cards,
     * four of which will be knight cards while the remaining
     * numCards - 4 will be number cards between 1 and 7.
     *
     * @param numCards the number of cards in the deck.
     */
    public GameDeck(int numCards)
    {
        super(numCards);
    }

    @Override
    protected Card makeKnight()
    {
        //ugly hack that I didn't anticipate...
        images = new ArrayList<>();
        CardLoader.loadImages(images);
        return new ImageCard(Face.KNIGHT, 0, images.get(images.size() - 1));
    }

    @Override
    protected Card makeNumber(int value)
    {
        return new ImageCard(Face.NORMAL, value, images.get(value));
    }
}
