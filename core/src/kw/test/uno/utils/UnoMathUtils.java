package kw.test.uno.utils;

import com.kw.gdx.constant.Constant;

public class UnoMathUtils {
    public static float bgScale(){
        return Math.max(Constant.GAMEWIDTH/Constant.WIDTH,Constant.GAMEHIGHT/Constant.HIGHT);
    }
}
