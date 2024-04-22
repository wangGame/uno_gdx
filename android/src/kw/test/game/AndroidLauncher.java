package kw.test.game;

import android.os.Build;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import kw.test.uno.UnoGame;


public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }

        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        //指南针
        configuration.useCompass = false;
        //加速度
        configuration.useAccelerometer = false;
        configuration.useWakelock = true;
        configuration.numSamples = 2;
        if (Build.MODEL.equals("MediaPad 10 FHD")) {
            configuration.numSamples = 0;
        }
        initialize(new UnoGame(), configuration);
    }
}