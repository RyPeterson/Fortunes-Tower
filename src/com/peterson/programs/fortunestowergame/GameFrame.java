package com.peterson.programs.fortunestowergame;

import com.peterson.programs.fortunestower.Board;
import com.peterson.programs.fortunestower.Board2D;
import com.peterson.programs.fortunestower.Deck;
import com.peterson.programs.fortunestower.TestDeck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;

/**
 * Main GUI class for the game of Fortune's Tower.
 * This will display a frame that will allow the user
 * to play game of Fortunes Tower.
 * @author Peterson, Ryan
 *         Created 7/26/2014
 */
public class GameFrame extends JFrame
{
    /*
        Logic Objects
     */
    private GamePanel panel;
    private Board board;
    private Deck deck;
    private GameBoard map;

    /*
        Display Objects
     */
    private JPanel sidePanel;
    private JTextField[] fields;

    private JPanel buttonPanel;
    private JButton drawButton;
    private JButton cashOutButton;
    private JButton reset;
    private Container cp;
    private ActionListener draw;
    private int rowPtr;
    private int numCards;

    private long playerPoints;
    private JLabel pointLabel;



    /*
        Constants for the background and foreground colors
     */
    private static final Color BACKGROUND = new Color(12, 128, 37);
    private static final Color FOREGROUND = new Color(83, 128, 38);

    /*
        Coefficients that determine the size of the Cards
        based off of the percentage of these with
        getToolkit().getScreenSize().getWidth() and getToolkit().getScreenSize().getHeight().
        They allow the cards to grow and shrink based on the computer monitor this program
        runs on.
     */
    private static final int WIDTH_COEFF = 25;
    private static final int HEIGHT_COEFF = 10;

    /*
        The font that the buttons and text fields will have
     */
    private static final Font FONTZ = new Font("Font", Font.BOLD, 15);


    /**
     * Creates the window to play the game of Fortunes Tower.
     * This will do the necessary steps to construct the frame and add action listeners
     * for the user to draw cards, cash out, and reset the board
     * @param numberCards the number of cards to play with. Use the constants from the Deck class
     *                    for the standard sizes.
     */
    public GameFrame(final int numberCards)
    {
        super("Fortunes Tower");

        playerPoints = 0;
        pointLabel = new JLabel("Points: " + playerPoints);


        deck = new TestDeck(numberCards);
        panel = new GamePanel();
        board = new Board2D(deck);


        fields = new JTextField[8];
        for (int i = 0; i < fields.length; i++)
        {
            fields[i] = new JTextField();
            fields[i].setEditable(false);
            fields[i].setColumns(5);
            fields[i].setFont(FONTZ);
            fields[i].setHorizontalAlignment(SwingConstants.RIGHT);
        }

        fields[0].setText("Points");
        fields[0].setBackground(Color.DARK_GRAY);
        fields[0].setForeground(Color.WHITE);
        fields[0].setEnabled(false);

        numCards = numberCards;
        rowPtr = 1;
        create();

    }

    /*
        Constructs the initial state of the GUI.
     */
    private void create()
    {
        cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.setBackground(BACKGROUND);
        panel.setBackground(BACKGROUND);
        panel.setBackgroundColor(FOREGROUND);
        cp.add(panel, BorderLayout.CENTER);

        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(8, 1, 100, 100));

        cp.add(sidePanel, BorderLayout.WEST);

        sidePanel.setBackground(BACKGROUND);
        for (JTextField f : fields)
            sidePanel.add(f);


        buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND);
        cp.add(buttonPanel, BorderLayout.SOUTH);

        drawButton = new JButton("Deal Next Row");

        drawButton.setFont(FONTZ);
        draw = new DealListener();
        drawButton.addActionListener(draw);
        buttonPanel.add(drawButton);

        cashOutButton = new JButton("Cash Out");
        cashOutButton.setFont(FONTZ);
        cashOutButton.addActionListener(new CashOutListener());
        buttonPanel.add(cashOutButton);

        reset = new JButton("Reset");
        reset.setFont(FONTZ);
        reset.addActionListener(new ResetListener());
        buttonPanel.add(reset);

        buttonPanel.add(pointLabel);
        pointLabel.setFont(FONTZ);
        pointLabel.setForeground(Color.BLUE);


        JMenuBar bar = new JMenuBar();
        setJMenuBar(bar);

        JMenu m = new JMenu("Info");
        JMenuItem i = new JMenuItem("Rules");

        i.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JOptionPane.showMessageDialog(null, RulesLoader.getRules(), "Rules",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        m.add(i);
        bar.add(m);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        /*
            Set the width to fill the screen from left to right, but
            set the height to be 50 less than the screen size
         */
        int width = (int) getToolkit().getScreenSize().getWidth();
        int height = (int) (getToolkit().getScreenSize().getHeight() - 50);
        setSize(width, height);

        postInit();
    }

    /*
        Post GUI initialzation to display the first
        two rows of the Board.
     */
    private void postInit()
    {
        double pWidth = getToolkit().getScreenSize().getWidth();
        double pHeight = getToolkit().getScreenSize().getHeight();

        map = new GameBoard(board, (int) pWidth / WIDTH_COEFF, (int) pHeight / HEIGHT_COEFF);

        panel.addRow(map.mapRow(0));
        panel.addRow(map.mapRow(1));
        fields[1].setText(board.lastRowValue() + "");
    }

    /*
        Loads and displays the rules of the game.
     */
    private static class RulesLoader
    {
        private static final String FILE = "rules.txt";

        /*
            Gets the rules of the game
         */
        public static String getRules()
        {
            try
            {
                StringBuilder b = new StringBuilder();
                InputStream is = GameFrame.class.getResourceAsStream(FILE);
                while(is.available() > 0)
                {
                		b.append((char)is.read());
                }
                is.close();
                return b.toString();
            }
            catch (Exception e)
            {
                /*
                    This should never happen; if it does, come beat me with a
                    rubber chicken
                 */
                throw new RuntimeException(e);
            }
        }
    }

    /*
        Game Logic class to run the game of Fortunes Tower.
        This action listener will draw a row of cards, display them,
        and if a Misfortune occurs, try to correct it if one hasn't happened yet.
     */
    private class DealListener implements ActionListener
    {
        private boolean trySave;

        public DealListener()
        {
            trySave = false;
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {

            /*
                See the file "fortunes tower algorithm" for the details
                of this algorithm.
             */
            if (!board.isComplete())
            {
                board.nextRow();
                rowPtr++;
                panel.addRow(map.mapRow(rowPtr));
                fields[rowPtr].setText("" + board.lastRowValue());

                if (board.misFortune())
                {
                    if (!trySave)
                    {
                        StringBuilder b = new StringBuilder("A Misfortune has Occured!\n");
                        b.append("A save will now be attempted");
                        trySave = true;
                        JOptionPane.showMessageDialog(null, b.toString(), "Misfortune",
                                JOptionPane.INFORMATION_MESSAGE);
                        board.trySave();
                        panel.flipGate();
                        panel.removeLastRow();
                        panel.addRow(map.mapRow(rowPtr));
                        fields[rowPtr].setText("" + board.lastRowValue());

                        //panel.repaint();
                        if (board.misFortune())
                        {
                            JOptionPane.showMessageDialog(null, "Misfortune. Game Over");
                            //test.setEnabled(false);
                            drawButton.setEnabled(false);
                            cashOutButton.setEnabled(false);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Misfortune. Game Over", "Misfortune",
                                JOptionPane.INFORMATION_MESSAGE);
                        //test.setEnabled(false);
                        drawButton.setEnabled(false);
                        cashOutButton.setEnabled(false);
                    }

                }
                if (board.isComplete() && !board.misFortune())
                {
                    if (board.hitJackpot())
                    {
                        int total = board.jackpotValue();
                        JOptionPane.showMessageDialog(null, "Jackpot!\n" + total);
                        playerPoints += total;
                    }
                    else
                    {
                        int total = board.rowValue(7);
                        JOptionPane.showMessageDialog(null, "Winner\n" + total);
                        playerPoints += total;
                    }
                    //test.setEnabled(false);
                    pointLabel.setText("Points: " + playerPoints);
                    drawButton.setEnabled(false);
                    cashOutButton.setEnabled(false);
                }
            }
        }
    }

    /*
        Allows the player to cash out.
        The player wins the current row's point value,
        and the game is continued, displaying the remainder of the rows,
        but does not check for a misfortune
     */
    private class CashOutListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (drawButton.isEnabled())
            {
                int total = board.rowValue(rowPtr);
                JOptionPane.showMessageDialog(null, "Cashed out and won:\n" + total);
                cashOutButton.setEnabled(false);
                drawButton.setEnabled(false);
                playerPoints += total;
                pointLabel.setText("Points: " + playerPoints);

                if (!board.isComplete())
                    JOptionPane.showMessageDialog(null, "The game will now continue and show the results\n",
                            "What if?", JOptionPane.PLAIN_MESSAGE);
                while (!board.isComplete())
                {
                    board.nextRow();
                    rowPtr++;
                    panel.addRow(map.mapRow(rowPtr));
                    fields[rowPtr].setText("" + board.lastRowValue());
                }
            }
        }
    }

    /*
     * Resets the game to the initial state.
     * Each game object is recreated and set to their initial states
     * and the frame is reset to display the first two rows.
     */
    private class ResetListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            cp.remove(panel);
            deck = new TestDeck(numCards);
            panel = new GamePanel();
            board = new Board2D(deck);
            for (int i = 1; i < fields.length; i++)
                fields[i].setText("");
            rowPtr = 1;

            cp.add(panel, BorderLayout.CENTER);
            panel.setBackgroundColor(FOREGROUND);
            panel.setBackground(FOREGROUND);

            drawButton.setEnabled(true);
            drawButton.removeActionListener(draw);
            draw = new DealListener();
            drawButton.addActionListener(draw);
            cashOutButton.setEnabled(true);
            postInit();
        }
    }
}
