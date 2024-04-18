package kw.test.uno.group;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.listener.OrdinaryButtonListener;

import java.util.Comparator;

import kw.test.uno.data.Card;
import kw.test.uno.player.Aplayer;
import kw.test.uno.sign.SignListener;

public class UserGroup extends Group {
    private Aplayer aplayer;
    private ArrayMap<Card,CardGroup> cardGroupMaps;
    private Array<CardGroup> cardGroups;
    private Group cardPanel;
    private Image unoImg;

    public UserGroup(Aplayer aplayer){
        setSize(280,180);
        this.aplayer = aplayer;
        this.cardGroupMaps = new ArrayMap<>();
        this.cardGroups = new Array<>();
        this.cardPanel = new Group();
        cardPanel.setSize(getWidth(),getHeight());
        cardPanel.setPosition(getWidth()/2.0f,getHeight()/2.0f, Align.center);
        addActor(cardPanel);
        this.unoImg = new Image(Asset.getAsset().getTexture("common/say_uno.png"));
        addActor(unoImg);
        unoImg.setOrigin(Align.center);
        unoImg.setScale(0.5f);
        unoImg.setPosition(getWidth()/2.0f,250,Align.center);
        unoImg.setVisible(false);
        unoImg.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                unoImg.setVisible(false);
            }
        });
    }

    public void addCard(Array<Card> cards, Vector2 vector2, float time, SignListener signListener){
        for (Card card : cards) {
            stageToLocalCoordinates(vector2);
            CardGroup cardGroup = new CardGroup(card);
            cardGroupMaps.put(card,cardGroup);
            cardGroups.add(cardGroup);
            aplayer.sendCard(card);
            cardPanel.addActor(cardGroup);
            cardGroup.setVisible(false);
            cardGroup.toBack();
            cardGroup.setPosition(vector2.x,vector2.y,Align.center);

            cardGroup.addListener(new OrdinaryButtonListener(){
                float yyy = 0;
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    super.enter(event, x, y, pointer, fromActor);
                    cardGroup.setY(yyy + 30);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    super.exit(event, x, y, pointer, toActor);
                    cardGroup.setY(yyy);
                }

                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    signListener.sign(card);
                }
            });
            cardGroup.addAction(Actions.sequence(
                    Actions.delay(time),
                    Actions.visible(true),
                    Actions.moveTo(0,0,0.2f),
                    Actions.run(()->{
                        layoutCard();
                    })
            ));
        }
    }

    public void layoutCard(){
        cardGroups.sort(Comparator);
        SnapshotArray<Actor> children = cardPanel.getChildren();
        children.sort(Comparator);
        float v = (cardPanel.getWidth()-80) / children.size;
        for (int i = 0; i < children.size; i++) {
            children.get(i).setX(v * i);
        }
    }

    private Comparator Comparator = new Comparator<CardGroup>() {
        @Override
        public int compare(CardGroup cardGroup, CardGroup t1) {
            int vordinal1 = cardGroup.getCard().getCardValue().ordinal();
            int vordinal2 = t1.getCard().getCardValue().ordinal();
            int cordinal1 = cardGroup.getCard().getCardColor().ordinal();
            int cordinal2 = t1.getCard().getCardColor().ordinal();
            if (cordinal1>cordinal2) {
                return 1;
            }else if (cordinal1<cordinal2){
                return -1;
            }else {
                return vordinal1 - vordinal2;
            }
        }
    };

    public CardGroup sendOutCard(Card card){
        CardGroup cardGroup = cardGroupMaps.get(card);
        aplayer.outCard(card); //删除数据
        cardGroupMaps.removeKey(card);
        cardGroups.removeValue(cardGroup,false);
        if (aplayer.getCards().size == 1) {
            unoImg.setVisible(true);
        }
        return cardGroup;
    }
}
