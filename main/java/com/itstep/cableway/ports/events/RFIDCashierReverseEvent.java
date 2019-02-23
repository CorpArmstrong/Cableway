package com.itstep.cableway.ports.events;

import com.itstep.cableway.db.entities.Customer;

public class RFIDCashierReverseEvent {

    private Customer customer;

    public RFIDCashierReverseEvent(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
