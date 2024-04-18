package kw.test.uno.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.github.hikari_toyama.unocard.core.Card;
import com.kw.gdx.asset.Asset;

import kw.test.uno.data.UnoCardData;

public class OutCardGroup extends Group {
    private Array<CardGroup> cards = new Array<>();
    public OutCardGroup(){
        setDebug(true);
        Image image = new Image(Asset.getAsset().getTexture("dark_b1.png"));
        addActor(image); //121    181
        setSize(700,500);
        image.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
    }

    public void outCard(CardGroup cardGroup){
        cards.add(cardGroup);
    }

    public Array<CardGroup> clearAllCardGroup(){
        Array<CardGroup> cardGroups = new Array<>(cards);
        cards.clear();
        return cardGroups;
    }
}
