package gomokugame;

import java.util.*;
import minimax.*;


/**
 * @author ndevr Simulates a game of gomoku in the console only really for
 *         testing purposes
 */
public class ConsoleGomoku
{

    // stores the board, the players
    private GomokuBoard board;

    private ArrayList<Player> players;

    // if the game is a bidding game
    private boolean bidding;


    /**
     * @param b
     *            if its a bidding game
     * @param t
     *            the starting bidding token amount
     */
    public ConsoleGomoku( boolean b, int t )
    {
        this.board = new GomokuBoard( 15 );
        bidding = b;

        players = new ArrayList<Player>();
        players.add( new ConsolePlayer( "p1", "X", this.board, t ) );
        players.add( new ConsolePlayer( "p2", "O", this.board, t ) );
    }


    /**
     * @param c
     *            i really dont remember
     * @param s
     *            i think i added this just to seperate it from other
     *            constructors
     */
    public ConsoleGomoku( boolean c, String s )
    {
        this.board = new GomokuBoard( 15 );
        bidding = false;
        players = new ArrayList<Player>();
        players.add( new ConsolePlayer( "p1", "X", this.board, 0 ) );
        MmPlayer a = new MmPlayer( "p2", "O", this.board );
        a.setup( players.get( 0 ), 3 );
        players.add( a );

    }


    /**
     * returns the gomokuboard
     * 
     * @return the board
     */
    public GomokuBoard getBoard()
    {
        return board;
    }


    /**
     * plays a round with no bidding
     * 
     * @return if the game is over
     */
    public boolean playNoBidRound()
    {
        for ( Player p : this.players )
        {
            System.out.println( this.getBoard() );
            GamePiece a = new GamePiece( p, p.playTurn() );
            board.placePiece( a );
            if ( board.check4Win( a ) != null )
            {
                System.out.println( p + " won the game" );
                return true;
            }
        }
        return false;
    }


    /**
     * executes the bidding in console
     * 
     * @return the player who plays next
     */
    public Player doBidding()
    {
        for ( Player p : this.players )
        {
            p.bid();
        }
        Player bidWinner = players.get( 0 ).lastBid > players.get( 1 ).lastBid
            ? players.get( 0 )
            : players.get( 1 );
        Player bidLoser = players.get( 0 ).lastBid > players.get( 1 ).lastBid
            ? players.get( 1 )
            : players.get( 0 );
        int highestBid = players.get( 0 ).lastBid > players.get( 1 ).lastBid
            ? players.get( 0 ).lastBid
            : players.get( 1 ).lastBid;
        bidWinner.subTokens( highestBid );
        bidLoser.addTokens( highestBid );
        return bidWinner;
    }


    /**
     * does a round with bidding
     * 
     * @return if game is over
     */
    public boolean playBidRound()
    {
        Player bidWinner = doBidding();
        GamePiece a = new GamePiece( bidWinner, bidWinner.playTurn() );
        board.placePiece( a );
        if ( board.check4Win( a ) != null )
        {
            System.out.println(
                "Congratulations!!!! " + bidWinner + " won the game" );
            return true;
        }
        return false;
    }


    /**
     * plays gomoku
     */
    public void playGomoku()
    {
        boolean stop = false;
        while ( true )
        {
            stop = bidding ? playBidRound() : playNoBidRound();
            if ( stop )
            {
                return;
            }
        }
    }


    /**
     * runs gomoku game in console
     * 
     * @param args
     *            args
     */
    public static void main( String[] args )
    {
        ConsoleGomoku game = new ConsoleGomoku( true, "hi" );
        game.playGomoku();
    }
}