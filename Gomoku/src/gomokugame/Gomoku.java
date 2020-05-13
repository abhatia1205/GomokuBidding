package gomokugame;

import java.util.*;

public class Gomoku{
    private GomokuBoard board;
    private ArrayList<Player> players;
    private boolean bidding;

    public Gomoku(boolean b, int t){
        this.board = new GomokuBoard(15);
        bidding = b;

        players = new ArrayList<Player>();
        players.add(new ConsolePlayer("p1", "X", this.board, t));
        players.add(new ConsolePlayer("p2", "O", this.board, t));
    }

    public GomokuBoard getBoard(){
        return board;
    }

    public boolean playNoBidRound(){
        for(Player p : this.players){
            GamePiece a = new GamePiece(p, p.playTurn());
            board.placePiece(a);
            if(board.check4Win(a) != null){
                System.out.println(p + " won the game");
                return true;
            }
        }
        return false;
    }

    public Player doBidding(){
        for(Player p : this.players){
            p.bid();
        }
        Player bidWinner = players.get(0).lastBid > players.get(1).lastBid ? players.get(0) : players.get(1);
        Player bidLoser  = players.get(0).lastBid > players.get(1).lastBid ? players.get(1) : players.get(0);
        int highestBid  = players.get(0).lastBid > players.get(1).lastBid ? players.get(0).lastBid : players.get(1).lastBid;
        bidWinner.subTokens(highestBid);
        bidLoser.addTokens(highestBid);
        return bidWinner;
    }

    public boolean playBidRound(){
        Player bidWinner = doBidding();
        GamePiece a = new GamePiece(bidWinner, bidWinner.playTurn());
        board.placePiece(a);
        if(board.check4Win(a) != null){
            System.out.println(bidWinner + " won the game");
            return true;
        }
        return false;
    }

    
    public void playGomoku(){
        boolean stop = false;
        while(true){
            stop = bidding ? playBidRound() : playNoBidRound();
            if(stop){
                return;
            }
        }
    }

    public static void main(String[] args){
        Gomoku game = new Gomoku(true, 100);
        game.playGomoku();
    }
}