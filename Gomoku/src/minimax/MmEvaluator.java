package minimax;

import gomokugame.*;

public class MmEvaluator {

    private GomokuBoard board;
    private Player player;

    private boolean counting;
    private int score;
    private int count;
    private int openEnds;

    public MmEvaluator(GomokuBoard b, Player p){
        board = b;
        player = p;
    }

    public int boardStateScore(boolean myTurn){
        int score = scoreHoriz(myTurn) + scoreVerti(myTurn) + scoreBackDiag(myTurn) + scoreDiag(myTurn);
        return score;
    }

    public int scoreHoriz(boolean myTurn){
        GamePiece[][] b = board.board();
        score = 0;
        for(int r = 0; r < b.length; r++){ 
            
            count = 0;
            counting = false;
            openEnds = 0;
            for(int c = 0; c < b.length; c++){
                evalPiece(b[r][c], myTurn);
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        return score;
    }

    public int scoreVerti(boolean myTurn){
        GamePiece[][] b = board.board();
        score = 0;
        for(int c = 0; c < b.length; c++){ 
            count = 0;
            counting = false;
            openEnds = 0;
            for(int r = 0; r < b.length; r++){
                evalPiece(b[r][c], myTurn);
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        return score;
    }

    public int scoreBackDiag(boolean myTurn){
        GamePiece[][] b = board.board();
        score = 0;
        for(int col = 0; col < b.length; col++){
            count = 0;
            counting = false;
            openEnds = 0;
            int c = col;
            int r = 0;
            while(c < b.length && r < b.length){
                evalPiece(b[r][c], myTurn);
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
                evalPiece(b[r][c], myTurn);
                c ++;
                r ++;
            }
            if(counting){
                score += scorePieces(myTurn, openEnds, count);
            }
        }
        return score;
    }

    public int scoreDiag(boolean myTurn){           //not working for some reason :(
        GamePiece[][] b = board.board();
        score = 0;
        for(int col = 0; col < b.length; col++){
            count = 0;
            counting = false;
            openEnds = 0;
            int c = col;
            int r = 0;
            while(c >= 0 && r < b.length){
                evalPiece(b[r][c], myTurn);
                c --;
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
            int c = b.length-1;
            int r = row;
            while(c >= 0 && r < b.length){
                evalPiece(b[r][c], myTurn);
                c --;
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

    public void evalPiece(GamePiece p, boolean myTurn){
        if(counting && p == null){
            score += scorePieces(myTurn, openEnds + 1, count);
            count = 0;
            counting = false;
            openEnds = 1;
        }
        else if(p == null){
            openEnds = 1;
        }
        else if(counting && p.getPlayer() != player){
            score += scorePieces(myTurn, openEnds, count);
            count = 0;
            counting = false;
            openEnds = 0;
        }
        else if(p.getPlayer() != player){
            openEnds = 0;
        }
        else if(p.getPlayer() == player){
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
                            return 10000000;
                        }
                        return 50000;
                    case 1:
                        if(myTurn){
                            return 10000000;
                        }
                        return 50;
                }
            case 3:
                switch(openEnds){
                    case 2:
                        if(myTurn){
                            return 5000;
                        }
                        return 1000;
                    case 1:
                        if(myTurn){
                            return 50;
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
        return 20000000;
    }
}