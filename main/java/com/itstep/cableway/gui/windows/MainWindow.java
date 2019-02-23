package com.itstep.cableway.gui.windows;

import com.itstep.cableway.cableways.CablewaysManager;
import com.itstep.cableway.cableways.CablewayConstants;
import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.utils.CurrentUser;
import com.itstep.cableway.db.entities.Customer;
import com.itstep.cableway.db.entities.Definitions;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.db.utils.HibernateUtils;
import com.itstep.cableway.gui.tables.CablewaysTableModel;
import com.itstep.cableway.gui.tables.CustomersTableModel;
import com.itstep.cableway.gui.tables.SubscriptionsTableModel;
import com.itstep.cableway.ports.DeviceManager;
import com.itstep.cableway.ports.RFIDReader;
import com.itstep.cableway.ports.RFIDReaderCashier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.event.*;

public class MainWindow {

    private static final Logger log = LogManager.getLogger(MainWindow.class);

    private CustomH2TableAdapter customH2TableAdapter;
    private DeviceManager deviceManager;
    private CablewaysManager cablewaysManager;

    private CustomersTableModel customersTableModel;

    private RFIDReaderCashier rfidReaderCashier;

    private RFIDReader[] rfidReaders;

	private JFrame frame;
	private JTable subscriptionTable;
	private JTable cablewayTable;
	private JTable customersTable;

    private JList<String> list;

