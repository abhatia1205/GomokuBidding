package minimax;
import gomokugame.*;

public class MmPlayer extends Player{

    private MmEvaluator eval;

    public MmPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
        eval  = new MmEvaluator(this, b);
    }

    public int bid(){
        System.out.println("MmPlayer.bid() not set up yet");
        return 10;
    }

    public boolean playTurn(){
        System.out.println("MmPlayer.playTurn() not set up yet");
        return false;
    }

    public boolean playTest(Location l){
        return board.placePiece(new GamePiece(this, l));
    }

    public int evaluateBoardState(boolean myTurn){
        return eval.boardStateScore(myTurn);
    }

    /*

    this thing is still VERRRRRRRRRYYYYYYYYY broken, dont use
    */
    public Location depthBestMove(int depth, int highScore, Location bestMove, boolean myTurn, GamePiece[] g){
        if(depth < 1){
            return bestMove;
        }
        int score = 0;
        for(int r = 0; r < getBoard().board().length; r++){
            for(int c = 0; c < getBoard().board().length; c++){
                getBoard().placePiece(new GamePiece(this, new Location(r,c)));
                score = evaluateBoardState(myTurn);
                if(score > highScore){
                    highScore = score;
                    bestMove.set(r,c);
                }
            }
        }
        return bestMove;
    }
}