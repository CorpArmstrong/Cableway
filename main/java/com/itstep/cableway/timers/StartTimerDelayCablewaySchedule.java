package com.itstep.cableway.timers;

import com.itstep.cableway.cableways.Cableway;
import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.db.entities.CablewayData;
import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.entities.Subscription;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;

import static com.itstep.cableway.cableways.CablewayConstants.*;

public class StartTimerDelayCablewaySchedule extends AbstractSchedule {

    private static final Logger log = LogManager.getLogger(StartTimerDelayCablewaySchedule.class);

    private CustomH2TableAdapter customH2TableAdapter;

    private Cableway cableway;
    private Customer customer;
    private Subscription subscription;
    private CablewayData cablewayData;

    public StartTimerDelayCablewaySchedule(Timer timer, Cableway cableway, Customer customer, CustomH2TableAdapter customH2TableAdapter, CablewayData cablewayData) {
        super(timer);
        this.cableway = cableway;
        this.customer = customer;
        this.customH2TableAdapter = customH2TableAdapter;
        this.cablewayData = cablewayData;
    }

    @Override
    protected void operation() {

        cableway.turnOnSiren();
        cableway.getTimerManager().startTimer2TurnOffSirenMain(cableway);
        cableway.startCableway();

        //JOptionPane.showMessageDialog(null, "In TimerDelayCableway\nCableway started!", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);

        // If there is a subscription!
        if (customer.getSubscriptionType() != 0 && cableway.isFlagSubscriptionWorkCableway()) {
            subscription = customH2TableAdapter.getSubscriptionHibernateDao().findById((long) customer.getSubscriptionType());

            DateController.getSystemTime();

            if (cableway.checkSubscriptionDateBounds(subscription)) {
                if (customer.getTimeLeftOnSubscriptionCurrentDate() >= cableway.getTimeToPlayVar()) {

                    // Sub full time from subscription!
                    customer.setTimeLeftOnSubscriptionCurrentDate(customer.getTimeLeftOnSubscriptionCurrentDate() - cableway.getTimeToPlayVar());
                } else {
                    if (customer.getTimeLeftOnSubscriptionCurrentDate() >= TIME_TO_PLAY) {
                        // Sub part 1 of time from subscription.
                        customer.setTimeLeftOnSubscriptionCurrentDate(customer.getTimeLeftOnSubscriptionCurrentDate() - TIME_TO_PLAY);

                        // Sub money!
                        customer.setMoneyOnKey(customer.getMoneyOnKey() - ((TIME_TO_PLAY * customer.getMoneyOnKey()) / customer.getPayedTime()));

                        // Sub part 2 of time from pre-ordered time!
                        customer.setPayedTime(customer.getPayedTime() - TIME_TO_PLAY);
                    }
                }
            }
        } else {
            try {
                // Sub money!
                customer.setMoneyOnKey(customer.getMoneyOnKey() - ((cableway.getTimeToPlayVar() * customer.getMoneyOnKey()) / customer.getPayedTime()));
            } catch (ArithmeticException e) {
                log.error("Divide by zero: " + e.getMessage());
            }

            customer.setPayedTime(customer.getPayedTime() - cableway.getTimeToPlayVar());
        }

        customer.setSumTimePlayedForDay(customer.getSumTimePlayedForDay() + cableway.getTimeToPlayVar());
        customer.setSumTimePlayedForWeek(customer.getSumTimePlayedForWeek() + cableway.getTimeToPlayVar());
        customer.setSumTimePlayedForMonth(customer.getSumTimePlayedForMonth() + cableway.getTimeToPlayVar());
        customer.setSumTimeForAllPeriod(customer.getSumTimeForAllPeriod() + cableway.getTimeToPlayVar());

        cablewayData.setSumPlayedTimeCablewayForDay(cablewayData.getSumPlayedTimeCablewayForDay() + cableway.getTimeToPlayVar());
        cablewayData.setSumPlayedTimeCablewayForWeek(cablewayData.getSumPlayedTimeCablewayForWeek() + cableway.getTimeToPlayVar());
        cablewayData.setSumPlayedTimeCablewayForMonth(cablewayData.getSumPlayedTimeCablewayForMonth() + cableway.getTimeToPlayVar());
        cablewayData.setSumPlayedTimeCablewayForAllPeriod(cablewayData.getSumPlayedTimeCablewayForAllPeriod() + cableway.getTimeToPlayVar());

        cableway.setWorking(true);

        if (cableway.isFlagSubscriptionWorkCableway()) {
            log.info(DateController.now() + " Ключ " + customer.getCardKey() + " катается " + cableway.getTimeToPlayVar() + " минут на канатке ПО АБОНЕМЕНТУ!");
        } else {
            log.info(DateController.now() + " Ключ " + customer.getCardKey() + " катается " + cableway.getTimeToPlayVar() + " минут на канатке.");
        }

        customH2TableAdapter.getCustomerHibernateDao().update(customer);
        customH2TableAdapter.getCablewayDataHibernateDao().update(cablewayData);
    }

    @Override
    public void run() {
        super.doTick();
    }
}
