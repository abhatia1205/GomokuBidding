package minimax;

import gomokugame.*;

public class MmPlayer extends Player{

    public MmPlayer(String n, String s, GomokuBoard b){
        super(n,s,b);
    }

    public int bid(){
        System.out.println("MmPlayer.bid() not set up yet");
        return -1000;
    }

    public boolean playTurn(){
        System.out.println("MmPlayer.playTurn() not set up yet");
        return false;
    }
}