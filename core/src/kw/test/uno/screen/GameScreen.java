package kw.test.uno.screen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.screen.BaseScreen;
import com.kw.gdx.utils.log.NLog;

import kw.test.uno.ai.ComputerAi;
import kw.test.uno.bean.RecentBean;
import kw.test.uno.contant.UnoConfig;
import kw.test.uno.data.Card;
import kw.test.uno.data.CardColor;
import kw.test.uno.data.UnoCardData;
import kw.test.uno.dialog.SelectColorDialog;
import kw.test.uno.dialog.SuccessDialog;
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
    private int playerNum = 10;
    private int initCardNum = 5;
    private Array<UserGroup> userGroups;
    private OutCardGroup outCardGroup;
    private DeskCardGroup deskCardGroup;
    private Vector2 deskCardV2;
    private RecentBean recentBean;
    private UnoUtils utils;
    private Image dirImg;
    private ComputerAi ai;
    private Array<Aplayer> aplayers;
    private Image aiBtn;

    public GameScreen(BaseGame game) {
        super(game);
        this.userGroups = new Array<>();
        UnoCardData unoCardData = new UnoCardData();
        this.outCardGroup = new OutCardGroup();
        this.deskCardGroup = new DeskCardGroup(unoCardData);
        this.deskCardV2 = new Vector2();
        recentBean = new RecentBean();
        this.utils = new UnoUtils(userGroups);
        this.aplayers = new Array<>();
        this.ai = new ComputerAi(aplayers);
        this.aiBtn = new Image(Asset.getAsset().getTexture("common/AI.png"));
    }

    private Card[] tempCard = new Card[1];

    @Override
    public void initView() {
        super.initView();
        initBg();
        initDeskCard();
        initPlayerPanel();
        sendCard();
        layoutCard();
        startGame();
        addActor(aiBtn);
        aiBtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                UserGroup userGroup = utils.currentPlayer();
                boolean b = ai.easyAI(userGroup.getAplayer(), recentBean, tempCard);
                if (b){
                    sendCard(tempCard[0]);
                }else {
                    //点击发牌
                    Array<Card> cards = deskCardGroup.sendCard(1);
                    createCard(0,userGroup,cards);
                    Card card = cards.get(0);
                    //揭牌刚好可以打出
                    if (utils.isLegalToPlay(recentBean,card)) {
                        sendCard(card);
                    }else {
                        utils.nextPlayer();
                    }
                }
            }
        });
    }

    private void initBg() {
        Image bg = new Image(Asset.getAsset().getTexture("bg_welcome.png"));
        rootView.addActor(bg);
        float bgScale = Math.max(Constant.GAMEHIGHT/1600.0f,Constant.GAMEHIGHT/900.0f); //1600 900
        bg.setOrigin(Align.center);
        bg.setScale(bgScale);
        bg.setPosition(960.0f,540.0f,Align.center);
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
        stage.addAction(Actions.sequence(
                Actions.delay(3,Actions.run(()->{
                    updateDirImg();
                }))
        ));
    }

    public void updateDirImg(){
        if (dirImg!=null){
            dirImg.clear();
            dirImg.remove();
        }
        this.dirImg = new Image(Asset.getAsset().getTexture("dirimg/"+utils.dirDirName(recentBean)));
        rootView.addActor(dirImg);
        dirImg.setPosition(Constant.WIDTH/2.0f,Constant.HIGHT/2.0f,Align.center);
        dirImg.setOrigin(Align.center);
        if (UnoConfig.DIR == UnoConfig.DIR_LEFT){
            dirImg.addAction(Actions.forever(
                    Actions.rotateBy(10,0.1f)
            ));
        }else {
            dirImg.addAction(Actions.forever(
                    Actions.rotateBy(-10,0.1f)
            ));
        }
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
                Card card = cards.get(0);
                //揭牌刚好可以打出
                if (utils.isLegalToPlay(recentBean,card)) {
                    sendCard(card);
                }else {
                    utils.nextPlayer();
                }
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
            aplayers.add(aplayer);
            rootView.addActor(userGroup);
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
                sendCard(card);
            }
        }
    };

    private void sendCard(Card card) {
        UserGroup userGroup = utils.currentPlayer();
        CardGroup cardGroup = userGroup.sendOutCard(card);
        Vector2 vector2 = new Vector2();
        vector2.set(cardGroup.getX(),cardGroup.getY());
        cardGroup.getParent().localToStageCoordinates(vector2);
        cardGroup.remove();
        outCardGroup.outCard(card,new Vector2(vector2),0);
        userGroup.layoutCard();
        CardColor oldCardColor = recentBean.getCardColor();
        recentBean.setCardValue(card.getCardValue());
        recentBean.setCardColor(card.getCardColor());
        if (oldCardColor != recentBean.getCardColor()){
            updateDirImg();
        }
        //出牌逻辑
        switch (card.getCardValue()){
            case DRAW2:
                Array<Card> cards = deskCardGroup.sendCard(2);
                UserGroup userGroupTemp = utils.nextTempPlayer();
                createCard(0,userGroupTemp,cards);
                utils.nextPlayer();
                break;
            case REV:
                UnoConfig.DIR =
                        UnoConfig.DIR==UnoConfig.DIR_LEFT ?
                                UnoConfig.DIR_RIGHT:UnoConfig.DIR_LEFT;
                updateDirImg();
                break;
            case SKIP:
                utils.nextPlayer();
                break;
            case WILD:
                showDialog(new SelectColorDialog(recentBean,new SignListener(){
                    @Override
                    public void sign(Object object) {
                        super.sign(object);
                        updateDirImg();
                    }
                }));
                break;
            case WILD_DRAW4:
                showDialog(new SelectColorDialog(recentBean, new SignListener(){
                    @Override
                    public void sign(Object object) {
                        super.sign(object);
                        updateDirImg();
                        Array<Card> cards1 = deskCardGroup.sendCard(4);
                        createCard(0,utils.currentPlayer(),cards1);
                        utils.nextPlayer();
                    }
                }));
                break;
        }
        UserGroup currentPlayer = utils.currentPlayer();
        if (currentPlayer.getAplayer().getCards().size == 0) {
            showDialog(new SuccessDialog());
            return;
        }
        utils.nextPlayer();
    }
}
