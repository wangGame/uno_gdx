package kw.test.uno.bean;

import kw.test.uno.data.CardColor;
import kw.test.uno.data.CardValue;

public class RecentBean {
    private CardColor cardColor;
    private CardValue cardValue;

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    public void setCardValue(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    @Override
    public String toString() {
        return "RecentBean{" +
                "cardColor=" + cardColor +
                ", cardValue=" + cardValue +
                '}';
    }
}
