package gomokugame;

public class Location{

    public int row;
    public int col;

    public Location(){
        row = 0;
        col = 0;
    }

    public Location(int r, int c){
        row = r;
        col = c;
    }

    public int row(){
        return row;
    }

    public int col(){
        return col;
    }
    
}