import java.util.*;

public class Gomoku{
    private GomokuBoard board;
    private Scanner scan;

    public Gomoku(){
        this.board = new GomokuBoard(15);
        board.addPlayer(new Player("p1", "X", this.board));
        board.addPlayer(new Player("p2", "O", this.board));
        scan = new Scanner(System.in);
    }

    public GomokuBoard getBoard(){
        return board;
    }

    public boolean playRound(){
        for(Player p : this.board.getPlayers()){
            System.out.println(this.board);
            
            GamePiece piece = null;
            while( piece == null){
                System.out.println(p.getName() + " play round");
                int r = scan.nextInt();
                int c = scan.nextInt();
                piece = p.playTurn(new Location(r,c));
            }
            Player winner = board.check4Win(piece);
            System.out.println(winner);
            if(winner != null){
                System.out.println(winner.getName() + " wins this round");
                return true;
            }
        }
        return false; 
    }

    public static void main(String[] args){
        Gomoku game = new Gomoku();
        boolean stop = false;
        while(!stop){
            stop = game.playRound();
        }
        
    }
}