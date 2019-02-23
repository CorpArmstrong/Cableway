package com.itstep.cableway.ports;

import com.itstep.cableway.cableways.CablewaysManager;
import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;

public class DeviceManager {

    // Количество портов по умолчанию
    private final static int NUMBER_PORTS = 2;
    private static final Logger log = LogManager.getLogger(DeviceManager.class);

    private CustomH2TableAdapter customH2TableAdapter;
    private PortController[] portControllers;

    private CablewaysManager cablewaysManager;

    private RFIDReaderCashier rfidReaderCashier;

    // Флаг demoState будет использован для индикации в главном окне.
    private boolean demoState;

    // Флаг, указывающий что в компьютере не найдено ни одного физического порта
    private boolean isNoPortsAvailable;

    public DeviceManager(CustomH2TableAdapter customH2TableAdapter, CablewaysManager cablewaysManager) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.cablewaysManager = cablewaysManager;
        this.rfidReaderCashier = cablewaysManager.getRfidReaderCashier();

        this.demoState = false;
        this.isNoPortsAvailable = false;
    }

    public void initialize() {

        if (!PortController.checkPorts()) {
            JOptionPane.showMessageDialog(null, "Ошибка! Не определяются порты!\n" +
                    "Проверьте подключения и драйвера! Программа работает в автономном режиме.",
                    "Предупреждение", JOptionPane.ERROR_MESSAGE);

            isNoPortsAvailable = true;
        }

        int numPorts = customH2TableAdapter.getCablewayDataHibernateDao().calculateNumCableways();

        // Если в базе данных отсутствуют сущности канатных дорог
        // Например первый запуск программы
        if (numPorts == 0) {
            numPorts = NUMBER_PORTS;
        }

        portControllers = new PortController[numPorts];
        RFIDReader[] rfidReaders = new RFIDReader[portControllers.length];

        for (int i = 0; i < rfidReaders.length; i++) {
            rfidReaders[i] = new RFIDReader(customH2TableAdapter);
        }

        cablewaysManager.setRfidReaders(rfidReaders);


        for (int i = 0; i < portControllers.length; i++) {
            portControllers[i] = new PortController(this, customH2TableAdapter, cablewaysManager);

            rfidReaders[i].setRfidReaderEventListener(portControllers[i]);
            rfidReaderCashier.setRfidCashierEventListener(portControllers[i]);
            portControllers[i].setRfidCashierReverseEventListener(cablewaysManager);
        }

        cablewaysManager.setCableways();

        int unusedPortsCounter = 0;

        for (PortController portController : portControllers) {
            if (!portController.isPortReady()) {
                ++unusedPortsCounter;
            }

            portController.startUpCableway();
        }

        if (unusedPortsCounter == portControllers.length) {
            JOptionPane.showMessageDialog(null, "Ошибка! Не возможно подключится к портам!",
                    "Предупреждение", JOptionPane.ERROR_MESSAGE);
            demoState = true;
        }
    }

    public void destroy() {

        for (int i = 0; i < portControllers.length; i++) {
            portControllers[i].closePort();
            log.info(DateController.now() + " Порт №" + (i+1) + " закрыт.");
        }

        portControllers = null;
        //JOptionPane.showMessageDialog(null, "All ports closed!", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        log.info("All ports closed!");
    }

    public boolean isDemoState() {
        return demoState;
    }

    public boolean isNoPortsAvailable() {
        return isNoPortsAvailable;
    }
}