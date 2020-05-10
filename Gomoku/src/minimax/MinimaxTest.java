package minimax;

import gomokugame.*;

public class MinimaxTest {

    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        MmPlayer p1 = new MmPlayer("p1", "X", b);
        p1.playTest(new Location(1,8));
        System.out.println(b);
        System.out.println(p1.evaluateDiag(true));
    }
}