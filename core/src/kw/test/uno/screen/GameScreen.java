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
import kw.test.uno.group.UserGroup;
import kw.test.uno.player.Aplayer;
import kw.test.uno.player.ComPlayer;
import kw.test.uno.player.UserPlayer;

public class GameScreen extends BaseScreen {
    private int playerNum = 7;
    private int initCardNum = 5;
    private Array<UserGroup> userGroups;
    private UnoCardData unoCardData;
    public GameScreen(BaseGame game) {
        super(game);
        this.userGroups = new Array<>();
        this.unoCardData = new UnoCardData();
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
            userGroup.setSize(600,100);
            userGroups.add(userGroup);
            addActor(userGroup);
            userGroup.setDebug(true);
//            Image image = new Image(Asset.getAsset().getTexture("back.png"));
//            addActor(image);
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
