package kw.test.uno.utils;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import kw.test.uno.bean.RecentBean;
import kw.test.uno.contant.UnoConfig;
import kw.test.uno.data.Card;
import kw.test.uno.data.CardColor;
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
        if (UnoConfig.lastUserIndex == 0) {
            userGroup.setTouchable(Touchable.enabled);
        }
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
            UnoConfig.lastUserIndex = (++UnoConfig.lastUserIndex+aplayers.size) % aplayers.size;
        }else {
            UnoConfig.lastUserIndex = (--UnoConfig.lastUserIndex+aplayers.size) % aplayers.size;
        }
        currentPlayer();
    }

    public UserGroup nextTempPlayer() {
        int userIndex = UnoConfig.lastUserIndex;
        if (UnoConfig.DIR == UnoConfig.DIR_LEFT) {
            userIndex = (++userIndex+aplayers.size) % aplayers.size;
        }else {
            userIndex = (--userIndex+aplayers.size) % aplayers.size;
        }
        return aplayers.get(userIndex);
    }

    public String dirDirName(RecentBean recentBean){
        if (UnoConfig.DIR == UnoConfig.DIR_LEFT){
            if (recentBean.getCardColor() == CardColor.BLUE){
                return "refresh_icon_blue_anticlock.png";
            }else if (recentBean.getCardColor() == CardColor.GREEN){
                return "refresh_icon_green_anticlock.png";
            }else if (recentBean.getCardColor() == CardColor.RED){
                return "refresh_icon_red_anticlock.png";
            }else if (recentBean.getCardColor() == CardColor.YELLOW){
                return "refresh_icon_yellow_anticlock.png";
            }
        }else {
            if (recentBean.getCardColor() == CardColor.BLUE){
                return "refresh_icon_blue.png";
            }else if (recentBean.getCardColor() == CardColor.GREEN){
                return "refresh_icon_green.png";
            }else if (recentBean.getCardColor() == CardColor.RED){
                return "refresh_icon_red.png";
            }else if (recentBean.getCardColor() == CardColor.BLUE){
                return "refresh_icon_yellow.png";
            }
        }
        return "refresh_icon_wild_anticlock.png";
    }

    public UserGroup prevTempPlayer() {
        int userIndex = UnoConfig.lastUserIndex;
        if (UnoConfig.DIR == UnoConfig.DIR_RIGHT) {
            userIndex = (++userIndex+aplayers.size) % aplayers.size;
        }else {
            userIndex = (--userIndex+aplayers.size) % aplayers.size;
        }
        return aplayers.get(userIndex);
    }
}
