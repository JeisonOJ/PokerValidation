package com.poker.TexasHoldem.utils;

public enum HandType {
    HIGH_CARD("HighCard"), // ORDINAL 0
    ONE_PAIR("OnePair"), // ORDINAL 1
    TWO_PAIR("TwoPair"), // ORDINAL 2
    THREE_OF_A_KIND("ThreeOfAKind"),  // ORDINAL 3
    STRAIGHT("Straight"); // ORDINAL 4

    private String handType;

    HandType(String handType) {
        this.handType = handType;
    }

    public String getHandType() {
        return handType;
    }
}
