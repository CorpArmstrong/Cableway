package com.itstep.cableway.gui.windows;

import com.itstep.cableway.db.entities.Customer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyStatisticWindow {

    private Customer customer;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(final Customer customer) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KeyStatisticWindow window = new KeyStatisticWindow(customer);
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
	private KeyStatisticWindow(Customer customer) {
        this.customer = customer;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					frame.dispose();
				}
			}
		});
		
		frame.setTitle("Статистика по браслету");
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(176, 224, 230));
		frame.setBackground(new Color(176, 224, 230));
		frame.setBounds(100, 100, 868, 583);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel idKeyLabel = new JLabel("№ ключа");
		idKeyLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumTimePlayedForDayLabel = new JLabel("Суммарное время, откатанное за день, мин");
		sumTimePlayedForDayLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumTimePlayedForWeekLabel = new JLabel("Суммарное время, откатанное за неделю, мин");
		sumTimePlayedForWeekLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumTimePlayedForMonthLabel = new JLabel("Суммарное время, откатанное за месяц, мин");
		sumTimePlayedForMonthLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumTimeForAllPeriodLabel = new JLabel("Общее время за весь период, мин");
		sumTimeForAllPeriodLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumMoneyForAllDayLabel = new JLabel("Внесенные деньги за день, грн");
		sumMoneyForAllDayLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumMoneyForAllWeekLabel = new JLabel("Внесенные деньги за неделю, грн");
		sumMoneyForAllWeekLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumMoneyForAllMonthLabel = new JLabel("Внесенные деньги за месяц, грн");
		sumMoneyForAllMonthLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JLabel sumMoneyForAllPeriodLabel = new JLabel("Общие деньги за весь период, грн");
		sumMoneyForAllPeriodLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel idKeyLabelField = new JLabel("New label");
		idKeyLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumTimePlayedForDayLabelField = new JLabel("New label");
		sumTimePlayedForDayLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumTimePlayedForWeekLabelField = new JLabel("New label");
		sumTimePlayedForWeekLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumTimePlayedForMonthLabelField = new JLabel("New label");
		sumTimePlayedForMonthLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumTimeForAllPeriodLabelField = new JLabel("New label");
		sumTimeForAllPeriodLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumMoneyForAllDayLabelField = new JLabel("New label");
		sumMoneyForAllDayLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumMoneyForAllWeekLabelField = new JLabel("New label");
		sumMoneyForAllWeekLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumMoneyForAllMonthLabelField = new JLabel("New label");
		sumMoneyForAllMonthLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		final JLabel sumMoneyForAllPeriodLabelField = new JLabel("New label");
		sumMoneyForAllPeriodLabelField.setFont(new Font("SansSerif", Font.BOLD, 18));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(sumTimePlayedForDayLabel)
						.addComponent(sumTimePlayedForWeekLabel)
						.addComponent(sumTimePlayedForMonthLabel)
						.addComponent(sumTimeForAllPeriodLabel)
						.addComponent(sumMoneyForAllPeriodLabel)
						.addComponent(idKeyLabel)
						.addComponent(sumMoneyForAllDayLabel)
						.addComponent(sumMoneyForAllWeekLabel)
						.addComponent(sumMoneyForAllMonthLabel))
					.addGap(137)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(sumMoneyForAllDayLabelField)
						.addComponent(idKeyLabelField)
						.addComponent(sumTimePlayedForDayLabelField)
						.addComponent(sumTimePlayedForWeekLabelField)
						.addComponent(sumTimePlayedForMonthLabelField)
						.addComponent(sumTimeForAllPeriodLabelField)
						.addComponent(sumMoneyForAllPeriodLabelField)
						.addComponent(sumMoneyForAllWeekLabelField)
						.addComponent(sumMoneyForAllMonthLabelField))
					.addContainerGap(184, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(37, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(idKeyLabel)
						.addComponent(idKeyLabelField))
					.addGap(33)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumTimePlayedForDayLabel)
						.addComponent(sumTimePlayedForDayLabelField))
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumTimePlayedForWeekLabel)
						.addComponent(sumTimePlayedForWeekLabelField))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumTimePlayedForMonthLabel)
						.addComponent(sumTimePlayedForMonthLabelField))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumTimeForAllPeriodLabel)
						.addComponent(sumTimeForAllPeriodLabelField))
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumMoneyForAllDayLabelField)
						.addComponent(sumMoneyForAllDayLabel))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumMoneyForAllWeekLabel)
						.addComponent(sumMoneyForAllWeekLabelField))
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumMoneyForAllMonthLabel)
						.addComponent(sumMoneyForAllMonthLabelField))
					.addGap(28)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(sumMoneyForAllPeriodLabel)
						.addComponent(sumMoneyForAllPeriodLabelField))
					.addGap(62))
		);
		frame.getContentPane().setLayout(groupLayout);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {

                idKeyLabelField.setText(String.valueOf(customer.getCardKey()));

                sumTimePlayedForDayLabelField.setText(String.valueOf(customer.getSumTimePlayedForDay()));
                sumTimePlayedForWeekLabelField.setText(String.valueOf(customer.getSumTimePlayedForWeek()));
                sumTimePlayedForMonthLabelField.setText(String.valueOf(customer.getSumTimePlayedForMonth()));

                sumTimeForAllPeriodLabelField.setText(String.valueOf(customer.getSumTimeForAllPeriod()));

                sumMoneyForAllDayLabelField.setText(String.valueOf(customer.getSumMoneyForAllDay()));
                sumMoneyForAllWeekLabelField.setText(String.valueOf(customer.getSumMoneyForAllWeek()));
                sumMoneyForAllMonthLabelField.setText(String.valueOf(customer.getSumMoneyForAllMonth()));

                sumMoneyForAllPeriodLabelField.setText(String.valueOf(customer.getSumTimeForAllPeriod()));
			}
		});
	}
}