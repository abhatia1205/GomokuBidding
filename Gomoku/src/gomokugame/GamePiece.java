package gomokugame;

public class GamePiece{
    
    private Player player;
    private Location loc;

    public GamePiece(){
        this.player = null;
        this.loc = new Location(-1,-1);
    }

    public GamePiece(Player p, Location l){
        this.player = p;
        this.loc = l;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Location getLoc(){
        return this.loc;
    }

    public String toString(){
        return player == null ? "*" : player.sym();
    }
}