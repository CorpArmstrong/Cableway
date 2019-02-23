package com.itstep.cableway.db.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Дмитрий
 *
 */

//@Column(name = "Occupation")

@Entity
public class Customer implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cardKey;								// № ключа клиента
    private String surname;                             // Фамилия

    private int payedTime;								// Оплаченное время
    private int moneyOnKey;								// Сумма денег на ключе в данный момент
    private int subscriptionType;						// Тип абонемента (0 - если его нет)
    private int timeLeftOnSubscriptionCurrentDate;		// Оставшееся время по абонементу на текущую дату
    private int discount;								// Скидка

    private int sumTimePlayedForDay;					// Суммарное время, откатанное за день
    private int sumTimePlayedForWeek;					// Суммарное время, откатанное за неделю
    private int sumTimePlayedForMonth;					// Суммарное время, откатанное за месяц

    private int sumMoneyForAllDay;						// Суммарные деньги за день
    private int sumMoneyForAllWeek;						// Суммарные деньги за неделю
    private int sumMoneyForAllMonth;					// Суммарные деньги за месяц

    private int sumTimeForAllPeriod;					// Общее время за весь период
    private int sumMoneyForAllPeriod;					// Общее деньги за весь период

    private int subscriptionExpirationDate;				// Дата окончания абонемента
    private int subscriptionExpirationMonth;			// Месяц окончания абонемента
    private int subscriptionExpirationYear;				// Год окончания абонемента

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCardKey() {
        return cardKey;
    }

    public void setCardKey(int cardKey) {
        this.cardKey = cardKey;
    }

    public int getPayedTime() {
        return payedTime;
    }

    public void setPayedTime(int payedTime) {
        this.payedTime = payedTime;
    }

    public int getMoneyOnKey() {
        return moneyOnKey;
    }

    public void setMoneyOnKey(int moneyOnKey) {
        this.moneyOnKey = moneyOnKey;
    }

    public int getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(int subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public int getTimeLeftOnSubscriptionCurrentDate() {
        return timeLeftOnSubscriptionCurrentDate;
    }

    public void setTimeLeftOnSubscriptionCurrentDate(int timeLeftOnSubscriptionCurrentDate) {
        this.timeLeftOnSubscriptionCurrentDate = timeLeftOnSubscriptionCurrentDate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getSumTimePlayedForDay() {
        return sumTimePlayedForDay;
    }

    public void setSumTimePlayedForDay(int sumPlayedForDay) {
        this.sumTimePlayedForDay = sumPlayedForDay;
    }

    public int getSumTimePlayedForWeek() {
        return sumTimePlayedForWeek;
    }

    public void setSumTimePlayedForWeek(int sumPlayedForWeek) {
        this.sumTimePlayedForWeek = sumPlayedForWeek;
    }

    public int getSumTimePlayedForMonth() {
        return sumTimePlayedForMonth;
    }

    public void setSumTimePlayedForMonth(int sumPlayedForMonth) {
        this.sumTimePlayedForMonth = sumPlayedForMonth;
    }

    public int getSumMoneyForAllDay() {
        return sumMoneyForAllDay;
    }

    public void setSumMoneyForAllDay(int sumMoneyForAllDay) {
        this.sumMoneyForAllDay = sumMoneyForAllDay;
    }

    public int getSumMoneyForAllWeek() {
        return sumMoneyForAllWeek;
    }

    public void setSumMoneyForAllWeek(int sumMoneyForAllWeek) {
        this.sumMoneyForAllWeek = sumMoneyForAllWeek;
    }

    public int getSumMoneyForAllMonth() {
        return sumMoneyForAllMonth;
    }

    public void setSumMoneyForAllMonth(int sumMoneyForAllMonth) {
        this.sumMoneyForAllMonth = sumMoneyForAllMonth;
    }

    public int getSumTimeForAllPeriod() {
        return sumTimeForAllPeriod;
    }

    public void setSumTimeForAllPeriod(int sumTimeForAllPeriod) {
        this.sumTimeForAllPeriod = sumTimeForAllPeriod;
    }

    public int getSumMoneyForAllPeriod() {
        return sumMoneyForAllPeriod;
    }

    public void setSumMoneyForAllPeriod(int sumMoneyForAllPeriod) {
        this.sumMoneyForAllPeriod = sumMoneyForAllPeriod;
    }

    public int getSubscriptionExpirationDate() {
        return subscriptionExpirationDate;
    }

    public void setSubscriptionExpirationDate(int subscriptionExpirationDate) {
        this.subscriptionExpirationDate = subscriptionExpirationDate;
    }

    public int getSubscriptionExpirationMonth() {
        return subscriptionExpirationMonth;
    }

    public void setSubscriptionExpirationMonth(int subscriptionExpirationMonth) {
        this.subscriptionExpirationMonth = subscriptionExpirationMonth;
    }

    public int getSubscriptionExpirationYear() {
        return subscriptionExpirationYear;
    }

    public void setSubscriptionExpirationYear(int subscriptionExpirationYear) {
        this.subscriptionExpirationYear = subscriptionExpirationYear;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "cardKey = " + cardKey +
                ", payedTime = " + payedTime +
                ", moneyOnKey = " + moneyOnKey +
                ", subscriptionType = " + subscriptionType +
                ", timeLeftOnSubscriptionCurrentDate = " + timeLeftOnSubscriptionCurrentDate +
                ", discount = " + discount +
                ", sumTimePlayedForDay = " + sumTimePlayedForDay +
                ", sumTimePlayedForWeek = " + sumTimePlayedForWeek +
                ", sumTimePlayedForMonth = " + sumTimePlayedForMonth +
                ", sumMoneyForAllDay = " + sumMoneyForAllDay +
                ", sumMoneyForAllWeek = " + sumMoneyForAllWeek +
                ", sumMoneyForAllMonth = " + sumMoneyForAllMonth +
                ", sumTimeForAllPeriod = " + sumTimeForAllPeriod +
                ", sumMoneyForAllPeriod = " + sumMoneyForAllPeriod +
                ", subscriptionExpirationDate = " + subscriptionExpirationDate +
                ", subscriptionExpirationMonth = " + subscriptionExpirationMonth +
                ", subscriptionExpirationYear = " + subscriptionExpirationYear +
                ", surname = '" + surname + '\'' + '}';
    }
}