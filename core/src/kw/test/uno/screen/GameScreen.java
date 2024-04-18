package kw.test.uno.screen;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.screen.BaseScreen;

import kw.test.uno.data.Card;
import kw.test.uno.data.UnoCardData;
import kw.test.uno.group.DeskCardGroup;
import kw.test.uno.group.OutCardGroup;
import kw.test.uno.group.UserGroup;
import kw.test.uno.player.Aplayer;
import kw.test.uno.player.ComPlayer;
import kw.test.uno.player.UserPlayer;

public class GameScreen extends BaseScreen {
    private int playerNum = 5;
    private int initCardNum = 5;
    private Array<UserGroup> userGroups;
    private UnoCardData unoCardData;
    private OutCardGroup outCardGroup;
    private DeskCardGroup deskCardGroup;
    public GameScreen(BaseGame game) {
        super(game);
        this.userGroups = new Array<>();
        this.unoCardData = new UnoCardData();
        this.outCardGroup = new OutCardGroup();
        this.deskCardGroup = new DeskCardGroup();
    }

    @Override
    public void initView() {
        super.initView();

        for (int i = 0; i < playerNum; i++) {
            Aplayer aplayer;
            if (i == 0){
                aplayer = new UserPlayer();
            }else {
                aplayer = new ComPlayer();
            }
            UserGroup userGroup = new UserGroup(aplayer);
            userGroups.add(userGroup);
            rootView.addActor(userGroup);
            userGroup.setDebug(true);
            userGroup.setPosition(
                    (float) (Constant.WIDTH/2.0f+(Constant.WIDTH-100)/2.0f*Math.cos(Math.toRadians(i * (360.f/playerNum)))),
                    (float)(Constant.HIGHT/2.0f+(Constant.HIGHT-100)/2.0f*Math.sin(Math.toRadians(i * (360.f/playerNum)))),
                    Align.center);
            userGroup.setOrigin(Align.center);
            userGroup.setRotation(90 + i * (360.f/playerNum));
        }

        unoCardData.initDeskCard();
        unoCardData.shuffle();

        sendCard();
        layoutCard();


        rootView.addActor(outCardGroup);
        rootView.addActor(deskCardGroup);
        outCardGroup.setPosition(Constant.WIDTH/2.0f + 100,Constant.HIGHT/2.0f,Align.center);
        deskCardGroup.setPosition(Constant.WIDTH/2.0f - 400,Constant.HIGHT/2.0f,Align.center);
    }

    private void layoutCard() {
        for (UserGroup userGroup : userGroups) {
            userGroup.layoutCard();
        }
    }

    private void sendCard() {
        for (int i = 0; i < initCardNum; i++) {
            for (UserGroup userGroup : userGroups) {
                Array<Card> cards = unoCardData.sendCard(1);
                userGroup.addCard(cards);
            }
        }
    }
}
