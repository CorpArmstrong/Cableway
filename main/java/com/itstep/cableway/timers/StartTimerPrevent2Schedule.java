package com.itstep.cableway.timers;

import com.itstep.cableway.cableways.Cableway;

import java.util.Timer;

public class StartTimerPrevent2Schedule extends AbstractSchedule {

    private Cableway cableway;

    public StartTimerPrevent2Schedule (Timer timer, Cableway cableway) {
        super(timer);
        this.cableway = cableway;
    }

    @Override
    protected void operation() {
        cableway.setCodeCardPrevent2(-1);
    }

    @Override
    public void run() {
        super.doTick();
    }
}
