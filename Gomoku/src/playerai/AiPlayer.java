package playerai;

import gomokugame.*;

public class AiPlayer extends Player {
    
    private GamePiece[][][] aiBoard;

    public AiPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
        aiBoard = new GamePiece[b.b().length][b.b().length][3];
    }

    public AiPlayer(String n, String s, GomokuBoard b, int t){
        this(n, s, b);
        tokens = t;
    }

    public void updateAiBoard(){
        GamePiece g;
        for(int r = 0; r < board.b().length; r++){
            for(int c = 0; c < board.b().length; c++){
                g = board.b()[r][c];
                if(g != null){
                    aiBoard[0][r][c] = g;
                    if(g.getPlayer() == this)
                        aiBoard[1][r][c] = g;
                    else
                        aiBoard[2][r][c] = g;
                }
            }
        }
    }

    public int bid(){
        return 10;
    }

    public Location playTurn(){
        return new Location();
    }

}