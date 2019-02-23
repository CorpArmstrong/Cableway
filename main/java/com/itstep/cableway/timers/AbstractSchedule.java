package com.itstep.cableway.timers;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractSchedule extends TimerTask {

    private Timer timer;
    private int count;

    public AbstractSchedule(Timer timer) {
        this.timer = timer;
        this.count = 0;
    }

    protected abstract void operation();

    public void doTick() {

        if (count >= 1) {
            operation();

            timer.cancel();
            timer.purge();
            timer = null;
        }

        while (count <= 0) {
            ++count;
        }
    }

    @Override
    public void run() {
        doTick();
    }
}
