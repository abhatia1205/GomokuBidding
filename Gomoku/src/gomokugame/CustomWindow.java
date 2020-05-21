package gomokugame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;


public class CustomWindow extends JFrame
{
    // ----------------------------------------------------------------------
    // INSTANCE VARIABLES for graphics

    private JPanel custPanel;

    private static JFrame thisWindow;

    private Container c;

    // Buttons:
    private JButton onePlay;

    private JButton twoPlay;

    private JButton bid;

    private JButton noBid;

    private JButton playBtn;

    private JButton closeBtn;

    // ----------------------------------------------------------------------
    // INSTANCE VARIABLES for game play
    private int numPlayer; // 1 is 1 v ai, 2 is 1 v 1

    private int bidding; // 0 is regular game, 1 is with bidding


    /**
     * Create a new custom window
     */
    public CustomWindow()
    {
        // Create a window
        super( "Gomoku!" );
        thisWindow = this;

        // Set instance variables
        bidding = -1;
        numPlayer = -1;

        // Build all graphics components.
        OneListener oneListener = new OneListener();
        TwoListener twoListener = new TwoListener();
        BidListener bidListener = new BidListener();
        RegListener regListener = new RegListener();
        PlayListener playListener = new PlayListener();
        CloseListener closeListener = new CloseListener();

        JLabel numPlayers = new JLabel( "1 or 2 players?: ", JLabel.RIGHT );
        onePlay = new JButton( "1 Player" );
        onePlay.setBackground( Color.LIGHT_GRAY );
        onePlay.addActionListener( oneListener );
        twoPlay = new JButton( "2 Player" );
        twoPlay.setBackground( Color.LIGHT_GRAY );
        twoPlay.addActionListener( twoListener );

        JLabel typeGame = new JLabel( "Bid or non bid?: ", JLabel.RIGHT );
        bid = new JButton( "Bidding" );
        bid.setBackground( Color.LIGHT_GRAY );
        bid.addActionListener( bidListener );
        noBid = new JButton( "No Bidding" );
        noBid.setBackground( Color.LIGHT_GRAY );
        noBid.addActionListener( regListener );

        playBtn = new JButton( "Play" );
        playBtn.setBackground( Color.LIGHT_GRAY );
        playBtn.addActionListener( playListener );

        closeBtn = new JButton( "Close" );
        closeBtn.setBackground( Color.LIGHT_GRAY );
        closeBtn.addActionListener( closeListener );

        custPanel = new JPanel( new GridLayout( 3, 3, 10, 10 ) );
        custPanel.setBorder( new EmptyBorder( 10, 10, 10, 10 ) );

        custPanel.add( numPlayers );
        custPanel.add( onePlay );
        custPanel.add( twoPlay );

        custPanel.add( typeGame );
        custPanel.add( bid );
        custPanel.add( noBid );

        custPanel.add( new JPanel() );
        custPanel.add( playBtn );
        custPanel.add( closeBtn );

        c = getContentPane();
        c.add( custPanel );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setVisible( true );
        setBounds( 0, 0, 360, 140 );
    }


    private class OneListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
        {
            numPlayer = 1;
            onePlay.setBackground( Color.GRAY );
            twoPlay.setBackground( Color.LIGHT_GRAY );
        }
    }


    private class TwoListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
        {
            numPlayer = 2;
            twoPlay.setBackground( Color.GRAY );
            onePlay.setBackground( Color.LIGHT_GRAY );
        }
    }


    private class BidListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
        {
            bidding = 1;
            bid.setBackground( Color.GRAY );
            noBid.setBackground( Color.LIGHT_GRAY );
        }
    }


    private class RegListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
        {
            bidding = 0;
            noBid.setBackground( Color.GRAY );
            bid.setBackground( Color.LIGHT_GRAY );
        }
    }


    private class PlayListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
        {
            if ( bidding == -1 || numPlayer == -1 )
            {
                complete();
            }
            else
            {
                c.setVisible( false );
                custPanel.setVisible( false );
                thisWindow.dispose();
                SwingUtilities.invokeLater( new Runnable()
                {
                    @Override
                    public void run()
                    {
                        new GomokuWindow( bidding, numPlayer );
                    }
                } );
            }

        }


        private void complete()
        {
            JOptionPane.showMessageDialog( thisWindow,
                "Please choose both bidding and number of players",
                "Start failed",
                JOptionPane.ERROR_MESSAGE );
        }
    }


    private class CloseListener implements ActionListener
    {
        public void actionPerformed( ActionEvent r )
        {
            c.setVisible( false );
            custPanel.setVisible( false );
            thisWindow.dispose();
        }
    }


    public static void main( String[] args )
    {
        CustomWindow window = new CustomWindow();
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setBounds( 0, 0, 360, 140 );
        window.setVisible( true );
    }
}