package kw.test.uno.group;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.kw.gdx.asset.Asset;

public class BtnGroup extends Group {
    public BtnGroup(String txt){
        Image image = new Image(Asset.getAsset().getTexture("common/btnbg.png"));
        addActor(image);
        setSize(image.getWidth(),image.getHeight());
        Label btnName = new Label(txt,new Label.LabelStyle(){{
            font = Asset.getAsset().loadBitFont("cocos/font/frmb-40.fnt");
        }});
        addActor(btnName);
        btnName.setAlignment(Align.center);
        btnName.setPosition(getWidth()/2.0f,getHeight()/2.0f,Align.center);
    }
}
