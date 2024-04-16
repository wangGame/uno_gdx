package com.github.hikari_toyama.unocard.core;

import static com.github.hikari_toyama.unocard.core.CardColorEnum.BLUE;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.GREEN;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.NONE;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.RED;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.YELLOW;

import java.util.List;
import java.util.TreeMap;

public class AI {
    private Uno uno;
    private TreeMap<Integer,Card> candidates;
    private AI(Uno uno){
        this.uno = uno;
    }

    public CardColorEnum calcBestColor4NowPlayer(){
        CardColorEnum bestCardColorEnum = CardColorEnum.NONE;
        Player next = uno.getNextPlayer();
        Player oppo = uno.getOppoPlayer();
        Player prev = uno.getPrevPlayer();
        CardColorEnum nextWeak = next.getWeakCardColorEnum();
        CardColorEnum oppoWeak = oppo.getWeakCardColorEnum();
        CardColorEnum prevWeak = prev.getWeakCardColorEnum();
        CardColorEnum nextStrong = next.getStrongCardColorEnum();
        CardColorEnum oppoStrong = oppo.getStrongCardColorEnum();
        CardColorEnum prevStrong = prev.getStrongCardColorEnum();
        boolean nextIsUno = next.getHandSize() == 1;
        boolean oppoIsUno = next.getHandSize() == 1;
        boolean prevIsUno = next.getHandSize() == 1;

        //uno  那么最好的颜色就是剩下的颜色
        if (nextIsUno && nextWeak!=CardColorEnum.NONE){
            bestCardColorEnum = nextWeak;
        }else if (oppoIsUno && oppoWeak != NONE && !uno.is2vs2()) {
            bestCardColorEnum = oppoWeak;
        } // else if (oppoIsUno && oppoWeak != NONE && !uno.is2vs2())
        else if (prevIsUno && prevWeak != NONE) {
            bestCardColorEnum = prevWeak;
        } else if (uno.is2vs2() &&
                oppoStrong != NONE &&
                oppo.getHandSize() <= uno.getCurrPlayer().getHandSize()) {
            bestCardColorEnum = oppoStrong;
        }else {
            int [] score = {0,0,0,0,0};
            List<Card> handCards = uno.getCurrPlayer().getHandCards();
            for (Card handCard : handCards) {
                switch (handCard.getContent()){
                    case WILD:
                    case WILD_DRAW4:
                        break;

                    case REV:
                    case NUM0:
                        score[handCard.cardColorEnum.ordinal()] += 2;
                        break;

                    case SKIP:
                    case DRAW2:
                        score[handCard.cardColorEnum.ordinal()]+=5;
                        break;

                    default:
                        score[handCard.cardColorEnum.ordinal()]+=4;
                        break;
                }

                if (score[RED.ordinal()]>score[bestCardColorEnum.ordinal()]){
                    bestCardColorEnum = RED;
                }
                if (score[BLUE.ordinal()]>score[bestCardColorEnum.ordinal()]){
                    bestCardColorEnum = BLUE;
                }
                if (score[GREEN.ordinal()]>score[bestCardColorEnum.ordinal()]){
                    bestCardColorEnum = GREEN;
                }
                if (score[YELLOW.ordinal()]>score[bestCardColorEnum.ordinal()]){
                    bestCardColorEnum = YELLOW;
                }
                if (bestCardColorEnum == NONE){
                    bestCardColorEnum = prevWeak != NONE ? prevWeak : oppoWeak != NONE && !uno.is2vs2() ? oppoWeak
                            :nextWeak != NONE ? nextWeak : RED;
                }

                while ((nextIsUno && bestCardColorEnum == nextStrong)
                        ||(oppoIsUno&&bestCardColorEnum == oppoStrong && !uno.is2vs2())
                        ||prevIsUno&&bestCardColorEnum == prevStrong){
                    bestCardColorEnum = CardColorEnum.values()[Constant.RNG.nextInt(4)+1];
                }
            }
        }
        return bestCardColorEnum;
    }

    public int calcBestSwapTarget4NowPlayer(){
        int target;
        Player next = uno.getNextPlayer();
        Player oppo = uno.getOppoPlayer();
        Player prev = uno.getPrevPlayer();
        Player currPlayer = uno.getCurrPlayer();
        List<Card> handCards = currPlayer.getHandCards();
        if (next.getHandSize() == 1) {
            target = uno.getNext();
        } // if (next.getHandSize() == 1)
        else if (prev.getHandSize() == 1) {
            target = uno.getPrev();
        } // else if (prev.getHandSize() == 1)
        else if (oppo.getHandSize() == 1) {
            target = uno.getOppo();
        } // else if (oppo.getHandSize() == 1)
        else if (prev.getStrongCardColorEnum() == uno.lastColor()) {
            target = uno.getPrev();
        } // else if (prev.getStrongColor() == uno.lastColor())
        else if (oppo.getStrongCardColorEnum() == uno.lastColor()) {
            target = uno.getOppo();
        } // else if (oppo.getStrongColor() == uno.lastColor())
        else {
            target = uno.getNext();
        } // else

        if (handCards.size() == 1
                &&target == uno.getNext()
                &&uno.isLegalToPlay(handCards.get(0))){
            target = uno.getPrev();
        }
        return target;
    }

    public int easyAI_bestCardIndex4NowPlayer(CardColorEnum[] outCardColorEnum){
        Card card;
        String errMsg;
        int yourSize;
        int nextSize;
        int oppoSize;
        int prevSize;
        CardColorEnum bestCardColorEnum;
        CardColorEnum lastCardColorEnum;
        Player next = uno.getNextPlayer();
        Player oppo = uno.getOppoPlayer();
        Player prev = uno.getPrevPlayer();
        CardColorEnum nextStrong = next.getStrongCardColorEnum();
        CardColorEnum oppoStrong = oppo.getStrongCardColorEnum();
        CardColorEnum prevStrong = prev.getStrongCardColorEnum();
        int i, i0, i7, iBest, matches;
        int iNM, iRV, iSK, iDW, iWD, iWD4;
        List<Card> handCards = uno.getCurrPlayer().getHandCards();
        if (outCardColorEnum == null || outCardColorEnum.length == 0){
            throw  new IllegalArgumentException();
        }

        yourSize = handCards.size();
        if (yourSize == 1){
            card = handCards.get(0);
            outCardColorEnum[0] = card.cardColorEnum;
            return uno.isLegalToPlay(card) ? 0 : -1;
        }

        lastCardColorEnum = uno.lastColor();
        bestCardColorEnum = calcBestColor4NowPlayer();
        iBest = i0 = i7 = iNM = iRV = iSK = iDW = iWD = iWD4 = -1;
        for (i = matches = 0;i<yourSize;++i){
            card = handCards.get(i);
            if (card.cardColorEnum == lastCardColorEnum){
                ++matches;
            }
            if (uno.isLegalToPlay(card)){
                switch (card.getContent()){
                    case DRAW2:
                        if (iWD<0 || card.cardColorEnum == bestCardColorEnum) {
                            iDW = i;
                        }
                            break;
                    case SKIP:
                        if(iSK < 0 || card.cardColorEnum == bestCardColorEnum){
                            iSK = i;
                        }
                        break;
                    case REV:
                        if (iRV<0||card.cardColorEnum == bestCardColorEnum){
                            iRV = i;
                        }
                        break;
                }
            }
        }
    }
}
