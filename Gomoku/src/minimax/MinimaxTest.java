package minimax;

import gomokugame.*;

public class MinimaxTest {

    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        MmPlayer p1 = new MmPlayer("p1", "X", b);
        p1.playTest(new Location(9,7));
        p1.playTest(new Location(8,8));
        p1.playTest(new Location(7,9));
        p1.playTest(new Location(6,10));
        p1.playTest(new Location(5,11));
        System.out.println(b);
        System.out.println(p1.evaluateBoardState(true));
    }
}