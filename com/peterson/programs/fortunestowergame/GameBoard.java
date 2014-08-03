package com.peterson.programs.fortunestowergame;

import com.peterson.programs.fortunestower.Board;
import com.peterson.programs.fortunestower.Card;
import com.peterson.programs.fortunestower.Face;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * An In-Between class, between the Board and the GUI.
 * This class maps images to the current states of the Board.
 * It is intended to display the cards on a GUI.
 * @author Peterson, Ryan
 *         Created 7/26/2014
 */
public class GameBoard
{
    private Board boardMap;
    private List<ImageIcon> list;

    /**
     * Constructs a GameBoard object.
     * Using the reference to the Board which
     * contains the states of the game, this loads the images that
     * are the desired size and prepares the object to map
     * row by row to the Board
     * @param ref a reference to the Board being used in the game
     * @param width the width the cards are to be
     * @param height the height the cards are to be
     */
    public GameBoard(Board ref, int width, int height)
    {
        boardMap = ref;
        list = new ArrayList<>();

        //load the single images into the list
        CardLoader.loadImages(list);

        //resize the card's images
        ImageResizer.resize(list, width, height);
    }

    /**
     * Maps the current row in the Board to the ImageCards.
     * This is a 1:1 mapping, meaning that each card in the current row in the Board
     * are mapped to a ImageCard that shows their face value.
     * This is done because of an incompatibility with the Board object,
     * which only references Card object, and cant cast to ImageCards.
     * That fail should be re-done so its not a fail.
     * @param rowNum the row of the Board to map
     * @return array of ImageCards to represent the row with ImageIcons
     */
    public ImageCard[] mapRow(int rowNum)
    {
        //the mapping arrays. The 'mapping' array is mapped to 'mapped' 1:1
        Card []mapping = boardMap.getCardsInRow(rowNum);
        ImageCard []mapped = new ImageCard[mapping.length];


        for(int i = 0; i < mapping.length; i++)
        {
            if (!mapping[i].isFaceUp())
            {
                /*
                    If the Card is face down, the image will be of a face down card
                 */
                if (mapping[i].isKnight())
                {
                    mapped[i] = new ImageCard(Face.KNIGHT, 0, list.get(0));
                }
                else
                {
                    mapped[i] = new ImageCard(Face.NORMAL, mapping[i].getValue(), list.get(0));
                }
            }
            else
            {
                /*
                    If the card is face up, map it to its face. The stitched cards happen to be
                    in index incrementation order; index 1 has a face of 1, index 2 has a face of 2, etc
                 */
                if (mapping[i].isKnight())
                {
                    mapped[i] = new ImageCard(Face.KNIGHT, 0, list.get(list.size() - 1));
                }
                else
                {
                    mapped[i] = new ImageCard(Face.NORMAL, mapping[i].getValue(), list.get(mapping[i].getValue()));
                }
            }

        }

        return mapped;
    }
}
