package playerai;

import gomokugame.*;
import java.io.*;
import java.util.*;

public class AiPlayer extends Player {
    
    private String folderPath;
    private final String createdFile = "Gomoku/src/transferdata/cringer.txt";
    private final String readFile = "Gomoku/src/transferdata/didthecringe.txt";
    private File outputFile;
    private Scanner scan;

    public AiPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
    }

    public AiPlayer(String n, String s, GomokuBoard b, int t){
        this(n, s, b);
        tokens = t;
    }

    public int bid(){
        return 10;
    }

    public Location playTurn(){
        return new Location();
    }

    private String board2String(){
        String s = "";
        GamePiece g = new GamePiece();
        for(int r = 0 ; r<board.b().length; r++){
            for(int c = 0 ; c<board.b().length; c++){
                g = board.b()[r][c];
                if(g == null){
                    s += "0 ";
                }
                else if (g.getPlayer() == this){
                    s += "1 ";
                }
                else{
                    s += "2 ";
                }
            }
            s += "\n";
        }
        return s;
    }

    public void outputBoardFile(){
        outputFile = new File(createdFile);
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

    public int[] neuralInput(){
        return null;
    }

}