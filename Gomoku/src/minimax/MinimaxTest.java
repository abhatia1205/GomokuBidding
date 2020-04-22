package minimax;

import gomokugame.*;

public class MinimaxTest {

    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        MmPlayer p1 = new MmPlayer("p1", "X", b);
        MmPlayer p2 = new MmPlayer("p2", "O", b);
        p1.playTest(new Location(0,0));
        p1.playTest(new Location(1,0));
        p1.playTest(new Location(2,0));
        p1.playTest(new Location(3,0));
        p1.playTest(new Location(4,0));
        System.out.println(b);
        //System.out.println(b.check4Win(b.getPiece(new Location(0,4))));
        System.out.println(p1.evaluateHorizontal(true));
    }
}