package gomokugame;

/**
 * Represents a game piece on a board
 *
 * @author ndevr
 * @version May 24, 2020
 * @author Period: 4
 * @author Assignment: src
 *
 * @author Sources: asdvawei
 */
public class GamePiece
{

    private Player player;

    private Location loc;


    /**
     * makes a new gamepiece at loc -1,-1
     */
    public GamePiece()
    {
        this.player = null;
        this.loc = new Location( -1, -1 );
    }


    /**
     * makes new piece at location
     * 
     * @param p
     *            owner of peice
     * @param l
     *            location of peice
     */
    public GamePiece( Player p, Location l )
    {
        this.player = p;
        this.loc = l;
    }


    /**
     * returns the player who owns this peice
     * 
     * @return the player
     */
    public Player getPlayer()
    {
        return this.player;
    }


    /**
     * gets the peice's location
     * 
     * @return the location
     */
    public Location getLoc()
    {
        return this.loc;
    }


    /**
     * returns the player's symbol, used for console gomoku
     */
    public String toString()
    {
        return player == null ? "*" : player.sym();
    }
}