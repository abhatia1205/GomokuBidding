package minimax;
import gomokugame.*;

public class MmPlayer extends Player{

    private MmEvaluator eval;
    private Player opp;
    private MmEvaluator evalOpp;

    public MmPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
        eval  = new MmEvaluator(b, this);
    }

    public void setupEvalOpp(Player p){
        opp = p;
        evalOpp = new MmEvaluator(this.getBoard(), p);
    }

    public int bid(){
        System.out.println("ERROR!: MiniMax player does not bid");
        return -1;
    }

    public boolean playTurn(){
        System.out.println("MmPlayer.playTurn() not set up yet");
        return false;
    }

    public int testEval(boolean myTurn){
        return eval.boardStateScore(myTurn);
    }

    public boolean testPiece(Location l){
        return board.placePiece(new GamePiece(this, l));
    }

    public Location nextMove(int depth){
        Location bestMove = new Location(-1,-1);
        int bestScore = -10000;
        int score;

        for(int r = 0; r < getBoard().board().length; r++){
            for(int c = 0; c < getBoard().board().length; c++){
                if(!getBoard().locationOccupied(new Location(r,c))){
                    getBoard().placePiece(new GamePiece(this, new Location(r,c)));
                    score = moveTreeScore(depth, false);
                    getBoard().removePiece(new Location(r,c));

                    //System.out.println(score + "  \t\t" + r + "  " + c);

                    if(score > bestScore){
                        bestScore = score;
                        bestMove.set(r,c);
                    }
                }
            }
        }
        return bestMove;
    }

    public int moveTreeScore(int depth, boolean myTurn){
        int score = myTurn ? -100000000 : 100000000;
        int highScore = 0;
        Location loc = new Location();
        Player p = this;
        if(depth <= 1){
            return eval.boardStateScore(myTurn) - evalOpp.boardStateScore(!myTurn);
        }
        for(int r = 0; r < getBoard().board().length; r++){
            for(int c = 0; c < getBoard().board().length; c++){

                loc.set(r,c);
                if(!board.locationOccupied(loc)){
                    p = myTurn ? this : opp;
                    getBoard().placePiece(new GamePiece(p, new Location(r,c)));
                    score = moveTreeScore(depth -1, !myTurn);
                    if(myTurn ? score > highScore : score < highScore){ //if its my turn then maximize, if not then minimize
                        highScore = score;
                    }
                    getBoard().removePiece(loc);
                }
            }
        }
        return score;
    }
}