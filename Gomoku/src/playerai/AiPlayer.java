package playerai;

import gomokugame.*;
import java.io.*;
import java.util.*;

public class AiPlayer extends Player {

    private String folderPath;
    private final String out_File_Plath = "Gomoku/src/transferdata/cringer.txt";
    private final String in_File_Path = "Gomoku/src/transferdata/cringer.txt";
    private int[] lastMoveData;

    public AiPlayer(String n, String s, GomokuBoard b) {
        super(n, s, b);
    }

    public AiPlayer(String n, String s, GomokuBoard b, int t) {
        this(n, s, b);
        tokens = t;
    }

    public int bid() {
        return lastMoveData[0];
    }

    public Location playTurn() {
        return new Location(lastMoveData[1], lastMoveData[2]);
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
        return s;
    }

    public void outputBoardFile() {
        File outputFile = new File(out_File_Plath);
        System.out.println(outputFile.isFile());
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            System.out.println("fileNotFound");
            return;
        }
        pw.write(board2String());
        pw.close();
    }

    public int[] neuralInput() {
        File inputFile = new File(in_File_Path);
        System.out.println(inputFile.isFile());
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
        inputFile.delete();

        return null;
    }

}