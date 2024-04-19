package kw.test.uno.player;

import com.badlogic.gdx.utils.Array;

import kw.test.uno.data.Card;
import kw.test.uno.data.CardColor;
import kw.test.uno.group.CardGroup;

public abstract class Aplayer {
    private Array<Card> cards;
    //用户的序号， 比如前一个用户后一个用户
    private int index;
    /**
     * 具体不清楚
     *
     * 1.开局给NONE
     * 2.需要抽卡的时候给最后一个用户的颜色
     * 3，万能牌的时候NONE
     */
    private CardColor weakCardColor;
    private CardColor strongCardColor;
    public Aplayer(){
        cards = new Array<>();
    }

    public void sendCard(Card card){
        cards.add(card);
    }

    public void outCard(Card card){
        cards.removeValue(card,false);
    }

    public Array<Card> getCards() {
        return cards;
    }

    public abstract void decision();

    public CardColor getWeakCardColor() {
        return weakCardColor;
    }

    public void setWeakCardColor(CardColor weakCardColor) {
        this.weakCardColor = weakCardColor;
    }

    public boolean isUno(){
        return cards.size == 1;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setStrongCardColor(CardColor strongCardColor) {
        this.strongCardColor = strongCardColor;
    }

    public CardColor getStrongCardColor() {
        return strongCardColor;
    }

    @Override
    public String toString() {
        return "Aplayer{" +
                "cards=" + cards +
                '}';
    }

}
