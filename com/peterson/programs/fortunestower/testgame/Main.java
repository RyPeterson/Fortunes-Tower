package com.peterson.programs.fortunestower.TestGame;

/**
 * Application Runner Class.
 * @author Peterson, Ryan
 *         Created 7/26/2014
 */
public class Main
{
    public static void main(String [] args)
    {
        /*
         * Run this on a thread for shits and grins. ;-)
         */
        Thread t = new Thread(new GameManager());
        t.start();
        try
        {
            t.join();
        }
        catch (InterruptedException e)
        {
            System.err.println("A unanticipated exception occurred, and program will now end");
            System.exit(1);
        }

        System.out.println("\n\nThank you for playing Fortunes Tower!");
    }
}
