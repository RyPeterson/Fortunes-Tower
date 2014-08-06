package com.peterson.programs.fortunestowergame;

import com.peterson.programs.fortunestower.Card;
import com.peterson.programs.fortunestower.Face;

import javax.swing.*;

/**
 * A Card that has an Image Icon associated with it.
 * This will allow a card to have a unique image on it.
 * @author Peterson, Ryan
 *         Created 7/26/2014
 */
public class ImageCard extends Card
{
    private ImageIcon image;

    /**
     * Creates a card with an ImageIcon.
     * The values and face is set, and the
     * icon passed in will be the card's face
     * @param f the face of the card
     * @param val the value of the card
     * @param icon the picture to be associated with the card
     */
    public ImageCard(Face f, int val, ImageIcon icon)
    {
        super(val, f);
        image = icon;
    }

    /**
     * Gets the Card's Face.
     * @return image the image of the Card's face
     */
    public ImageIcon getImage()
    {
        return image;
    }
}
