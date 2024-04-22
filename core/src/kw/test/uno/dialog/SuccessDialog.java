package kw.test.uno.dialog;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.view.dialog.base.BaseDialog;

import kw.test.uno.screen.GameScreen;

@ScreenResource("cocos/SuccessScreen.json")
public class SuccessDialog extends BaseDialog {
    private GameScreen screen;
    public SuccessDialog(GameScreen gameScreen) {
        this.screen = gameScreen;
    }

    @Override
    public void show() {
        super.show();
        addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            dialogManager.closeDialog(SuccessDialog.this);
            screen.setScreen(GameScreen.class);
            }
        });
    }
}
