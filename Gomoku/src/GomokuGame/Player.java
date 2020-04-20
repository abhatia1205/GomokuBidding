package gomokugame;

public abstract class Player {
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

    public GomokuBoard getBoard(){
        return board;
    }

    public boolean validBid(int amt){
        if(amt >= 0 && amt <= getTokens()){
            return true;
        }
        return false;
    }

    public abstract int bid();

    public abstract boolean playTurn();

}