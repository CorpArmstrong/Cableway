package com.itstep.cableway.gui.tables;

import com.itstep.cableway.cableways.CablewaysManager;
import com.itstep.cableway.utils.CurrentCableway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CablewaysTableModel extends AbstractTableModel {

    private static final Logger log = LogManager.getLogger(CablewaysTableModel.class);

    private CablewaysManager cablewaysManager;
    private List<CurrentCableway> currentCableways;

    public CablewaysTableModel(CablewaysManager cablewaysManager) {
        super();
        this.cablewaysManager = cablewaysManager;
        currentCableways = cablewaysManager.getCurrentCablewaysState();
    }

    @Override
    public int getRowCount() {
        return currentCableways.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        CurrentCableway currentCableway = null;

        if (currentCableways != null) {
            currentCableway = currentCableways.get(rowIndex);
        } else {
            log.error("NULL in current cableways.");
        }

        String currentTime = "-";
        String subscriptionType = "-";
        String currentCardKey = "-";

        if (currentCableway != null) {
            if (currentCableway.getCurrentTime() == 0) {
                currentTime = "0 : 00";
            } else {
                currentTime = currentCableway.getCurrentTime() / 60 + " : " + currentCableway.getCurrentTime() % 60;
            }

            if (currentCableway.isCurrentIsSubscription()) {
                subscriptionType = String.valueOf(currentCableway.getCurrentSubscriptionType());
            }

            if (currentCableway.getCurrentCardKey() != 0) {
                currentCardKey = String.valueOf(currentCableway.getCurrentCardKey());
            }

        } else {
            log.error("NULL in current cableway!");
        }

        assert currentCableway != null;
        Object[] values = new Object[] {

                currentCableway.getCurrentCablewayNumber(),
                currentTime,
                currentCardKey,
                currentCableway.getCurrentSurname(),
                subscriptionType,
                currentCableway.isCurrentIsSubscription()
        };

        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {

        String[] columnNames = new String[] {

                "<html>№<br>Канатки<br></html>",
                "<html>Время</html>",
                "<html>№ Браслета<br></html>",
                "<html>Фамилия<br></html>",
                "<html>№<br>Абонемента<br></html>",
                "<html>Абонемент<br></html>"
        };

        return columnNames[column];
    }

    Class[] columnTypes = new Class[] {
            String.class, String.class, String.class, String.class, String.class, Boolean.class
    };

    public Class getColumnClass(int columnIndex) {
        return columnTypes[columnIndex];
    }
}
