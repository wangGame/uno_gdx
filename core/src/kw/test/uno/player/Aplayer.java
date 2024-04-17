package kw.test.uno.player;

import com.badlogic.gdx.utils.Array;

import kw.test.uno.data.Card;
import kw.test.uno.group.CardGroup;

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

    public abstract void decision();

    @Override
    public String toString() {
        return "Aplayer{" +
                "cards=" + cards +
                '}';
    }
}
