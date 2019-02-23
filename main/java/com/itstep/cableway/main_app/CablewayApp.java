package com.itstep.cableway.main_app;

import com.itstep.cableway.cableways.CablewaysManager;
import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.utils.H2Example;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.db.utils.HibernateUtils;
import com.itstep.cableway.gui.windows.AccessWindow;
import com.itstep.cableway.ports.DeviceManager;
import com.itstep.cableway.ports.RFIDReaderCashier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class CablewayApp {

    private static final Logger log = LogManager.getLogger(CablewayApp.class);

    public static void main(String[] args) {

        try {
            DateController.getSystemTime();

            HibernateUtils.initDb();

            CustomH2TableAdapter customH2TableAdapter = new CustomH2TableAdapter();

            H2Example h2Example = new H2Example(customH2TableAdapter);

            h2Example.processDb();

            RFIDReaderCashier rfidReaderCashier = new RFIDReaderCashier(customH2TableAdapter);

            CablewaysManager cablewaysManager = new CablewaysManager(rfidReaderCashier);

            DeviceManager deviceManager = new DeviceManager(customH2TableAdapter, cablewaysManager);
            deviceManager.initialize();

            AccessWindow.main(customH2TableAdapter, deviceManager, cablewaysManager);

            log.info(DateController.now() + " Успешный запуск программы");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Something really bad have happened.\nWe are really sorry!", "Error", JOptionPane.ERROR_MESSAGE);
            log.error(e);
        }
    }
}
