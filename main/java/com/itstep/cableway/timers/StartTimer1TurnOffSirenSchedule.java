package com.itstep.cableway.timers;

import com.itstep.cableway.cableways.Cableway;

import javax.swing.*;
import java.util.Timer;

public class StartTimer1TurnOffSirenSchedule extends AbstractSchedule {

    private Cableway cableway;

    public StartTimer1TurnOffSirenSchedule(Timer timer, Cableway cableway) {
        super(timer);
        this.cableway = cableway;
    }

    @Override
    protected void operation() {
        //JOptionPane.showMessageDialog(null, "In timer TurnOffSiren 1.", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        cableway.turnOffSiren();
    }

    @Override
    public void run() {
        super.doTick();
    }
}
