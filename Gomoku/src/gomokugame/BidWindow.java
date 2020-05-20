package gomokugame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;


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

    public BidWindow( int numPl, ArrayList<Player> player )
    {
        super( "Bid!" );
        thisWindow = this;

        this.numPlayers = numPl;
        nextPlayer = 0;
        players = player;

        askAmount = new JLabel(
            "Bid Player 1 (White) (Remaining: " + 
            players.get( 0 ).getTokens() + " )",
            JLabel.RIGHT );
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


    public int nextPlayer()
    {
        return nextPlayer;
    }


    public void open( GomokuWindow win )
    {
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setVisible( true );
        setBounds( 0, 0, 500, 200 );
        returnWindow = win;
    }


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


    private class PlayListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
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

            makeMove( (int)numOne, (int)numTwo );
        }


        private void notInt()
        {
            JOptionPane.showMessageDialog( thisWindow,
                "Please input numbers",
                "Bid failed",
                JOptionPane.ERROR_MESSAGE );
        }


        private void makeMove( int num1, int num2 )
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


        private void close()
        {
            c.setVisible( false );
            custPanel.setVisible( false );
            thisWindow.dispose();
            returnWindow.bidSwitchPlayer( nextPlayer );
        }
    }


    public void main( int numPl, ArrayList<Player> player )
    {
        BidWindow window = new BidWindow( numPl, player );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setBounds( 0, 0, 360, 140 );
        window.setVisible( true );
    }

}
