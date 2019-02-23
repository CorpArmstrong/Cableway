package com.itstep.cableway.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class CablewayData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Суммарное откатанное время по Канатке за день
    private int sumPlayedTimeCablewayForDay;

    // Суммарное откатанное время по Канатке за неделю
    private int sumPlayedTimeCablewayForWeek;

    // Суммарное откатанное время по Канатке за месяц
    private int sumPlayedTimeCablewayForMonth;

    // Суммарное откатанное время по Канатке за весь период
    private int sumPlayedTimeCablewayForAllPeriod;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSumPlayedTimeCablewayForDay() {
        return sumPlayedTimeCablewayForDay;
    }

    public void setSumPlayedTimeCablewayForDay(int sumPlayedTimeCablewayForDay) {
        this.sumPlayedTimeCablewayForDay = sumPlayedTimeCablewayForDay;
    }

    public int getSumPlayedTimeCablewayForWeek() {
        return sumPlayedTimeCablewayForWeek;
    }

    public void setSumPlayedTimeCablewayForWeek(int sumPlayedTimeCablewayForWeek) {
        this.sumPlayedTimeCablewayForWeek = sumPlayedTimeCablewayForWeek;
    }

    public int getSumPlayedTimeCablewayForMonth() {
        return sumPlayedTimeCablewayForMonth;
    }

    public void setSumPlayedTimeCablewayForMonth(int sumPlayedTimeCablewayForMonth) {
        this.sumPlayedTimeCablewayForMonth = sumPlayedTimeCablewayForMonth;
    }

    public int getSumPlayedTimeCablewayForAllPeriod() {
        return sumPlayedTimeCablewayForAllPeriod;
    }

    public void setSumPlayedTimeCablewayForAllPeriod(int sumPlayedTimeCablewayForAllPeriod) {
        this.sumPlayedTimeCablewayForAllPeriod = sumPlayedTimeCablewayForAllPeriod;
    }

    @Override
    public String toString() {
        return "CablewayData{" +
                "id=" + id +
                ", sumPlayedTimeCablewayForDay=" + sumPlayedTimeCablewayForDay +
                ", sumPlayedTimeCablewayForWeek=" + sumPlayedTimeCablewayForWeek +
                ", sumPlayedTimeCablewayForMonth=" + sumPlayedTimeCablewayForMonth +
                ", sumPlayedTimeCablewayForAllPeriod=" + sumPlayedTimeCablewayForAllPeriod +
                '}';
    }
}
