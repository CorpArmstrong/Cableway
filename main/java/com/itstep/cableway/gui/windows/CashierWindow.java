package com.itstep.cableway.gui.windows;

import com.itstep.cableway.cableways.CablewaysManager;
import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.gui.tables.CustomersTableModel;
import com.itstep.cableway.utils.CurrentCableway;
import com.itstep.cableway.utils.CurrentUser;
import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.entities.Definitions;
import com.itstep.cableway.db.entities.Subscription;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.*;
import java.util.List;

import static com.itstep.cableway.datetime.DateController.*;
import static com.itstep.cableway.cableways.CablewayConstants.TIME_TO_PLAY;

public class CashierWindow {

    private static final Logger log = LogManager.getLogger(CashierWindow.class);

    private CustomH2TableAdapter customH2TableAdapter;

    private List<CurrentCableway> currentCableways;

    private Definitions definitions;
    private Customer customer;

    private JComboBox<String> comboBox;

    private List<Subscription> subscriptions;
    private Subscription subscription;

    private CustomersTableModel customersTableModel;

    private JLabel newKeyLabel;

    private JFrame frame;
    private JTextField numberKeyField;
    private JTextField surnameField;
    private JTextField discountField;
    private JTextField moneyLeftOnSubscriptionForDayField;
    private JTextField payedTimeField;
    private JTextField payedMoneyField;
    private JTextField priceWithDiscountField;
    private JTextField inputMoneyField;
    private JTextField returnMoneyField;

    private boolean flagAdmin = CurrentUser.isAdministrator;
    private boolean flagNewKey;

    /**
     * Launch the application.
     */
    public static void main(final Customer customer, final CustomH2TableAdapter customH2TableAdapter, final CustomersTableModel customersTableModel, final CablewaysManager cablewaysManager) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CashierWindow window = new CashierWindow(customer, customH2TableAdapter, customersTableModel, cablewaysManager);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkNewCustomer() {
        Customer findCustomer = customH2TableAdapter.getCustomerHibernateDao().getCustomerByCardKey(customer.getCardKey());
        return (findCustomer == null);
    }

    /**
     * Create the application.
     */
    private CashierWindow(Customer customer, CustomH2TableAdapter customH2TableAdapter, CustomersTableModel customersTableModel, CablewaysManager cablewaysManager) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.customer = customer;

        flagNewKey = checkNewCustomer();

        subscriptions = customH2TableAdapter.getSubscriptionHibernateDao().findAll();
        subscription = customH2TableAdapter.getSubscriptionHibernateDao().findById((long) customer.getSubscriptionType());

        definitions = customH2TableAdapter.getDefinitionsHibernateDao().findById((long) 1);

        this.customersTableModel = customersTableModel;

        this.currentCableways = cablewaysManager.getCurrentCablewaysState();

        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();

