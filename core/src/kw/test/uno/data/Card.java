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
}
