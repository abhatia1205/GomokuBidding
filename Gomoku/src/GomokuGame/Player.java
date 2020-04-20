package gomokugame;

import java.util.*;

public class Player {

    private String name;
    private String symbol;
    private GomokuBoard board;

    private int tokens;

    public int lastBid;

    public Player(String n, String s, GomokuBoard b){
        name = n;
        symbol = s;
        board = b;
    }

    public Player(String n, String s, GomokuBoard b, int t){
        this(n, s, b);
        tokens = t;
    }

    public void setName(String n){
        name = n;
    }

    public String getName(){
        return name;
    }

    public String sym(){
        return symbol;
    }

    public int getTokens(){
        return tokens;
    }

    public void subTokens(int t){
        tokens -= t;
    }

    public void addTokens(int t){
        tokens += t;
    }

    public boolean playTurn(Scanner scan){

        System.out.println(this.board);

        GamePiece piece = null;
        while( piece == null){
            System.out.println(this.getName() + " play round");
            int r = scan.nextInt();
            int c = scan.nextInt();
            piece = new GamePiece(this, new Location(r,c));
            if(!board.placePiece(piece)){
                piece = null;
            }
        }
        Player winner = board.check4Win(piece);
        if(winner != null){
            System.out.println(winner.getName() + " wins this round");
            return true;
        }
        return false; 
    }

    public boolean validBid(int amt){
        if(amt >= 0 && amt <= tokens){
            return true;
        }
        return false;
    }

    public int bid(Scanner scan){
        this.lastBid = 10000;
        while(! validBid(this.lastBid)){
            System.out.println(getName() + " has " + getTokens() + " tokens");
            System.out.print("Tokens to bid: ");
            this.lastBid = scan.nextInt();
        }
        return lastBid;
    }
}