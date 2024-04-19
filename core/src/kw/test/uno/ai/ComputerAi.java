package kw.test.uno.ai;

import com.badlogic.gdx.utils.Array;

import kw.test.uno.bean.RecentBean;
import kw.test.uno.contant.UnoConfig;
import kw.test.uno.data.Card;
import kw.test.uno.data.CardColor;
import kw.test.uno.data.CardValue;
import kw.test.uno.group.UserGroup;
import kw.test.uno.player.Aplayer;
import kw.test.uno.utils.UnoUtils;

public class ComputerAi {
    private Array<Aplayer> userPlayers;
    public ComputerAi(Array<Aplayer> aplayers){
        this.userPlayers = aplayers;
    }

    //计算最好的颜色
    public CardColor calcBestColor4NowPlayer(Aplayer currentPlayer){
        CardColor bestColor = CardColor.NONE;
        CardColor tempColor = CardColor.NONE;
        /**
         * 更新一下，从自己的下一个开始
         */
        int index = currentPlayer.getIndex();
        for (int i = 1; i < userPlayers.size; i++) {
            if (UnoConfig.DIR == UnoConfig.DIR_LEFT){
                index+=i;
            }else {
                index-=i;
            }
            index =( index + userPlayers.size ) % userPlayers.size;
            Aplayer userPlayer = userPlayers.get(index);
            CardColor weakCardColor = userPlayer.getWeakCardColor();
            /**
             * 如果用户已经uno了，最好的颜色就是他们不存在的颜色
             */
            boolean uno = userPlayer.isUno();
            if (tempColor == CardColor.NONE) {
                if (weakCardColor != CardColor.NONE) {
                    tempColor = weakCardColor;
                }
            }
            if (uno && weakCardColor!=CardColor.NONE){
                bestColor = weakCardColor;
                break;
            }
        }
        //都不存在弱色   那么就比较价值
        if (bestColor == CardColor.NONE){
            Array<Card> cards = currentPlayer.getCards();
            int []score = {0,0,0,0,0};
            for (Card card : cards) {
                CardValue cardValue = card.getCardValue();
                switch (cardValue){
                    case WILD:
                    case WILD_DRAW4:
                        break;
                    case REV:
                    case NUM0:
                        score[card.getCardColor().ordinal()]+=2;
                        break;
                    case SKIP:
                    case DRAW2:
                        score[card.getCardColor().ordinal()]+=5;
                        break;
                    default:
                        score[card.getCardColor().ordinal()]+=4;
                        break;
                }
            }
            if (score[CardColor.RED.ordinal()]>score[bestColor.ordinal()]){
                bestColor = CardColor.RED;
            }
            if (score[CardColor.BLUE.ordinal()]>score[bestColor.ordinal()]){
                bestColor = CardColor.BLUE;
            }
            if (score[CardColor.GREEN.ordinal()]>score[bestColor.ordinal()]){
                bestColor = CardColor.GREEN;
            }
            if (score[CardColor.YELLOW.ordinal()]>score[bestColor.ordinal()]){
                bestColor = CardColor.YELLOW;
            }
            if (bestColor == CardColor.NONE){
                if (tempColor == CardColor.NONE){
                    bestColor = tempColor;
                }else {
                    bestColor = CardColor.RED;
                }
            }
        }
        //如果自己颜色是下一个的强色，那么就不要使用。

        index = currentPlayer.getIndex();
        for (int i = 1; i < userPlayers.size; i++) {
            if (UnoConfig.DIR == UnoConfig.DIR_LEFT){
                index+=i;
            }else {
                index-=i;
            }
            index =( index + userPlayers.size ) % userPlayers.size;
            Aplayer userPlayer = userPlayers.get(index);
            if (userPlayer.isUno()) {
                if (bestColor == userPlayer.getStrongCardColor()) {
                    bestColor = CardColor.values()[UnoConfig.random.nextInt(4) + 1];
                }
            }
        }
        return bestColor;
    }

