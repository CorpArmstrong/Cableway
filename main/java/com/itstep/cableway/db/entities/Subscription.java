package com.itstep.cableway.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // День недели начала действия абонемента
    private int dayOfWeekStartSubscription;

    // День недели окончания действия абонемента
    private int dayOfWeekEndSubscription;

    // Время начала действия абонемента
    private int timeStartSubscription;

    // Время окончания действия абонемента
    private int timeEndSubscription;

    // Стоимость абонемента
    private int subscriptionPrice;

    // Лимит времени на день
    private int subscriptionTimeLimitForDay;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDayOfWeekStartSubscription() {
        return dayOfWeekStartSubscription;
    }

    public void setDayOfWeekStartSubscription(int dayOfWeekStartSubscription) {
        this.dayOfWeekStartSubscription = dayOfWeekStartSubscription;
    }

    public int getDayOfWeekEndSubscription() {
        return dayOfWeekEndSubscription;
    }

    public void setDayOfWeekEndSubscription(int dayOfWeekEndSubscription) {
        this.dayOfWeekEndSubscription = dayOfWeekEndSubscription;
    }

    public int getTimeStartSubscription() {
        return timeStartSubscription;
    }

    public void setTimeStartSubscription(int timeStartSubscription) {
        this.timeStartSubscription = timeStartSubscription;
    }

    public int getTimeEndSubscription() {
        return timeEndSubscription;
    }

    public void setTimeEndSubscription(int timeEndSubscription) {
        this.timeEndSubscription = timeEndSubscription;
    }

    public int getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(int subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public int getSubscriptionTimeLimitForDay() {
        return subscriptionTimeLimitForDay;
    }

    public void setSubscriptionTimeLimitForDay(int subscriptionTimeLimitForDay) {
        this.subscriptionTimeLimitForDay = subscriptionTimeLimitForDay;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", dayOfWeekStartSubscription=" + dayOfWeekStartSubscription +
                ", dayOfWeekEndSubscription=" + dayOfWeekEndSubscription +
                ", timeStartSubscription=" + timeStartSubscription +
                ", timeEndSubscription=" + timeEndSubscription +
                ", subscriptionPrice=" + subscriptionPrice +
                ", subscriptionTimeLimitForDay=" + subscriptionTimeLimitForDay +
                '}';
    }
}
