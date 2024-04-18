package kw.test.uno.screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.BaseGame;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.screen.BaseScreen;
import com.kw.gdx.utils.log.NLog;

import kw.test.uno.bean.RecentBean;
import kw.test.uno.data.Card;
import kw.test.uno.data.UnoCardData;
import kw.test.uno.group.CardGroup;
import kw.test.uno.group.DeskCardGroup;
import kw.test.uno.group.OutCardGroup;
import kw.test.uno.group.UserGroup;
import kw.test.uno.player.Aplayer;
import kw.test.uno.player.ComPlayer;
import kw.test.uno.player.UserPlayer;
import kw.test.uno.sign.SignListener;
import kw.test.uno.utils.UnoUtils;

public class GameScreen extends BaseScreen {
    private int playerNum = 5;
    private int initCardNum = 5;
    private Array<UserGroup> userGroups;
    private OutCardGroup outCardGroup;
    private DeskCardGroup deskCardGroup;
    private Vector2 deskCardV2;
    private RecentBean recentBean;
    private UnoUtils utils;

    public GameScreen(BaseGame game) {
        super(game);
        this.userGroups = new Array<>();
        UnoCardData unoCardData = new UnoCardData();
        this.outCardGroup = new OutCardGroup();
        this.deskCardGroup = new DeskCardGroup(unoCardData);
        this.deskCardV2 = new Vector2();
        recentBean = new RecentBean();
        this.utils = new UnoUtils(userGroups);
    }

    @Override
    public void initView() {
        super.initView();
        initDeskCard();
        initPlayerPanel();
        sendCard();
        layoutCard();
        startGame();
    }

    private void startGame() {
        Array<Card> cards = deskCardGroup.sendCard(1);
        Card card = cards.get(0);
        NLog.i("开局card:"+card);
        outCardGroup.outCard(card,new Vector2(deskCardV2),3);
        recentBean.setCardColor(card.getCardColor());
        recentBean.setCardValue(card.getCardValue());
        //当前用户
        utils.currentPlayer();
    }


    private void initDeskCard() {
        rootView.addActor(deskCardGroup);
        rootView.addActor(outCardGroup);
        outCardGroup.setPosition(Constant.WIDTH/2.0f + 100,Constant.HIGHT/2.0f,Align.center);
        deskCardGroup.setPosition(Constant.WIDTH/2.0f - 400,Constant.HIGHT/2.0f,Align.center);
        deskCardV2.set(deskCardGroup.getX(Align.center),deskCardGroup.getY(Align.center));
        deskCardGroup.getParent().localToStageCoordinates(deskCardV2);
        deskCardGroup.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //点击发牌
                UserGroup userGroup = utils.currentPlayer();
                Array<Card> cards = deskCardGroup.sendCard(1);
                createCard(0,userGroup,cards);
                utils.nextPlayer();
            }
        });
    }

    private void initPlayerPanel() {
        for (int i = 0; i < playerNum; i++) {
            Aplayer aplayer;
            if (i == 0){
                aplayer = new UserPlayer();
            }else {
                aplayer = new ComPlayer();
            }
            aplayer.setIndex(i);
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
    }

    private void layoutCard() {
        for (UserGroup userGroup : userGroups) {
            userGroup.layoutCard();
        }
    }

    private void sendCard() {
        float time = 0;
        for (int i = 0; i < initCardNum; i++) {
            for (UserGroup userGroup : userGroups) {
                time += 0.1f;
                Array<Card> cards = deskCardGroup.sendCard(1);
                createCard(time, userGroup, cards);
            }
        }
    }

    private void createCard(float time, UserGroup userGroup, Array<Card> cards) {
        System.out.println(deskCardV2);
        userGroup.addCard(cards,new Vector2(deskCardV2), time,signListener);
    }

    public SignListener signListener = new SignListener(){
        @Override
        public void sign(Object object) {
            super.sign(object);
            if (object instanceof Card){
                Card card = (Card)object;
                if (!utils.isLegalToPlay(recentBean,card)) {
                    return;
                }
                UserGroup userGroup = utils.currentPlayer();
                CardGroup cardGroup = userGroup.sendCard(card);
                Vector2 vector2 = new Vector2();
                vector2.set(cardGroup.getX(),cardGroup.getY());
                cardGroup.getParent().localToStageCoordinates(vector2);
//                outCardGroup.stageToLocalCoordinates(vector2);
//                cardGroup.setPosition(vector2.x,vector2.y,Align.center);
//                cardGroup.addAction(Actions.moveTo(0,0,0.2f));
//                outCardGroup.addActor(cardGroup);
                cardGroup.remove();
                outCardGroup.outCard(card,new Vector2(vector2),0);

                userGroup.layoutCard();
                recentBean.setCardValue(card.getCardValue());
                recentBean.setCardColor(card.getCardColor());
                utils.nextPlayer();
            }
        }
    };
}
