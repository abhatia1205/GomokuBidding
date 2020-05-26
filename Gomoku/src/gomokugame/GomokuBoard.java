package gomokugame;

/**
 * represents a gomokuboard
 *
 * @author ndevrak
 * @version May 24, 2020
 * @author Period: 4
 * @author Assignment: src
 *
 * @author Sources: 123
 */
public class GomokuBoard
{

    private GamePiece[][] board;


    /**
     * makes a new empty 15 x 15 game board
     */
    public GomokuBoard()
    {
        this.board = new GamePiece[15][15];
    }


    /**
     * new board
     * 
     * @param width
     *            makes X by X board
     */
    public GomokuBoard( int width )
    {
        this.board = new GamePiece[width][width];
    }


    /**
     * returns the board array
     * 
     * @return the gamepice array
     */
    public GamePiece[][] b()
    {
        return board;
    }


    /**
     * the peice at a location
     * 
     * @param l
     *            the loc to get
     * @return the game peice at that loc
     */
    public GamePiece getPiece( Location l )
    {
        return board[l.row()][l.col()];
    }


    /**
     * checks if location is in the board
     * 
     * @param l
     *            location to check
     * @return if valid
     */
    public boolean locationValid( Location l )
    {
        if ( l.row() >= board.length || l.row() < 0 )
        {
            return false;
        }
        if ( l.col() >= board.length || l.col() < 0 )
        {
            return false;
        }
        return true;
    }


    /**
     * checks if location is occupied
     * 
     * @param l
     *            the location
     * @return if occupied
     */
    public boolean locationOccupied( Location l )
    {
        if ( locationValid( l ) && board[l.row()][l.col()] != null )
        {
            return true;
        }
        return false;
    }


    /**
     * gets the player who owns the peice at a location
     * 
     * @param l
     *            the location
     * @return the player
     */
    public Player getPiecePlayer( Location l )
    {
        if ( locationValid( l ) && locationOccupied( l ) )
        {
            return getPiece( l ).getPlayer();
        }
        return null;
    }


    /**
     * if its a valid move
     * 
     * @param l
     *            location
     * @return if valid
     */
    public boolean validMove( Location l )
    {
        return locationValid( l ) && !locationOccupied( l );
    }


    /**
     * updates board by adding a game piece at the location
     * 
     * @param p
     *            the game piece to be placed
     * @return if the piece was placed properly with no errors
     */
    public boolean placePiece( GamePiece p )
    {
        if ( locationValid( p.getLoc() ) && !locationOccupied( p.getLoc() ) )
        {
            board[p.getLoc().row()][p.getLoc().col()] = p;
            return true;
        }
        return false;
    }


    /**
     * removes the piece at a location
     * 
     * @param l
     *            the location
     * @return if a piece was removed
     */
    public boolean removePiece( Location l )
    {
        if ( board[l.row()][l.col()] == null )
        {
            return false;
        }
        board[l.row()][l.col()] = null;
        return true;
    }


    /**
     * prints out the board into the console, with row and column indicators
     */
    public String toString()
    {
        String s = "   0  1  2  3  4  5  6  7  8  9  0  1  2  3  4\n";
        Integer[] a = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4 };
        int i = 0;
        for ( GamePiece[] r : board )
        {
            s += a[i++] + " ";
            for ( GamePiece g : r )
            {

                s += g == null ? " * " : " " + g + " ";
            }
            s += "\n";
        }
        return s;
    }


    /**
     * checks if the a placed peice won the game
     * 
     * @param g
     *            the placed piece
     * @return the player who won, or null if none
     */
    public Player check4Win( GamePiece g )
    {
        if ( check4WinHoriz( g ) || check4WinVertical( g ) || check4WinDia( g )
            || check4WinDiaBack( g ) )
        {
            return g.getPlayer();
        }
        return null;
    }


    /**
     * checks 4 left right the piece to find consecutive 5
     * 
     * @param g
     *            the piece
     * @return if there is 5 in a row
     */
    private boolean check4WinHoriz( GamePiece g )
    {
        int row = g.getLoc().row();
        int center = g.getLoc().col();
        int counter = 0;
        for ( int col = center - 4; col < center + 5; col++ )
        {
            if ( getPiecePlayer( new Location( row, col ) ) == g.getPlayer() )
            {
                counter++;
                if ( counter >= 5 )
                {
                    return true;
                }
            }
            else
            {
                counter = 0;
            }
        }
        return false;
    }


    /**
     * checks up and down for win
     * 
     * @param g
     *            peice
     * @return if 5 in row
     */
    private boolean check4WinVertical( GamePiece g )
    {
        int center = g.getLoc().row();
        int col = g.getLoc().col();
        int counter = 0;
        for ( int row = center - 4; row < center + 5; row++ )
        {
            if ( getPiecePlayer( new Location( row, col ) ) == g.getPlayer() )
            {
                counter++;
                if ( counter >= 5 )
                {
                    return true;
                }
            }
            else
            {
                counter = 0;
            }
        }
        return false;
    }


    /**
     * checks diag for win
     * 
     * @param g
     *            peice
     * @return if 5 in row
     */
    private boolean check4WinDiaBack( GamePiece g )
    {
        int centerR = g.getLoc().row();
        int centerC = g.getLoc().col();
        int counter = 0;
        int col = centerC - 4;
        for ( int row = centerR - 4; row < centerR + 5; row++ )
        {
            if ( getPiecePlayer( new Location( row, col ) ) == g.getPlayer() )
            {
                counter++;
                if ( counter >= 5 )
                {
                    return true;
                }
            }
            else
            {
                counter = 0;
            }
            col++;
        }
        return false;
    }


    /**
     * checks diag for win
     * 
     * @param g
     *            peice
     * @return if 5 in row
     */
    private boolean check4WinDia( GamePiece g )
    {
        int centerR = g.getLoc().row();
        int centerC = g.getLoc().col();
        int counter = 0;
        int col = centerC + 4;
        for ( int row = centerR - 4; row < centerR + 5; row++ )
        {
            if ( getPiecePlayer( new Location( row, col ) ) == g.getPlayer() )
            {
                counter++;
                if ( counter >= 5 )
                {
                    return true;
                }
            }
            else
            {
                counter = 0;
            }
            col--;
        }
        return false;
    }
}