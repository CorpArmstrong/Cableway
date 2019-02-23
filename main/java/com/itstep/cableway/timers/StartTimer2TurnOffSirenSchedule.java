package com.itstep.cableway.timers;

import com.itstep.cableway.cableways.Cableway;

import javax.swing.*;
import java.util.Timer;

public class StartTimer2TurnOffSirenSchedule extends AbstractSchedule {

    private Cableway cableway;

    public StartTimer2TurnOffSirenSchedule(Timer timer, Cableway cableway) {
        super(timer);
        this.cableway = cableway;
    }

    @Override
    protected void operation() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //JOptionPane.showMessageDialog(null, "In TimerTurnOffSiren 2.", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        cableway.turnOffSiren();
    }

    @Override
    public void run() {
        super.doTick();
    }
}
