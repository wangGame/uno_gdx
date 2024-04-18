package kw.test.uno.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;

import kw.test.uno.data.Card;

public class DeskCardGroup extends Group {
    private Array<Card> cards;
    public DeskCardGroup(){
        setSize(121,181);
        setDebug(true);
//        card
        Image image = new Image(Asset.getAsset().getTexture("dark_b0.png"));
        addActor(image);
        image.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
        addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                sendCard(1);
            }
        });
    }

    public Array<Card> sendCard(int num){
        Array<Card> sendCards = new Array<>();
        if (num<cards.size) {
            Card card = cards.removeIndex(0);
            sendCards.add(card);
        }else {
            //收集废弃
        }
        return sendCards;
    }
}
