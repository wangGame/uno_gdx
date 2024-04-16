package com.github.hikari_toyama.unocard.core;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class Constant {
    /**
     * Your player ID.
     */
    public static final int YOU = 0;

    /**
     * WEST's player ID.
     */
    public static final int COM1 = 1;

    /**
     * NORTH's player ID.
     */
    public static final int COM2 = 2;

    /**
     * EAST's player ID.
     */
    public static final int COM3 = 3;

    /**
     * Easy level ID.
     */
    public static final int LV_EASY = 0;

    /**
     * Hard level ID.
     */
    public static final int LV_HARD = 1;

    /**
     * Direction value (clockwise).
     */
    public static final int DIR_LEFT = 1;

    /**
     * Direction value (counter-clockwise).
     */
    public static final int DIR_RIGHT = 3;

    /**
     * In this application, everyone can hold 26 cards at most.
     */
    public static final int MAX_HOLD_CARDS = 26;

    static final Random RNG = new Random();

    static final String TAG = "Uno";

    static final Map<Character, Integer> CHAR_MAP = new TreeMap<>();

    static {
        char[] hanZi = new char[]{
                '一', '上', '下', '东', '为', '乐', '人', '仍',
                '从', '令', '以', '传', '余', '你', '保', '再',
                '准', '出', '击', '分', '则', '到', '剩', '功',
                '加', '北', '南', '发', '变', '叠', '可', '合',
                '向', '否', '和', '回', '堆', '备', '多', '失',
                '始', '定', '家', '将', '已', '度', '开', '张',
                '戏', '成', '或', '战', '所', '打', '托', '择',
                '指', '挑', '换', '接', '摸', '改', '效', '数',
                '新', '方', '无', '时', '是', '最', '有', '来',
                '标', '次', '欢', '法', '游', '点', '牌', '留',
                '的', '目', '管', '红', '给', '绿', '置', '色',
                '蓝', '被', '西', '规', '认', '设', '败', '跳',
                '过', '迎', '选', '重', '难', '音', '颜', '黄'
        }; // new char[]{}

        for (int i = 0x20; i <= 0x7f; ++i) {
            int r = (i >>> 4) - 2, c = i & 0x0f;
            CHAR_MAP.put((char) i, (r << 4) | c);
        } // for (int i = 0x20; i <= 0x7f; ++i)

        for (int i = 0; i < hanZi.length; ++i) {
            int r = (i >>> 3) + 6, c = i & 0x07;
            CHAR_MAP.put(hanZi[i], (r << 4) | c);
        } // for (int i = 0; i < hanZi.length; ++i)
    } // static

}
