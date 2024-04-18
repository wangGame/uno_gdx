package kw.test.uno.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.kw.gdx.listener.OrdinaryButtonListener;
import com.kw.gdx.resource.annotation.ScreenResource;
import com.kw.gdx.view.dialog.base.BaseDialog;

import kw.test.uno.bean.RecentBean;
import kw.test.uno.data.CardColor;

@ScreenResource("cocos/SelectColorDialog.json")
public class SelectColorDialog extends BaseDialog {
    private RecentBean recentBean;
    public SelectColorDialog(RecentBean recentBean){
        this.recentBean = recentBean;
    }

    @Override
    public void show() {
        super.show();
        Actor rBtn = dialogGroup.findActor("rBtn");
        rBtn.setTouchable(Touchable.enabled);
        rBtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                recentBean.setCardColor(CardColor.RED);
                dialogManager.closeDialog(SelectColorDialog.this);
            }
        });
        Actor bBtn = dialogGroup.findActor("bBtn");
        bBtn.setTouchable(Touchable.enabled);
        bBtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                recentBean.setCardColor(CardColor.BLUE);
                dialogManager.closeDialog(SelectColorDialog.this);
            }
        });
        Actor gBtn = dialogGroup.findActor("gBtn");
        gBtn.setTouchable(Touchable.enabled);
        gBtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                recentBean.setCardColor(CardColor.GREEN);
                dialogManager.closeDialog(SelectColorDialog.this);
            }
        });
        Actor yBtn = dialogGroup.findActor("yBtn");
        yBtn.setTouchable(Touchable.enabled);
        yBtn.addListener(new OrdinaryButtonListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                recentBean.setCardColor(CardColor.YELLOW);
                dialogManager.closeDialog(SelectColorDialog.this);
            }
        });
    }
}