	/**
	 * Launch the application.
	 */
	public static void main(final CustomH2TableAdapter customH2TableAdapter, final DeviceManager deviceManager, final CablewaysManager cablewaysManager) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
            log.error(e.getMessage());
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow(customH2TableAdapter, deviceManager, cablewaysManager);
					window.frame.setVisible(true);
				} catch (Exception e) {
                    log.error(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	private MainWindow(CustomH2TableAdapter customH2TableAdapter, DeviceManager deviceManager, CablewaysManager cablewaysManager) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.deviceManager = deviceManager;
        this.cablewaysManager = cablewaysManager;
        this.rfidReaders = this.cablewaysManager.getRfidReaders();
        this.rfidReaderCashier = cablewaysManager.getRfidReaderCashier();
        this.cablewaysManager.setMainWindow(this);
        this.list = new JList<String>();

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Главное окно");
		frame.getContentPane().setBackground(new Color(240, 230, 140));
		frame.setBounds(100, 100, 1201, 780);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JLabel statisticHintLabel = new JLabel("F6 - Статистика по браслету");
		statisticHintLabel.setForeground(new Color(0, 0, 255));
		statisticHintLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		final JLabel usernameHintLabel = new JLabel("Имя пользователя");
		usernameHintLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        usernameHintLabel.setForeground(Color.RED);
		
		JLabel moneyCountHintLabel = new JLabel("Пересчет:");
		moneyCountHintLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		JLabel subscriptionsHintLabel = new JLabel("Абонементы");
        subscriptionsHintLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		final JLabel inputMoneyCountLabel = new JLabel("New label");
		inputMoneyCountLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		JScrollPane cablewayScrollPane = new JScrollPane();
		
		JScrollPane customersScrollPane = new JScrollPane();
		
		JScrollPane subscriptionScrollPane = new JScrollPane();
		
		final JLabel timerLabel = new JLabel("0 : 00");
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setBackground(new Color(255, 255, 255));
		timerLabel.setFont(new Font("SansSerif", Font.BOLD, 40));
		timerLabel.setOpaque(true);
		
		JScrollPane messagesScrollPane = new JScrollPane();
		
		JLabel messagesWindowLabel = new JLabel("Системные сообщения");
		messagesWindowLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		JLabel hiddenLabel = new JLabel("Статистика");
		hiddenLabel.setFont(new Font("SansSerif", Font.BOLD, 16));

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(256)
							.addComponent(messagesWindowLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(messagesScrollPane, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
								.addComponent(customersScrollPane, GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(subscriptionScrollPane, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(statisticHintLabel)
											.addGap(30)
											.addComponent(usernameHintLabel))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(moneyCountHintLabel)
											.addGap(18)
											.addComponent(inputMoneyCountLabel))
										.addComponent(cablewayScrollPane, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(211)
									.addComponent(hiddenLabel)))
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(timerLabel, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
							.addGap(160))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(subscriptionsHintLabel)
							.addGap(204))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(statisticHintLabel)
								.addComponent(usernameHintLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(moneyCountHintLabel)
								.addComponent(inputMoneyCountLabel))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(subscriptionsHintLabel)
							.addGap(6))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(messagesWindowLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(messagesScrollPane, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(customersScrollPane, GroupLayout.PREFERRED_SIZE, 572, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(subscriptionScrollPane, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(hiddenLabel)
							.addGap(11)
							.addComponent(cablewayScrollPane, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
							.addComponent(timerLabel, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(18)))
					.addGap(14))
		);

        frame.getContentPane().setLayout(groupLayout);

        list.setForeground(new Color(255, 0, 0));

        list.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JOptionPane.showMessageDialog(null, list.getSelectedValue(), "Сообщение", JOptionPane.ERROR_MESSAGE);
                }

                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getClickCount() == 2 && !e.isConsumed()) {
                        e.consume();
                        //handle double click.
                        JOptionPane.showMessageDialog(null, list.getSelectedValue(), "Сообщение", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

		messagesScrollPane.setViewportView(list);

		subscriptionTable = new JTable();

        SubscriptionsTableModel subscriptionsTableModel = new SubscriptionsTableModel(customH2TableAdapter);
        subscriptionTable.setModel(subscriptionsTableModel);

        subscriptionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        subscriptionTable.getTableHeader().setReorderingAllowed(false);
        subscriptionTable.getTableHeader().setResizingAllowed(false);

        // remove editor
        for (int i = 0; i < subscriptionTable.getColumnCount(); i++)
        {
            Class<?> subscriptionColumn_class = subscriptionTable.getColumnClass(i);
            subscriptionTable.setDefaultEditor(subscriptionColumn_class, null);
        }

        subscriptionTable.getTableHeader().setPreferredSize(new Dimension(40, 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        subscriptionTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Added.
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }
            }
        });

        subscriptionScrollPane.setViewportView(subscriptionTable);

        customersTable = new JTable();

        customersTableModel = new CustomersTableModel(customH2TableAdapter);
        customersTable.setModel(customersTableModel);

        customersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customersTable.getTableHeader().setReorderingAllowed(false);
        customersTable.getTableHeader().setResizingAllowed(false);

        // Remove editor
        for (int i = 0; i < customersTable.getColumnCount(); i++)
        {
            Class<?> col_class = customersTable.getColumnClass(i);
            customersTable.setDefaultEditor(col_class, null);
        }

        customersTable.getColumnModel().getColumn(0).setPreferredWidth(56);
        customersTable.getColumnModel().getColumn(1).setPreferredWidth(97);
        customersTable.getColumnModel().getColumn(2).setPreferredWidth(136);
        customersTable.getColumnModel().getColumn(3).setPreferredWidth(140);
        customersTable.getColumnModel().getColumn(4).setPreferredWidth(92);
        customersTable.getColumnModel().getColumn(5).setPreferredWidth(103);

        customersTable.getTableHeader().setPreferredSize(new Dimension(50, 50));

        customersTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Customer tmpCustomer = customersTableModel.getCurrentCustomer(customersTable.getSelectedRow());
                    CashierWindow.main(tmpCustomer, customH2TableAdapter, customersTableModel, cablewaysManager);
                }
            }
        });

        customersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (e.getClickCount() == 2 && !e.isConsumed()) {
                        e.consume();
                        //handle double click.
                        Customer tmpCustomer = customersTableModel.getCurrentCustomer(customersTable.getSelectedRow());
                        CashierWindow.main(tmpCustomer, customH2TableAdapter, customersTableModel, cablewaysManager);
                    }
                }
            }
        });

        customersScrollPane.setViewportView(customersTable);

        cablewayTable = new JTable();
		cablewayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        final CablewaysTableModel cablewaysTableModel = new CablewaysTableModel(cablewaysManager);
        cablewayTable.setModel(cablewaysTableModel);

		cablewayTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        cablewayTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        cablewayTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		
		cablewayTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cablewayTable.getTableHeader().setReorderingAllowed(false);
		cablewayTable.getTableHeader().setResizingAllowed(false);

        // Remove editor
        for (int i = 0; i < cablewayTable.getColumnCount(); i++)
        {
            Class<?> col_class = cablewayTable.getColumnClass(i);
            cablewayTable.setDefaultEditor(col_class, null);
        }
        
        cablewayTable.getTableHeader().setPreferredSize(new Dimension(40,40));

        cablewayTable.setRowSelectionInterval(0, 0);

        cablewaysManager.setCablewayTable(cablewayTable);
        cablewaysManager.setTimerLabel(timerLabel);

        cablewayTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() >= KeyEvent.VK_F1 || e.getKeyChar() <= KeyEvent.VK_F6) {
                    frame.dispatchEvent(e);
                }
            }
        });

        cablewayScrollPane.setViewportView(cablewayTable);

        JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Файл");
		menuBar.add(menuFile);
		
		JMenuItem menuItemChangeUser = new JMenuItem("Сменить пользователя");
		menuItemChangeUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				AccessWindow.main(customH2TableAdapter, deviceManager, cablewaysManager);
				frame.dispose();
			}
		});
		menuFile.add(menuItemChangeUser);
		
		JMenuItem menuItemExit = new JMenuItem("Выход");
		menuItemExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                cleanResources();
                System.exit(0);
            }
        });
		menuFile.add(menuItemExit);
		
		final JMenu menuWindow = new JMenu("Окно");
		menuBar.add(menuWindow);
		
		JMenuItem menuItemAdminWindow = new JMenuItem("Окно администратора");
        menuItemAdminWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                AdminWindow.main(customH2TableAdapter, subscriptionTable);
            }
        });
		menuWindow.add(menuItemAdminWindow);

		cablewayTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                timerLabel.setText((String) cablewayTable.getValueAt(cablewayTable.getSelectedRow(), 1));
            }
        });

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                frame.requestFocusInWindow();
                //frame.setTitle("Focus gained");
            }

            @Override
            public void windowLostFocus(WindowEvent e) {}
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F6) {
                    frame.setTitle("Works");

                    Customer tmpCustomer = customersTableModel.getCurrentCustomer(customersTable.getSelectedRow());
                    KeyStatisticWindow.main(tmpCustomer);
                }

                // For demonstration purposes only!
                if (e.getKeyCode() == KeyEvent.VK_F1) {
                    // Ride!
                    rfidReaders[0].rideFirstCustomer();
                }
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    // Customer wants to ride
                    rfidReaderCashier.applyPresentKey();
                }
                if (e.getKeyCode() == KeyEvent.VK_F3) {
                    // Double time ride!
                    try {
                        rfidReaders[0].rideFirstCustomerDoubleTime();
                    } catch (Exception e1) {
                        log.error(e1.getMessage());
                    }
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                if (deviceManager.isDemoState()) {
                    frame.setTitle(frame.getTitle() + " - Автономная работа");
                }

                if (!CurrentUser.isAdministrator) {
                    menuWindow.setVisible(false);
                }

                usernameHintLabel.setText(CurrentUser.name);
                Definitions definitions = customH2TableAdapter.getDefinitionsHibernateDao().findById((long) 1);
                inputMoneyCountLabel.setText(definitions.getOneMinutePrice() + " грн / " + CablewayConstants.TIME_TO_PLAY + " мин");
            }

            @Override
            public void windowClosing(WindowEvent e) {
                cleanResources();
            }
        });
    }

    public void runCashierWindowPresentationKey(int cardKey) {
        Customer customer = customH2TableAdapter.getCustomerHibernateDao().getCustomerByCardKey(cardKey);
        CashierWindow.main(customer, customH2TableAdapter, customersTableModel, cablewaysManager);
    }

    public void cleanResources() {
        deviceManager.destroy();
        HibernateUtils.close();

        log.info(DateController.now() + " Успешное завершение программы");

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
