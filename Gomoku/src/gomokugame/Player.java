package gomokugame;

/**
 * represents all the players has all the base methods and constructors and
 * stuff idk
 *
 * @author ndevr
 * @version May 24, 2020
 * @author Period: 4
 * @author Assignment: src
 *
 * @author Sources: 12424
 */
public abstract class Player
{
    protected String name;

    protected String symbol;

    protected GomokuBoard board;

    protected int tokens;

    public int lastBid;


    /**
     * @param n
     *            name of player
     * @param s
     *            symbol of player
     * @param b
     *            the game board
     */
    public Player( String n, String s, GomokuBoard b )
    {
        name = n;
        symbol = s;
        board = b;
    }


    /**
     * @param n
     *            name of player
     * @param s
     *            symbol of player
     * @param b
     *            the game board
     * @param t
     *            the tokens the player starts with
     */
    public Player( String n, String s, GomokuBoard b, int t )
    {
        this( n, s, b );
        tokens = t;
    }


    /**
     * returns the name
     * 
     * @return the name
     */
    public String getName()
    {
        return name;
    }


    /**
     * returns the symbol
     * 
     * @return the symbol
     */
    public String sym()
    {
        return symbol;
    }


    /**
     * toekns
     * 
     * @return player token amount
     */
    public int getTokens()
    {
        return tokens;
    }


    /**
     * subtracts tokens
     * 
     * @param t
     *            the amount
     */
    public void subTokens( int t )
    {
        tokens -= t;
    }


    /**
     * adds tokens
     * 
     * @param t
     *            the amount
     */
    public void addTokens( int t )
    {
        tokens += t;
    }


    /**
     * returns the board
     * 
     * @return the game board
     */
    public GomokuBoard getBoard()
    {
        return board;
    }


    /**
     * returns if a bid is valid
     * 
     * @param amt
     *            bid amount
     * @return if valid
     */
    public boolean validBid( int amt )
    {
        if ( amt >= 0 && amt <= getTokens() )
        {
            return true;
        }
        return false;
    }


    /**
     * returns the player's bid amount
     * 
     * @return bid amount
     */
    public abstract int bid();


    /**
     * the location of the player's next move
     * 
     * @return the loc
     */
    public abstract Location playTurn();


    /**
     * the player as a string
     * 
     * @return the player's name
     */
    public String toString()
    {
        return name;
    }

}