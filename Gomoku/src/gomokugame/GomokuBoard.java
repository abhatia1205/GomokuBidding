package gomokugame;

public class GomokuBoard{

    private GamePiece[][] board;

    public GomokuBoard(){
        this.board = new GamePiece[15][15];
    }

    public GomokuBoard(int width){
        this.board = new GamePiece[width][width];
    }

    public GamePiece[][] b(){
        return board;
    }

    public GamePiece getPiece(Location l){
        return board[l.row()][l.col()];
    }

    public boolean locationValid(Location l){
        if(l.row() >= board.length || l.row() < 0){
            return false;
        }
        if(l.col() >= board.length || l.col() < 0){
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

    public boolean validMove(Location l){
        return locationValid(l) && !locationOccupied(l);
    }

    public boolean placePiece(GamePiece p){
        if(locationValid(p.getLoc()) && ! locationOccupied(p.getLoc())){
            board[p.getLoc().row()][p.getLoc().col()] = p;
            return true;
        }
        return false;
    }

    public boolean removePiece(Location l){
        if(board[l.row()][l.col()] == null){
            return false;
        }
        board[l.row()][l.col()] = null;
        return true;
    }

    public String toString(){
        String s = "   0  1  2  3  4  5  6  7  8  9  0  1  2  3  4\n";
        Integer[] a = {0,1,2,3,4,5,6,7,8,9,0,1,2,3,4};
        int i = 0;
        for(GamePiece[] r : board){
            s += a[i++] + " ";
            for(GamePiece g : r){
                
                s += g==null ? " * " : " " + g + " ";
            }
            s += "\n";
        }
        return s;
    }

    public Player check4Win(GamePiece g){
        if(check4WinHoriz(g) || check4WinVertical(g) || check4WinDia(g) || check4WinDiaBack(g)){
            return g.getPlayer();
        }
        return null;
    }

    private boolean check4WinHoriz(GamePiece g){
        int row = g.getLoc().row();
        int center = g.getLoc().col();
        int counter = 0;
        for(int col = center - 4; col < center + 5; col++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return true;
                }
            }
            else{
                counter = 0;
            }
        }
        return false;
    }

    private boolean check4WinVertical(GamePiece g){
        int center = g.getLoc().row();
        int col = g.getLoc().col();
        int counter = 0;
        for(int row = center - 4; row < center + 5; row++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return true;
                }
            }
            else{
                counter = 0;
            }
        }
        return false;
    }

    private boolean check4WinDiaBack(GamePiece g){
        int centerR = g.getLoc().row();
        int centerC = g.getLoc().col();
        int counter = 0;
        int col = centerC - 4;
        for(int row = centerR - 4; row < centerR + 5; row++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return true;
                }
            }
            else{
                counter = 0;
            }
            col++;
        }
        return false;
    }

    private boolean check4WinDia(GamePiece g){
        int centerR = g.getLoc().row();
        int centerC = g.getLoc().col();
        int counter = 0;
        int col = centerC + 4;
        for(int row = centerR - 4; row < centerR + 5; row++){
            if(getPiecePlayer(new Location(row, col)) == g.getPlayer()){
                counter++;
                if(counter >= 5){
                    return true;
                }
            }
            else{
                counter = 0;
            }
            col--;
        }
        return false;
    }
}