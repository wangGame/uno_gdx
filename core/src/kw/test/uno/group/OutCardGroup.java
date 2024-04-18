package kw.test.uno.group;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.asset.Asset;

import kw.test.uno.data.Card;
import kw.test.uno.data.UnoCardData;

public class OutCardGroup extends Group {
    private Array<Card> cards = new Array<>();
    public OutCardGroup(){
        setDebug(true);
        Image image = new Image(Asset.getAsset().getTexture("dark_b1.png"));
        addActor(image); //121    181
        setSize(700,500);
        image.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
    }

    public void outCard(Card card, Vector2 vector2, int i){
        cards.add(card);
        CardGroup cardGroup = new CardGroup(card);
        addActor(cardGroup);
        stageToLocalCoordinates(vector2);
        cardGroup.setDebug(true);
        cardGroup.setPosition(vector2.x,vector2.y,Align.center);
        cardGroup.addAction(Actions.sequence(
                Actions.delay(i),
                Actions.moveTo(0,0,0.2f)
        ));
    }

    public Array<Card> clearAllCardGroup(){
        Array<Card> cardGroups = new Array<>(cards);
        cards.clear();
        return cardGroups;
    }
}
