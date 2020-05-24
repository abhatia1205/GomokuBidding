package playerai;

import minimax.*;
import gomokugame.*;

public class aiPlayertesting {
    public static void main(String args[]){
        GomokuBoard b = new GomokuBoard();
        AiPlayer a = new AiPlayer("p1", "X", b);
        b.placePiece(new GamePiece(a, new Location(3,4)));
        a.outputBoardFile();
        //a.neuralInput();
    }
}