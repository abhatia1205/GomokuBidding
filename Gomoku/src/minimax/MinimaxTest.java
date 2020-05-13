package minimax;

import gomokugame.*;

public class MinimaxTest {

    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        MmPlayer p1 = new MmPlayer("p1", "X", b);
        MmPlayer p2 = new MmPlayer("p2", "O", b);
        p1.setupEvalOpp(p2);
        p2.setupEvalOpp(p1);
        
        p1.testPiece(new Location(7,7));
        p2.testPiece(new Location(7,8));
        p1.testPiece(new Location(8,7));
        p2.testPiece(new Location(7,9));

        MmPlayer[] players = {p1, p2};
        System.out.println(b);
        for(int i = 0; i < 7; i++)
            for(MmPlayer p : players){
                Location a = p.nextMove(1);
                System.out.println(a);
                p.testPiece(a);
                System.out.println(b);
            }
        
    }
}