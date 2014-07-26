package com.peterson.programs.fortunestower.testgame;

import com.peterson.programs.fortunestower.Board;
import com.peterson.programs.fortunestower.Deck;
import com.peterson.programs.fortunestower.TestDeck;

import java.util.Scanner;

/**
 * A CLI Game manager.
 * This will manage the game of Fortunes Tower,
 * and run it via the command line.
 * This is mostly for demonstration purposes.
 * The Deck will be an instance of TestDeck, using a standard
 * Diamond deck.
 * @author Peterson, Ryan
 *         Created 7/25/2014
 */
public class GameManager implements Runnable
{
    private Deck deck;
    private Board board;
    private Scanner cin;

    /**
     * Prepares the GameManager for running.
     * The Deck and Board are initialized,
     * as well as the command line input.
     */
    public GameManager()
    {
        deck = new TestDeck(Deck.DIAMOND_DECK);
        board = new Board(deck);
        cin = new Scanner(System.in);
    }

    /**
     * Invoking this will run the game via the command line.
     */
    public void run()
    {
        boolean keepGoing = true;
        while(keepGoing)
        {
            boolean done = false;
            boolean trySave = false;

            //while the game is not complete.
            while (!done)
            {
                //print out the board and the current line's points
                System.out.println("Current Board:\n");
                System.out.print(board);
                System.out.println("\nPoints: " + board.rowValue());

                //if the user wants to quit the game, cashing in on the current points
                //let them
                if (cashIn())
                {
                    done = true;
                    System.out.println("You won: " + board.rowValue());
                }
                else
                {
                    //Main game logic.

                    //deal the next row, and check for misfortune
                    board.nextRow();

                    if (board.misFortune() && !trySave) //if its a misfortune and no attempt to save has been made
                    {
                        trySave = true;
                        System.out.println("Yikes! A potential Misfortune!\n" + board);

                        //try to save the row
                        board.trySave();

                        //if, even after the row has been changed to save it, its a misfortune, game over
                        if (board.misFortune())
                        {
                            System.out.println("MISFORTUNE");
                            System.out.println(board);
                            done = true;
                        }

                        System.out.println("\n\n\n\n");
                    }
                }

                //if all 36 cards have been used, then the game is complete without misfortune
                if (board.isComplete() && !board.misFortune())
                {
                    //if there was a jackpot (the gate card isn't used)
                    if (board.hitJackpot())
                    {
                        System.out.println("Hit the Jack Pot!");
                        System.out.println("Jackpot Value: " + board.jackpotValue());
                    }
                    else
                    {
                        //if the gate card is used, then summate the last row as points
                        System.out.println("\n\n" + board);
                        System.out.print("Board is complete\nFinal Points:");
                        System.out.println(board.rowValue());
                    }
                    done = true;
                }
            }

            keepGoing = playAgain();

            if(keepGoing)
            {
                deck = new TestDeck(Deck.DIAMOND_DECK);
                board = new Board(deck);
            }
        }
    }

    /*
        Asks the user to input Y or N to not continue or continue, respectivly.
     */
    private boolean cashIn()
    {
        /*
         * This loop will end once a Y or N is entered.
         */
        while(true)
        {
            System.out.print("Would you like to cash in and end the game? Y/N >");
            String choice = cin.nextLine();
            choice = choice.toUpperCase();
            if (choice.contains("Y"))
                return true;
            else if (choice.contains("N"))
                return false;
            else
                System.out.println("Sorry, invalid character was entered\n\n\n");
        }
    }

    /*
        Asks the player to play again. Yes = play again, no = end game
     */
    private boolean playAgain()
    {
        /*
            This loop will end once correct input is made
         */
        while(true)
        {
            System.out.print("Would you like to play again? Y/N >");
            String line = cin.nextLine();
            line = line.toUpperCase();
            if (line.contains("Y"))
                return true;
            else if (line.contains("N"))
                return false;
            else
                System.out.println("Invalid character. Try again!\n\n\n");
        }
    }
}
