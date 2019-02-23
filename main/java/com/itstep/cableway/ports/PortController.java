package com.itstep.cableway.ports;

import java.io.*;
import java.util.*;
import java.util.List;

import com.itstep.cableway.cableways.*;
import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.demonstration.sound.SoundUtils;
import com.itstep.cableway.ports.events.*;
import gnu.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.itstep.cableway.ports.SimulatedPortCommands.*;

public class PortController implements SerialPortEventListener, ComPortCommandListener, RFIDReaderEventListener, RFIDCashierEventListener {

    private static final Logger log = LogManager.getLogger(PortController.class);

    private RFIDCashierReverseEventListener rfidCashierReverseEventListener;

    private static CommPortIdentifier commPortIdentifier;

    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;

    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;

    private SerialPort serialPort = null;
    private CommPort commPort = null;

    private static List<String> commList = new ArrayList<String>();

    private InputStream inputStream;
    private OutputStream outputStream;

    private Cableway cableway;
    private Thread cablewayThread;

    private boolean isPortReady;

    private int cardKey;

    // ТАБЛИЦЫ для 4-х битного РАСЧЕТА CRC
    private byte[] CRC_Table_High = {
            (byte) 0x00, (byte) 0x10, (byte) 0x20, (byte) 0x30,
            (byte) 0x40, (byte) 0x50, (byte) 0x60, (byte) 0x70,
            (byte) 0x81, (byte) 0x91, (byte) 0xA1, (byte) 0xB1,
            (byte) 0xC1, (byte) 0xD1, (byte) 0xE1, (byte) 0xF1
    };

    private byte[] CRC_Table_Low = {
            (byte) 0x00, (byte) 0x21, (byte) 0x42, (byte) 0x63,
            (byte) 0x84, (byte) 0xA5, (byte) 0xC6, (byte) 0xE7,
            (byte) 0x08, (byte) 0x29, (byte) 0x4A, (byte) 0x6B,
            (byte) 0x8C, (byte) 0xAD, (byte) 0xCE, (byte) 0xEF
    };

    private byte CRC_High = (byte) 0xFF;
    private byte CRC_Low = (byte) 0xFF;

    public PortController(DeviceManager deviceManager, CustomH2TableAdapter customH2TableAdapter, CablewaysManager cablewaysManager) {

        cardKey = 0;
        isPortReady = false;

        if (initialize() && !deviceManager.isNoPortsAvailable()) {
            createPort();
        }

        // Зарегистрировать канатку.
        CablewaysManager.numCableways++;

        int numCableway = CablewaysManager.numCableways;

        cableway = new Cableway(customH2TableAdapter, numCableway);

        //JOptionPane.showMessageDialog(null, "Created cableway. № " + numCableway, "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        log.info("Created cableway. № " + numCableway);

        cableway.setComPortCommandListener(this);
        cableway.setCablewayListener(cablewaysManager);

        cablewayThread = new Thread(cableway);
    }

    public void startUpCableway() {
        cablewayThread.start();
    }

    private void dispose() {
        if (cablewayThread != null) {
            cablewayThread.interrupt();
        }
    }

    // ТАБЛИЧНЫЙ РАСЧЕТ
    private void CRC_Update (byte mByte) {
        byte n = (byte)(CRC_High >> 4);
        n ^= mByte;
        CRC_High = (byte) ((CRC_High << 4) | (CRC_Low >> 4));
        CRC_Low <<= 4;
        CRC_High ^= CRC_Table_High [n];
        CRC_Low ^= CRC_Table_Low [n];
    }

    // РАСЧЕТ CRC ТЕКУЩЕГО БАЙТА
    private void Calc_CRC (byte mByte) {
        CRC_Update ((byte)(mByte >> 4));
        CRC_Update ((byte)(mByte & 0x0F));
    }

    public static boolean checkPorts() {
        boolean result = false;

        Enumeration ports = CommPortIdentifier.getPortIdentifiers();

        while(ports.hasMoreElements()) {
            CommPortIdentifier portId = (CommPortIdentifier) ports.nextElement();

            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(portId.getName());
                commList.add(portId.getName());
            }
        }

