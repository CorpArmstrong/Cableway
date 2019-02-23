package com.itstep.cableway.timers;

public final class TimerConstants {

    /* sec! Время от поднесения ключа до старта дороги */
    public final static long CABLEWAY_START_DELAY = 10; // 10 sec.

    /* msec! Время звучания сирены, когда ключ первый раз поднесен и прошел проверку.*/
    public final static long TIME_ALARM_BEEP_WHEN_PRESENTATION_RFID = 300; // msec.

    public final static long TIME_DURATION_BEEP_START_STOP = 1000;

    public final static long TIMER_DELAY_USE_CARD_RFID = 2000;    // FIXED!

    public final static long TIMER_ONE_SECOND = 1000;
}
