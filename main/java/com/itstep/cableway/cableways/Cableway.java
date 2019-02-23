package com.itstep.cableway.cableways;

import java.util.Timer;
import java.util.TimerTask;

import com.itstep.cableway.cableways.events.CablewayEvent;
import com.itstep.cableway.cableways.events.CablewayListener;
import com.itstep.cableway.datetime.DateController;

import static com.itstep.cableway.cableways.CablewayConstants.*;
import static com.itstep.cableway.timers.TimerConstants.*;

import com.itstep.cableway.db.entities.CablewayData;
import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.entities.Subscription;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.ports.events.ComPortCommandEvent;
import com.itstep.cableway.ports.events.ComPortCommandListener;
import com.itstep.cableway.timers.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Cableway implements Runnable {

    private static final Logger log = LogManager.getLogger(Cableway.class);

    private ComPortCommandListener comPortCommandListener;
    private CablewayListener cablewayListener;

    private CustomH2TableAdapter customH2TableAdapter;
    private CablewayData cablewayData;

    public class TimerManager {

        void startTimerDelayCablewayMain(Cableway cableway, Customer customer, CustomH2TableAdapter h2Adapter, CablewayData cablewayData) {
            Timer tempTimer = new Timer();

            TimerTask timerSchedule = new StartTimerDelayCablewaySchedule(tempTimer, cableway, customer, h2Adapter, cablewayData);
            tempTimer.schedule(timerSchedule, 0, CABLEWAY_START_DELAY * 1000);
        }

        void startTimer1TurnOffSirenMain(Cableway cableway) {
            Timer tempTimer = new Timer();

            TimerTask timerSchedule = new StartTimer1TurnOffSirenSchedule(tempTimer, cableway);
            tempTimer.schedule(timerSchedule, 0, TIME_ALARM_BEEP_WHEN_PRESENTATION_RFID);
        }

        // Also in CommonTimers.
        public void startTimer2TurnOffSirenMain(Cableway cableway) {
            Timer tempTimer = new Timer();

            TimerTask timerSchedule = new StartTimer2TurnOffSirenSchedule(tempTimer, cableway);
            tempTimer.schedule(timerSchedule, 0, TIME_DURATION_BEEP_START_STOP);
        }

        void startTimerDelayUseCard_RFIDMain(Cableway cableway) {
            Timer tempTimer = new Timer();

            TimerTask timerSchedule = new StartTimerDelayUseCard_RFIDSchedule(tempTimer, cableway);
            tempTimer.schedule(timerSchedule, 0, TIMER_DELAY_USE_CARD_RFID);
        }

        void startTimerPrevent1Main(Cableway cableway) {
            Timer tempTimer = new Timer();

            TimerTask timerSchedule = new StartTimerPrevent1Schedule(tempTimer, cableway);
            tempTimer.schedule(timerSchedule, 0, timePrevent1);
        }

        void startTimerPrevent2Main(Cableway cableway) {
            Timer tempTimer = new Timer();

            TimerTask timerSchedule = new StartTimerPrevent2Schedule(tempTimer, cableway);
            tempTimer.schedule(timerSchedule, 0, timePrevent2);
        }

        void startTimerOneSecondMain(Cableway cableway) {
            Timer tempTimer = new Timer();

            TimerTask timerSchedule = new StartTimerOneSecondSchedule(tempTimer, cableway);
            tempTimer.schedule(timerSchedule, 0, TIMER_ONE_SECOND);
        }
    }

    private TimerManager timerManager;

    // Первичное поднесения ключа, неопределенный клиент.
    private Customer tempCustomer;

    // Повторное поднесение ключа или уже назначенный клиент.
    private Customer customer;

    private Subscription subscription;

    private int numCableway;

    private boolean done;

    private int codeCardPrevent1;
    private int codeCardPrevent2;

    private int timePrevent1;
    private int timePrevent2;

    private int cardKey;

    // Флаг, показывающий, что Канатка работает.
    private volatile boolean isWorking;

    private boolean flagSubscriptionWorkCableway;

    private boolean rfidResponse = false;

    // Флаг, показывающий что есть запрет на повторное поднесение (3 сек).
    private boolean flagDelayUseCard;

    // (Сек.) Время, оставшееся до конца катания Канатки.
    private int timeCableway;

    // (Мин.) Время, за которое будет кататься на Канатке.
    private int timeToPlayVar;

    // for debugging only.
    private int timeCableway2 = 42;

    public Cableway(CustomH2TableAdapter customH2TableAdapter, int numCableway) {

        this.customH2TableAdapter = customH2TableAdapter;
        this.numCableway = numCableway;

        cablewayData = this.customH2TableAdapter.getCablewayDataHibernateDao().findById((long) this.numCableway);

        if (cablewayData == null) {
            cablewayData = new CablewayData();
            customH2TableAdapter.getCablewayDataHibernateDao().save(cablewayData);
        } else {
            //JOptionPane.showMessageDialog(null, "Found cableway №: " + this.numCableway, "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
            log.info("Found cableway №: " + this.numCableway);
        }

        timerManager = new TimerManager();

        cardKey = 0;

        // For debugging only (30 seconds).
        // timeCableway = 30;

        timeCableway = 0;
        timeToPlayVar = 0;

        timePrevent1 = 0;
        timePrevent2 = 0;

        codeCardPrevent1 = -1;
        codeCardPrevent2 = -1;

        done = false;

        isWorking = false;

        flagSubscriptionWorkCableway = false;
        flagDelayUseCard = false;

        timerManager.startTimerOneSecondMain(this);
    }

    public TimerManager getTimerManager()
    {
        return timerManager;
    }

    // Проверить ответ RFIDReader.
    private boolean isRFIDResponse() {
        return rfidResponse;
    }

    // Проверка задержки на использование.
    private boolean isPreventUse() {
        return (cardKey != codeCardPrevent1 && cardKey != codeCardPrevent2);
    }

    private void sendEmptyEvent() {
        CablewayEvent cablewayEvent = new CablewayEvent(
                getNumCableway(),
                0,
                0,
                "-",
                0,
                false,
                0,
                0);
        fireCablewayCommandEvent(cablewayEvent);
    }

    private void sendEvent() {
        CablewayEvent cablewayEvent = new CablewayEvent(
                getNumCableway(),
                timeCableway,
                customer.getCardKey(),
                customer.getSurname(),
                customer.getSubscriptionType(),
                flagSubscriptionWorkCableway,
                codeCardPrevent1,
                codeCardPrevent2);
        fireCablewayCommandEvent(cablewayEvent);
    }

    // Имитация события катания на 2-й канатной дороге.
    public void sendAnotherEvent() {
        CablewayEvent cablewayEvent = new CablewayEvent(
                2,
                timeCableway2,
                740480,
                "Doblo",
                0,
                false,
                0,
                0);
        fireCablewayCommandEvent(cablewayEvent);
    }

    private boolean isFirstTimeCustomer() {
        boolean result = true;

        if (customer != null) {
            if (tempCustomer.getCardKey() == customer.getCardKey()) {
                result = false;
            }
        }

        return result;
    }

    public synchronized boolean checkSubscriptionDateBounds(Subscription s) {
        boolean result = false;

        if (DateController.currentDay >= s.getDayOfWeekStartSubscription() && DateController.currentDay <= s.getDayOfWeekEndSubscription()) {
            result = checkSubscriptionTimeBounds(s);
        }

        return result;
    }

    private synchronized boolean checkSubscriptionTimeBounds(Subscription s) {
        return DateController.timeHours >= s.getTimeStartSubscription() &&
                DateController.timeHours <= s.getTimeEndSubscription();
    }

    private void checkTimeLeftOnSubscriptionCurrentDate(Customer customer) {
        if (customer.getTimeLeftOnSubscriptionCurrentDate() >= TIME_TO_PLAY) {

            flagDelayUseCard = true;
            timerManager.startTimerDelayUseCard_RFIDMain(this);
            timerManager.startTimerDelayCablewayMain(this, customer, customH2TableAdapter, cablewayData);

            flagSubscriptionWorkCableway = true;

            turnOnSiren();

            timerManager.startTimer1TurnOffSirenMain(this);

            timeToPlayVar = TIME_TO_PLAY;
            timeCableway = TIME_TO_PLAY * 60;
        }
    }

    private void checkPayedTimeToOneEquivalent(Customer customer) {
        if (customer.getPayedTime() >= TIME_TO_PLAY) {

            flagDelayUseCard = true;
            timerManager.startTimerDelayUseCard_RFIDMain(this);
            timerManager.startTimerDelayCablewayMain(this, customer, customH2TableAdapter, cablewayData);

            turnOnSiren();

            timerManager.startTimer1TurnOffSirenMain(this);

            timeToPlayVar = TIME_TO_PLAY;
            timeCableway = TIME_TO_PLAY * 60;
        }
    }

    // Оплата двойного проката, установка флага задержки на использование ключа.
    private void payDoubleTimeRide() {

        // Double check.

        flagDelayUseCard = true;
        timerManager.startTimerDelayUseCard_RFIDMain(this);

        //JOptionPane.showMessageDialog(null, "Pay double price without subscription.", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        log.info("Pay double price without subscription.");

        timeToPlayVar = 2 * TIME_TO_PLAY;
        timeCableway = 2 * 60 * TIME_TO_PLAY;
    }

    public void checkTimeLeft() {
        // For debugging only:
        System.out.println(timeCableway);

        if (isWorking) {
            if (timeCableway > 0 /*&& timeCableway2 > 0*/) {
                sendEvent();
                //sendAnotherEvent();
                timeCableway--;
                //timeCableway2--;

                if (timeCableway == TIME_TO_PLAY) {
                    turnOnSiren();
                    timerManager.startTimer2TurnOffSirenMain(this);
                }
            } else {

                stopCableway();

                if (flagSubscriptionWorkCableway) {
                    flagSubscriptionWorkCableway = false;

                    if (codeCardPrevent1 == -1) {
                        timePrevent1 = timeToPlayVar * 60 * 1000;
                        codeCardPrevent1 = customer.getCardKey();
                        timerManager.startTimerPrevent1Main(this);
                    } else {
                        if (codeCardPrevent2 == -1) {
                            timePrevent2 = timeToPlayVar * 60 * 1000;
                            codeCardPrevent2 = customer.getCardKey();
                            timerManager.startTimerPrevent2Main(this);
                        }
                    }
                }

                sendEmptyEvent();

                flagDelayUseCard = false;

                isWorking = false;
                //JOptionPane.showMessageDialog(null, "Cableway Stopped!", "Error", JOptionPane.ERROR_MESSAGE);
                log.info("Cableway " + numCableway + " stopped!");

                customer = null;
                subscription = null;
            }
        }
    }

    @Override
    public void run() {
        //JOptionPane.showMessageDialog(null, "Cableway start success.", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
        log.info("Cableway " + numCableway + " start success.");

        sendEmptyEvent();

        while (!done) {

            //System.out.println("isWorking: " + isWorking);

            // Есть ли ответ от RFIDReader ? && Есть ли запрет на повторное поднесение (3 сек) ? && Есть ли задержка на использование ?
            if (isRFIDResponse() && !flagDelayUseCard && isPreventUse()) {
                if (!isWorking) {

                    // Set up template customer
                    tempCustomer = new Customer();
                    tempCustomer.setCardKey(cardKey);

                    // Проверить был ли ключ уже назначен ?
                    if (isFirstTimeCustomer()) {
                        // Вероятно первое поднесение ключа?

                        // Человек найден в базе данных по ключу ?
                        if (customH2TableAdapter.getCustomerHibernateDao().findCustomerByCardKey(tempCustomer.getCardKey())) {

                            // Установить клиента.
                            customer = customH2TableAdapter.getCustomerHibernateDao().getCustomerByCardKey(tempCustomer.getCardKey());
                            tempCustomer = null;

                            // Проверить есть ли у клиента абонемент ?
                            if (customer.getSubscriptionType() != 0) {
                                // Получить абонемент относящийся к данному клиенту
                                subscription = customH2TableAdapter.getSubscriptionHibernateDao().findById((long) customer.getSubscriptionType());

                                // Double check: Sunday = 0;
                                DateController.getSystemTime();

                                // Проверка границ абонемента.
                                if (checkSubscriptionDateBounds(subscription)) {
                                    checkTimeLeftOnSubscriptionCurrentDate(customer);
                                }

                                // Проверить что клиент не будет кататься по абонементу ?
                                if (!flagSubscriptionWorkCableway) {
                                    checkPayedTimeToOneEquivalent(customer);
                                }
                            } else {
                                // Нет абонемента!
                                checkPayedTimeToOneEquivalent(customer);
                            }
                        }
                    } else {
                        // Ключ уже был назначен.
                        // Как минимум повторное поднесение ключа.

                        // Проверить что на устройстве поднесен тот же ключ (в течении 3 сек).
                        if (tempCustomer.getCardKey() == customer.getCardKey()) {
                            // Проверить что клиент уже будет кататься по абонементу.
                            if (flagSubscriptionWorkCableway) {
                                // Проверить что оставшееся время по абонементу на текущую дату >= двойной единицы проката.
                                if (customer.getTimeLeftOnSubscriptionCurrentDate() >= (2 * TIME_TO_PLAY)) {
                                    // Оплата двойного проката.
                                    // Установить флаг задержки на использование ключа.
                                    payDoubleTimeRide();
                                } else {
                                    // Проверить что оплаченное время >= минимальной единицы проката.
                                    if (customer.getPayedTime() >= TIME_TO_PLAY) {
                                        // Оплата двойного проката.
                                        // Установить флаг задержки на использование ключа.
                                        payDoubleTimeRide();

                                        try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            log.error(e.getMessage());
                                        }
                                    }
                                }
                            } else {
                                // Не намерен кататься по абонементу.
                                // Повторное поднесение.

                                // Проверить что оплаченное время >= двойной единицы проката.
                                if (customer.getPayedTime() >= (2 * TIME_TO_PLAY)) {
                                    // Оплата двойного проката.
                                    // Установить флаг задержки на использование ключа.
                                    payDoubleTimeRide();

                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        log.error(e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            cardKey = 0;
            rfidResponse = false;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // This is used to shutdown TimerOneSecond.
                done = true;
                //JOptionPane.showMessageDialog(null, "Cableway stop success.", "Предупреждение", JOptionPane.INFORMATION_MESSAGE);
                log.info("Cableway " + numCableway + " stop success.");
            }
        }
    }

    public void setFlagDelayUseCard(boolean flagDelayUseCard) {
        this.flagDelayUseCard = flagDelayUseCard;
    }

    public void setCodeCardPrevent1(int codeCardPrevent1) {
        this.codeCardPrevent1 = codeCardPrevent1;
    }

    public void setCodeCardPrevent2(int codeCardPrevent2) {
        this.codeCardPrevent2 = codeCardPrevent2;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public boolean isDone() {
        return done;
    }

    public boolean isFlagSubscriptionWorkCableway() {
        return flagSubscriptionWorkCableway;
    }

    public int getTimeToPlayVar() {
        return timeToPlayVar;
    }

    public int getNumCableway() {
        return numCableway;
    }

    public void fireComPortCommandEvent(ComPortCommandEvent comPortCommandEvent) {
        if (comPortCommandListener != null) {
            comPortCommandListener.commandPerformed(comPortCommandEvent);
        }
    }

    public void fireCablewayCommandEvent(CablewayEvent cablewayEvent) {
        if (cablewayListener != null) {
            cablewayListener.commandPerformed(cablewayEvent);
        }
    }

    public void startCableway() {
        fireComPortCommandEvent(new ComPortCommandEvent(numCableway, (byte) 0x01, (byte) 0x01, (byte) 0x01));
    }

    public void stopCableway() {
        fireComPortCommandEvent(new ComPortCommandEvent(numCableway, (byte) 0x02, (byte) 0x02, (byte) 0x02));
    }

    public void turnOnSiren() {
        fireComPortCommandEvent(new ComPortCommandEvent(numCableway, (byte) 0x03, (byte) 0x03, (byte) 0x03));
    }

    public void turnOffSiren() {
        fireComPortCommandEvent(new ComPortCommandEvent(numCableway, (byte) 0x04, (byte) 0x04, (byte) 0x04));
    }

    public void setComPortCommandListener(ComPortCommandListener comPortCommandListener) {
        this.comPortCommandListener = comPortCommandListener;
    }

    public void setCablewayListener(CablewayListener cablewayListener) {
        this.cablewayListener = cablewayListener;
    }

    public void setCardKey(int cardKey) {
        this.cardKey = cardKey;
        rfidResponse = true;
    }
}
