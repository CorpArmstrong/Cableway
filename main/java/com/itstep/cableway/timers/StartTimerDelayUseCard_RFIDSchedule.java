package com.itstep.cableway.timers;

import com.itstep.cableway.cableways.Cableway;

import javax.swing.*;
import java.util.Timer;

public class StartTimerDelayUseCard_RFIDSchedule extends AbstractSchedule {

    private Cableway cableway;

    public StartTimerDelayUseCard_RFIDSchedule(Timer timer, Cableway cableway) {
        super(timer);
        this.cableway = cableway;
    }

    @Override
    protected void operation() {
        cableway.setFlagDelayUseCard(false);
        //JOptionPane.showMessageDialog(null, "In timer DelayUseCard", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void run() {
        super.doTick();
    }
}
