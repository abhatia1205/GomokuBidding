package playerai;

import gomokugame.*;
import java.io.*;
import java.util.*;

/**
 * is the ai player for bidding games
 *
 * @author Sam
 * @author Assignment: Gomoku Final Project
 */
public class AiPlayer extends Player {

    private final String out_File_Plath = "/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/transferdata/cringer.txt";
    private final String in_File_Path = "/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/transferdata/didthecringe.txt";
    private int[] nextMoveData;

    private Player opponent;

    /**
     * Constructs the ai player
     * 
     * @param n name
     * @param s symbol
     * @param b game baord
     */
    public AiPlayer(String n, String s, GomokuBoard b) {
        super(n, s, b);
    }

    /**
     * Constructs it
     * 
     * @param n name
     * @param s symbol
     * @param b board
     * @param t tokens to start
     */
    public AiPlayer(String n, String s, GomokuBoard b, int t) {
        this(n, s, b);
        tokens = t;
    }

    /**
     * sets up the opponent for this player
     * 
     * @param opp opponent
     */
    public void setup(Player opp) {
        this.opponent = opp;
    }

    /**
     * gets bid value
     * 
     * @return the bid amt
     */
    public int bid() {
        outputBoardFile();
        this.nextMoveData = neuralInput();
        return nextMoveData[0];
    }

    /**
     * plays turn
     * 
     * @return the location of the move
     */
    public Location playTurn() {
        return new Location(nextMoveData[1], nextMoveData[2]);
    }

    /**
     * turns the board into a string
     * 
     * @return teh board string thing
     */
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

    /**
     * sents out the game board to the python code
     */
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

    /**
     * receives the move data from the python code
     * 
     * @return the move data as an array
     */
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
        for (int i : out) {
            System.out.print(i);
        }
        scan.close();
        boolean a = false;
        while (!a) {
            a = inputFile.delete();
        }
        if (out[0] < 0) {
            out[0] = 0;
        }
        if (out[0] > this.getTokens()) {
            out[0] = this.getTokens();
        }
        return out;
    }

}