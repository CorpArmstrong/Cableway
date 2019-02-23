package com.itstep.cableway.cableways.events;

public class CablewayEvent {
    private int cablewayNumber;

    private int time;

    private int cardKey;

    private String surname;

    private int subscriptionType;

    private boolean isSubscription;

    private int codeCardPrevent1;
    private int codeCardPrevent2;

    public CablewayEvent(int cablewayNumber, int time, int cardKey, String surname, int subscriptionType, boolean subscription, int codeCardPrevent1, int codeCardPrevent2) {
        this.cablewayNumber = cablewayNumber;
        this.time = time;
        this.cardKey = cardKey;
        this.surname = surname;
        this.subscriptionType = subscriptionType;
        isSubscription = subscription;
        this.codeCardPrevent1 = codeCardPrevent1;
        this.codeCardPrevent2 = codeCardPrevent2;
    }

    public int getCablewayNumber() {
        return cablewayNumber;
    }

    public void setCablewayNumber(int cablewayNumber) {
        this.cablewayNumber = cablewayNumber;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCardKey() {
        return cardKey;
    }

    public void setCardKey(int cardKey) {
        this.cardKey = cardKey;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(int subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public boolean isSubscription() {
        return isSubscription;
    }

    public void setSubscription(boolean subscription) {
        isSubscription = subscription;
    }

    public int getCodeCardPrevent1() {
        return codeCardPrevent1;
    }

    public void setCodeCardPrevent1(int codeCardPrevent1) {
        this.codeCardPrevent1 = codeCardPrevent1;
    }

    public int getCodeCardPrevent2() {
        return codeCardPrevent2;
    }

    public void setCodeCardPrevent2(int codeCardPrevent2) {
        this.codeCardPrevent2 = codeCardPrevent2;
    }
}
