package gomokugame;

public class GomokuBoard{
    
    private int width;
    private GamePiece[][] board;

    public GomokuBoard(){
        this.width = 15;
        this.board = new GamePiece[width][width];
    }

    public GomokuBoard(int width){
        this.width = width;
        this.board = new GamePiece[width][width];
    }

    public GamePiece getPiece(Location l){
        return board[l.row()][l.col()];
    }

    public boolean locationValid(Location l){
        if(l.row() >= width || l.row() < 0){
            return false;
        }
        if(l.col() >= width || l.col() < 0){
            return false;
        }
        return true;
    }

    public boolean locationOccupied(Location l){
        if(board[l.row()][l.col()] != null){
            return true;
        }
        return false;
    }

    public Player getPiecePlayer(Location l){
        if(locationValid(l) && locationOccupied(l)){
            return getPiece(l).getPlayer();
        }
        return null;
    }

    public boolean placePiece(GamePiece p){
        if(locationValid(p.getLoc()) && ! locationOccupied(p.getLoc())){
            board[p.getLoc().row()][p.getLoc().col()] = p;
            return true;
        }
        return false;
    }

    public String toString(){
        String s = "";
        for(GamePiece[] r : board){
            for(GamePiece g : r){
                
                s += g==null ? " * |" : " " + g + " |";
            }
            s += "\n ---------------------------------------------------------------\n";
        }
        return s;
    }

    public Player check4Win(GamePiece g){
        if(check4WinHoriz(g) == 0 || check4WinVertical(g) == 0 || check4WinDia(g) == 0 || check4WinDiaBack(g) == 0){
            return g.getPlayer();
        }
        return null;
    }

    private int check4WinHoriz(GamePiece g){
        int row = g.getLoc().row();
        int center = g.getLoc().col();
        int counter = 0;
        for(int col = center - 4; col < center + 5; col++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return 0;
                }
            }
            else{
                counter = 0;
            }
        }
        return -1;
    }

    private int check4WinVertical(GamePiece g){
        int center = g.getLoc().row();
        int col = g.getLoc().col();
        int counter = 0;
        for(int row = center - 4; row < center + 5; row++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return 0;
                }
            }
            else{
                counter = 0;
            }
        }
        return -1;
    }

    private int check4WinDiaBack(GamePiece g){
        int centerR = g.getLoc().row();
        int centerC = g.getLoc().col();
        int counter = 0;
        int col = centerC - 4;
        for(int row = centerR - 4; row < centerR + 5; row++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return 0;
                }
            }
            else{
                counter = 0;
            }
            col++;
        }
        return -1;
    }

    private int check4WinDia(GamePiece g){
        int centerR = g.getLoc().row();
        int centerC = g.getLoc().col();
        int counter = 0;
        int col = centerC + 4;
        for(int row = centerR - 4; row < centerR + 5; row++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return 0;
                }
            }
            else{
                counter = 0;
            }
            col--;
        }
        return -1;
    }
}