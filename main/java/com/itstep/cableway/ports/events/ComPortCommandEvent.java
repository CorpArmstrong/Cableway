package com.itstep.cableway.ports.events;

public class ComPortCommandEvent {

    private int numCableway;

    private int currentCommand;

    private byte packetByteCount;
    private byte command;
    private byte data;

    public ComPortCommandEvent(int numCableway, byte packetByteCount, byte command, byte data) {
        this.numCableway = numCableway;
        this.packetByteCount = packetByteCount;
        this.command = command;
        this.data = data;

        currentCommand = (packetByteCount + command + data) * 100;
    }

    public int getNumCableway() {
        return numCableway;
    }

    public int getCurrentCommand() {
        return currentCommand;
    }
}
