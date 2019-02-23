package com.itstep.cableway.timers;

import com.itstep.cableway.cableways.Cableway;

import java.util.Timer;

public class StartTimerPrevent1Schedule extends AbstractSchedule {

    private Cableway cableway;

    public StartTimerPrevent1Schedule (Timer timer, Cableway cableway) {
        super(timer);
        this.cableway = cableway;
    }

    @Override
    protected void operation() {
        cableway.setCodeCardPrevent1(-1);
    }

    @Override
    public void run() {
        super.doTick();
    }
}
