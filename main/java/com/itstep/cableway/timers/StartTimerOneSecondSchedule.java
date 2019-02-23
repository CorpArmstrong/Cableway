package com.itstep.cableway.timers;

import com.itstep.cableway.cableways.Cableway;

import java.util.Timer;
import java.util.TimerTask;

public class StartTimerOneSecondSchedule extends TimerTask {

    private Cableway cableway;
    private Timer timer;

    public StartTimerOneSecondSchedule (Timer timer, Cableway cableway) {
        this.timer = timer;
        this.cableway = cableway;
    }

    public void run() {
        if (cableway != null && !cableway.isDone()) {
            cableway.checkTimeLeft();
        } else {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}
