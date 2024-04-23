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
        StringBuilder str = new StringBuilder();
        str.append("card/");
        if (!hide){
            str.append("front_");
            if (cOrdinal == 1) {
                str.append("r");
            } else if (cOrdinal == 2) {
                str.append("b");
            } else if (cOrdinal == 3) {
                str.append("g");
            } else if (cOrdinal == 4) {
                str.append("y");
            }
            if (vOrdinal == 10) {
                str.append("d2");
            } else if (vOrdinal == 11) {
                str.append("r");
            } else if (vOrdinal == 12) {
                str.append("s");
            } else if (vOrdinal == 13) {
                str.append("w");
            } else if (vOrdinal == 14) {
                str.append("w4");
            } else {
                str.append(vOrdinal);
            }
            str.append(".png");
        }else {
             str.append("back.png");
         }
        cardImg = new Image(Asset.getAsset().getTexture(str.toString()));
        addActor(cardImg);
        setSize(cardImg.getWidth(),cardImg.getHeight());
    }

    public Card getCard() {
        return card;
    }
}
