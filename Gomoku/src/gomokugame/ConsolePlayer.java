package gomokugame;

import java.util.*;


/**
 * a Player who plays in the console
 *
 * @author ndevr
 * @version May 24, 2020
 * @author Period: 4
 * @author Assignment: src
 *
 * @author Sources: asdfawevasd
 */
public class ConsolePlayer extends Player
{

    // a scanner
    Scanner scan;


    /**
     * just calls the super constructor
     * 
     * @param n
     *            name
     * @param s
     *            symbol
     * @param b
     *            game board
     */
    public ConsolePlayer( String n, String s, GomokuBoard b )
    {
        super( n, s, b );
        scan = new Scanner( System.in );
    }


    /**
     * same as previous but adds a set token value
     * 
     * @param n
     *            name
     * @param s
     *            sym
     * @param b
     *            board
     * @param t
     *            token amount
     */
    public ConsolePlayer( String n, String s, GomokuBoard b, int t )
    {
        super( n, s, b, t );
        scan = new Scanner( System.in );
    }


    /**
     * plays a turn of this player in the console
     * 
     * @return the location of next move
     */
    public Location playTurn()
    {
        Location a = null;
        while ( a == null )
        {
            System.out.println( this.getName() + " play round" );
            System.out.print( "Row: " );
            int r = scan.nextInt();
            System.out.print( "Col: " );
            int c = scan.nextInt();
            a = new Location( r, c );
            if ( !getBoard().validMove( a ) )
            {
                a = null;
            }
        }
        return a;
    }


    /**
     * tells the amount the player bids gets value through the scanner input
     * 
     * @return the bid amount
     */
    public int bid()
    {
        this.lastBid = 10000;
        while ( !validBid( this.lastBid ) )
        {
            System.out.println( getName() + " has " + getTokens() + " tokens" );
            System.out.print( "Tokens to bid: " );
            this.lastBid = scan.nextInt();
        }
        return lastBid;
    }
}