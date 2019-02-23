package com.itstep.cableway.utils;

import com.itstep.cableway.cableways.events.CablewayEvent;

public class CurrentCableway {

    private int currentCablewayNumber;

    private int currentTime;

    private int currentCardKey;

    private String currentSurname;

    private int currentSubscriptionType;

    private boolean currentIsSubscription;

    private int codeCardPrevent1;

    private int codeCardPrevent2;

    public CurrentCableway(CablewayEvent cablewayEvent) {
        if (cablewayEvent != null) {
            currentCablewayNumber = cablewayEvent.getCablewayNumber();
            currentTime = cablewayEvent.getTime();
            currentCardKey = cablewayEvent.getCardKey();
            currentSurname = cablewayEvent.getSurname();
            currentSubscriptionType = cablewayEvent.getSubscriptionType();
            currentIsSubscription = cablewayEvent.isSubscription();
            codeCardPrevent1 = cablewayEvent.getCodeCardPrevent1();
            codeCardPrevent2 = cablewayEvent.getCodeCardPrevent2();
        }
    }

    public int getCurrentCablewayNumber() {
        return currentCablewayNumber;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getCurrentCardKey() {
        return currentCardKey;
    }

    public String getCurrentSurname() {
        return currentSurname;
    }

    public int getCurrentSubscriptionType() {
        return currentSubscriptionType;
    }

    public boolean isCurrentIsSubscription() {
        return currentIsSubscription;
    }

    public int getCodeCardPrevent1() {
        return codeCardPrevent1;
    }

    public int getCodeCardPrevent2() {
        return codeCardPrevent2;
    }
}
