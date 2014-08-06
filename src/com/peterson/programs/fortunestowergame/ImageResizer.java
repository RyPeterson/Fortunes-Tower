package com.peterson.programs.fortunestowergame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A Static Utility class that will resize a list of Image Icons.
 * For a larger program, this might want to be done on a thread,
 * iff it can be done safely.
 * @author Peterson, Ryan
 *         Created 8/2/2014
 */
public class ImageResizer
{
    /**
     * Resizes a List of ImageIcons.
     * The scaling will be done based on Image.SCALE_SMOOTH, which will
     * maintain quality of the image.
     * @see java.awt.Image for details of scaling
     * @param list the list containing the ImageIcons to be resized
     * @param newWidth the desired width of the new Images
     * @param newHeight the desired height of the new Images
     */
    public static void resize(List<ImageIcon> list, int newWidth, int newHeight)
    {
        for(ImageIcon i : list)
        {
            i.setImage(i.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        }
    }
}
