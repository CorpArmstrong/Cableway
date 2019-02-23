package com.itstep.cableway.gui.windows;

import com.itstep.cableway.cableways.CablewaysManager;
import com.itstep.cableway.datetime.DateController;
import com.itstep.cableway.utils.CurrentUser;
import com.itstep.cableway.db.entities.User;
import com.itstep.cableway.db.utils.CustomH2TableAdapter;
import com.itstep.cableway.db.utils.HibernateUtils;
import com.itstep.cableway.ports.DeviceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import java.awt.Font;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class AccessWindow {

    private static final Logger log = LogManager.getLogger(AccessWindow.class);

	private JFrame frame;
	private JPasswordField passwordField;

    private CustomH2TableAdapter customH2TableAdapter;
    private DeviceManager deviceManager;
    private CablewaysManager cablewaysManager;

    private List<User> userList;

	/**
	 * Launch the application.
	 */
	public static void main(final CustomH2TableAdapter customH2TableAdapter, final DeviceManager deviceManager, final CablewaysManager cablewaysManager) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccessWindow window = new AccessWindow(customH2TableAdapter, deviceManager, cablewaysManager);
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
	private AccessWindow(CustomH2TableAdapter customH2TableAdapter, DeviceManager deviceManager, CablewaysManager cablewaysManager) {
        this.customH2TableAdapter = customH2TableAdapter;
        this.deviceManager = deviceManager;
        this.cablewaysManager = cablewaysManager;
        this.userList = customH2TableAdapter.getUserHibernateDao().findAll();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		
		frame.setResizable(false);
		frame.setTitle("Вход в программу");
		frame.getContentPane().setBackground(new Color(240, 230, 140));
		frame.setBounds(100, 100, 600, 290);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JLabel userLabel = new JLabel("Пользователь");
		userLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		JLabel passwordLabel = new JLabel("Пароль");
		passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		
		JButton accessButton = new JButton("Войти");
		accessButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(160)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(userLabel)
                                        .addComponent(passwordLabel))
                                .addGap(40)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(passwordField)
                                        .addComponent(comboBox, 0, 139, Short.MAX_VALUE))
                                .addContainerGap(160, Short.MAX_VALUE))
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap(238, Short.MAX_VALUE)
                                .addComponent(accessButton, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
                                .addGap(229))
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(55)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(userLabel)
                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(97)
                                                .addComponent(accessButton))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(38)
                                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                        .addComponent(passwordLabel)
                                                        .addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(60, Short.MAX_VALUE))
        );
		frame.getContentPane().setLayout(groupLayout);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
                for (User user : userList) {
                    comboBox.addItem(user.getName());
                }
			}

            @Override
            public void windowClosing(WindowEvent e) {
                cleanResources();
            }
		});
		//-------------------------------------------------
		accessButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				processLogin(comboBox.getSelectedItem().toString(), passwordField.getPassword());
			}
		});
		//-------------------------------------------------
		comboBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					processLogin(comboBox.getSelectedItem().toString(), passwordField.getPassword());
				}
			}
		});
		//-------------------------------------------------
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					processLogin(comboBox.getSelectedItem().toString(), passwordField.getPassword());
				}
			}
		});
		//-------------------------------------------------
	}
	
	private void processLogin(String user, char[] pwd) {

        User checkUser = null;
        String password = new String(pwd);

        System.out.println(passwordField.getPassword());

        for (User tmpUser : userList) {
            if (tmpUser.getName().equals(user)) {
                checkUser = tmpUser;
                break;
            }
        }

        if (checkUser != null) {
            if (checkUser.getPassword().equals(password)) {
                System.out.println("password granted.");

                log.info(DateController.now() + " Вход " + checkUser.getName());

                CurrentUser.registerUser(checkUser);
                frame.dispose();
                MainWindow.main(customH2TableAdapter, deviceManager, cablewaysManager);

            } else {
                log.info(DateController.now() + " Вход " + checkUser.getName() + ": неверный пароль.");
                JOptionPane.showMessageDialog(null, "Пароль не правильный!", "Предупреждение", JOptionPane.ERROR_MESSAGE);
            }
        }
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