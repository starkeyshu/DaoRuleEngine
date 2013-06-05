package org.starkeylab.dre.ruleengine.display;

import javax.swing.JTextField;

public class CreditLimitControl extends JTextField {

	public final void decrementCreditLimit(double amount) {
		setText(String.valueOf(Double.parseDouble(getText()) - amount));
	}

	public String getText() {
		String t = super.getText();
		if ("".equals(t))
			return null;
		return t;
	}

};
