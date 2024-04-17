package com.github.hikari_toyama.unocard.core;

import static com.github.hikari_toyama.unocard.core.CardColorEnum.BLUE;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.GREEN;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.NONE;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.RED;
import static com.github.hikari_toyama.unocard.core.CardColorEnum.YELLOW;
import static com.github.hikari_toyama.unocard.core.Content.NUM0;
import static com.github.hikari_toyama.unocard.core.Content.NUM7;
import static com.github.hikari_toyama.unocard.core.Content.WILD;
import static com.github.hikari_toyama.unocard.core.Content.WILD_DRAW4;

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
                    case WILD:
                        iWD = i;
                        break; // case WILD

                    case WILD_DRAW4:
                        iWD4 = i;
                        break; // case WILD_DRAW4

                    case NUM7:
                        if (uno.isSevenZeroRule()) {
                            if (i7 < 0 || card.cardColorEnum == bestCardColorEnum)
                                i7 = i;
                            break; // case NUM7
                        } // if (uno.isSevenZeroRule())
                        // fall through

                    case NUM0:
                        if (uno.isSevenZeroRule()) {
                            if (i0 < 0 || card.cardColorEnum == bestCardColorEnum)
                                i0 = i;
                            break; // case NUM0
                        } // if (uno.isSevenZeroRule())
                        // fall through

                    default: // number cards
                        if (iNM < 0 || card.cardColorEnum == bestCardColorEnum)
                            iNM = i;
                        break; // default
                } // switch (card.content)
            } // if (uno.isLegalToPlay(card))
        } // for (i = matches = 0; i < yourSize; ++i)

        // Decision tree
        nextSize = next.getHandSize();
        oppoSize = oppo.getHandSize();
        prevSize = prev.getHandSize();
        if (nextSize == 1) {
            // Strategies when your next player remains only one card.
            // Firstly consider to use a 7 to steal the UNO, if can't,
            // limit your next player's action as well as you can.
            if (i7 >= 0 && (yourSize > 2
                    || (handCards.get(1 - i7).getContent() != NUM7
                    && handCards.get(1 - i7).getContent() != WILD
                    && handCards.get(1 - i7).getContent() != WILD_DRAW4
                    && handCards.get(1 - i7).cardColorEnum != handCards.get(i7).cardColorEnum)))
                iBest = i7;
            else if (i0 >= 0 && (yourSize > 2
                    || (handCards.get(1 - i0).getContent() != NUM0
                    && handCards.get(1 - i0).getContent() != WILD
                    && handCards.get(1 - i0).getContent() != WILD_DRAW4
                    && handCards.get(1 - i0).cardColorEnum != handCards.get(i0).cardColorEnum)))
                iBest = i0;
            else if (iDW >= 0)
                iBest = iDW;
            else if (iSK >= 0)
                iBest = iSK;
            else if (iRV >= 0)
                iBest = iRV;
            else if (iWD4 >= 0 && matches == 0)
                iBest = iWD4;
            else if (iWD >= 0 && lastCardColorEnum != bestCardColorEnum)
                iBest = iWD;
            else if (iWD4 >= 0 && lastCardColorEnum != bestCardColorEnum)
                iBest = iWD4;
            else if (iNM >= 0 && handCards.get(iNM).cardColorEnum != nextStrong)
                iBest = iNM;
            else if (iWD >= 0 && i7 + i0 > -2)
                iBest = iWD;
        } // if (nextSize == 1)
        else if (prevSize == 1) {
            // Strategies when your previous player remains only one card.
            // Consider to use a 0 or 7 to steal the UNO.
            if (i0 >= 0)
                iBest = i0;
            else if (i7 >= 0)
                iBest = i7;
            else if (iNM >= 0 && handCards.get(iNM).cardColorEnum != prevStrong)
                iBest = iNM;
            else if (iSK >= 0 && handCards.get(iSK).cardColorEnum != prevStrong)
                iBest = iSK;
            else if (iDW >= 0 && handCards.get(iDW).cardColorEnum != prevStrong)
                iBest = iDW;
            else if (iWD >= 0 && lastCardColorEnum != bestCardColorEnum)
                iBest = iWD;
            else if (iWD4 >= 0 && lastCardColorEnum != bestCardColorEnum)
                iBest = iWD4;
            else if (iNM >= 0)
                iBest = iNM;
        } // else if (prevSize == 1)
        else if (oppoSize == 1) {
            // Strategies when your opposite player remains only one card.
            // Consider to use a 7 to steal the UNO.
            if (i7 >= 0)
                iBest = i7;
            else if (i0 >= 0)
                iBest = i0;
            else if (iNM >= 0 && handCards.get(iNM).cardColorEnum != oppoStrong)
                iBest = iNM;
            else if (iRV >= 0 && prevSize > nextSize)
                iBest = iRV;
            else if (iSK >= 0 && handCards.get(iSK).cardColorEnum != oppoStrong)
                iBest = iSK;
            else if (iDW >= 0 && handCards.get(iDW).cardColorEnum != oppoStrong)
                iBest = iDW;
            else if (iWD >= 0 && lastCardColorEnum != bestCardColorEnum)
                iBest = iWD;
            else if (iWD4 >= 0 && lastCardColorEnum != bestCardColorEnum)
                iBest = iWD4;
            else if (iNM >= 0)
                iBest = iNM;
        } // else if (oppoSize == 1)
        else {
            // Normal strategies
            if (i0 >= 0 && handCards.get(i0).cardColorEnum == prevStrong)
                iBest = i0;
            else if (i7 >= 0 && (handCards.get(i7).cardColorEnum == prevStrong
                    || handCards.get(i7).cardColorEnum == oppoStrong
                    || handCards.get(i7).cardColorEnum == nextStrong))
                iBest = i7;
            else if (iRV >= 0 && (prevSize > nextSize
                    || prev.getRecent() == null))
                iBest = iRV;
            else if (iNM >= 0)
                iBest = iNM;
            else if (iSK >= 0)
                iBest = iSK;
            else if (iDW >= 0)
                iBest = iDW;
            else if (iRV >= 0)
                iBest = iRV;
            else if (iWD >= 0)
                iBest = iWD;
            else if (iWD4 >= 0)
                iBest = iWD4;
            else if (i0 >= 0 && (yourSize > 2
                    || (handCards.get(1 - i0).getContent() != NUM0
                    && handCards.get(1 - i0).getContent() != WILD
                    && handCards.get(1 - i0).getContent() != WILD_DRAW4
                    && handCards.get(1 - i0).cardColorEnum != handCards.get(i0).cardColorEnum)))
                iBest = i0;
            else if (i7 >= 0)
                iBest = i7;
        } // else

        outCardColorEnum[0] = bestCardColorEnum;
        return iBest;
    } // easyAI_bestCardIndex4NowPlayer(Color[])

}
