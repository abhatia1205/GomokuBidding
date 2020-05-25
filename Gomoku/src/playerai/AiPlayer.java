package playerai;

import gomokugame.*;
import java.io.*;
import java.util.*;

public class AiPlayer extends Player {

    private final String out_File_Plath = "/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/transferdata/cringer.txt";
    private final String in_File_Path = "/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/transferdata/didthecringe.txt";
    private int[] nextMoveData;

    private Player opponent;

    public AiPlayer(String n, String s, GomokuBoard b) {
        super(n, s, b);
    }

    public AiPlayer(String n, String s, GomokuBoard b, int t) {
        this(n, s, b);
        tokens = t;
    }

    public void setup(Player opp){
        this.opponent = opp;
    }

    public int bid() {
        outputBoardFile();
        this.nextMoveData = neuralInput();
        return nextMoveData[0];
    }

    public Location playTurn() {
        return new Location(nextMoveData[1], nextMoveData[2]);
    }

    private String board2String() {
        String s = "";
        GamePiece g = new GamePiece();
        for (int r = 0; r < board.b().length; r++) {
            for (int c = 0; c < board.b().length; c++) {
                g = board.b()[r][c];
                if (g == null) {
                    s += "0 ";
                } else if (g.getPlayer() == this) {
                    s += "1 ";
                } else {
                    s += "2 ";
                }
            }
            s += "\n";
        }
        s += "" + this.getTokens() + " " + opponent.getTokens();
        return s;
    }

    public void outputBoardFile() {
        File outputFile = new File(out_File_Plath);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            System.out.println("error in cinger.txt file creation");
            return;
        }
        pw.write(board2String());
        pw.close();
    }

    public int[] neuralInput() {
        File inputFile = new File(in_File_Path);
        Scanner scan = null;
        while (scan == null) {
            try {
                scan = new Scanner(inputFile);
            } catch (FileNotFoundException e) {
                scan = null;
            }
        }
        int[] out = new int[3];
        out[0] = scan.nextInt();
        out[1] = scan.nextInt();
        out[2] = scan.nextInt();
        for(int i : out){
            System.out.print(i);
        }
        scan.close();
        boolean a = false;
        while(! a){
            a = inputFile.delete();
        }

        return out;
    }

}