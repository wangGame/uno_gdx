package kw.test.uno.data;

import com.badlogic.gdx.utils.Array;
import com.github.hikari_toyama.unocard.core.CardColorEnum;
import com.github.hikari_toyama.unocard.core.Content;

import kw.test.uno.contant.UnoConfig;

public class UnoCardData {
    public Array<Card> deskCard;
    public UnoCardData(){
        deskCard = new Array<>();
    }

    public void initDeskCard(){
        CardValue[] cardValues = CardValue.values();
        CardColor[] cardColors = CardColor.values();
        //0-9
        for (int i = 0; i < 10; i++) {
            for (int i1 = 1; i1 <= 4; i1++) {
//                Card card = new Card();
//                System.out.println(cardValues[i].name() + "   "+cardColors[i1].name());
                deskCard.add(new Card(cardValues[i],cardColors[i1]));
            }
        }
        //1-9
        for (int i = 1; i < 10; i++) {
            for (int i1 = 1; i1 <= 4; i1++) {
//                System.out.println(cardValues[i].name() + "   "+cardColors[i1].name());
                deskCard.add(new Card(cardValues[i],cardColors[i1]));
            }
        }
        //禁止
        for (int i = 0; i < 2; i++) {
            for (int i1 = 1; i1 <= 4; i1++) {
//                System.out.println(cardValues[12].name() + "   "+cardColors[i1].name());
                deskCard.add(new Card(cardValues[i],cardColors[i1]));
            }
        }
        //转换
        for (int i = 0; i < 2; i++) {
            for (int i1 = 1; i1 <= 4; i1++) {
//                System.out.println(cardValues[11].name() + "   "+cardColors[i1].name());
                deskCard.add(new Card(cardValues[i],cardColors[i1]));
            }
        }
        //加+2
        for (int i = 0; i < 2; i++) {
            for (int i1 = 1; i1 < 4; i1++) {
//                System.out.println(cardValues[10].name() + "   "+cardColors[i1].name());
                deskCard.add(new Card(cardValues[10],cardColors[i1]));
            }
        }
        //变色
        for (int i1 = 0; i1 < 4; i1++) {
//            System.out.println(cardValues[13].name() + "   "+cardColors[0].name());
            deskCard.add(new Card(cardValues[13],cardColors[0]));
        }
        //变色+4
        for (int i1 = 0; i1 < 4; i1++) {
//            System.out.println(cardValues[14].name() + "   "+cardColors[0].name());
            deskCard.add(new Card(cardValues[14],cardColors[0]));
        }
    }

    public void shuffle(){
        deskCard.shuffle(UnoConfig.random);
    }

    /**
     * 有时候会有两张或者更多
     * @param num
     */
    public Array<Card> sendCard(int num){
        Array<Card> cards = new Array<>();
        if (deskCard.size>num) {
            cards.add(deskCard.removeIndex(0));
        }
        return cards;
    }

    public static void main(String[] args) {
        UnoCardData data = new UnoCardData();
        data.initDeskCard();
    }
}
