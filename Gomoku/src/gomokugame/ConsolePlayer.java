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

    public Location playTurn(){
        Location a = null;
        while( a == null){
            System.out.println(this.getName() + " play round");
            System.out.print("Row: ");
            int r = scan.nextInt();
            System.out.print("Col: ");
            int c = scan.nextInt();
            a = new Location(r,c);
            if(!getBoard().validMove(a)){
                a = null;
            }
        }
        return a;
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