        try {
            if (commList.isEmpty()) {
                log.error("No ports available");
            } else {
                result = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        //JOptionPane.showMessageDialog(null, "Num ports: " + commList.size(), "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        log.info("Num ports: " + commList.size());

        return result;
    }

    private boolean initialize() {
        boolean result = false;

        try {
            // Double check.
            commPortIdentifier = CommPortIdentifier.getPortIdentifier(commList.get(0));
            result = true;
        } catch (NoSuchPortException e) {
            log.error(e.getMessage());
        } finally {
            return result;
        }
    }

    private void createPort() {
        try {
            commPort = commPortIdentifier.open("ComPort1", TIME_OUT);
            serialPort = (SerialPort) commPort;

            //System.out.println("Initialising com1 port..");
            log.info("Initialising com1 port..");
        } catch (PortInUseException e) {
            log.error(e.getMessage());
        }

        try {
            // This flags is set to "true" by default.
            // serialPort.setDTR(true);
            // serialPort.setRTS(true);

            try {
                Thread.sleep(200);

                serialPort.setDTR(false);
                serialPort.setRTS(false);

                Thread.sleep(200);

                serialPort.setRTS(true);
                serialPort.setRTS(true);

                Thread.sleep(200);

                serialPort.setDTR(false);
                serialPort.setRTS(false);

                Thread.sleep(200);

                serialPort.setRTS(true);
                serialPort.setRTS(true);

                Thread.sleep(200);

                serialPort.setDTR(false);
                serialPort.setRTS(false);

            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }

            //JOptionPane.showMessageDialog(null, "Port created!", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
            log.info("Port created!");

            isPortReady = true;

            // utf-8 encoding is used by default!
            // port1.disableReceiveTimeout();

            serialPort.enableReceiveThreshold(0);
            serialPort.enableReceiveTimeout(5000);

            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        } catch (UnsupportedCommOperationException e) {
            log.error(e.getMessage());
        } catch (NullPointerException exception) {
            log.error("Port is not available!");
            //JOptionPane.showMessageDialog(null, "Port is not available!", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        }

        try {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public synchronized void closePort() {
        if (serialPort != null) {

            dispose();

            serialPort.removeEventListener();
            serialPort.close();

            log.info("Port closed");
            //System.out.println("Port closed");
        }
    }

    private synchronized String readPort() {
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        String myStr = null;

        try {

            byte singleData;
            boolean finished = false;

            List<Byte> readBuffer = new ArrayList<Byte>();

            while (!finished) {
                singleData = (byte) inputStream.read();

                if (singleData != -1) {
                    readBuffer.add(singleData);
                } else {

                    byte[] arr = new byte[readBuffer.size()];

                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = readBuffer.get(i);
                    }

                    myStr = new String(arr);
                    finished = true;
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            cleanBufferPort();

            try {
                inputStream.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }

            return myStr;
        }
    }

    //-------------------------------------------------------------------
    // Simulated cableway commands
    //-------------------------------------------------------------------
    public synchronized void startCableway() {
        real_writePort((byte) 0x01, (byte) 0x01, (byte) 0x01);
    }

    public synchronized void stopCableway() {
        real_writePort((byte) 0x02, (byte) 0x02, (byte) 0x02);
    }

    public synchronized void turnOnSiren() {
        real_writePort((byte) 0x03, (byte) 0x03, (byte) 0x03);
    }

    public synchronized void turnOffSiren() {
        real_writePort((byte) 0x04, (byte) 0x04, (byte) 0x04);
    }

    // packetByteCount = N in WBUS protocol
    public synchronized void real_writePort(byte packetByteCount, byte command, byte data) {
        cleanBufferPort();
        byte[] writeBuffer = new byte[] {0x3A, 0x00, 0x00, 0x00, packetByteCount, command, data, 0x00, 0x00};

        for (int i = 0; i < (5 + writeBuffer[4]); i++) {
            Calc_CRC(writeBuffer[i]);
        }

        writeBuffer[5 + writeBuffer[4]] = CRC_Low;
        writeBuffer[(5 + writeBuffer[4]) + 1] = CRC_High;

        CRC_High = (byte) 0xFF;
        CRC_Low = (byte) 0xFF;

        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(writeBuffer, 0, (7 + writeBuffer[4]));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public synchronized void simulated_writePort(String str) {
        try {
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

        try {
            bufferedWriter.write(str);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        try {
            bufferedWriter.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public void demonstrateReadLED() {

        // RTS - is green.  (WorkCableway).
        // DTR - is red.    (Siren).

        try {
            serialPort.setDTR(true);
            Thread.sleep(500);

            serialPort.setRTS(true);
            serialPort.setDTR(false);
            Thread.sleep(500);

            serialPort.setDTR(true);
            serialPort.setRTS(false);
            Thread.sleep(500);

            serialPort.setDTR(false);
            serialPort.setRTS(true);
            Thread.sleep(500);

            serialPort.setRTS(false);

        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public synchronized void cleanBufferPort() {
        serialPort.setInputBufferSize(0);
        serialPort.setOutputBufferSize(0);
    }

    @Override
    public synchronized void serialEvent(SerialPortEvent serialPortEvent) {
        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

            String portAnswer = readPort();

            //JOptionPane.showMessageDialog(null, "Serial event readPort: " + portAnswer, "Info", JOptionPane.ERROR_MESSAGE);

            // letter "r" - means event from RFIDReader.
            if (portAnswer.startsWith("r")) {
                String newStr = portAnswer.replace("r", "");
                cardKey = Integer.valueOf(newStr);
                //JOptionPane.showMessageDialog(null, "Serial event from reader card key: " + cardKey, "Info", JOptionPane.ERROR_MESSAGE);
                //System.out.println("Serial event from reader card key: " + cardKey);
                log.info("Serial event from reader card key: " + cardKey);

                cableway.setCardKey(cardKey);
                return;
            }

            // letter "c" - means event from RFIDReaderCashier.
            if (portAnswer.startsWith("c")) {
                String newStr = portAnswer.replace("c", "");
                cardKey = Integer.valueOf(newStr);
                //JOptionPane.showMessageDialog(null, "Serial event from cashier card key: " + cardKey, "Info", JOptionPane.ERROR_MESSAGE);
                //System.out.println("Serial event from cashier card key: " + cardKey);
                log.info("Serial event from cashier card key: " + cardKey);

                Customer customer = new Customer();
                customer.setCardKey(cardKey);
                fireRFIDCashierReverseEvent(new RFIDCashierReverseEvent(customer));
                return;
            }

            // If we're here, that means there is no letter in port string
            // Here goes port commands:

            int portCommand;

            try {
                portCommand = Integer.valueOf(portAnswer);
            } catch (NumberFormatException e) {
                portAnswer = portAnswer.substring(1);
                //JOptionPane.showMessageDialog(null, "Exception port answer string: " + portAnswer, "Info", JOptionPane.ERROR_MESSAGE);
                //System.out.println("Exception port answer string: " + portAnswer);
                log.error("Exception port answer string: " + portAnswer);
                portCommand = Integer.valueOf(portAnswer);
            } finally {
                cleanBufferPort();
            }

            switch (portCommand) {
                case ANSWER_START_CABLEWAY:

                    serialPort.setRTS(true);

                    break;
                case ANSWER_STOP_CABLEWAY:

                    serialPort.setRTS(false);

                    break;
                case ANSWER_TURN_ON_SIREN:

                    serialPort.setDTR(true);

                    try {
                        SoundUtils.main(null);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case ANSWER_TURN_OFF_SIREN:

                    serialPort.setDTR(false);

                    break;
            }
        }
    }

    @Override
    public void commandPerformed(ComPortCommandEvent comPortCommandEvent) {

        //JOptionPane.showMessageDialog(null, "ComPortCommandEvent: " + comPortCommandEvent.getCurrentCommand(), "Info", JOptionPane.ERROR_MESSAGE);

        log.error("ComPortCommandEvent: " + comPortCommandEvent.getCurrentCommand());
        //System.out.println("ComPortCommandEvent: " + comPortCommandEvent.getCurrentCommand());

        switch (comPortCommandEvent.getCurrentCommand()) {
            case START_CABLEWAY:
                simulated_writePort(String.valueOf(ANSWER_START_CABLEWAY));
                break;
            case STOP_CABLEWAY:
                simulated_writePort(String.valueOf(ANSWER_STOP_CABLEWAY));
                break;
            case TURN_ON_SIREN:
                simulated_writePort(String.valueOf(ANSWER_TURN_ON_SIREN));
                break;
            case TURN_OFF_SIREN:
                simulated_writePort(String.valueOf(ANSWER_TURN_OFF_SIREN));
                break;
        }
    }

    @Override
    public void commandPerformed(RFIDReaderEvent rfidReaderEvent) {
        //JOptionPane.showMessageDialog(null, "Event in reader!", "Info", JOptionPane.ERROR_MESSAGE);
        log.info("Event in reader!");

        // Using direct access to cableway here without port, because answer from port is longer than
        // delay_use_card timeout (if we're considered using double time ride).

        //simulated_writePort("r" + rfidReaderEvent.getCardKey());
        cableway.setCardKey(rfidReaderEvent.getCardKey());
        demonstrateReadLED();
    }

    @Override
    public void commandPerformed(RFIDCashierEvent rfidCashierEvent) {
        //JOptionPane.showMessageDialog(null, "Event in cashier!", "Info", JOptionPane.ERROR_MESSAGE);
        log.info("Event in cashier!");
        simulated_writePort("c" + rfidCashierEvent.getCardKey());
        demonstrateReadLED();
    }

    public void setRfidCashierReverseEventListener(RFIDCashierReverseEventListener rfidCashierReverseEventListener) {
        this.rfidCashierReverseEventListener = rfidCashierReverseEventListener;
    }

    public void fireRFIDCashierReverseEvent(RFIDCashierReverseEvent rfidCashierReverseEvent) {
        if (rfidCashierReverseEventListener != null) {
            rfidCashierReverseEventListener.commandPerformed(rfidCashierReverseEvent);
        }
    }

    public boolean isPortReady() {
        return isPortReady;
    }
}
