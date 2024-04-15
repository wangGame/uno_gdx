package kw.test.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.libGdx.test.base.LibGdxTestMain;;

import kw.test.pool.UnoGame;

public class DesktopLauncher extends LibGdxTestMain {
    public void start(LibGdxTestMain test) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 0;
        config.stencil = 8;
        config.y = 0;
        config.height = 740;
        config.width = 1280;
        new LwjglApplication(new UnoGame(), config);
    }

    public static void main(String[] args) {
        DesktopLauncher launcher = new DesktopLauncher();
        launcher.start();
    }
}