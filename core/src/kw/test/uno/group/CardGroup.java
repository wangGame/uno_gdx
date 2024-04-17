package kw.test.uno.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.test.uno.data.Card;

public class CardGroup extends Group {
    private Card card;
    private Image cardImg;
    public CardGroup(Card card){
        this.card = card;
    }

    public Card getCard() {
        return card;
    }
}
