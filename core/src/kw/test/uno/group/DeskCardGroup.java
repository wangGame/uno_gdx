package kw.test.uno.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;

import kw.test.uno.data.Card;
import kw.test.uno.data.UnoCardData;

public class DeskCardGroup extends Group {
    private UnoCardData unoCardData;
    public DeskCardGroup(UnoCardData unoCardData){
        setSize(121,181);
        this.unoCardData = unoCardData;
//        card
        Image image = new Image(Asset.getAsset().getTexture("card/back.png"));
        addActor(image);
        image.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);

        unoCardData.initDeskCard();
        unoCardData.shuffle();
    }


    public Array<Card> sendCard(int num){
        Array<Card> sendCards = new Array<>();
        Array<Card> cards = unoCardData.sendCard(num);
        sendCards.addAll(cards);
        return sendCards;
    }

    public void addOutCard(Array<Card> cards1) {
        unoCardData.addOutCard(cards1);
    }
}
