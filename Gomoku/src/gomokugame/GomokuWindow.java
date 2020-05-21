package gomokugame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import minimax.MmPlayer;

import java.util.ArrayList;
import java.util.Random;


public class GomokuWindow extends JFrame
{

    // ----------------------------------------------------------------------
    // INSTANCE VARIABLE CONSTANTS

    private static final int ROWS = 16; // ROWS by COLS cells

    private static final int COLS = 16;

    // Named-constants of the various dimensions used for graphics drawing
    private static final int CELL_SIZE = 50; // cell width and height (square)

    private static final int CANVAS_WIDTH = CELL_SIZE * COLS; // the drawing
                                                              // canvas

    private static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

    private static final int GRID_WIDTH = 4; // Grid-line's width

    private static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Grid-line's
                                                               // half-width

    // Symbols (cross/nought) are displayed inside a cell, with padding from
    // border
    private static final int CELL_PADDING = CELL_SIZE / 6;

    // width/height
    private static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; 
 
    private static final int SYMBOL_STROKE_WIDTH = 4; // pen's stroke width

    // ----------------------------------------------------------------------
    // INSTANCE VARIABLES for game play

    private GameState currentState; // the current game state

    public ArrayList<Player> players; // holds all the players

    private Player currentPlayer; // the current player (1 or 2)

    private GomokuBoard board; // the board to play on

    private int bidding; // 0 for not a bidding game, 1 for a bidding game

    private int numPlayers; // 1 for 1 v ai; 2 for 1 v 1

    public GamePiece[][] b; // 2D array of type GamePiece to hold information
                            // on the pieces on the board

    // ----------------------------------------------------------------------
    // INSTANCE VARIABLES for graphics

    private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board

    private JLabel statusBar; // Status Bar

    private Container cp; // Container for the graphics

    // ----------------------------------------------------------------------
    // INNER CLASSES


    // Use an enumeration (inner class) to represent the various states of the
    // game.
    public enum GameState {
        PLAYING, DRAW, WH_WON, BL_WON
    }


    // ----------------------------------------------------------------------
    // CONSTRUCTOR

    public GomokuWindow( int bid, int num )
    {
        // ----------------------------------------
        // Build all graphics components.

        canvas = new DrawCanvas(); // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize( new Dimension( CANVAS_WIDTH, CANVAS_HEIGHT ) );

        // set the instance variables according to the parameters
        bidding = bid;
        numPlayers = num;

        // set players
        this.board = new GomokuBoard( 15 );
        players = new ArrayList<Player>();
        players.add( new ConsolePlayer( "p1", "X", this.board, bid * 50 ) );
        if ( numPlayers == 1 )// 1 player (1 v ai)
        {
            MmPlayer a = new MmPlayer( "p2", "O", this.board );
            a.setup( players.get( 0 ), 3 );
            players.add( a );
        }
        else // 2 player (1 v 1)
        {
            players.add( new ConsolePlayer( "p2", "O", this.board, bid * 50 ) );
        }

        // The canvas (JPanel) fires a MouseEvent upon mouse-click
        canvas.addMouseListener( new MouseAdapter()
        {
            @Override
            public void mouseClicked( MouseEvent e )
            {   
                // mouse-clicked handler
                // Get the x and y coordinates of the screen pixel that was
                // clicked.
                int mouseX = e.getX() - 35;
                int mouseY = e.getY() - 35;
                if ( mouseX < 0 )
                {
                    mouseX = 0;
                }
                if ( mouseY < 0 )
                {
                    mouseY = 0;
                }
                // Convert the x,y screen coordinates to a row and column index.
                int rowSelected = mouseY / CELL_SIZE;
                int colSelected = mouseX / CELL_SIZE;

                makeMoveOrRestart( rowSelected, colSelected );
                
                // Refresh the drawing canvas by posting the repaint event,
                // which signals
                // the JPanel code to call its paintComponent method
                repaint();
                
                if ( numPlayers == 1 && currentPlayer == players.get( 1 ) )
                {
                    aiMakeMove();
                    repaint();
                }
            }
        } );

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel( "  " );
        statusBar.setFont( new Font( Font.DIALOG_INPUT, Font.BOLD, 15 ) );
        statusBar.setBorder( BorderFactory.createEmptyBorder( 2, 5, 4, 5 ) );

        // Create a container for holding graphics components, and add the
        // canvas and status bar.
        cp = getContentPane();
        cp.setLayout( new BorderLayout() );
        cp.add( canvas, BorderLayout.CENTER );
        cp.add( statusBar, BorderLayout.PAGE_END );

        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        pack(); // pack all the components in this JFrame
        setBounds( 0,
            0,
            CELL_SIZE * ROWS + GRID_WIDTH * ROWS - 28,
            CELL_SIZE * COLS + GRID_WIDTH * ROWS + 28 );
        setTitle( "Gomoku" );
        setVisible( true ); // show this JFrame

        // set everything visible
        canvas.setVisible( true );
        statusBar.setVisible( true );
        cp.setVisible( true );

        // ----------------------------------------
        // Define game components and initialize the game.

        b = board.board(); // allocate array
        canvas.setB( b );
        currentPlayer = players.get( 0 );
        restartGame(); // initialize the game board contents and game variables

        if ( bidding == 1 ) // If a bidding game, open the bid window
        {
            bidCallWindow();
        }
    }


