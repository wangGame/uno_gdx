package kw.test.uno.data;

public class Card {
    private CardColor cardColor;
    private CardValue cardValue;

    public Card(CardValue cardValue,CardColor cardColor) {
        this.cardColor = cardColor;
        this.cardValue = cardValue;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public void setCardValue(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public CardValue getCardValue() {
        return cardValue;
    }


    @Override
    public String toString() {
        return "Card{" +
                "cardColor=" + cardColor +
                ", cardValue=" + cardValue +
                '}';
    }

    public boolean isWild() {
        return cardValue == CardValue.WILD || cardValue == CardValue.WILD_DRAW4;
    }
}
