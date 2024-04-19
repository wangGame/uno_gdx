package kw.test.uno.group;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;

import kw.test.uno.data.Card;

public class CardGroup extends Group {
    private Card card;
    private Image cardImg;
    public CardGroup(Card card,boolean hide) {
        this.card = card;
        int vOrdinal = card.getCardValue().ordinal();
        int cOrdinal = card.getCardColor().ordinal();
        String str;
        if (!hide){
            str = "front_";
            if (cOrdinal == 1) {
                str += "r";
            } else if (cOrdinal == 2) {
                str += "b";
            } else if (cOrdinal == 3) {
                str += "g";
            } else if (cOrdinal == 4) {
                str += "y";
            }
            if (vOrdinal == 10) {
                str += "d2";
            } else if (vOrdinal == 11) {
                str += "r";
            } else if (vOrdinal == 12) {
                str += "s";
            } else if (vOrdinal == 13) {
                str += "w";
            } else if (vOrdinal == 14) {
                str += "w4";
            } else {
                str += vOrdinal;
            }
            str += ".png";
        }else {
             str = "back.png";
         }
        cardImg = new Image(Asset.getAsset().getTexture(str));
        addActor(cardImg);
        setSize(cardImg.getWidth(),cardImg.getHeight());
    }

    public Card getCard() {
        return card;
    }
}
