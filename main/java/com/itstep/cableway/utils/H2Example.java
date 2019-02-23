package com.itstep.cableway.utils;

import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.db.dao.implementation.hibernate.*;
import com.itstep.cableway.db.entities.*;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class H2Example {

    private static final Logger log = LogManager.getLogger(H2Example.class);

    private CustomH2TableAdapter customH2TableAdapter;

	private Random random;
	
    private final static int MAX_CUSTOMERS = 80;
    private final static int MAX_SUBSCRIPTIONS = 16;
    private final static int MAX_CABLEWAYS = 2;

    private String[] surnames = new String[] {

                "Кучеренко О.П",
                "Андронов Р.Г",
                "Сидоренко С.П",
                "Савичев Д.П",
                "Димитрова О.О",
                "Юцик Л.М",
                "Малиниченко П.О",
                "Саваева У.П",
                "Угличев И.В",
                "Пономаренко Г.Г",

                "Матюхин С.П",
                "Смирнова О.Г",
                "Захара У.П",
                "Аврах А.П",
                "Политаева И.И",
                "Колесникова Н.Н",
                "Манухина Ю.П",
                "Овчинник М.Н",
                "Пинаев В.П",
                "Гусакова Т.Н",

                "Горащенко Р.М",
                "Гребенюк Т.П",
                "Серебрин Н.Г",
                "Покатило Л.Я",
                "Стоянчев Е.И",
                "Задорожный К.И",
                "Писарева Т.П",
                "Пиминова М.Г",
                "Куйбышев В.С",
                "Наливайченко Д.А",

                "Сокол П.П",
                "Стрижевская В.И",
                "Миронова А.П",
                "Закарпатский Л.Л",
                "Повайло В.В",
                "Оберной Д.В",
                "Ульрих Л.П",
                "Колотов М.И",
                "Макарова О.Н",
                "Калашников И.В",

                "Васильев Г.М",
                "Добарев Р.И",
                "Матицкий О.П",
                "Долгорукая А.И",
                "Поливайченко Е.Г",
                "Мамонтов С.С",
                "Зыгало А.В",
                "Бивень Е.Б",
                "Расманов К.Н",
                "Краснощеков С.М",

                "Ничипоренко О.П",
                "Калинов П.П",
                "Боярова И.Г",
                "Нечаева С.П",
                "Подольская О.П",
                "Портной И.Г",
                "Каверина О.В",
                "Карелин И.А",
                "Чашкин Г.О",
                "Олянишин В.И",

                "Шашин Д.А",
                "Долбиков Г.С",
                "Бобарев И.В",
                "Деева А.А",
                "Фридлянд А.О",
                "Помайницкий Г.П",
                "Видовая Н.П",
                "Картанов Д.В",
                "Глющ Г.Н",
                "Валиев Е.П",

                "Повх Т.А",
                "Дибрицкая А.А",
                "Сабриев Г.Г",
                "Васильевич Н.Г",
                "Панасенко И.И",
                "Кайдашев С.И",
                "Можарова О.И",
                "Калинчик Н.П",
                "Бобрик Г.М",
                "Кастромской С.О"
    };

    public H2Example(CustomH2TableAdapter customH2TableAdapter) {
        this.customH2TableAdapter = customH2TableAdapter;
		this.random = new Random();
    }

    public void processDb() {
        if (isDbEmpty()) {
            fillInDb();
            log.info(DateController.now() + " База данных отсутствует! Создается тестовый экземпляр.");
            //JOptionPane.showMessageDialog(null, "Db is empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            log.info(DateController.now() + " База данных успешно загружена.");
        }
    }

    private boolean isDbEmpty() {
        boolean result = false;

        int numCustomers = customH2TableAdapter.getCustomerHibernateDao().findAll().size();
        int numSubscriptions = customH2TableAdapter.getSubscriptionHibernateDao().findAll().size();
        int numCablewayDatas = customH2TableAdapter.getCablewayDataHibernateDao().findAll().size();
        int numUsers = customH2TableAdapter.getUserHibernateDao().findAll().size();
        int numDefinitions = customH2TableAdapter.getDefinitionsHibernateDao().findAll().size();

        int tablesCounter = numCustomers + numSubscriptions + numCablewayDatas + numUsers + numDefinitions;

        if (tablesCounter == 0) {
            result = true;
        }

        return result;
    }

    private void fillInDb() {
        createTemplateDefinitions();
        createCablewayData();
        createUsers();
        //createFixedCustomers();
        createCustomers();
        createSubscriptions();
    }

    private void createCustomers() {
        CustomerHibernateDao customerHibernateDao = customH2TableAdapter.getCustomerHibernateDao();

        Customer[] customers = new Customer[MAX_CUSTOMERS];
        Customer customer;

        String alphabet = "abcdefghijklmnoprstuvwxyz";

        for (int i = 0; i < customers.length; i++)
        {
            customer = new Customer();

            customer.setCardKey(Math.abs(random.nextInt()));
			
            customer.setSubscriptionExpirationDate(random.nextInt(31));
            customer.setSubscriptionExpirationMonth(random.nextInt(12));
            customer.setSubscriptionExpirationYear(random.nextInt(1) + 2014);
            
			customer.setSubscriptionType(random.nextInt(10));
            
			customer.setDiscount(random.nextInt(100));
            customer.setMoneyOnKey(random.nextInt(200));
            customer.setPayedTime(random.nextInt(200));
            customer.setTimeLeftOnSubscriptionCurrentDate(random.nextInt(200));
            
			int sumMoneyForAllDay = random.nextInt(200);
			int sumMoneyForAllWeek = random.nextInt(sumMoneyForAllDay + random.nextInt(2000));
			int sumMoneyForAllMonth = random.nextInt(sumMoneyForAllWeek + random.nextInt(20000));
			int sumMoneyForAllPeriod = random.nextInt(sumMoneyForAllMonth + random.nextInt(200000));
			
			customer.setSumMoneyForAllDay(sumMoneyForAllDay);
            customer.setSumMoneyForAllWeek(sumMoneyForAllWeek);
            customer.setSumMoneyForAllMonth(sumMoneyForAllMonth);
            customer.setSumTimeForAllPeriod(sumMoneyForAllPeriod);
            
			//customer.setSurname(alphabet.substring(random.nextInt(alphabet.length())));
            customer.setSurname(surnames[i]);

            customers[i] = customer;
        }

        for (Customer tempCustomer : customers)
        {
            customerHibernateDao.save(tempCustomer);
        }
    }

    private void createFixedCustomers() {
        CustomerHibernateDao customerHibernateDao = customH2TableAdapter.getCustomerHibernateDao();
        Customer customer1, customer2;

        //----------------------------------------------------------------------------
        customer1 = new Customer();

        customer1.setCardKey(111000111);
        customer1.setSubscriptionExpirationDate(DateController.currentDay + 7);
        customer1.setSubscriptionExpirationMonth(DateController.currentMonth + 3);
        customer1.setSubscriptionExpirationYear(DateController.currentYear + 1);
        customer1.setSubscriptionType(1);
        customer1.setDiscount(50);
        customer1.setMoneyOnKey(170);
        customer1.setPayedTime(100);
        customer1.setTimeLeftOnSubscriptionCurrentDate(120);
        customer1.setSumMoneyForAllDay(200);
        customer1.setSumMoneyForAllWeek(200*7);
        customer1.setSumMoneyForAllMonth(200*7*4);
        customer1.setSumTimeForAllPeriod(200*7*4*12);
        customer1.setSurname("Doblo");

        customerHibernateDao.save(customer1);
        //----------------------------------------------------------------------------
        customer2 = new Customer();

        customer2.setCardKey(111000222);
        customer2.setSubscriptionExpirationDate(DateController.currentDay + 7);
        customer2.setSubscriptionExpirationMonth(DateController.currentMonth + 3);
        customer2.setSubscriptionExpirationYear(DateController.currentYear + 1);
        customer2.setSubscriptionType(1);
        customer2.setDiscount(45);
        customer2.setMoneyOnKey(180);
        customer2.setPayedTime(120);
        customer2.setTimeLeftOnSubscriptionCurrentDate(120);
        customer2.setSumMoneyForAllDay(200);
        customer2.setSumMoneyForAllWeek(200*7);
        customer2.setSumMoneyForAllMonth(200*7*4);
        customer2.setSumTimeForAllPeriod(200*7*4*12);
        customer2.setSurname("Dibloid");

        customerHibernateDao.save(customer2);
        //----------------------------------------------------------------------------
    }

    private void createSubscriptions() {
        SubscriptionHibernateDao subscriptionHibernateDao = customH2TableAdapter.getSubscriptionHibernateDao();

        Subscription[] subscriptions = new Subscription[MAX_SUBSCRIPTIONS];
        Subscription subscription;

        for (int i = 0; i < subscriptions.length; i++) {
            subscription = new Subscription();

            subscription.setTimeStartSubscription(random.nextInt(12));
            subscription.setTimeEndSubscription(random.nextInt(24));

            subscription.setDayOfWeekStartSubscription(random.nextInt(6));
            subscription.setDayOfWeekEndSubscription(random.nextInt(6));

            subscription.setSubscriptionTimeLimitForDay(random.nextInt(5));

            subscription.setSubscriptionPrice(random.nextInt(60));

            subscriptions[i] = subscription;
        }

        for (Subscription tempSubscription : subscriptions) {
            subscriptionHibernateDao.save(tempSubscription);
        }
    }

    private void createTemplateDefinitions() {
        DefinitionsHibernateDao definitionsHibernateDao = customH2TableAdapter.getDefinitionsHibernateDao();
        Definitions definitions = new Definitions();

        definitions.setOneMinutePrice(25);

        definitions.setMoneyForDay(250);
        definitions.setMoneyForWeek(400);
        definitions.setMoneyForMonth(1250);
        definitions.setMoneyForAllTimePeriod(2600);

        definitions.setReturnMoneyForDay(120);
        definitions.setReturnMoneyForWeek(420);
        definitions.setReturnMoneyForMonth(730);
        definitions.setReturnMoneyForAllTimePeriod(1130);

        definitions.setLastDayOfWeek(5);

        definitions.setLastDay(12);
        definitions.setLastMonth(7);
        definitions.setLastYear(2014);

        definitionsHibernateDao.save(definitions);
    }

    private void createCablewayData() {
        CablewayDataHibernateDao cablewayDataHibernateDao = customH2TableAdapter.getCablewayDataHibernateDao();

        CablewayData[] cablewayDatas = new CablewayData[MAX_CABLEWAYS];
        CablewayData cablewayData;

        Random random = new Random();

        for (int i = 0; i < cablewayDatas.length; i++) {
            cablewayData = new CablewayData();

			int sumTimePlayedForDay = random.nextInt(1000);
			int sumTimePlayedForWeek = random.nextInt(sumTimePlayedForDay + random.nextInt(10000));
			int sumTimePlayedForMonth = random.nextInt(sumTimePlayedForWeek + random.nextInt(100000));
			int sumTimePlayedForAllPeriod = random.nextInt(sumTimePlayedForMonth + random.nextInt(1000000));
			
            cablewayData.setSumPlayedTimeCablewayForDay(sumTimePlayedForDay);
            cablewayData.setSumPlayedTimeCablewayForWeek(sumTimePlayedForWeek);
            cablewayData.setSumPlayedTimeCablewayForMonth(sumTimePlayedForMonth);
            cablewayData.setSumPlayedTimeCablewayForAllPeriod(sumTimePlayedForAllPeriod);

            cablewayDatas[i] = cablewayData;
        }

        for (CablewayData tempData : cablewayDatas) {
            cablewayDataHibernateDao.save(tempData);
        }
    }

    private void createUsers() {
        UserHibernateDao userHibernateDao = customH2TableAdapter.getUserHibernateDao();

        User userCashier1 = new User();

        userCashier1.setName("Кассир 1");
        userCashier1.setPassword("");
        userCashier1.setAdministrator(false);
        userCashier1.setRoot(false);

        User userCashier2 = new User();

        userCashier2.setName("Кассир 2");
        userCashier2.setPassword("cas2");
        userCashier2.setAdministrator(false);
        userCashier2.setRoot(false);

        User userAdministrator = new User();

        userAdministrator.setName("Администратор");
        userAdministrator.setPassword("admin");
        userAdministrator.setAdministrator(true);
        userAdministrator.setRoot(false);

        userHibernateDao.save(userCashier1);
        userHibernateDao.save(userCashier2);
        userHibernateDao.save(userAdministrator);
    }
}