        frame.setResizable(false);
        frame.getContentPane().setBackground(new Color(240, 230, 140));
        frame.setTitle("Окно кассира");
        frame.setBounds(100, 100, 761, 675);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel returnMoneyHotkeyHint = new JLabel("F1 - Возврат денег");
        returnMoneyHotkeyHint.setForeground(new Color(0, 0, 255));
        returnMoneyHotkeyHint.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel assignSurnameHotkeyHint = new JLabel("F2 - Присвоить фамилию");
        assignSurnameHotkeyHint.setForeground(new Color(0, 0, 255));
        assignSurnameHotkeyHint.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel payHotkeyHint = new JLabel("F4 - Оплатить");
        payHotkeyHint.setForeground(new Color(0, 0, 255));
        payHotkeyHint.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel discountHotkeyHint = new JLabel("F5 - Скидка");
        discountHotkeyHint.setForeground(new Color(0, 0, 255));
        discountHotkeyHint.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel statisticsHotkeyHint = new JLabel("F6 - Статистика");
        statisticsHotkeyHint.setForeground(new Color(0, 0, 255));
        statisticsHotkeyHint.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel numberKeyLabel = new JLabel("№ ключа");
        numberKeyLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel surnameLabel = new JLabel("Фамилия");
        surnameLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel discountLabel = new JLabel("Скидка, %");
        discountLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel subscriptionNumberLabel = new JLabel("Абонемент, №");
        subscriptionNumberLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        comboBox = new JComboBox<String>();
        comboBox.setEnabled(true);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 18));

        newKeyLabel = new JLabel("НОВЫЙ КЛЮЧ !");
        newKeyLabel.setForeground(new Color(220, 20, 60));
        newKeyLabel.setBackground(new Color(240, 248, 255));
        newKeyLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        newKeyLabel.setVisible(false);

        final JLabel daysOfWeekLabel = new JLabel("Дни недели");
        daysOfWeekLabel.setForeground(new Color(0, 100, 0));
        daysOfWeekLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        final JLabel timeForPlayLabel = new JLabel("Время проката");
        timeForPlayLabel.setForeground(new Color(0, 0, 255));
        timeForPlayLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel moneyLeftOnSubscriptionForDayLabel = new JLabel("Остаток времени по абонементу на день");
        moneyLeftOnSubscriptionForDayLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel payedTimeLabel = new JLabel("Оплаченное время");
        payedTimeLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel payedMoneyLabel = new JLabel("Оплаченная сумма");
        payedMoneyLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel priceWithDiscount = new JLabel("Стоимость проката с учетом скидки");
        priceWithDiscount.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel inputMoneyLabel = new JLabel("Вносимая сумма");
        inputMoneyLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JLabel returnMoneyLabel = new JLabel("Возврат денег");
        returnMoneyLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        numberKeyField = new JTextField();
        numberKeyField.setEditable(false);
        numberKeyField.setColumns(10);

        surnameField = new JTextField();
        surnameField.setEditable(false);
        surnameField.setColumns(10);

        discountField = new JTextField();
        discountField.setEditable(false);
        discountField.setColumns(10);

        moneyLeftOnSubscriptionForDayField = new JTextField();
        moneyLeftOnSubscriptionForDayField.setEditable(false);
        moneyLeftOnSubscriptionForDayField.setColumns(10);

        payedTimeField = new JTextField();
        payedTimeField.setEditable(false);
        payedTimeField.setColumns(10);

        payedMoneyField = new JTextField();
        payedMoneyField.setEditable(false);
        payedMoneyField.setColumns(10);

        priceWithDiscountField = new JTextField();
        priceWithDiscountField.setEditable(false);
        priceWithDiscountField.setColumns(10);

        inputMoneyField = new JTextField();
        inputMoneyField.setColumns(10);

        returnMoneyField = new JTextField();
        returnMoneyField.setEditable(false);
        returnMoneyField.setColumns(10);

        final JButton deleteKeyButton = new JButton("Удалить ключ");
        deleteKeyButton.setEnabled(false);
        deleteKeyButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        deleteKeyButton.setVisible(false);

        final JButton deleteSubscriptionButton = new JButton("Аннулировать абонемент");
        deleteSubscriptionButton.setEnabled(false);
        deleteSubscriptionButton.setForeground(new Color(0, 0, 255));
        deleteSubscriptionButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        deleteSubscriptionButton.setVisible(false);

        JLabel buySubscriptionHotkeyHint = new JLabel("F3 - Купить абонемент");
        buySubscriptionHotkeyHint.setForeground(new Color(0, 0, 255));
        buySubscriptionHotkeyHint.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel closeWindowHotkeyHint = new JLabel("ESC - Закрыть окно");
        closeWindowHotkeyHint.setForeground(new Color(0, 0, 255));
        closeWindowHotkeyHint.setFont(new Font("SansSerif", Font.BOLD, 16));
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(22)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addComponent(deleteKeyButton, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 181, Short.MAX_VALUE)
                                                .addComponent(deleteSubscriptionButton))
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addComponent(subscriptionNumberLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED, 409, Short.MAX_VALUE)
                                                .addComponent(timeForPlayLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                        .addComponent(returnMoneyHotkeyHint)
                                                                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(assignSurnameHotkeyHint))
                                                                .addGroup(groupLayout.createSequentialGroup()
                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                                .addComponent(payHotkeyHint)
                                                                                .addComponent(numberKeyLabel))
                                                                        .addGap(43)
                                                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                                .addComponent(newKeyLabel)
                                                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                                                                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                                                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                                .addComponent(daysOfWeekLabel))
                                                                                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                                                                                .addComponent(discountHotkeyHint)
                                                                                                .addGap(49)
                                                                                                .addComponent(statisticsHotkeyHint))))))
                                                        .addComponent(surnameLabel)
                                                        .addComponent(discountLabel)
                                                        .addComponent(moneyLeftOnSubscriptionForDayLabel)
                                                        .addComponent(payedTimeLabel)
                                                        .addComponent(payedMoneyLabel)
                                                        .addComponent(priceWithDiscount)
                                                        .addComponent(inputMoneyLabel)
                                                        .addComponent(returnMoneyLabel))
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(numberKeyField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(surnameField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(discountField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(moneyLeftOnSubscriptionForDayField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(payedTimeField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(payedMoneyField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(priceWithDiscountField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(inputMoneyField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                                                                        .addComponent(returnMoneyField, GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGap(61)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(closeWindowHotkeyHint)
                                                                        .addComponent(buySubscriptionHotkeyHint))))))
                                .addGap(57))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(25)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(returnMoneyHotkeyHint)
                                        .addComponent(assignSurnameHotkeyHint)
                                        .addComponent(buySubscriptionHotkeyHint))
                                .addGap(26)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(payHotkeyHint)
                                        .addComponent(discountHotkeyHint)
                                        .addComponent(statisticsHotkeyHint)
                                        .addComponent(closeWindowHotkeyHint))
                                .addGap(48)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(numberKeyLabel)
                                        .addComponent(newKeyLabel)
                                        .addComponent(numberKeyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(surnameLabel)
                                        .addComponent(surnameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(discountLabel)
                                        .addComponent(discountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(timeForPlayLabel)
                                        .addComponent(subscriptionNumberLabel)
                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(daysOfWeekLabel))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(moneyLeftOnSubscriptionForDayLabel)
                                        .addComponent(moneyLeftOnSubscriptionForDayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(payedTimeLabel)
                                        .addComponent(payedTimeField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(payedMoneyLabel)
                                        .addComponent(payedMoneyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(priceWithDiscount)
                                        .addComponent(priceWithDiscountField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(inputMoneyLabel)
                                        .addComponent(inputMoneyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(returnMoneyLabel)
                                        .addComponent(returnMoneyField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(deleteKeyButton)
                                        .addComponent(deleteSubscriptionButton))
                                .addContainerGap(18, Short.MAX_VALUE))
        );
        frame.getContentPane().setLayout(groupLayout);
        //-----------------------------------------------------------------------------
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {

                if (flagAdmin) {
                    deleteKeyButton.setEnabled(true);
                    deleteKeyButton.setVisible(true);

                    deleteSubscriptionButton.setEnabled(true);
                    deleteSubscriptionButton.setVisible(true);

                    numberKeyField.setText(String.valueOf(customer.getCardKey()));
                } else {
                    numberKeyField.setText("**" + (customer.getCardKey() % 1000));
                }

                surnameField.setText(customer.getSurname());
                discountField.setText(String.valueOf(customer.getDiscount()));

                payedTimeField.setText(String.valueOf(customer.getPayedTime()));
                payedMoneyField.setText(String.valueOf(customer.getMoneyOnKey()));

                int priceWithDiscount = (definitions.getOneMinutePrice() * (100 - customer.getDiscount())) / 100;
                priceWithDiscountField.setText(String.valueOf(priceWithDiscount));

                moneyLeftOnSubscriptionForDayField.setText(String.valueOf(customer.getTimeLeftOnSubscriptionCurrentDate()));

                daysOfWeekLabel.setVisible(false);
                timeForPlayLabel.setVisible(false);
        //--------------------------------------------------------------------------
                if (customer.getSubscriptionType() != 0) {

                    String startSubscriptionDay = null;
                    String endSubscriptionDay = null;

                    try {

                        if (subscription.getDayOfWeekStartSubscription() > daysOfWeek.length &&
                            subscription.getDayOfWeekStartSubscription() < daysOfWeek.length) {

                            startSubscriptionDay = "Error!";
                        } else {
                            startSubscriptionDay = daysOfWeek[subscription.getDayOfWeekStartSubscription()];
                        }

                        if (subscription.getDayOfWeekEndSubscription() > daysOfWeek.length &&
                            subscription.getDayOfWeekEndSubscription() < daysOfWeek.length) {

                            startSubscriptionDay = "Error!";
                        } else {
                            endSubscriptionDay = daysOfWeek[subscription.getDayOfWeekEndSubscription()];
                        }

                    } catch (NullPointerException ignored) {
                        System.out.println(ignored.getMessage());
                    }

                    String timeStartSubscription = null;
                    String timeEndSubscription = null;

                    try {
                        // Possible NullPointerException!
                        timeStartSubscription = Integer.toString(subscription.getTimeStartSubscription());
                        timeEndSubscription = Integer.toString(subscription.getTimeEndSubscription());
                    } catch (ArrayIndexOutOfBoundsException ee) {
                        log.error(ee.getMessage());
                    }

                    daysOfWeekLabel.setText(startSubscriptionDay + " - " +
                            endSubscriptionDay + "  с " +
                            timeStartSubscription + " до " +
                            timeEndSubscription);

                    timeForPlayLabel.setText("по " + customer.getSubscriptionExpirationDate() + "."
                            + customer.getSubscriptionExpirationMonth() + "."
                            + customer.getSubscriptionExpirationYear());

                    daysOfWeekLabel.setVisible(true);
                    timeForPlayLabel.setVisible(true);
                }
                //-----------------------------------------------------------------------------
                initSubscriptionsList();
                //-----------------------------------------------------------------------------
                if (flagNewKey) {
                    flagNewKey = false;
                    newKeyLabel.setVisible(true);
                }
                //-----------------------------------------------------------------------------
                checkPauseCardKey();
                //-----------------------------------------------------------------------------
                numberKeyField.setEditable(false);
                inputMoneyField.setText("");
                inputMoneyField.setEditable(true);
            }
        });
        //-----------------------------------------------------------------------------
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                inputMoneyField.requestFocusInWindow();
                //inputMoneyField.setFocusable(true);
                //KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
            }
        });
        //-----------------------------------------------------------------------------
        returnMoneyField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (returnMoneyField.isEnabled()) {
                        if (returnMoneyField.getText() == null || returnMoneyField.getText().isEmpty()) {
                            returnMoneyField.setText("0");
                        }

                        int returnMoney = Integer.parseInt(returnMoneyField.getText());

                        log.info(DateController.now() + " Ключ " + customer.getCardKey() + " возврат денег: " + returnMoney + " грн.");

                        JOptionPane.showMessageDialog(null, "Возврат денег: " + returnMoney, "Предупреждение", JOptionPane.INFORMATION_MESSAGE);

                        int tempPayedTime = 0;

                        try {
                            tempPayedTime = TIME_TO_PLAY * (returnMoney / (definitions.getOneMinutePrice() * (100 - customer.getDiscount()) / 100));
                        } catch (ArithmeticException ignored) {
                            log.error("Divide by zero: " + e);
                        }

                        customer.setPayedTime(customer.getPayedTime() - tempPayedTime);

                        if (customer.getPayedTime() < 0) {
                            customer.setPayedTime(0);
                        }

                        definitions.setReturnMoneyForDay(definitions.getReturnMoneyForDay() + returnMoney);
                        definitions.setReturnMoneyForWeek(definitions.getReturnMoneyForWeek() + returnMoney);
                        definitions.setReturnMoneyForMonth(definitions.getReturnMoneyForMonth() + returnMoney);
                        definitions.setReturnMoneyForAllTimePeriod(definitions.getReturnMoneyForAllTimePeriod() + returnMoney);

                        customer.setMoneyOnKey(customer.getMoneyOnKey() - returnMoney);

                        customer.setSumMoneyForAllDay(customer.getSumMoneyForAllDay() - returnMoney);
                        customer.setSumMoneyForAllWeek(customer.getSumMoneyForAllWeek() - returnMoney);
                        customer.setSumMoneyForAllMonth(customer.getSumMoneyForAllMonth() - returnMoney);
                        customer.setSumMoneyForAllPeriod(customer.getSumTimeForAllPeriod() - returnMoney);

                        customH2TableAdapter.getCustomerHibernateDao().update(customer);
                        customH2TableAdapter.getDefinitionsHibernateDao().update(definitions);

                        frame.dispose();
                    }
                }
            }

            @Override
             public void keyReleased(KeyEvent e) {
                if ((e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    returnMoneyField.setText("");
                }
            }
        });
        //-----------------------------------------------------------------------------
        surnameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (surnameField.isEnabled()) {
                        customer.setSurname(surnameField.getText());

                        customH2TableAdapter.getCustomerHibernateDao().update(customer);

                        frame.dispose();
                    }
                }
            }
        });
        //-----------------------------------------------------------------------------
        comboBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (comboBox.isEnabled()) {

                        int tempIndex = comboBox.getSelectedIndex();

                        if (tempIndex != 0) {
                            customer.setSubscriptionType(tempIndex);

                            int ridePrice = subscription.getSubscriptionPrice() * (100 - customer.getDiscount()) / 100;

                            customer.setTimeLeftOnSubscriptionCurrentDate(subscription.getSubscriptionTimeLimitForDay());
                            customer.setSumMoneyForAllDay(customer.getSumMoneyForAllDay() + ridePrice);
                            customer.setSumMoneyForAllWeek(customer.getSumMoneyForAllWeek() + ridePrice);
                            customer.setSumMoneyForAllMonth(customer.getSumMoneyForAllMonth() + ridePrice);
                            customer.setSumMoneyForAllPeriod(customer.getSumMoneyForAllPeriod() + ridePrice);

                            customer.setSubscriptionExpirationDate(1);
                            customer.setSubscriptionExpirationMonth(1 + 1);
                            customer.setSubscriptionExpirationYear(1);

                            definitions.setMoneyForDay(definitions.getMoneyForDay() + ridePrice);
                            definitions.setMoneyForWeek(definitions.getMoneyForWeek() + ridePrice);
                            definitions.setMoneyForMonth(definitions.getMoneyForMonth() + ridePrice);
                            definitions.setMoneyForAllTimePeriod(definitions.getMoneyForAllTimePeriod() + ridePrice);

                            log.info(DateController.now() + " Ключ " + customer.getCardKey() + " куплен абонемент №: " + tempIndex + ", оплачено " + ridePrice + " грн.");

                            JOptionPane.showMessageDialog(null, "Куплен абонемент № " + comboBox.getSelectedIndex(), "Предупреждение", JOptionPane.INFORMATION_MESSAGE);

                            customH2TableAdapter.getCustomerHibernateDao().update(customer);
                            customH2TableAdapter.getDefinitionsHibernateDao().update(definitions);

                            frame.dispose();
                        }
                    }
                }
            }
        });
        //-----------------------------------------------------------------------------
        inputMoneyField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (inputMoneyField.isEnabled()) {
                        if (inputMoneyField.getText() == null || inputMoneyField.getText().isEmpty()) {
                            inputMoneyField.setText("0");
                        }

                        int getMoney = Integer.parseInt(inputMoneyField.getText());

                        log.info(DateController.now() + " Ключ " + customer.getCardKey() + " внесено: " + getMoney + " грн.");

                        JOptionPane.showMessageDialog(null, "Оплата средств: " + getMoney, "Предупреждение", JOptionPane.INFORMATION_MESSAGE);

                        definitions.setMoneyForDay(definitions.getMoneyForDay() + getMoney);
                        definitions.setMoneyForWeek(definitions.getMoneyForWeek() + getMoney);
                        definitions.setMoneyForMonth(definitions.getMoneyForMonth() + getMoney);
                        definitions.setMoneyForAllTimePeriod(definitions.getMoneyForAllTimePeriod() + getMoney);

                        int tempGetMoney = 0;

                        try {
                            tempGetMoney = TIME_TO_PLAY * (getMoney / (definitions.getOneMinutePrice() * (100 - customer.getDiscount()) / 100));
                        } catch (ArithmeticException ignored) {
                            // Divide by zero!
                            log.error(ignored.getMessage());
                        }

                        customer.setPayedTime(customer.getPayedTime() + tempGetMoney);
                        customer.setMoneyOnKey(customer.getMoneyOnKey() + getMoney);

                        customer.setSumMoneyForAllDay(customer.getSumMoneyForAllDay() + getMoney);
                        customer.setSumMoneyForAllWeek(customer.getSumMoneyForAllWeek() + getMoney);
                        customer.setSumMoneyForAllMonth(customer.getSumMoneyForAllMonth() + getMoney);
                        customer.setSumMoneyForAllPeriod(customer.getSumTimeForAllPeriod() + getMoney);

                        customH2TableAdapter.getDefinitionsHibernateDao().update(definitions);
                        customH2TableAdapter.getCustomerHibernateDao().update(customer);

                        frame.dispose();
                    }
                }
            }
            //-----------------------------------------------------------------------------
            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    returnMoneyField.setText("");
                }
            }
        });
        //-----------------------------------------------------------------------------
        discountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (discountField.isEnabled()) {

                        if (discountField.getText() == null || discountField.getText().isEmpty()) {
                            discountField.setText("0");
                        }

                        int discount = Integer.parseInt(discountField.getText());

                        if (discount >= 0 && discount <= 99) {
                            customer.setDiscount(discount);
                        }

                        customH2TableAdapter.getCustomerHibernateDao().update(customer);

                        frame.dispose();
                    }
                }
            }
            //-----------------------------------------------------------------------------
            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    returnMoneyField.setText("");
                }
            }
        });
        //-----------------------------------------------------------------------------
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    // Return money
                    case KeyEvent.VK_F1:
                        comboBox.setEnabled(false);
                        surnameField.setEditable(false);
                        discountField.setEditable(false);
                        inputMoneyField.setEditable(false);

                        returnMoneyField.setText("");
                        returnMoneyField.setEditable(true);
                        returnMoneyField.requestFocusInWindow();
                        break;
                    // Assign surname
                    case KeyEvent.VK_F2: {
                        if (customer.getSurname().equals(" ") || flagAdmin) {
                            comboBox.setEnabled(false);

                            surnameField.setEditable(true);
                            surnameField.requestFocusInWindow();

                            discountField.setEditable(false);
                            inputMoneyField.setEditable(false);
                            returnMoneyField.setEditable(false);
                        }

                        break;
                    }
                    // Buy subscription
                    case KeyEvent.VK_F3: {
                        if (customer.getSubscriptionType() == 0) {
                            comboBox.setEnabled(true);
                            comboBox.requestFocusInWindow();

                            surnameField.setEditable(false);
                            discountField.setEditable(false);
                            inputMoneyField.setEditable(false);
                            returnMoneyField.setEditable(false);
                        }

                        break;
                    }
                    // Pay money
                    case KeyEvent.VK_F4:
                        comboBox.setEnabled(false);
                        surnameField.setEditable(false);
                        discountField.setEditable(false);

                        inputMoneyField.setEditable(true);
                        inputMoneyField.requestFocusInWindow();

                        returnMoneyField.setEditable(false);
                        break;
                    // Discount
                    case KeyEvent.VK_F5: {
                        if (flagAdmin) {
                            comboBox.setEnabled(false);
                            surnameField.setEditable(false);

                            discountField.setEditable(true);
                            discountField.requestFocusInWindow();

                            inputMoneyField.setEditable(false);
                            returnMoneyField.setEditable(false);
                        }

                        break;
                    }
                    // Open statistics window
                    case KeyEvent.VK_F6: {
                        if (flagAdmin) {
                            KeyStatisticWindow.main(customer);
                        }

                        break;
                    }
                    // Close window
                    case KeyEvent.VK_ESCAPE:
                        frame.dispose();
                        break;
                    case KeyEvent.VK_ENTER:
                        break;
                }
            }
        });
        //-----------------------------------------------------------------------------
        deleteKeyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int deleteKeyResult = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить ключ?", "Удаление ключа", JOptionPane.YES_NO_OPTION);

                if (deleteKeyResult == JOptionPane.YES_OPTION) {

                    // Double check: implement hibernate ID reordering strategy.

                    log.info(DateController.now() + " Удален ключ " + customer.getCardKey());

                    customH2TableAdapter.getCustomerHibernateDao().delete(customer.getId());
                    customersTableModel.reloadDatabase();

                    frame.dispose();
                }
            }
        });
        //-----------------------------------------------------------------------------
        deleteSubscriptionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int deleteSubscriptionResult = JOptionPane.showConfirmDialog(null, "Вы действительно хотите удалить абонемент?", "Удаление абонемента", JOptionPane.YES_NO_OPTION);

                if (deleteSubscriptionResult == JOptionPane.YES_OPTION) {

                    if (flagAdmin) {
                        customer.setSubscriptionType(0);
                        customer.setTimeLeftOnSubscriptionCurrentDate(0);

                        customH2TableAdapter.getCustomerHibernateDao().update(customer);

                        daysOfWeekLabel.setText(" ");
                        timeForPlayLabel.setText(" ");

                        comboBox.setSelectedIndex(0);
                    }
                }
            }
        });
        //-----------------------------------------------------------------------------
        surnameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (customer.getSurname().equals(" ")) {
                    surnameField.setEnabled(true);
                }
            }
        });
        //-----------------------------------------------------------------------------
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                customersTableModel.reloadDatabase();
            }
        });
    }
    //-----------------------------------------------------------------------------
    public void checkPauseCardKey() {
        for (CurrentCableway currentCableway : currentCableways) {
            if (currentCableway.getCodeCardPrevent1() == customer.getCardKey() ||
                currentCableway.getCodeCardPrevent2() == customer.getCardKey()) {
                newKeyLabel.setVisible(true);
                newKeyLabel.setText("Пауза");
                break;
            }
        }
    }
    //-----------------------------------------------------------------------------
    public void initSubscriptionsList() {
        String temp;
        comboBox.addItem("");

        for (int i = 0; i < subscriptions.size(); i++) {
            int subPrice = (subscriptions.get(i).getSubscriptionPrice() * (100 - customer.getDiscount())) / 100;
            temp = new StringBuilder().append(i+1).append(" - ").append(subPrice).append(" грн").toString();
            comboBox.addItem(temp);
        }

        try {
            comboBox.setSelectedIndex(customer.getSubscriptionType());
        } catch (IndexOutOfBoundsException e1) {
            log.error(e1.getMessage());
        }
    }
    //-----------------------------------------------------------------------------
}
