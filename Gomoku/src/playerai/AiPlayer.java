package playerai;

import gomokugame.*;


public class AiPlayer extends Player {
    
    public int[][][] aiBoard;
    public int[][] aiFlatBoard;
    public String folderPath;
    public final String createdFile = "cringer.txt";
    public final String readFile = "didTheCring.txt";

    public AiPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
        aiBoard = new int[15][15][2];
        aiFlatBoard = new int[1][450];
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
                    if(g.getPlayer() == this){
                        aiBoard[r][c][0] = 1;
                        aiBoard[r][c][1] = 0;
                    }
                    else{
                        aiBoard[r][c][0] = 0;
                        aiBoard[r][c][1] = 1;
                    }
                }
            }
        }
    }

    public void flattenBoard(){
        for(int r = 0; r < aiBoard.length; r++){
            for(int c = 0; c < aiBoard[0].length; c++){
                for(int d = 0; d < aiBoard[0][0].length; d++){
                    aiFlatBoard[0][2*(15*r+c)+d] = aiBoard[r][c][d];
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