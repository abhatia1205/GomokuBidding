package gomokugame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import playerai.*;


/**
 * This is a GUI front end for Gomoku, and will collect the data of the player’s
 * bet choices for the game. It creates the a panel for users to input their
 * bets.
 *
 * @author Angela
 * @author Assignment: Gomoku Final Project
 */
public class BidWindow extends JFrame
{
    // ----------------------------------------------------------------------
    // INSTANCE VARIABLES for graphics

    // Buttons:
    private JButton playBtn;

    // Fields:
    private JTextField oneF;

    private JTextField twoF;

    private JLabel askAmount;

    private JLabel askAmount2;

    // Panel set up:
    private JPanel custPanel;

    private Container c;

    private static JFrame thisWindow;

    private GomokuWindow returnWindow;

    // ----------------------------------------------------------------------
    // INSTANCE VARIABLES for game play

    private int numPlayers;

    private int nextPlayer;

    private ArrayList<Player> players;


    /**
     * @param numPl
     *            the number of Players
     * @param player
     *            an array of the players
     */
    public BidWindow( int numPl, ArrayList<Player> player )
    {
        super( "Bid!" );
        thisWindow = this;

        this.numPlayers = numPl;
        nextPlayer = 0;
        players = player;

        askAmount = new JLabel(
            "Bid Player 1 (White) (Remaining: " + 
                            players.get( 0 ).getTokens() + " )", JLabel.RIGHT );
        askAmount2 = new JLabel(
            "Bid Player 2 (Black) (Remaining: " + 
                            players.get( 1 ).getTokens() + " )",
            JLabel.RIGHT );
        playBtn = new JButton( "Bet" );
        PlayListener playListener = new PlayListener();
        playBtn.addActionListener( playListener );

        if ( numPlayers == 1 )
        {
            onePlayerSetUp();
        }
        else
        {
            twoPlayerSetUp();
        }
    }


    /**
     * Returns the number of the next player
     * 
     * @return the next player
     */
    public int nextPlayer()
    {
        return nextPlayer;
    }


    /**
     * Open the window and make it visible
     * 
     * @param win
     *            the gomoku window information
     */
    public void open( GomokuWindow win )
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setVisible( true );
        setBounds( 0, 0, 500, 200 );
        returnWindow = win;
    }


    /**
     * Sets up the window for one player (one input)
     */
    private void onePlayerSetUp()
    {
        oneF = new JTextField( 3 );

        custPanel = new JPanel( new GridLayout( 2, 2, 10, 10 ) );
        custPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

        custPanel.add( askAmount );
        custPanel.add( oneF );
        custPanel.add( new JPanel() );
        custPanel.add( playBtn );

        c = getContentPane();
        c.add( custPanel );

    }


    /**
     * Sets up the window for two players (two inputs).
     */
    private void twoPlayerSetUp()
    {
        oneF = new JTextField( 3 );
        twoF = new JTextField( 3 );

        custPanel = new JPanel( new GridLayout( 3, 2, 10, 10 ) );
        custPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

        custPanel.add( askAmount );
        custPanel.add( oneF );
        custPanel.add( askAmount2 );
        custPanel.add( twoF );
        custPanel.add( new JPanel() );
        custPanel.add( playBtn );

        c = getContentPane();
        c.add( custPanel );

    }


    /**
     * Creates a listener for action for the button in order to check the number
     * inputed into the field of the window
     *
     * @author Angela
     * @version May 24, 2020
     * @author Period: 5
     * @author Assignment: Gomoku Final Project
     *
     * @author Sources: 5
     */
    private class PlayListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
        {
            if ( numPlayers == 1 )
            {
                oneAction();
            }
            else
            {
                twoAction();
            }
        }


        /**
         * Checks the one number inputed to make sure it is valid
         */
        private void oneAction()
        {
            String amountOne = oneF.getText().trim();

            Integer numOne = null;

            try
            {
                numOne = Integer.parseInt( amountOne );
            }
            catch ( NumberFormatException e )
            {
                notInt();
            }

            makeMove( (int)numOne );
        }


        /**
         * Makes the move for one player, using the bid from the AI
         * 
         * @param num1
         *            the number inputed by the user
         */
        private void makeMove( int num1 )
        {
            if ( players.get( 0 ).validBid( num1 ) )
            {
                int num2 = ( (AiPlayer)players.get( 1 ) ).bid();
                if ( num1 > num2 )
                {
                    nextPlayer = 1;
                    players.get( 1 ).addTokens( num1 );
                    players.get( 0 ).subTokens( num1 );
                    close();
                }

                else if ( num2 > num1 )
                {
                    players.get( 0 ).addTokens( num1 );
                    players.get( 1 ).subTokens( num1 );
                    nextPlayer = 2;
                    close();
                }
                else
                {
                    JOptionPane.showMessageDialog( thisWindow,
                        "Bid amounts were the same",
                        "Bid failed",
                        JOptionPane.ERROR_MESSAGE );
                }
            }
            else
            {
                JOptionPane.showMessageDialog( thisWindow,
                    "Bid number not valid",
                    "Bid failed",
                    JOptionPane.ERROR_MESSAGE );
            }
        }


        /**
         * Checks the two numbers inputed by the users
         */
        private void twoAction()
        {
            String amountOne = oneF.getText().trim();
            String amountTwo = twoF.getText().trim();

            Integer numOne = null;
            Integer numTwo = null;

            try
            {
                numOne = Integer.parseInt( amountOne );
            }
            catch ( NumberFormatException e )
            {
                notInt();
            }

            try
            {
                numTwo = Integer.parseInt( amountTwo );
            }
            catch ( NumberFormatException e )
            {
                notInt();
            }

            makeMoveTwo( (int)numOne, (int)numTwo );
        }


        /**
         * Displays a pop up message if the info entered is not a valid number
         */
        private void notInt()
        {
            JOptionPane.showMessageDialog( thisWindow,
                "Please input numbers",
                "Bid failed",
                JOptionPane.ERROR_MESSAGE );
        }


        /**
         * Checks the two numbers inputed and decides which one is larger
         * 
         * @param num1
         *            the first number inputed
         * @param num2
         *            the second number inputed
         */
        private void makeMoveTwo( int num1, int num2 )
        {
            if ( players.get( 0 ).validBid( num1 ) && 
                            players.get( 1 ).validBid( num2 ) )
            {
                if ( num1 > num2 )
                {
                    nextPlayer = 1;
                    players.get( 1 ).addTokens( num1 );
                    players.get( 0 ).subTokens( num1 );
                    close();
                }

                else if ( num2 > num1 )
                {
                    players.get( 0 ).addTokens( num2 );
                    players.get( 1 ).subTokens( num2 );
                    nextPlayer = 2;
                    close();
                }
                else
                {
                    JOptionPane.showMessageDialog( thisWindow,
                        "Bid amounts were the same",
                        "Bid failed",
                        JOptionPane.ERROR_MESSAGE );
                }
            }
            else
            {
                JOptionPane.showMessageDialog( thisWindow,
                    "Bid number not valid",
                    "Bid failed",
                    JOptionPane.ERROR_MESSAGE );
            }
        }


        /**
         * Close the bid window
         */
        private void close()
        {
            c.setVisible( false );
            custPanel.setVisible( false );
            thisWindow.dispose();
            returnWindow.bidSwitchPlayer( nextPlayer );
        }
    }
}
