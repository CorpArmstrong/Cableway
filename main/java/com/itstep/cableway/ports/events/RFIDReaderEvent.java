package com.itstep.cableway.ports.events;

public class RFIDReaderEvent {

    private int cardKey;
    private int cablewayNumber;

    public RFIDReaderEvent(int cardKey, int cablewayNumber) {
        this.cardKey = cardKey;
        this.cablewayNumber = cablewayNumber;
    }

    public int getCardKey() {
        return cardKey;
    }

    public int getCablewayNumber() {
        return cablewayNumber;
    }

    public void setCablewayNumber(int cablewayNumber) {
        this.cablewayNumber = cablewayNumber;
    }
}
