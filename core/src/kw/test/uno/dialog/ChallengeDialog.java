package kw.test.uno.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.view.dialog.base.BaseDialog;

import kw.test.uno.sign.SignListener;

@ScreenResource("cocos/ChallengeDialog.json")
public class ChallengeDialog extends BaseDialog {
    private SignListener signListener;
    public ChallengeDialog(SignListener signListener){
        this.signListener = signListener;
    }

    @Override
    public void show() {
        super.show();
        Actor yes = dialogGroup.findActor("yes");
        Actor no = dialogGroup.findActor("no");
        yes.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                signListener.sign(0);
            }
        });
        no.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                signListener.sign(1);
            }
        });
    }
}
