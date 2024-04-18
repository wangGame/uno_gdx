package kw.test.uno.contant;

import java.util.Random;

public class UnoConfig {
    /**
     * Direction value (clockwise).
     */
    public static final int DIR_LEFT = 1;

    /**
     * Direction value (counter-clockwise).
     */
    public static final int DIR_RIGHT = 3;
    public static Random random = new Random(1);
    //方向
    public static int DIR = DIR_LEFT;
    public static int lastUserIndex;
}
