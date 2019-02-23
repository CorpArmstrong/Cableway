package com.itstep.cableway.gui.tables;

import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;

import java.util.*;
import javax.swing.table.AbstractTableModel;

public class CustomersTableModel extends AbstractTableModel
{
    private static final long serialVersionUID = 6105842825518764825L;

    private CustomH2TableAdapter customH2TableAdapter;

    private List<Customer> customersList;

    public CustomersTableModel(CustomH2TableAdapter customH2TableAdapter) {
        super();

        this.customH2TableAdapter = customH2TableAdapter;
        customersList = this.customH2TableAdapter.getCustomerHibernateDao().fillTableMainWindow();
    }

    public Customer getCurrentCustomer(int rowIndex) {
        return customersList.get(rowIndex);
    }

    public int getRowCount() {
        return customersList.size();
    }

    public int getColumnCount() {
        return 6;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        Customer p = customersList.get(rowIndex);

        Object[] values = new Object[] { p.getId(),
        								 p.getCardKey(),
                                         p.getPayedTime(),
                                         p.getMoneyOnKey(),
                                         p.getSubscriptionType(),
                                         p.getSurname() };

        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {

        String[] columnNames = new String[]{ "<html>№<br></html>",
                                             "<html>Браслет</html>",
                                             "<html>Оплаченное время<br>(мин)</html>",
                                             "<html>Оплаченная сумма<br>(грн)</html>",
                                             "<html>№<br>Абонемента</html>",
                                             "<html>Фамилия</html>" };
        return columnNames[column];
    }

    public void reloadDatabase() {
        customersList = null;
        customersList = customH2TableAdapter.getCustomerHibernateDao().fillTableMainWindow();
    }
}