    /**
     * Opens a bid window
     */
    public void bidCallWindow()
    {
        if ( currentState == GameState.PLAYING )
        {
            BidWindow bidWindow = new BidWindow( numPlayers, players );
            bidWindow.open( this );
        }
        else
        {
            close();
        }
    }


    /**
     * Switches the current player according to the bid amount
     * 
     * @param nextPlayer
     *            either 1 or 2, which means player 1 or player 2, respectively
     */
    public void bidSwitchPlayer( int nextPlayer )
    {
        currentPlayer = null;
        currentPlayer = ( nextPlayer == 1 ) ? players.get( 0 ) : 
            players.get( 1 );

        statusBar.setForeground( Color.BLACK ); // update the current player
        if ( currentPlayer == players.get( 0 ) )// on the bottom of the screen
        {
            statusBar.setText( "White's Turn" );
        }
        else
        {
            statusBar.setText( "Black's Turn" );
        }
    }


    /**
     * If game is not a win or draw yet and this is a valid move, make the move
     * and update the game state. Add code here to automatically make the
     * opponent's move, if the currentState is still GameState.PLAYING
     * 
     * @param rowSelected
     *            the row clicked on
     * @param colSelected
     *            the col clicked on
     */
    public void makeMoveOrRestart( int rowSelected, int colSelected )
    {
        if ( currentState == GameState.PLAYING )
        {
            Location loc = new Location( rowSelected, colSelected );
            if ( board.locationValid( loc ) == true && 
                            board.locationOccupied( loc ) == false )
            {
                GamePiece a = new GamePiece( currentPlayer, loc );
                updateGameState( a, rowSelected, colSelected );
                if ( bidding == 0 )
                {
                    switchPlayer();
                }
                else
                {
                    bidCallWindow();
                    if ( numPlayers == 1 && currentPlayer == players.get( 1 ) )
                    {
                        aiMakeMove(); // FIX TO BE THE CORRECT AI PLAYER
                    }
                }

            }
        }
        else
        {            // game over
            close(); // restart the game
        }
    }


    /**
     * Initialize the game-board contents and the status.
     */
    public void restartGame()
    {
        for ( int row = 0; row < ROWS - 1; ++row )
        {
            for ( int col = 0; col < COLS - 1; ++col )
            {
                b[row][col] = null; // all cells empty
            }
        }
        currentState = GameState.PLAYING; // ready to play
        currentPlayer = players.get( 0 ); // white plays first
    }


    /**
     * Close the gomoku window, and re open the customization game window
     */
    private void close()
    {
        setVisible( false );
        SwingUtilities.invokeLater( new Runnable()
        {
            @Override
            public void run()
            {
                new CustomWindow(); // Let the constructor do the job
            }
        } );
    }


    /**
     * The AI player makes a move, or if the game is over, closes the window
     */
    public void aiMakeMove()
    {
        if ( currentState == GameState.PLAYING )
        {
            MmPlayer ai = (MmPlayer)players.get( 1 );
            Location next = ai.playTurn();
            int row = next.row();
            int col = next.col();
            makeMoveOrRestart( row, col );
        }
        else
        { // game over
            close(); // restart the game
        }
    }


    /**
     * Make the move and update the state of the game.
     * 
     * @param a
     *            the piece played (holds info on player and location)
     * @param rowSelected
     *            the row selected
     * @param colSelected
     *            the column selected
     */
    public void updateGameState( GamePiece a, int rowSelected, int colSelected )
    {
        b[rowSelected][colSelected] = a; // Make the move
        if ( board.check4Win( a ) != null ) // check for win
        {
            currentState = ( currentPlayer == players.get( 0 ) ) ? 
                GameState.WH_WON : GameState.BL_WON;
        }
        else if ( isDraw() ) // check for draw
        {
            currentState = GameState.DRAW;
        }
        // Otherwise, no change to current state (still GameState.PLAYING).
    }


    /**
     * Change to the next player
     */
    public void switchPlayer()
    {
        currentPlayer = ( currentPlayer == players.get( 0 ) ) ? 
            players.get( 1 ) : players.get( 0 );
    }


    /**
     * Return true if it is a draw (i.e., no more empty cell)
     * 
     * @return true for draw, false for not a draw yet
     */
    public boolean isDraw()
    {
        for ( int row = 0; row < ROWS - 1; ++row )
        {
            for ( int col = 0; col < COLS - 1; ++col )
            {
                if ( b[row][col] == null )
                {
                    return false; // an empty cell found, not draw, exit
                }
            }
        }
        return true; // no more empty cell, it's a draw
    }


    // ----------------------------------------------------------------------
    // INNER CLASS for performing graphics

