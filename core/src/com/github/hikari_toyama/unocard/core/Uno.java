package com.github.hikari_toyama.unocard.core;

import java.util.ArrayList;
import java.util.List;

public class Uno {
    private List<Card> desk;
    private List<Card> used;
    private RecentInfo[] recentInfo;
    private RecentInfo[] constRecent;
    private Card[] table;

    public Uno(){
        this.desk = new ArrayList<>();
        this.used = new ArrayList<>();
    }

    public void cycle(){

    }

    boolean checkParam(String val, int lo, int hi) {
        int iVal;

        try {
            iVal = Integer.parseInt(val);
        } // try
        catch (NumberFormatException e) {
            iVal = 0;
        } // catch (NumberFormatException e)

        return lo <= iVal && iVal <= hi;
    } // checkParam(String, int, int)

    public Player getNextPlayer() {
        return null;
    }

    public Player getOppoPlayer() {
        return null;
    }

    public Player getPrevPlayer() {
        return null;
    }

    public boolean is2vs2() {
        return false;
    }

    public Player getCurrPlayer() {
        return null;
    }

    public int getNext() {
        return 0;
    }

    public int getPrev() {
        return 0;
    }

    public int getOppo() {
        return 0;
    }

    public CardColorEnum lastColor() {
        return null;
    }

    public boolean isLegalToPlay(Card card) {
        return false;
    }

    public boolean isSevenZeroRule() {
        return false;
    }

    public int getDifficulty() {
        return 0;
    }

    public int getNow() {
        return 0;
    }

    public Card play(int now, int index, CardColorEnum cardColorEnum) {
        return null;
    }

    public Player getPlayer(int com1) {
        return null;
    }

    public int switchNow() {
        return 0;
    }

    public boolean isDraw2StackRule() {
        return false;
    }

    public int getDraw2StackCount() {
        return 0;
    }

    public void switchDirection() {


    }

    public void start() {

    }
}
