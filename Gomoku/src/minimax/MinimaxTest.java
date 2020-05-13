package minimax;

import gomokugame.*;

public class MinimaxTest {

    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        MmPlayer p1 = new MmPlayer("p1", "X", b);
        MmPlayer p2 = new MmPlayer("p2", "O", b);
        p1.setup(p2, 1);
        p2.setup(p1, 2);
        
        p1.testPiece(new Location(7,7));
        p2.testPiece(new Location(7,8));
        System.out.println(p1.testEval(false));
        System.out.println(p1.boundBoxStart());
        System.out.println(p1.boundBoxEnd());
        MmPlayer[] players = {p1, p2};
        System.out.println(b);
        
        for(int i = 0; i < 14; i++){
            for(MmPlayer p : players){
                Location a = p.nextMove();
                System.out.println(a +" " + p);
                p.testPiece(a);
                System.out.println(b);
            }
        }
        
    }
}