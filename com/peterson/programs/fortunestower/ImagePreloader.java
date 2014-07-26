package com.peterson.programs.fortunestower;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A image pre-loading and resizing thread.
 * This class allows ImageIcons to be pre-loaded
 * so that their height and width are ready for
 * when they are used.
 * This is done by loading the images into a list,
 * then iterating through the list to
 * resize to the desired size, all while on a thread.
 * @author Peterson, Ryan
 *         Created 7/24/2014
 */
public class ImagePreloader implements Runnable
{
    private int destWidth;
    private int destHeight;

    private List<ImageIcon> images;
    private final String imageDir;

    /**
     * Constructs the image pre-loading thread.
     * After construction, the thread is ready
     * to be run.
     * @param dir the directory to find the images in
     * @param desiredWidth the width the images will be
     * @param desiredHeight the height the images will be.
     * @deprecated See the other class constructor and learn how references work
     */
    public ImagePreloader(String dir, int desiredWidth, int desiredHeight)
    {
        imageDir = dir;
        destWidth = desiredWidth;
        destHeight = desiredHeight;
        images = new ArrayList<>();
    }

    /**
     * Constructs the image pre-loading thread.
     * After construction, the thread is ready
     * to be run.
     * This is the preferred method, as the list reference
     * passed in will have all the images upon completion
     * of the thread
     * @param dir the directory to find the images in
     * @param desiredWidth the width the images will be
     * @param desiredHeight the height the images will be.
     * @param listRef the list to put the image icons in.
     */
    public ImagePreloader(String dir, int desiredWidth, int desiredHeight, List<ImageIcon> listRef)
    {
        //noinspection deprecation
        this(dir, desiredWidth, desiredHeight);
        images = listRef;
    }

    public void run()
    {
        Thread t = new Thread(new InitLoader());
        t.start();
        try
        {
            t.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        for(ImageIcon i : images)
        {
            i.setImage(i.getImage().getScaledInstance(destWidth, destHeight, Image.SCALE_DEFAULT));
        }
    }

    /**
     * Gets the list of ImageIcons.
     * @return list of ImageIcons.
     * @deprecated learn how references work
     */
    public List<ImageIcon> getImages()
    {
        return images;
    }

    public String toString()
    {
        StringBuilder b = new StringBuilder();
        for(ImageIcon i : images)
        {
            b.append(i.getIconWidth());
            b.append(" ");
            b.append(i.getIconHeight());
            b.append("\n");
        }
        return b.toString();
    }

    /*
        nested class that loads all the images
        into the initial list. This will throw a
        NullPointerException if the directory
        passed in is not a directory; that is, if its a file.
     */
    private class InitLoader implements Runnable
    {
        public void run()
        {
            File dir = new File(imageDir);
            File []arr = dir.listFiles();

            if(arr == null)
                throw new NullPointerException("The file passed in is NOT a proper directory");
            for(File f : arr)
                images.add(new ImageIcon(f.getAbsolutePath()));
        }
    }
}
