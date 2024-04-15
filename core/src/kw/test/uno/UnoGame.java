package kw.test.uno;

import com.kw.gdx.BaseGame;
import com.kw.gdx.resource.annotation.GameInfo;

import kw.test.uno.screen.LoadingScreen;

@GameInfo(width = 1280 ,height = 720)
public class UnoGame extends BaseGame {
    @Override
    public void create() {
        super.create();
        setScreen(new LoadingScreen(this));
    }
}
