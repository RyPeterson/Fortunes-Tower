package com.peterson.programs.fortunestowergame;

import javax.swing.*;
import java.awt.*;

/**
 * The JPanel to hold the Cards in the game of Fortune's Tower.
 * The Cards will be layed out in a single column with
 * 8 rows. Each row will show the pictures of the cards in the rows
 * that have been dealt. When a row is drawn from the deck, adding the cards
 * from that row will result in the visual cards to be shown on the appropriate
 * row.
 * @author Peterson, Ryan
 *         Created 7/26/2014
 */
public class GamePanel extends JPanel
{
    private LevelPanel []panels;
    private int currPtr;

    /**
     * Creates a GamePanel.
     * Each row of cards will have its faces displayed on a row
     * of this panel.
     */
    public GamePanel()
    {
        super();
        panels = new LevelPanel[8];
        for(int i = 0; i < panels.length; i++)
            panels[i] = new LevelPanel(i + 1);

        currPtr = 0;
        setLayout(new GridLayout(8, 1));
        for(LevelPanel l : panels)
            add(l);
    }

    /**
     * Adds the next row of cards to the panel.
     * The new row will be on the level below the last.
     * Also, the new row will have 1 more card than the one above it
     * if done correctly for the game of Fortunes Tower.
     * Note that ONLY 8 rows will appear on this panel.
     * @param row the row to add to the frame.
     */
    public void addRow(ImageCard []row)
    {
        if(currPtr < 8)
        {
            panels[currPtr].addCards(row);
            currPtr++;
        }

        revalidate();
    }

    /**
     * Flips the card at the first position on the panel.
     * This has nothing to do with placing the card in the row
     * its used on; rather, it just sets that card to a flipped state
     * and greys out the image that was at the top of the card tree.
     * Also note that this will not re-flip the card.
     */
    public void flipGate()
    {
        if(!panels[0].ref[0].isFaceUp())
        {
            panels[0].ref[0].flip();
            panels[0].cards[0].setEnabled(false);
        }
    }

    /**
     * Sets the background of the panel.
     * This is not an override of setBackground(Color c).
     * @param c the color to make the background as.
     */
    public void setBackgroundColor(Color c)
    {
        for(LevelPanel p : panels)
            p.setBackground(c);
    }

    /**
     * Steps back a row to deal with changing the card images.
     * This should not be called more than once, since the game logic
     * dictates that only one misfortune can be made before the game
     * is over.
     */
    public void removeLastRow()
    {
        currPtr--;
        if(currPtr < 0)
            currPtr = 0;

        //panels[currPtr].revalidate();
        //revalidate();
    }

    /*
        Hidden, inner class that holds the images to be displayed.
     */
    private class LevelPanel extends JPanel
    {
        private JLabel []cards;
        private ImageCard []ref;

        /*
            Constructs the panel to hold the number of cards.
         */
        public LevelPanel(int numCards)
        {
            cards = new JLabel[numCards];
            for(int i = 0; i < numCards; i++)
                cards[i] = new JLabel();
        }

        /*
            Adds the cards images to this panel
         */
        public void addCards(ImageCard []row)
        {
            //setLayout(new GridLayout(1, row.length));
            for(int i = 0; i < row.length; i++)
            {
                cards[i].setIcon(row[i].getImage());
                add(cards[i]);
            }
           // revalidate();

            ref = row;
        }

    }
}
