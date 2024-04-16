package com.github.hikari_toyama.unocard.core;

public class App {
    public static void main(String[] args) {
        System.out.println(13 * (CardColorEnum.RED.ordinal() - 1) + Content.NUM0.ordinal());
    }
}
