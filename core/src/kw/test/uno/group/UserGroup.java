package kw.test.uno.group;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.SnapshotArray;
import com.kw.gdx.asset.Asset;

import java.util.Comparator;

import kw.test.uno.data.Card;
import kw.test.uno.player.Aplayer;

public class UserGroup extends Group {
    private Aplayer aplayer;
    private ArrayMap<Card,CardGroup> cardGroupMaps;
    private Array<CardGroup> cardGroups;
    private Group cardPanel;
    public UserGroup(Aplayer aplayer){
        this.aplayer = aplayer;
        this.cardGroupMaps = new ArrayMap<>();
        this.cardGroups = new Array<>();
        this.cardPanel = new Group();
    }

    public void addCard(Array<Card> cards){
        for (Card card : cards) {
            CardGroup cardGroup = new CardGroup(card);
            cardGroupMaps.put(card,cardGroup);
            cardGroups.add(cardGroup);
            aplayer.sendCard(card);
        }
        Image o = new Image(Asset.getAsset().getTexture("dark_b0.png"));
        addActor(o);
    }

    public void layoutCard(){
        cardGroups.sort(Comparator);
        SnapshotArray<Actor> children = cardPanel.getChildren();
        children.sort(Comparator);
        for (int i = 0; i < children.size; i++) {
            children.get(i).setX(10 * i);
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
}
