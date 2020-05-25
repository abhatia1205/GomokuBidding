package minimax;

import gomokugame.*;


/**
 * is the actual minimax player
 *
 * @author ndevr
 * @version May 24, 2020
 * @author Period: 123124
 * @author Assignment: src
 *
 * @author Sources: 123151245
 */
public class MmPlayer extends Player
{

    private MmEvaluator eval;

    private Player opp;

    private MmEvaluator evalOpp;

    private static int boundingBoxAddition = 2;

    private int searchDepth;


    /**
     * @param n
     *            name
     * @param s
     *            symbol
     * @param b
     *            board
     */
    public MmPlayer( String n, String s, GomokuBoard b )
    {
        super( n, s, b );
        eval = new MmEvaluator( b, this );
    }


    /**
     * assigns the opponent and the search depth
     * 
     * @param p
     *            opponent
     * @param d
     *            depth
     */
    public void setup( Player p, int d )
    {
        opp = p;
        evalOpp = new MmEvaluator( this.getBoard(), p );
        searchDepth = d;
    }


    /**
     * just here to satisfy the extends thing thing
     * 
     * @return the bid amount
     */
    public int bid()
    {
        System.out.println( "ERROR!: MiniMax player does not bid" );
        return -1;
    }


    /**
     * returns the next mvoe to make
     * 
     * @return the location of the next move
     */
    public Location playTurn()
    {
        return nextMove();
    }


    /**
     * a method for testing the board evaluation
     * 
     * @param myTurn
     *            if my turn
     * @return the board score
     */
    public int testEval( boolean myTurn )
    {
        return eval.boardStateScore( myTurn );
    }


    /**
     * places a peice for testing purposes
     * 
     * @param l
     *            the location to move
     * @return if the peice was placed
     */
    public boolean testPiece( Location l )
    {
        return board.placePiece( new GamePiece( this, l ) );
    }


    /**
     * gets a location for the top of a box bounding all existing peices
     * 
     * @return the upper left corner of box
     */
    public Location boundBoxStart()
    {
        int boardWidth = getBoard().b().length;
        int r1 = boardWidth - 1;
        int c1 = boardWidth - 1;
        for ( int r = 0; r < boardWidth; r++ )
        {
            for ( int c = 0; c < boardWidth; c++ )
            {
                if ( getBoard().b()[r][c] != null )
                {
                    if ( r < r1 )
                    {
                        r1 = r;
                    }
                    if ( c < c1 )
                    {
                        c1 = c;
                    }
                }
            }
        }
        r1 = Math.max( 0, r1 - boundingBoxAddition );
        c1 = Math.max( 0, c1 - boundingBoxAddition );
        return new Location( r1, c1 );
    }


    /**
     * gets the bottom right corner of a box bounding all existing pieces
     * 
     * @return the loc of bot right corner
     */
    public Location boundBoxEnd()
    {
        int boardWidth = getBoard().b().length;
        int r1 = 0;
        int c1 = 0;
        for ( int r = 0; r < boardWidth; r++ )
        {
            for ( int c = 0; c < boardWidth; c++ )
            {
                if ( getBoard().b()[r][c] != null )
                {
                    if ( r > r1 )
                    {
                        r1 = r;
                    }
                    if ( c > c1 )
                    {
                        c1 = c;
                    }
                }
            }
        }
        r1 = Math.min( boardWidth - 1, r1 + boundingBoxAddition );
        c1 = Math.min( boardWidth - 1, c1 + boundingBoxAddition );
        return new Location( r1, c1 );
    }


    /**
     * returns the next move to make
     * 
     * @return loc of next mvoe
     */
    public Location nextMove()
    {
        return nextMove( searchDepth );
    }


    /**
     * represents first layer of minimax calculation
     * 
     * @param depth
     *            the depth to search
     * @return location of best move
     */
    public Location nextMove( int depth )
    {
        Location bestMove = new Location();
        int bestScore = -100000000;
        int score;
        Location bStart = boundBoxStart();
        Location bEnd = boundBoxEnd();
        for ( int r = bStart.row(); r < bEnd.row(); r++ )
        {
            for ( int c = bStart.col(); c < bEnd.col(); c++ )
            {

                if ( !getBoard().locationOccupied( new Location( r, c ) ) )
                {
                    getBoard().placePiece(
                        new GamePiece( this, new Location( r, c ) ) );
                    score = moveTreeScore( depth, false, bStart, bEnd );
                    getBoard().removePiece( new Location( r, c ) );
                    // System.out.println(score + "\t\t" + r + " " + c);

                    if ( score > bestScore )
                    {
                        bestScore = score;
                        bestMove.set( r, c );
                    }
                }
            }
        }
        // System.out.println(bestScore + "\t\t" + bestMove.row() + " " +
        // bestMove.col());
        return bestMove;
    }


    /**
     * goes "down the tree" of possible next moves for each first move to make
     * gets the max value in each tree and returns it to nextMove(int)
     * 
     * @param depth
     *            the layers left to go, counts down each recursive call
     * @param myTurn
     *            if its my turn
     * @param boundStart
     *            the start of the bounding box
     * @param boundEnd
     *            the end of the bounding box
     * @return the minimax value of the subtree so far
     */
    public int moveTreeScore(
        int depth,
        boolean myTurn,
        Location boundStart,
        Location boundEnd )
    {
        int score = 0;
        int highScore = myTurn ? -1000000000 : 1000000000;
        // int highScore = 0;
        if ( depth <= 1 )
        {
            return eval.boardStateScore( myTurn )
                - evalOpp.boardStateScore( !myTurn );
        }
        Location loc = new Location();
        Player p = this;
        for ( int r = boundStart.row(); r < boundEnd.row(); r++ )
        {
            for ( int c = boundStart.col(); c < boundEnd.col(); c++ )
            {

                loc.set( r, c );
                if ( !board.locationOccupied( loc ) )
                {
                    p = myTurn ? this : opp;
                    getBoard()
                        .placePiece( new GamePiece( p, new Location( r, c ) ) );
                    score = moveTreeScore( depth - 1,
                        !myTurn,
                        boundBoxStart(),
                        boundBoxEnd() );
                    if ( myTurn ? score > highScore : score < highScore )
                    { // if its my turn then maximize, if not then minimize
                        highScore = score;
                    }
                    getBoard().removePiece( loc );
                }
            }
        }
        return highScore;
    }
}