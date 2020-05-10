package minimax;

import gomokugame.*;

public class MmPlayer extends Player{

    private boolean counting;
    private int score;
    private int count;
    private int openEnds;

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

    public long evaluateHorizontal(boolean myTurn){
        GamePiece[][] b = this.getBoard().board();
        score = 0;
        for(int r = 0; r < b.length; r++){ 
            
            count = 0;
            counting = false;
            openEnds = 0;
            for(int c = 0; c < b.length; c++){
                evaluatePiece(b[r][c], myTurn);
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        return score;
    }

    public long evaluateVertical(boolean myTurn){
        GamePiece[][] b = this.getBoard().board();
        score = 0;
        for(int c = 0; c < b.length; c++){ 
            count = 0;
            counting = false;
            openEnds = 0;
            for(int r = 0; r < b.length; r++){
                evaluatePiece(b[r][c], myTurn);
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        return score;
    }

    public long evaluateBackDiag(boolean myTurn){
        GamePiece[][] b = this.getBoard().board();
        score = 0;
        for(int col = 0; col < b.length; col++){
            count = 0;
            counting = false;
            openEnds = 0;
            int c = col;
            int r = 0;
            while(c < b.length && r < b.length){
                evaluatePiece(b[r][c], myTurn);
                c ++;
                r ++;
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        for(int row = 1; row < b.length; row++){
            count = 0;
            counting = false;
            openEnds = 0;
            int c = 0;
            int r = row;
            while(c < b.length && r < b.length){
                evaluatePiece(b[r][c], myTurn);
                c ++;
                r ++;
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        return score;
    }
    /*      _
     *  .__(.)<  (MEOW)
     *   \____)
     */

    public long evaluateDiag(boolean myTurn){           //not working for some reason :(
        GamePiece[][] b = this.getBoard().board();
        for(int col = 0; col < b.length; col++){
            count = 0;
            counting = false;
            openEnds = 0;
            int c = col;
            int r = 0;
            while(c >= 0 && r < b.length){
                evaluatePiece(b[r][c], myTurn);
                c --;
                r ++;
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        return 1;
    }

    public void evaluatePiece(GamePiece p, boolean myTurn){
        if(counting && p == null){
            score += scorePieces(myTurn, openEnds + 1, count);
            count = 0;
            counting = false;
            openEnds = 1;
        }
        else if(counting && p.getPlayer() != this){
            score += scorePieces(myTurn, openEnds, count);
            count = 0;
            counting = false;
            openEnds = 0;
        }
        else if(p == null){
            openEnds = 1;
        }
        else if(p.getPlayer() != this){
            openEnds = 0;
        }
        else if(p.getPlayer() == this){
            counting = true;
            count ++;
        }
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