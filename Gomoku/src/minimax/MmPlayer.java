package minimax;

import gomokugame.*;

public class MmPlayer extends Player{

    private MmEvaluator eval;

    public MmPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
        eval = new MmEvaluator(this, b);
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

    public int evaluateBoardState(boolean myTurn, Location loc){
        GamePiece p = new GamePiece(this, loc);
        getBoard().placePiece(p);
        int score = eval.boardStateScore(myTurn);
        getBoard().removePiece(p);
        return score;
    }

    public Location mmDepthSearch(int depth, Location l){
        if(depth < 1){
            return l;
        }
        for(int d = 0; d < depth; d++){
            for(int r = 0; r < getBoard().board().length; r++){
                for(int c = 0; c < getBoard().board().length; c++){

                }
            }
        }

    }

}