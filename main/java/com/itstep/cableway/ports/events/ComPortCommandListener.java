package com.itstep.cableway.ports.events;

public interface ComPortCommandListener {
    void commandPerformed(ComPortCommandEvent comPortCommandEvent);
}