    /**
     * 和谁交换
     * @return
     */
    public Aplayer calcBestSwapTarget4NowPlayer(Aplayer aplayer, RecentBean recentBean, UnoUtils utils) {
        int index = aplayer.getIndex();
        for (int i = 1; i < userPlayers.size; i++) {
            if (UnoConfig.DIR == UnoConfig.DIR_LEFT){
                index+=i;
            }else {
                index-=i;
            }
            index =( index + userPlayers.size ) % userPlayers.size;
            Aplayer userPlayer = userPlayers.get(index);
            if (aplayer == userPlayer) {
                continue;
            }
            if (userPlayer.isUno()) {
                return userPlayer;
            }
        }
        index = aplayer.getIndex();
        for (int i = 1; i < userPlayers.size; i++) {
            if (UnoConfig.DIR == UnoConfig.DIR_LEFT){
                index+=i;
            }else {
                index-=i;
            }
            index =( index + userPlayers.size ) % userPlayers.size;
            Aplayer userPlayer = userPlayers.get(index);
            if (recentBean.getCardColor() == userPlayer.getStrongCardColor()) {
                return userPlayer;
            }
        }
        UserGroup currentPlayer = utils.currentPlayer();
        UserGroup temp = utils.nextTempPlayer();
        if (currentPlayer.getAplayer().isUno()&&
            isLegalToPlay(recentBean,currentPlayer.getAplayer().getCards().get(0))){
            temp = utils.prevTempPlayer();
        }
        return temp.getAplayer();
    }

    /**
     * 挑战规则我在查查
     * @param recentBean
     * @param oldCardColor
     * @return
     */
    public boolean needToChallenge(RecentBean recentBean, UnoUtils utils, CardColor oldCardColor){
        UserGroup prevTempPlayer = utils.prevTempPlayer();
        UserGroup currentPlayer = utils.currentPlayer();

        if (prevTempPlayer.getAplayer().isUno()) {
            return true;
        }
        if (prevTempPlayer.getAplayer().getCards().size>10){
            return true;
        }
        if (currentPlayer.getAplayer().getCards().size>10){
            return true;
        }
        /**
         * 没有改变颜色的时候
         */
        if (recentBean.getCardColor() == oldCardColor){
            return true;
        }
        return false;
    }

    /**
     * 直接操作用户的顺序  也就是数组的顺序
     */
    public void swap(Array userPlayers,int index1,int index2){
        Object o = userPlayers.get(index1);
        Object o1 = userPlayers.get(index2);
        userPlayers.set(index1,o1);
        userPlayers.set(index2,o);
    }

    public void cycle(Array userPlayers){
        Object o = userPlayers.removeIndex(userPlayers.size - 1);
        userPlayers.insert(0,o);
    }

    public boolean easyAI(Aplayer aplayer,RecentBean bean,Card[] outCard){
        outCard[0] = null;
        Array<Card> hand = aplayer.getCards();
        Card card;
        if (hand.size == 1){
            card = hand.get(0);
            //检测合法
            if (isLegalToPlay(bean,card)) {
                outCard[0] = card;
                return true;
            }
            return false;
        }
        CardColor lastCardColorEnum = bean.getCardColor();
        CardColor bestColor4NowPlayer = calcBestColor4NowPlayer(aplayer);
        int matches = 0;
        int iDW = -1;
        int iSK = -1;
        int iRV = -1;
        int iWD = -1;
        int iWD4 = -1;
        int iNM = -1;//数字牌
        for (int i = 0; i < hand.size; i++) {
            card = hand.get(i);
            if (card.getCardColor() == lastCardColorEnum) {
                matches++;  //可以匹配
            }
            if (isLegalToPlay(bean,card)){
                outCard[0]=card;
                switch (card.getCardValue()){
                    case DRAW2: {
                        if (iDW < 0 || card.getCardColor() == bestColor4NowPlayer) {
                            iDW = i;
                        }
                        break;
                    }
                    case SKIP:{
                        if (iSK<0 || card.getCardColor() == bestColor4NowPlayer){
                            iSK = i;
                        }
                        break;
                    }
                    case REV:
                        if (iRV < 0 || card.getCardColor() == bestColor4NowPlayer)
                            iRV = i;
                        break; // case REV

                    case WILD:
                        iWD = i;
                        break; // case WILD

                    case WILD_DRAW4:
                        iWD4 = i;
                        break; // case WILD_DRAW4

                    case NUM7:
//                        if (uno.isSevenZeroRule()) {
//                            if (i7 < 0 || card.cardColorEnum == bestCardColorEnum)
//                                i7 = i;
//                            break; // case NUM7
//                        } // if (uno.isSevenZeroRule())
                        // fall through
                    default: // number cards
                        if (iNM < 0 || card.getCardColor() == bestColor4NowPlayer)
                            iNM = i;
                        break; // default
                }
            }
        }
        if (outCard[0] == null){
            return false;
        }
        return true;
    }

    public boolean isLegalToPlay(RecentBean bean,Card card){
        if (bean.getCardColor() == card.getCardColor()) {
            return true;
        }else if (bean.getCardValue()==card.getCardValue()){
            return true;
        }
        return false;
    }
}
