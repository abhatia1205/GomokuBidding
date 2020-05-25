package playerai;

import minimax.*;
import gomokugame.*;
import java.util.*;

public class aiPlayertesting {
    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        AiPlayer a = new AiPlayer("p1", "X", b, 100);
        AiPlayer c = new AiPlayer("p2", "o", b, 100);
        a.setup(c);
        b.placePiece(new GamePiece(a, new Location(3,4)));
        System.out.println(a.bid());
    }
}