package com.itstep.cableway.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Definitions implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Стоимость мин. единицы проката (деньги/время)
    private int oneMinutePrice;

    // Деньги за день
    private int moneyForDay;

    // Деньги за неделю
    private int moneyForWeek;

    // Деньги за месяц
    private int moneyForMonth;

    // Деньги за весь период
    private int moneyForAllTimePeriod;

    // Возврат денег за день
    private int returnMoneyForDay;

    // Возврат денег за неделю
    private int returnMoneyForWeek;

    // Возврат денег за месяц
    private int returnMoneyForMonth;

    // Возврат денег за все время
    private int returnMoneyForAllTimePeriod;

    // День последнего запуска программы
    private int lastDay;

    // Месяц последнего запуска программы
    private int lastMonth;

    // Год последнего запуска программы
    private int lastYear;

    // Последний день недели последнего запуска программы
    private int lastDayOfWeek;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOneMinutePrice() {
        return oneMinutePrice;
    }

    public void setOneMinutePrice(int oneMinutePrice) {
        this.oneMinutePrice = oneMinutePrice;
    }

    public int getMoneyForDay() {
        return moneyForDay;
    }

    public void setMoneyForDay(int moneyForDay) {
        this.moneyForDay = moneyForDay;
    }

    public int getMoneyForWeek() {
        return moneyForWeek;
    }

    public void setMoneyForWeek(int moneyForWeek) {
        this.moneyForWeek = moneyForWeek;
    }

    public int getMoneyForMonth() {
        return moneyForMonth;
    }

    public void setMoneyForMonth(int moneyForMonth) {
        this.moneyForMonth = moneyForMonth;
    }

    public int getMoneyForAllTimePeriod() {
        return moneyForAllTimePeriod;
    }

    public void setMoneyForAllTimePeriod(int moneyForAllTimePeriod) {
        this.moneyForAllTimePeriod = moneyForAllTimePeriod;
    }

    public int getReturnMoneyForDay() {
        return returnMoneyForDay;
    }

    public void setReturnMoneyForDay(int returnMoneyForDay) {
        this.returnMoneyForDay = returnMoneyForDay;
    }

    public int getReturnMoneyForWeek() {
        return returnMoneyForWeek;
    }

    public void setReturnMoneyForWeek(int returnMoneyForWeek) {
        this.returnMoneyForWeek = returnMoneyForWeek;
    }

    public int getReturnMoneyForMonth() {
        return returnMoneyForMonth;
    }

    public void setReturnMoneyForMonth(int returnMoneyForMonth) {
        this.returnMoneyForMonth = returnMoneyForMonth;
    }

    public int getReturnMoneyForAllTimePeriod() {
        return returnMoneyForAllTimePeriod;
    }

    public void setReturnMoneyForAllTimePeriod(int returnMoneyForAllTimePeriod) {
        this.returnMoneyForAllTimePeriod = returnMoneyForAllTimePeriod;
    }

    public int getLastDay() {
        return lastDay;
    }

    public void setLastDay(int lastDay) {
        this.lastDay = lastDay;
    }

    public int getLastMonth() {
        return lastMonth;
    }

    public void setLastMonth(int lastMonth) {
        this.lastMonth = lastMonth;
    }

    public int getLastYear() {
        return lastYear;
    }

    public void setLastYear(int lastYear) {
        this.lastYear = lastYear;
    }

    public int getLastDayOfWeek() {
        return lastDayOfWeek;
    }

    public void setLastDayOfWeek(int lastDayOfWeek) {
        this.lastDayOfWeek = lastDayOfWeek;
    }

    @Override
    public String toString() {
        return "Definitions{" +
                "id=" + id +
                ", oneMinutePrice=" + oneMinutePrice +
                ", moneyForDay=" + moneyForDay +
                ", moneyForWeek=" + moneyForWeek +
                ", moneyForMonth=" + moneyForMonth +
                ", moneyForAllTimePeriod=" + moneyForAllTimePeriod +
                ", returnMoneyForDay=" + returnMoneyForDay +
                ", returnMoneyForWeek=" + returnMoneyForWeek +
                ", returnMoneyForMonth=" + returnMoneyForMonth +
                ", returnMoneyForAllTimePeriod=" + returnMoneyForAllTimePeriod +
                ", lastDay=" + lastDay +
                ", lastMonth=" + lastMonth +
                ", lastYear=" + lastYear +
                ", lastDayOfWeek=" + lastDayOfWeek +
                '}';
    }
}
