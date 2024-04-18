package kw.test.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.libGdx.test.base.LibGdxTestMain;;
import kw.test.uno.UnoGame;

public class DesktopLauncher extends LibGdxTestMain {
    public void start(LibGdxTestMain test) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 1200;
        config.stencil = 8;
        config.y = 0;
        config.height = (int) (740/6.0f);
        config.width = (int) (1280/4.0f);
        new LwjglApplication(new UnoGame(), config);
    }

    public static void main(String[] args) {
        DesktopLauncher launcher = new DesktopLauncher();
        launcher.start();
    }
}