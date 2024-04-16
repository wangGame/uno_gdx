package com.github.hikari_toyama.unocard.core;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.kw.gdx.asset.Asset;

public class Card extends Group implements Comparable<Card>{
    private static final String[] CardColor = {
            "",
            "[R]",
            "[B]",
            "[G]",
            "[Y]"
    };

    private static final String[] CardValue = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "+2", "Reverse", "Skip", "Wild", "Wild +4"
    };

    public CardColorEnum cardColorEnum;
    private Image image;
    private Image darkImage;
    private Content content;
    private String name; //"[B]3"
    private int id; //13 * (content-1) + content

    public Card(String imgName,String darkName,CardColorEnum cardColor,Content content){
        this.image = new Image(Asset.getAsset().getTexture(imgName));
        this.darkImage = new Image(Asset.getAsset().getTexture(darkName));
        this.cardColorEnum = cardColor;
        this.content = content;
        this.name = CardColor[cardColor.ordinal()]+CardValue[content.ordinal()];
        this.id = isWild()
                ? 39 + content.ordinal()
                : 13 * (cardColorEnum.ordinal() - 1) + content.ordinal();
    }

    public boolean isWild() {
        return cardColorEnum == CardColorEnum.NONE;
    }

    @Override
    public int compareTo(Card card) {
        return this.id - card.id;
    }

    public Content getContent() {
        return content;
    }
}
