package com.itstep.cableway.ports;

import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.ports.events.RFIDReaderEvent;
import com.itstep.cableway.ports.events.RFIDReaderEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.util.Random;

public class RFIDReader {

    private static final Logger log = LogManager.getLogger(RFIDReader.class);

    private CustomH2TableAdapter customH2TableAdapter;
    private RFIDReaderEventListener rfidReaderEventListener;

    public RFIDReader(CustomH2TableAdapter customH2TableAdapter) {
        this.customH2TableAdapter = customH2TableAdapter;
    }

    public void rideFirstCustomer() {
        Customer customer = customH2TableAdapter.getCustomerHibernateDao().findById((long) 1);
        fireRFIDEvent(new RFIDReaderEvent(customer.getCardKey(), 1));
    }

    public void rideFirstCustomerDoubleTime() {
        Customer customer = customH2TableAdapter.getCustomerHibernateDao().findById((long) 1);

        fireRFIDEvent(new RFIDReaderEvent(customer.getCardKey(), 1));

        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }

        fireRFIDEvent(new RFIDReaderEvent(customer.getCardKey(), 1));
    }

    public void createAndRideRandomCustomer() {
        Customer customer = new Customer();

        String alphabet = "abcdefghijklmnoprstuvwxyz";

        Random random = new Random();

        customer.setId(customH2TableAdapter.getCustomerHibernateDao().findLastId() + 1);
        customer.setCardKey(random.nextInt());
        customer.setSubscriptionExpirationDate(random.nextInt(200));
        customer.setSubscriptionExpirationMonth(random.nextInt(200));
        customer.setSubscriptionExpirationYear(random.nextInt(200));
        customer.setSubscriptionType(random.nextInt(10));
        customer.setDiscount(random.nextInt(100));
        customer.setMoneyOnKey(random.nextInt(200) + 100);
        customer.setPayedTime(random.nextInt(200) + 100);
        customer.setTimeLeftOnSubscriptionCurrentDate(random.nextInt(200));
        customer.setSumMoneyForAllDay(random.nextInt(200));
        customer.setSumMoneyForAllWeek(random.nextInt(200));
        customer.setSumMoneyForAllMonth(random.nextInt(200));
        customer.setSumTimeForAllPeriod(random.nextInt(200));
        customer.setSurname(alphabet.substring(random.nextInt(alphabet.length())));

        customH2TableAdapter.getCustomerHibernateDao().save(customer);

        String customerString = customer.toString();

        JOptionPane.showMessageDialog(null, "Customer: " + customerString, "Error", JOptionPane.ERROR_MESSAGE);

        fireRFIDEvent(new RFIDReaderEvent(customer.getCardKey(), 1));
    }

    public void setRfidReaderEventListener(RFIDReaderEventListener rfidReaderEventListener) {
        this.rfidReaderEventListener = rfidReaderEventListener;
    }

    public void fireRFIDEvent(RFIDReaderEvent rfidReaderEvent) {
        if (rfidReaderEventListener != null) {
            rfidReaderEventListener.commandPerformed(rfidReaderEvent);
        }
    }
}
