package gomokugame;

public class Location{

    public int row;
    public int col;

    public Location(){
        row = -1;
        col = -1;
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

    public void set(int r, int c){
        row = r;
        col = c;
    }

    public String toString(){
        String s = "(" + row + ", " + col + ")";
        return s;
    }
    
}