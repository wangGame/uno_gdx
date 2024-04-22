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
        setSize(50,50);
    }

    public void outCard(Card card, Vector2 vector2, int i){
        cards.add(card);
        CardGroup cardGroup = new CardGroup(card,false);
        addActor(cardGroup);
        stageToLocalCoordinates(vector2);
        cardGroup.setPosition(vector2.x,vector2.y,Align.center);
        cardGroup.setVisible(false);
        cardGroup.setOrigin(Align.center);
        cardGroup.addAction(
                Actions.parallel(
                    Actions.sequence(
                        Actions.delay(i),
                        Actions.visible(true),
                        Actions.moveToAligned((float) ((getWidth())*Math.random()),
                                (float) ((getHeight())*Math.random()),Align.center,0.2f)
                    ),
                    Actions.sequence(
                            Actions.delay(i),
                        Actions.rotateTo((float) (360 * Math.random()),0.2f))
                ));
    }

    public Array<Card> clearAllCardGroup(){
        Array<Card> cardGroups = new Array<>(cards);
        cards.clear();
        return cardGroups;
    }
}
