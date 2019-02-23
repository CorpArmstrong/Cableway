package com.itstep.cableway.gui.windows;

import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.db.entities.CablewayData;
import com.itstep.cableway.db.entities.Definitions;
import com.itstep.cableway.db.entities.Subscription;
import com.itstep.cableway.db.entities.User;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.gui.tables.CablewayDataTableModel;
import com.itstep.cableway.gui.tables.ManageSubscriptionsTableModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AdminWindow {

    private static final Logger log = LogManager.getLogger(AdminWindow.class);

    private CustomH2TableAdapter customH2TableAdapter;

    private java.util.List<User> userList;

    private Definitions definitions;

    private JTable subscriptionsTable;

    private CablewayDataTableModel cablewayDataTableModel;

    private User userAdmin;
    private String userAdminPwd;

	private JFrame frame;
	private JTable manageSubscriptionsTable;
	private JScrollPane cablewayDataScrollPane;
	private JTextField textField;
	private JButton saveStatisticsButton;
	private JTable cablewaysDataTable;
	private JPasswordField accessPasswordField;
	private JPasswordField oldPwdPasswordField;
	private JPasswordField newPwdPasswordField;
	private JButton confirmChangePasswordButton;

    private JComboBox<String> usersList = new JComboBox<String>();

    private JLabel inputMoneyDayLabel;
    private JLabel inputMoneyForWeekLabel;
    private JLabel inputWeekLabel;
    private JLabel inputMoneyForMonthLabel;
    private JLabel inputMonthLabel;
    private JLabel inputMoneyForAllTimePeriodLabel;
    private JLabel inputMoneyPeriodLabel;
    private JLabel returnMoneyForDayLabel;
    private JLabel returnDayLabel;
    private JLabel returnMoneyForWeekLabel;
    private JLabel returnWeekLabel;
    private JLabel returnMoneyForMonthLabel;
    private JLabel returnMonthLabel;
    private JLabel returnMoneyForAllTimePeriodLabel;
    private JLabel returnPeriodLabel;

	/**
	 * Launch the application.
	 */
	public static void main(final CustomH2TableAdapter customH2TableAdapter, final JTable subscriptionsTable) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Throwable e) {
            log.error(e.getMessage());
        }

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWindow window = new AdminWindow(customH2TableAdapter, subscriptionsTable);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminWindow(CustomH2TableAdapter customH2TableAdapter, JTable subscriptionsTable) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.definitions = this.customH2TableAdapter.getDefinitionsHibernateDao().findById((long) 1);
        this.subscriptionsTable = subscriptionsTable;
        this.userList = this.customH2TableAdapter.getUserHibernateDao().findAll();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setTitle("Окно администратора");
		frame.setBounds(100, 100, 1280, 800);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		JScrollPane manageSubscriptionsScrollPane = new JScrollPane();

        cablewayDataScrollPane = new JScrollPane();
		
		JLabel lblPriceForOne = new JLabel("Цена за 5 минут:");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblGrn = new JLabel("грн");
		
		JButton btnSaveChanges = new JButton("Сохранить изменения");

        saveStatisticsButton = new JButton("Сохранить статистику");

		JLabel changePasswordsHotkeyLabel = new JLabel("Для изменения паролей введите текущий пароль администратора");

        accessPasswordField = new JPasswordField();
		
		JButton confirmChangeAccess = new JButton("OK");

        oldPwdPasswordField = new JPasswordField();
        newPwdPasswordField = new JPasswordField();
		
		confirmChangePasswordButton = new JButton("Сохранить пароль");
        JLabel intputMoneyForDay = new JLabel("Деньги за день:");

        inputMoneyDayLabel = new JLabel("input_day");

        inputMoneyForWeekLabel = new JLabel("Деньги за неделю:");

        inputWeekLabel = new JLabel("input_week");

        inputMoneyForMonthLabel = new JLabel("Деньги за месяц:");

        inputMonthLabel = new JLabel("input_month");

        inputMoneyForAllTimePeriodLabel = new JLabel("Деньги за весь период:");

        inputMoneyPeriodLabel = new JLabel("input_period");

        returnMoneyForDayLabel = new JLabel("Возврат денег за день:");

        returnDayLabel = new JLabel("return_day");

        returnMoneyForWeekLabel = new JLabel("Возврат денег за неделю:");

        returnWeekLabel = new JLabel("return_week");

        returnMoneyForMonthLabel = new JLabel("Возврат денег за месяц:");

        returnMonthLabel = new JLabel("return_month");

        returnMoneyForAllTimePeriodLabel = new JLabel("Возврат денег за весь период:");

        returnPeriodLabel = new JLabel("return_period");
        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(accessPasswordField, GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
                                                                .addGap(57)
                                                                .addComponent(usersList, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(86))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(confirmChangeAccess, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(490)))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(oldPwdPasswordField, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                                .addGap(29)
                                                .addComponent(newPwdPasswordField, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                                .addGap(40)
                                                .addComponent(confirmChangePasswordButton)
                                                .addGap(66))
                                        .addComponent(manageSubscriptionsScrollPane, GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
                                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addPreferredGap(ComponentPlacement.RELATED, 436, Short.MAX_VALUE)
                                                                .addComponent(lblPriceForOne)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(textField, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                                .addComponent(lblGrn)
                                                                .addGap(20))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(cablewayDataScrollPane, GroupLayout.PREFERRED_SIZE, 587, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(57)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(inputMoneyForAllTimePeriodLabel)
                                                                        .addComponent(intputMoneyForDay)
                                                                        .addComponent(inputMoneyForWeekLabel)
                                                                        .addComponent(inputMoneyForMonthLabel))
                                                                .addGap(18)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(inputMonthLabel)
                                                                        .addComponent(inputMoneyPeriodLabel)
                                                                        .addComponent(inputWeekLabel)
                                                                        .addComponent(inputMoneyDayLabel))
                                                                .addGap(2)))
                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addComponent(btnSaveChanges)
                                                                .addGap(2)
                                                                .addComponent(saveStatisticsButton))
                                                        .addGroup(groupLayout.createSequentialGroup()
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(returnMoneyForDayLabel)
                                                                        .addComponent(returnMoneyForWeekLabel)
                                                                        .addComponent(returnMoneyForMonthLabel)
                                                                        .addComponent(returnMoneyForAllTimePeriodLabel))
                                                                .addGap(26)
                                                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                                        .addComponent(returnPeriodLabel)
                                                                        .addComponent(returnMonthLabel)
                                                                        .addComponent(returnWeekLabel)
                                                                        .addComponent(returnDayLabel))))
                                                .addGap(28)))
                                .addContainerGap())
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(14)
                                .addComponent(changePasswordsHotkeyLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(701))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(manageSubscriptionsScrollPane, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)
                                .addGap(34)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblPriceForOne)
                                        .addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblGrn)
                                        .addComponent(btnSaveChanges)
                                        .addComponent(saveStatisticsButton))
                                .addGap(35)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(cablewayDataScrollPane, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(intputMoneyForDay)
                                                        .addComponent(inputMoneyDayLabel)
                                                        .addComponent(returnMoneyForDayLabel)
                                                        .addComponent(returnDayLabel))
                                                .addGap(28)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(inputMoneyForWeekLabel)
                                                        .addComponent(inputWeekLabel)
                                                        .addComponent(returnMoneyForWeekLabel)
                                                        .addComponent(returnWeekLabel))
                                                .addGap(32)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(inputMoneyForMonthLabel)
                                                        .addComponent(inputMonthLabel)
                                                        .addComponent(returnMoneyForMonthLabel)
                                                        .addComponent(returnMonthLabel))
                                                .addGap(33)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(inputMoneyForAllTimePeriodLabel)
                                                        .addComponent(inputMoneyPeriodLabel)
                                                        .addComponent(returnMoneyForAllTimePeriodLabel)
                                                        .addComponent(returnPeriodLabel))))
                                .addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                                .addComponent(changePasswordsHotkeyLabel)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(accessPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(usersList, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(oldPwdPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newPwdPasswordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmChangePasswordButton))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(confirmChangeAccess)
                                .addGap(118))
        );

        //-----------------------------------------------------------------------------
        cablewaysDataTable = new JTable();
        cablewayDataTableModel = new CablewayDataTableModel(customH2TableAdapter);
        cablewaysDataTable.setModel(cablewayDataTableModel);

        cablewaysDataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cablewaysDataTable.getTableHeader().setReorderingAllowed(false);
        cablewaysDataTable.getTableHeader().setResizingAllowed(false);

        // Remove editor
        for (int i = 0; i < cablewaysDataTable.getColumnCount(); i++)
        {
            Class<?> col_class = cablewaysDataTable.getColumnClass(i);
            cablewaysDataTable.setDefaultEditor(col_class, null);
        }

        cablewaysDataTable.getTableHeader().setPreferredSize(new Dimension(115, 115));

        cablewaysDataTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });

        cablewaysDataTable.setFont(new Font("SansSerif", Font.PLAIN, 11));
        cablewayDataScrollPane.setViewportView(cablewaysDataTable);
        //-----------------------------------------------------------------------------
		manageSubscriptionsTable = new JTable();

        final ManageSubscriptionsTableModel manageSubscriptionsTableModel = new ManageSubscriptionsTableModel(customH2TableAdapter);

        manageSubscriptionsTable.setModel(manageSubscriptionsTableModel);

        manageSubscriptionsTable.getTableHeader().setReorderingAllowed(false);
        manageSubscriptionsTable.getTableHeader().setResizingAllowed(false);

		manageSubscriptionsTable.getColumnModel().getColumn(0).setResizable(false);
		manageSubscriptionsTable.getColumnModel().getColumn(0).setPreferredWidth(95);
		manageSubscriptionsTable.getColumnModel().getColumn(1).setResizable(false);
		manageSubscriptionsTable.getColumnModel().getColumn(1).setPreferredWidth(232);
		manageSubscriptionsTable.getColumnModel().getColumn(2).setResizable(false);
		manageSubscriptionsTable.getColumnModel().getColumn(2).setPreferredWidth(250);
		manageSubscriptionsTable.getColumnModel().getColumn(3).setResizable(false);
		manageSubscriptionsTable.getColumnModel().getColumn(3).setPreferredWidth(147);
		manageSubscriptionsTable.getColumnModel().getColumn(4).setResizable(false);
		manageSubscriptionsTable.getColumnModel().getColumn(4).setPreferredWidth(164);
		manageSubscriptionsTable.getColumnModel().getColumn(5).setResizable(false);
		manageSubscriptionsTable.getColumnModel().getColumn(5).setPreferredWidth(132);
		manageSubscriptionsTable.getColumnModel().getColumn(6).setResizable(false);

        manageSubscriptionsTable.getTableHeader().setPreferredSize(new Dimension(85, 85));

        manageSubscriptionsTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });

		manageSubscriptionsTable.setFont(new Font("SansSerif", Font.PLAIN, 11));
        manageSubscriptionsScrollPane.setViewportView(manageSubscriptionsTable);
        //-----------------------------------------------------------------------------
		frame.getContentPane().setLayout(groupLayout);
        //-----------------------------------------------------------------------------
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                for (User user : userList) {
                    if (user.isAdministrator()) {
                        userAdmin = user;
                    }

                    usersList.addItem(user.getName());
                }

                usersList.setEnabled(false);
                oldPwdPasswordField.setEnabled(false);
                newPwdPasswordField.setEnabled(false);
                confirmChangePasswordButton.setEnabled(false);

                textField.setText(String.valueOf(definitions.getOneMinutePrice()));

                inputMoneyDayLabel.setText(String.valueOf(definitions.getMoneyForDay()));
                inputWeekLabel.setText(String.valueOf(definitions.getMoneyForWeek()));
                inputMonthLabel.setText(String.valueOf(definitions.getMoneyForMonth()));
                inputMoneyPeriodLabel.setText(String.valueOf(definitions.getMoneyForAllTimePeriod()));

                returnDayLabel.setText(String.valueOf(definitions.getReturnMoneyForDay()));
                returnWeekLabel.setText(String.valueOf(definitions.getReturnMoneyForWeek()));
                returnMonthLabel.setText(String.valueOf(definitions.getReturnMoneyForMonth()));
                returnPeriodLabel.setText(String.valueOf(definitions.getReturnMoneyForAllTimePeriod()));
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
                    frame.dispose();
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    textField.setText("");
                }
            }
        });

        confirmChangeAccess.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                validatePasswordsChange();
            }
        });

        confirmChangeAccess.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });

        accessPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    validatePasswordsChange();
                }
            }
        });

        confirmChangePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String pwdField1 = new String(oldPwdPasswordField.getPassword());
                String pwdField2 = new String(newPwdPasswordField.getPassword());

                if (pwdField1.equals(pwdField2)) {
                    User changeUserPwd = null;

                    for (User user : userList) {
                        if (user.getName().equals(usersList.getSelectedItem())) {
                            changeUserPwd = user;
                            changeUserPwd.setPassword(pwdField2);
                            break;
                        }
                    }

                    customH2TableAdapter.getUserHibernateDao().update(changeUserPwd);
                    JOptionPane.showMessageDialog(null, "Пароль успешно сохранен." , "Информация", JOptionPane.INFORMATION_MESSAGE);
                    oldPwdPasswordField.setText("");
                    newPwdPasswordField.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Пароли не совпадают!" , "Ошибка", JOptionPane.ERROR_MESSAGE);
                    oldPwdPasswordField.setText("");
                    newPwdPasswordField.setText("");
                }
            }
        });

        confirmChangePasswordButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });

        btnSaveChanges.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (Subscription subscription : manageSubscriptionsTableModel.getSubscriptionList()) {
                    customH2TableAdapter.getSubscriptionHibernateDao().update(subscription);
                }

                definitions.setOneMinutePrice(Integer.valueOf(textField.getText()));
                customH2TableAdapter.getDefinitionsHibernateDao().update(definitions);

                subscriptionsTable.repaint();
                JOptionPane.showMessageDialog(null, "Изменения сохранены." , "Информация", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnSaveChanges.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });

        saveStatisticsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                FileFilter fileFilter = new FileNameExtensionFilter("Cableway statistics files", "cbl");

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(fileFilter);
                fileChooser.setDialogTitle("Сохранение статистики");
                fileChooser.setSelectedFile(new File("CBL_Stats_" + DateController.date() + ".cbl"));

                int returnVal = fileChooser.showSaveDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile(), true));
                        String writeStr = formStatisticsString();

                        writer.write(writeStr);
                        writer.flush();
                        writer.close();

                    } catch (IOException e1) {
                        log.error(e1);
                    }

                    JOptionPane.showMessageDialog(null, "Статистика сохранена." , "Информация", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        saveStatisticsButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });

        oldPwdPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });

        newPwdPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    frame.dispatchEvent(e);
                }
            }
        });
	}

    public String formStatisticsString() {
        StringBuilder statsString = new StringBuilder();

        statsString.append("Статистика на ").append(DateController.now()).append("\n\n");

        for (CablewayData cablewayData : cablewayDataTableModel.getCablewayDatas()) {
            statsString.append("Канатка №: ").append(cablewayData.getId()).append("\n\n");

            statsString.append("Суммарное откатанное время за день: ").append(cablewayData.getSumPlayedTimeCablewayForDay()).append("\n");
            statsString.append("Суммарное откатанное время за неделю: ").append(cablewayData.getSumPlayedTimeCablewayForWeek()).append("\n");
            statsString.append("Суммарное откатанное время за месяц: ").append(cablewayData.getSumPlayedTimeCablewayForMonth()).append("\n");
            statsString.append("Суммарное откатанное время за весь период: ").append(cablewayData.getSumPlayedTimeCablewayForAllPeriod()).append("\n\n");
        }

        statsString.append("Статистика по деньгам:\n\n");

        statsString.append("Деньги за день: ").append(inputMoneyDayLabel.getText()).append("\n");
        statsString.append("Деньги за неделю: ").append(inputWeekLabel.getText()).append("\n");
        statsString.append("Деньги за месяц: ").append(inputMonthLabel.getText()).append("\n");
        statsString.append("Деньги за весь период: ").append(inputMoneyPeriodLabel.getText()).append("\n\n");

        statsString.append("Возврат денег за день: ").append(returnDayLabel.getText()).append("\n");
        statsString.append("Возврат денег за неделю: ").append(returnWeekLabel.getText()).append("\n");
        statsString.append("Возврат денег за месяц: ").append(returnMonthLabel.getText()).append("\n");
        statsString.append("Возврат денег за весь период: ").append(returnPeriodLabel.getText()).append("\n\n");

        return statsString.toString();
    }

    public void validatePasswordsChange() {
        userAdminPwd = new String(accessPasswordField.getPassword());

        if (userAdminPwd.equals(userAdmin.getPassword())) {
            usersList.setEnabled(true);
            oldPwdPasswordField.setEnabled(true);
            newPwdPasswordField.setEnabled(true);
            confirmChangePasswordButton.setEnabled(true);
            accessPasswordField.setText("");
        } else {
            accessPasswordField.setText("");
            JOptionPane.showMessageDialog(null, "Пароль администратора не правильный!" , "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
}
