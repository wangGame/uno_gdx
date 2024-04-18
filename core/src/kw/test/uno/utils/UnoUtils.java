package kw.test.uno.utils;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;
import com.github.hikari_toyama.unocard.core.Uno;

import kw.test.uno.bean.RecentBean;
import kw.test.uno.contant.UnoConfig;
import kw.test.uno.data.Card;
import kw.test.uno.group.UserGroup;
import kw.test.uno.player.Aplayer;

public class UnoUtils {
    private Array<UserGroup> aplayers;
    public UnoUtils(Array<UserGroup> array){
        this.aplayers = array;
    }

    public UserGroup currentPlayer(){
        for (UserGroup aplayer : aplayers) {
            aplayer.setTouchable(Touchable.disabled);
        }
        UserGroup userGroup = aplayers.get(UnoConfig.lastUserIndex);
        userGroup.setTouchable(Touchable.enabled);
        return userGroup;
    }

    public boolean isLegalToPlay(RecentBean bean, Card card){
        if (bean.getCardColor() == card.getCardColor()) {
            return true;
        }else if (bean.getCardValue()==card.getCardValue()){
            return true;
        }
        return false;
    }

    public void nextPlayer() {
        if (UnoConfig.DIR == UnoConfig.DIR_LEFT) {
            UnoConfig.lastUserIndex = (++UnoConfig.lastUserIndex) % aplayers.size;
        }else {
            UnoConfig.lastUserIndex = (--UnoConfig.lastUserIndex) % aplayers.size;
        }
        currentPlayer();
    }

    public UserGroup nextTempPlayer() {
        int userIndex = UnoConfig.lastUserIndex;
        if (UnoConfig.DIR == UnoConfig.DIR_LEFT) {
            userIndex = (++userIndex) % aplayers.size;
        }else {
            userIndex = (--userIndex) % aplayers.size;
        }
        return aplayers.get(userIndex);
    }
}
