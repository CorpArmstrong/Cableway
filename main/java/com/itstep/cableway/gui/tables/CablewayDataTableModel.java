package com.itstep.cableway.gui.tables;

import com.itstep.cableway.db.entities.CablewayData;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CablewayDataTableModel extends AbstractTableModel {

    private CustomH2TableAdapter customH2TableAdapter;
    private List<CablewayData> cablewayDatas;

    public CablewayDataTableModel(CustomH2TableAdapter customH2TableAdapter) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.cablewayDatas = this.customH2TableAdapter.getCablewayDataHibernateDao().findAll();
    }

    @Override
    public int getRowCount() {
        return cablewayDatas.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        CablewayData cablewayData = cablewayDatas.get(rowIndex);

        Object[] values = new Object[] {
                cablewayData.getId(),
                cablewayData.getSumPlayedTimeCablewayForDay(),
                cablewayData.getSumPlayedTimeCablewayForWeek(),
                cablewayData.getSumPlayedTimeCablewayForMonth(),
                cablewayData.getSumPlayedTimeCablewayForAllPeriod()
        };

        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {

        String[] columnNames = new String[] {

                "<html>№ Канатки</html>",
                "<html>Суммарное время работы за день (мин)</html>",
                "<html>Суммарное время работы за неделю (мин)</html>",
                "<html>Суммарное время работы за месяц (мин)</html>",
                "<html>Суммарное время работы за весь период (мин)</html>"
        };

        return columnNames[column];
    }

    public List<CablewayData> getCablewayDatas() {
        return cablewayDatas;
    }
}
