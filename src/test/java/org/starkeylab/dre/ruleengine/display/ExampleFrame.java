package org.starkeylab.dre.ruleengine.display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.starkeylab.dre.ruleengine.RuleWithComponentTest;

/**
 * <p>
 * Title: JRuleEngine Project
 * </p>
 * <p>
 * Description: Frame used to view customer and invoice input controls. When
 * user clicks on "Calculate" button the rule engine is called.
 * </p>
 * <p>
 * Copyright: Copyright (C) 2006 Mauro Carniel
 * </p>
 * 
 * <p>
 * This file is part of JRuleEngine project. This library is free software; you
 * can redistribute it and/or modify it under the terms of the (LGPL) Lesser
 * General Public License as published by the Free Software Foundation;
 * 
 * GNU LESSER GENERAL PUBLIC LICENSE Version 2.1, February 1999
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Library General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Library General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 675 Mass Ave, Cambridge, MA 02139, USA.
 * 
 * The author may be contacted at: maurocarniel@tin.it
 * </p>
 * 
 * @author Mauro Carniel
 * @version 1.0
 */
public class ExampleFrame extends JFrame {

	JPanel customerPanel = new JPanel();

	JLabel creditLimitLabel = new JLabel();
	FlowLayout flowLayout1 = new FlowLayout();
	JPanel invoicePanel = new JPanel();
	FlowLayout flowLayout2 = new FlowLayout();
	JLabel amountLabel = new JLabel();
	TitledBorder titledBorder1;
	TitledBorder titledBorder2;
	JButton calcButton = new JButton();

	CreditLimitControl creditLimitControl = new CreditLimitControl();
	StatusControl statusControl = new StatusControl();
	JTextField amountControl = new JTextField();

	RuleWithComponentTest controller = null;

	public ExampleFrame(RuleWithComponentTest controller) {
		super("Customer Credit Limit");
		creditLimitControl.setName("creditLimitControl");
		amountControl.setName("amountControl");
		statusControl.setName("statusControl");
		try {
			this.controller = controller;
			init();
			jbInit();
			setSize(400, 200);
			setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize()
					.getWidth() - getSize().getWidth()) / 2,
					(int) (Toolkit.getDefaultToolkit().getScreenSize()
							.getHeight() - getSize().getHeight()) / 2);
			setVisible(false);
			calcButton.doClick();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CreditLimitControl getCreditLimitControl() {
		return creditLimitControl;
	}

	public StatusControl getStatusControl() {
		return statusControl;
	}

	public JTextField getAmountControl() {
		return amountControl;
	}

	private void init() {
		DefaultComboBoxModel model = new DefaultComboBoxModel(new String[] {
				"unpaid", "paid" });
		statusControl.setModel(model);
		statusControl.setSelectedIndex(0);

	}

	private void jbInit() throws Exception {
		titledBorder1 = new TitledBorder("");
		titledBorder2 = new TitledBorder("");
		creditLimitControl.setText("4000");
		creditLimitControl.setColumns(10);
		creditLimitLabel.setText("Credit Limit");
		customerPanel.setLayout(flowLayout1);
		flowLayout1.setAlignment(FlowLayout.LEFT);
		customerPanel.setBorder(titledBorder2);
		invoicePanel.setBorder(titledBorder2);
		invoicePanel.setLayout(flowLayout2);
		flowLayout2.setAlignment(FlowLayout.LEFT);
		amountLabel.setText("Invoice Amount");
		amountControl.setText("2000");
		amountControl.setColumns(10);
		titledBorder1.setTitle("Customer Settings");
		titledBorder1.setTitleColor(Color.blue);
		titledBorder2.setTitle("Invoice Settings");
		titledBorder2.setTitleColor(Color.blue);
		calcButton.setMnemonic('C');
		calcButton.setText("Calculate");
		calcButton
				.addActionListener(new Example5Frame_calcButton_actionAdapter(
						this));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().add(customerPanel, BorderLayout.NORTH);
		customerPanel.add(creditLimitLabel, null);
		customerPanel.add(creditLimitControl, null);
		this.getContentPane().add(invoicePanel, BorderLayout.CENTER);
		invoicePanel.add(amountLabel, null);
		invoicePanel.add(amountControl, null);
		invoicePanel.add(statusControl, null);
		invoicePanel.add(calcButton, null);
	}

	void calcButton_actionPerformed(ActionEvent e) {
		try {
			// Add this form as rule engine input object
			controller.getStatefulRuleSession().addObject(amountControl);
			controller.getStatefulRuleSession().addObject(creditLimitControl);
			controller.getStatefulRuleSession().addObject(statusControl);

			System.out.println("Called addObject on Stateful Rule Session: "
					+ controller.getStatefulRuleSession());

			controller.getStatefulRuleSession().executeRules();
			System.out.println("Called executeRules");

			// extract the Objects from the statefulRuleSession
			java.util.List results = controller.getStatefulRuleSession()
					.getObjects();
			System.out.println("Result of calling getObjects: "
					+ results.size() + " results.");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public final void disableInvoice() {
		amountControl.setEnabled(false);
		statusControl.setEnabled(false);
		calcButton.setEnabled(false);
	}

	public final void enableInvoice() {
		amountControl.setEnabled(true);
		statusControl.setEnabled(true);
		calcButton.setEnabled(true);
	}

}

class Example5Frame_calcButton_actionAdapter implements
		java.awt.event.ActionListener {
	ExampleFrame adaptee;

	Example5Frame_calcButton_actionAdapter(ExampleFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.calcButton_actionPerformed(e);
	}
}