package com.itstep.cableway.gui.tables;

import com.itstep.cableway.db.entities.Subscription;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;

import javax.swing.table.AbstractTableModel;
import java.util.List;

import static com.itstep.cableway.datetime.DateController.*;

public class SubscriptionsTableModel extends AbstractTableModel {

    private List<Subscription> subscriptionsList;

    private CustomH2TableAdapter customH2TableAdapter;

    public SubscriptionsTableModel(CustomH2TableAdapter customH2TableAdapter) {
        super();

        this.customH2TableAdapter = customH2TableAdapter;
        subscriptionsList = this.customH2TableAdapter.getSubscriptionHibernateDao().fillSubscriptionTableMainWindow();
    }

    @Override
    public int getRowCount() {
        return subscriptionsList.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Subscription s = subscriptionsList.get(rowIndex);

        Object[] values = new Object[] { s.getId(),
                                         daysOfWeek[s.getDayOfWeekStartSubscription()] + " - " + daysOfWeek[s.getDayOfWeekEndSubscription()],
                                         s.getTimeStartSubscription() + ":00 - " + s.getTimeEndSubscription() + ":00",
                                         s.getSubscriptionPrice() };

        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {

        String[] columnNames = new String[]{ "<html>№ Абонемента<br></html>",
                                             "<html>Дни катания</html>",
                                             "<html>Время катания<br>(мин)</html>",
                                             "<html>Стоимость<br>(грн)</html>" };
        return columnNames[column];
    }
}
