package com.itstep.cableway.cableways;

import com.itstep.cableway.cableways.events.CablewayEvent;
import com.itstep.cableway.cableways.events.CablewayListener;
import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.utils.CurrentCableway;
import com.itstep.cableway.gui.windows.MainWindow;
import com.itstep.cableway.ports.RFIDReader;
import com.itstep.cableway.ports.RFIDReaderCashier;
import com.itstep.cableway.ports.events.RFIDCashierReverseEvent;
import com.itstep.cableway.ports.events.RFIDCashierReverseEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class CablewaysManager implements CablewayListener, RFIDCashierReverseEventListener {

    private static final Logger log = LogManager.getLogger(CablewaysManager.class);

    private RFIDReaderCashier rfidReaderCashier;

    private RFIDReader[] rfidReaders;

    private CurrentCableway[] currentCableways;

    public static int numCableways;

    private JTable cablewayTable;
    private JLabel timerLabel;

    private MainWindow mainWindow;

    public CablewaysManager(RFIDReaderCashier rfidReaderCashier) {
        this.rfidReaderCashier = rfidReaderCashier;
    }

    @Override
    public void commandPerformed(CablewayEvent cablewayEvent) {
        if (cablewayEvent.getCablewayNumber() >= 0 && cablewayEvent.getCablewayNumber() <= currentCableways.length) {
            int cablewayIndex = cablewayEvent.getCablewayNumber() - 1;
            currentCableways[cablewayIndex] = new CurrentCableway(cablewayEvent);
        }

        if (cablewayTable != null) {

            cablewayTable.repaint();

            String timeValue = (String) cablewayTable.getValueAt(cablewayTable.getSelectedRow(), 1);

            timerLabel.repaint();
            timerLabel.setText(timeValue);
        }
    }

    @Override
    public void commandPerformed(RFIDCashierReverseEvent rfidCashierReverseEvent) {
        int cardKey = rfidCashierReverseEvent.getCustomer().getCardKey();
        mainWindow.runCashierWindowPresentationKey(cardKey);
    }

    public void setCablewayTable(JTable cablewayTable) {
        this.cablewayTable = cablewayTable;
    }

    public void setTimerLabel(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public List<CurrentCableway> getCurrentCablewaysState() {
        return Arrays.asList(currentCableways);
    }

    public void setCableways() {
        currentCableways = new CurrentCableway[numCableways/*+1*/];
        //JOptionPane.showMessageDialog(null, "Cableways: " + currentCableways.length, "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        log.info(DateController.now() + " Количество канатных дорог: " + currentCableways.length);
    }

    public RFIDReaderCashier getRfidReaderCashier() {
        return rfidReaderCashier;
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public RFIDReader[] getRfidReaders() {
        return rfidReaders;
    }

    public void setRfidReaders(RFIDReader[] rfidReaders) {
        this.rfidReaders = rfidReaders;
    }
}
