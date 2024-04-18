package kw.test.uno;

import com.badlogic.gdx.Gdx;
import com.kw.gdx.BaseGame;
import com.kw.gdx.resource.annotation.GameInfo;

import kw.test.uno.screen.LoadingScreen;

@GameInfo(width = 1920 ,height = 1080)
public class UnoGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        Gdx.isJiami = true;
        setScreen(new LoadingScreen(this));
    }
}
