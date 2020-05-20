package minimax;

import gomokugame.*;

public class MinimaxTest {

    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        MmPlayer p1 = new MmPlayer("p1", "X", b);
        MmPlayer p2 = new MmPlayer("p2", "O", b);
        p1.setup(p2, 2);
        p2.setup(p1, 2);
        p2.testPiece(new Location(7,7));
        p2.testPiece(new Location(7,8));
        p2.testPiece(new Location(7,9));
        p2.testPiece(new Location(7,10));
        p1.testPiece(new Location(2,4));
        p1.testPiece(new Location(3,4));
        p1.testPiece(new Location(4,4));
        p1.testPiece(new Location(5,4));
        System.out.println(p1.sym() + "  " +p1.nextMove());
        System.out.println(p2.sym() + "  " +p2.nextMove());
        System.out.println(b);
        //MmPlayer[] players = {p1, p2};
        /*
        for(int i = 0; i < 14; i++){
            for(MmPlayer p : players){
                Location a = p.nextMove();
                System.out.println(a +" " + p);
                p.testPiece(a);
                System.out.println(b);
            }
        }
        */
    }
}