package gomokugame;

import java.util.*;

public class ConsolePlayer extends Player {

    Scanner scan;

    public ConsolePlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
        scan = new Scanner(System.in);
    }

    public ConsolePlayer(String n, String s, GomokuBoard b, int t){
        super(n,s,b,t);
        scan = new Scanner(System.in);
    }

    public boolean playTurn(){

        System.out.println(this.getBoard());

        GamePiece piece = null;
        while( piece == null){
            System.out.println(this.getName() + " play round");
            int r = scan.nextInt();
            int c = scan.nextInt();
            piece = new GamePiece(this, new Location(r,c));
            if(!this.getBoard().placePiece(piece)){
                piece = null;
            }
        }
        Player winner = this.getBoard().check4Win(piece);
        if(winner != null){
            System.out.println(winner.getName() + " wins this round");
            return true;
        }
        return false; 
    }

    public int bid(){
        this.lastBid = 10000;
        while(! validBid(this.lastBid)){
            System.out.println(getName() + " has " + getTokens() + " tokens");
            System.out.print("Tokens to bid: ");
            this.lastBid = scan.nextInt();
        }
        return lastBid;
    }
}
