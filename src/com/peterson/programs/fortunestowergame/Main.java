package com.peterson.programs.fortunestowergame;

import com.peterson.programs.fortunestower.Deck;

import javax.swing.*;

/**
 * Runner Class.
 * Runs the GameFrame, which in turn runs the game of Fortunes Tower.
 * @author Peterson, Ryan
 *         Created 7/26/2014
 */
public class Main
{
    /*
        Constants to allow the user to choose how many cards are in the deck.
     */
    private static final Integer []OPTIONS = {Deck.DIAMOND_DECK, Deck.EMERALD_DECK, Deck.RUBY_DECK};
    private static final String []DECK_OPTIONS = {"Diamond Deck: " + OPTIONS[0] + " Cards",
            "Emerald Deck: " + OPTIONS[1] + " Cards", "Ruby Deck: " + OPTIONS[2] + " Cards"};

    /**
     * Asks the user for which deck they want to play with.
     * @see com.peterson.programs.fortunestower.Deck for the standard
     * number of cards in the deck.
     * @return the number of cards to use in the game
     */
    public static int getNumberCards()
    {

        int choice = -1;

        //prevent an out of bounds exception
        while(choice < 0)
        {
            choice = JOptionPane.showOptionDialog(null, "Choose a deck size", "Choose Deck Size",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DECK_OPTIONS, DECK_OPTIONS[0]);
        }

        return OPTIONS[choice];
    }

    public static void main(String [] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                int choice = getNumberCards();
                new GameFrame(choice);
            }
        });
    }
}
