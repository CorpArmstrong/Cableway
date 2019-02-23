package com.itstep.cableway.ports.events;

public class RFIDCashierEvent {

    private int cardKey;

    public RFIDCashierEvent(int cardKey) {
        this.cardKey = cardKey;
    }

    public int getCardKey() {
        return cardKey;
    }
}
