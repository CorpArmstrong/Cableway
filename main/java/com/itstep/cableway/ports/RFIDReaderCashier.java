package com.itstep.cableway.ports;

import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.ports.events.RFIDCashierEvent;
import com.itstep.cableway.ports.events.RFIDCashierEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RFIDReaderCashier {

    private CustomH2TableAdapter customH2TableAdapter;

    private List<RFIDCashierEventListener> rfidCashierEventListeners;

    public RFIDReaderCashier(CustomH2TableAdapter customH2TableAdapter) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.rfidCashierEventListeners = new ArrayList<RFIDCashierEventListener>();
    }

    public void applyNewKey() {
        Customer demoCustomer = new Customer();
        demoCustomer.setCardKey(new Random().nextInt() + 1001001);
    }

    public void applyPresentKey() {
        Customer customer = customH2TableAdapter.getCustomerHibernateDao().findById((long) 1);
        fireRFIDCashierEvent(new RFIDCashierEvent(customer.getCardKey()), 1);
    }

    public void setRfidCashierEventListener(RFIDCashierEventListener rfidCashierEventListener) {
        rfidCashierEventListeners.add(rfidCashierEventListener);
    }

    public void fireRFIDCashierEvent(RFIDCashierEvent rfidCashierEvent, int numCableway) {
        int properIndex = numCableway - 1;

        // Double check.
        if (properIndex >= 0 && properIndex <= rfidCashierEventListeners.size()) {
            if (rfidCashierEventListeners.get(properIndex) != null) {
                rfidCashierEventListeners.get(properIndex).commandPerformed(rfidCashierEvent);
            }
        }
    }
}
