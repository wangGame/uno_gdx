package com.github.hikari_toyama.unocard.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    private List<Card> handCards;
    private List<Card> constHandCards;
    private CardColorEnum strongCardColorEnum;
    private CardColorEnum weakCardColorEnum;
    private Card recent;
    private int strongCount;
    private int open;
    public Player(){
        this.handCards = new ArrayList<>();
        this.constHandCards = Collections.unmodifiableList(handCards);
        this.strongCardColorEnum = CardColorEnum.NONE;
        this.weakCardColorEnum = CardColorEnum.NONE;
        this.open  = 0x00000000;
    }

    public List<Card> getHandCards() {
        return constHandCards;
    }

    public int getHandScore(){
        int score = 0;
        for (Card handCard : handCards) {
            switch (handCard.getContent()){
                case WILD:
                case WILD_DRAW4:
                    score += 50;
                    break;

                case REV:
                case SKIP:
                case DRAW2:
                    score += 20;
                    break;
                default:
                    score += handCard.getContent().ordinal();
                    break;
            }
        }
        return score;
    }

    public int getHandSize(){
        return handCards.size();
    }


    public CardColorEnum getStrongCardColorEnum() {
        return strongCardColorEnum;
    }

    public CardColorEnum getWeakCardColorEnum() {
        return weakCardColorEnum;
    }

    public Card getRecent() {
        return recent;
    }

    public boolean isOpen(int index){
        return index < 0
                ? open == (~(0xffffffff << handCards.size()))
                : 0x01 == (0x01 & (open >> index));
    }

}
