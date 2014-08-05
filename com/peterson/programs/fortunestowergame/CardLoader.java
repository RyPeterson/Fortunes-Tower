package com.peterson.programs.fortunestowergame;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Static Utility class that will load up
 * the images of cards to be used for Fortunes Tower.
 * The card images will be loaded into the list passed.
 * The sequence of cards will be:
 * the back of the card at index 0, the values 1-7 at index 1-7 at a 1:1 map,
 * and index 8 will be the Knight Card
 * @author Peterson, Ryan
 *         Created 8/2/2014
 */
public class CardLoader
{
    /**
     * The directory of the stitched cards
     */
    public static final String FILE = "FortuneCards.png";

    /*
        This constants should never be messed with
        unless the card's image file is resized.
     */
    private static final int DELTA_X = 100;
    private static final int DELTA_Y = 150;

    /**
     * Loads the ImageIcons into the passed List.
     * Care should be used, since the original list is cleared if it contains
     * other ImageIcons. The list will then have 9 ImageIcons loaded into it.
     * @see com.peterson.programs.fortunestowergame.CardLoader.CardSequence for
     * the sequence used.
     * @param images the list to put the images in
     */
    public static void loadImages(List<ImageIcon> images)
    {
        BufferedImage mainImage;
        try
        {
            URL imgURL = CardLoader.class.getResource(FILE);
            mainImage = ImageIO.read(imgURL);
        }
        catch (IOException e)
        {
            /*
                This should NEVER happen.
                If it does, may my balls rot.
             */
            throw new RuntimeException(e);
        }


        if(images.size() != 0)
            images.clear();


        int x = 0;
        int y = 0;

        /*
            i = row = y-pos
            j = col = x-pos
            Reads across the stitched cards,
            then advances down to read the next
            3 cards
         */
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                images.add(new ImageIcon(mainImage.getSubimage(x, y, DELTA_X, DELTA_Y)));

                //jump x to the next card
                x += DELTA_X;
            }

            //reset x to start position
            x = 0;

            //move down to the next row of cards
            y += DELTA_Y;
        }
    }

    public static enum CardSequence
    {
           BACK, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, KNIGHT
    }
}
