package kw.test.uno.screen;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.BaseGame;
import com.kw.gdx.asset.Asset;
import com.kw.gdx.constant.Constant;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.screen.BaseScreen;

import kw.test.uno.group.BtnGroup;
import kw.test.uno.utils.UnoMathUtils;

public class MainScreen extends BaseScreen {
    public MainScreen(BaseGame game) {
        super(game);
    }

    @Override
    public void initView() {
        super.initView();
        Image bg = new Image(Asset.getAsset().getTexture("bg_welcome.png"));
        rootView.addActor(bg);
        bg.setSize(Constant.GAMEWIDTH,Constant.GAMEHIGHT);
        bg.setPosition(Constant.WIDTH/2.0f,Constant.HIGHT/2.0f, Align.center);
        float v = UnoMathUtils.bgScale();
        bg.setOrigin(Align.center);
        bg.setScale(v);
        rootView.addActor();
        BtnGroup startGame = new BtnGroup("start game");
        addActor(startGame);
        startGame.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                setScreen(GameScreen.class);
            }
        });
    }
}
