package com.itstep.cableway.gui.tables;

import com.itstep.cableway.db.entities.Subscription;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ManageSubscriptionsTableModel extends AbstractTableModel {

    private CustomH2TableAdapter customH2TableAdapter;
    private List<Subscription> subscriptionList;

    private final int ID = 0;
    private final int DAY_OF_WEEK_START_SUBSCRIPTION = 1;
    private final int DAY_OF_WEEK_END_SUBSCRIPTION = 2;
    private final int TIME_START_SUBSCRIPTION = 3;
    private final int TIME_END_SUBSCRIPTION = 4;
    private final int SUBSCRIPTION_PRICE = 5;
    private final int SUBSCRIPTION_TIME_LIMIT_FOR_DAY = 6;

    public ManageSubscriptionsTableModel(CustomH2TableAdapter customH2TableAdapter) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.subscriptionList = this.customH2TableAdapter.getSubscriptionHibernateDao().findAll();
    }

    @Override
    public int getRowCount() {
        return subscriptionList.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Subscription subscription = subscriptionList.get(rowIndex);

        Object[] values = new Object[] {
                subscription.getId(),
                subscription.getDayOfWeekStartSubscription(),
                subscription.getDayOfWeekEndSubscription(),
                subscription.getTimeStartSubscription(),
                subscription.getTimeEndSubscription(),
                subscription.getSubscriptionPrice(),
                subscription.getSubscriptionTimeLimitForDay()
        };

        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {

        String[] columnNames = new String[] {

                "<html>Тип абонемента</html>",
                "<html>День недели начала действия абонемента</html>",
                "<html>День недели окончания действия абонемента</html>",
                "<html>Прокатные часы (начало)</html>",
                "<html>Прокатные часы (окончание)</html>",
                "<html>Стоимость абонемента</html>",
                "<html>Лимит времени на сутки</html>"
        };

        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        // Don't edit ID's-value!
        return column != 0;
    }

    @Override
    public void setValueAt(Object value, int row, int col) {

        Subscription subscription = subscriptionList.get(row);
        boolean isValidated = false;

        switch (col) {
            case ID:
                break;
            case DAY_OF_WEEK_START_SUBSCRIPTION:

                int dayStart = Integer.valueOf(value.toString());

                if (dayStart >= 0 && dayStart < 7) {
                    isValidated = true;
                    subscription.setDayOfWeekStartSubscription(dayStart);
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка в колонке: " + getColumnName(col) +
                            " ,\nСтрока: " + ++row + "\nЗначение: " + dayStart + " не допустимо!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case DAY_OF_WEEK_END_SUBSCRIPTION:

                int dayEnd = Integer.valueOf(value.toString());

                if (dayEnd >= 0 && dayEnd < 7) {
                    isValidated = true;
                    subscription.setDayOfWeekEndSubscription(dayEnd);
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка в колонке: " + getColumnName(col) +
                            " ,\nСтрока: " + ++row + "\nЗначение: " + dayEnd + " не допустимо!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case TIME_START_SUBSCRIPTION:

                int timeStart = Integer.valueOf(value.toString());

                if (timeStart >= 0 && timeStart < 24) {
                    isValidated = true;
                    subscription.setTimeStartSubscription(timeStart);
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка в колонке: " + getColumnName(col) +
                            " ,\nСтрока: " + ++row + "\nЗначение: " + timeStart + " не допустимо!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case TIME_END_SUBSCRIPTION:

                int timeEnd = Integer.valueOf(value.toString());

                if (timeEnd >= 0 && timeEnd < 24) {
                    isValidated = true;
                    subscription.setTimeEndSubscription(timeEnd);
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка в колонке: " + getColumnName(col) +
                            " ,\nСтрока: " + ++row + "\nЗначение: " + timeEnd + " не допустимо!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case SUBSCRIPTION_PRICE:

                int subPrice = Integer.valueOf(value.toString());

                if (subPrice >= 0) {
                    isValidated = true;
                    subscription.setSubscriptionPrice(subPrice);
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка в колонке: " + getColumnName(col) +
                            " ,\nСтрока: " + ++row + "\nЗначение: " + subPrice + " не допустимо!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

                break;
            case SUBSCRIPTION_TIME_LIMIT_FOR_DAY:

                int subTimeLimit = Integer.valueOf(value.toString());

                if (subTimeLimit >= 0) {
                    isValidated = true;
                    subscription.setSubscriptionTimeLimitForDay(subTimeLimit);
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка в колонке: " + getColumnName(col) +
                            " ,\nСтрока: " + ++row + "\nЗначение: " + subTimeLimit + " не допустимо!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }

                break;
        }

        if (isValidated) {
            subscriptionList.set(row, subscription);
            fireTableCellUpdated(row, col);
        }
    }

    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }
}
