package gomokugame;

/**
 * represents a location on the board
 *
 * @author ndevr
 * @version May 24, 2020
 * @author Period: 4
 * @author Assignment: src
 *
 * @author Sources: asdfe
 */
public class Location
{

    public int row;

    public int col;


    /**
     * makes new invalid location
     */
    public Location()
    {
        row = -1;
        col = -1;
    }


    /**
     * @param r
     *            the row of piece
     * @param c
     *            the col
     */
    public Location( int r, int c )
    {
        row = r;
        col = c;
    }


    /**
     * gets the piece's row location
     * 
     * @return the row
     */
    public int row()
    {
        return row;
    }


    /**
     * gets the piece's col location
     * 
     * @return the col
     */
    public int col()
    {
        return col;
    }


    /**
     * resets the peice location
     * 
     * @param r
     *            row
     * @param c
     *            col
     */
    public void set( int r, int c )
    {
        row = r;
        col = c;
    }


    /**
     * prints out the location in (r, c) format
     */
    public String toString()
    {
        String s = "(" + row + ", " + col + ")";
        return s;
    }

}