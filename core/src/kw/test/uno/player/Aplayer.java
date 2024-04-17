package kw.test.uno.player;

import com.badlogic.gdx.utils.Array;

import kw.test.uno.data.Card;

public abstract class Aplayer {
    private Array<Card> cards;
    public Aplayer(){
        cards = new Array<>();
    }

    public void sendCard(Card card){
        cards.add(card);
    }

    public void outCard(int index){
        cards.removeIndex(index);
    }

    @Override
    public String toString() {
        return "Aplayer{" +
                "cards=" + cards +
                '}';
    }
}
