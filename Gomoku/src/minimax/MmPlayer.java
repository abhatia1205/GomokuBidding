package minimax;

import gomokugame.*;

public class MmPlayer extends Player{

    public MmPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
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

    public int evaluateBoard(boolean myTurn){
        return -1000;
    }

    public int evaluateHorizontal(boolean myTurn){
        GamePiece[][] b = this.getBoard().board();
        int score = 0;
        int fIndex = -1;
        int openEnds = 0;
        for(int r = 0; r < b.length; r++){ 
            for(int c = 0; c < b.length; c++){
                if(b[r][c] == null){
                    openEnds = 1;
                }
                else if(fIndex < 0 && b[r][c].getPlayer() == this){           //if first index is <0 no value is found
                    fIndex = c;                                               //implement with the boolean myTurn (not done yet)
                }
                else if(fIndex >= 0 && b[r][c].getPlayer() != this){
                    if(b[r][c]  == null){
                        openEnds ++;
                    }
                    score += scorePieces(myTurn, c - fIndex, openEnds);
                    fIndex = -1;
                    openEnds = 0;
                }
            }
        }
        return score;
    }

    public int scorePieces(boolean myTurn, int openEnds, int consec){
        if(openEnds == 0 && consec < 5){
            return 0;
        }
        switch (consec){
            case 4:
                switch(openEnds){
                    case 2:
                        if(myTurn){
                            return 100000000;
                        }
                        return 500000;
                    case 1:
                        if(myTurn){
                            return 100000000;
                        }
                        return 50;
                }
            case 3:
                switch(openEnds){
                    case 2:
                        if(myTurn){
                            return 10000;
                        }
                        return 50;
                    case 1:
                        if(myTurn){
                            return 7;
                        }
                        return 5;
                }
            case 2:
                switch(openEnds){
                    case 2:
                        return 5;
                    case 1:
                        return 2;
                }
            case 1:
                switch(openEnds){
                    case 2:
                        return 2;
                    case 1:
                        return 1;
                }
        }
        return 200000000;
    }
}