
public class Player {

    private String name;
    private String symbol;

    private GomokuBoard board;

    public Player(String n, String s, GomokuBoard b){
        name = n;
        symbol = s;
        board = b;
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

    public GamePiece playTurn(Location l){
        GamePiece piece = new GamePiece(this, l);
        if(board.placePiece(piece) <0 ){
            return null;
        }
        return piece;
    }
}