    class DrawCanvas extends JPanel
    {

        private GamePiece[][] b;


        /**
         * sets instance variable b to a
         * 
         * @param a
         *            GamePiece array
         */
        public void setB( GamePiece[][] a )
        {
            b = a;
        }


        @Override
        public void paintComponent( Graphics g ) // invoke via repaint()
        {
            super.paintComponent( g ); // fill background
            setBackground( Color.WHITE ); // set its background color

            // Draw the grid-lines
            g.setColor( Color.GRAY );
            for ( int row = 1; row < ROWS; ++row )
            {
                g.fillRoundRect( 10,
                    CELL_SIZE * row - GRID_WIDTH_HALF + 10,
                    CANVAS_WIDTH - 1,
                    GRID_WIDTH,
                    GRID_WIDTH,
                    GRID_WIDTH );
            }
            for ( int col = 1; col < COLS; ++col )
            {
                g.fillRoundRect( CELL_SIZE * col - GRID_WIDTH_HALF
                    + 10, 10, GRID_WIDTH, CANVAS_HEIGHT - 1, 
                    GRID_WIDTH, GRID_WIDTH );
            }

            // Draw the border lines
            g.setColor( Color.DARK_GRAY );
            g.fillRoundRect( 0, // top line
                0,
                CANVAS_WIDTH - 1 + 20,
                10,
                GRID_WIDTH,
                GRID_WIDTH );

            g.fillRoundRect( 0, // bot line
                CELL_SIZE * ROWS - GRID_WIDTH_HALF + 10,
                CANVAS_WIDTH - 1 + 20,
                10,
                GRID_WIDTH,
                GRID_WIDTH );

            g.fillRoundRect( 0, // left line
                0,
                10,
                CANVAS_HEIGHT - 1 + 20,
                GRID_WIDTH,
                GRID_WIDTH );

            g.fillRoundRect( CELL_SIZE * COLS - GRID_WIDTH_HALF + 10, // right
                0,                                                    // line
                10,
                CANVAS_HEIGHT - 1 + 20,
                GRID_WIDTH,
                GRID_WIDTH );

            // Draw the Marks of all the cells if they are not empty
            // Draw the circles at each intersection
            // Use Graphics2D which allows us to set the pen's stroke
            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke( new BasicStroke( SYMBOL_STROKE_WIDTH,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND ) ); // Graphics2D only

            for ( int row = 1; row < ROWS; ++row ) // Draws circles at the
            { // intersections
                for ( int col = 1; col < COLS; ++col )
                {
                    g2d.setColor( Color.GRAY );
                    g2d.drawOval( CELL_SIZE * col - GRID_WIDTH_HALF + 7,
                        CELL_SIZE * row - GRID_WIDTH_HALF + 7,
                        10,
                        10 );
                    g2d.fillOval( CELL_SIZE * col - GRID_WIDTH_HALF + 7,
                        CELL_SIZE * row - GRID_WIDTH_HALF + 7,
                        10,
                        10 );
                }
            }

            for ( int row = 0; row < ROWS - 1; ++row ) // Draw the game pieces
            {
                for ( int col = 0; col < COLS - 1; ++col )
                {
                    int x1 = col * CELL_SIZE + CELL_PADDING + 35;
                    int y1 = row * CELL_SIZE + CELL_PADDING + 35;
                    if ( b[row][col] != null )
                    {
                        if ( b[row][col].getPlayer() == players.get( 0 ) )
                        {
                            g2d.setColor( Color.LIGHT_GRAY );
                            g2d.drawOval( x1, y1, SYMBOL_SIZE, SYMBOL_SIZE );
                            g2d.fillOval( x1, y1, SYMBOL_SIZE, SYMBOL_SIZE );

                        }
                        else if ( b[row][col].getPlayer() == players.get( 1 ) )
                        {
                            g2d.setColor( Color.BLACK );
                            g2d.drawOval( x1, y1, SYMBOL_SIZE, SYMBOL_SIZE );
                            g2d.fillOval( x1, y1, SYMBOL_SIZE, SYMBOL_SIZE );
                        }
                    }
                }
            }

            // Print status-bar message
            if ( currentState == GameState.PLAYING )
            {
                statusBar.setForeground( Color.BLACK );
                if ( currentPlayer == players.get( 0 ) )
                {
                    statusBar.setText( "White's Turn" );
                }
                else
                {
                    statusBar.setText( "Black's Turn" );
                }
            }
            else if ( currentState == GameState.DRAW )
            {
                statusBar.setForeground( Color.RED );
                statusBar.setText( "It's a Draw! Click to play again." );
            }
            else if ( currentState == GameState.WH_WON )
            {
                statusBar.setForeground( Color.RED );
                statusBar.setText( "White Won! Click to play again." );
            }
            else if ( currentState == GameState.BL_WON )
            {
                statusBar.setForeground( Color.RED );
                statusBar.setText( "Black Won! Click to play again." );
            }
        }
    }

}