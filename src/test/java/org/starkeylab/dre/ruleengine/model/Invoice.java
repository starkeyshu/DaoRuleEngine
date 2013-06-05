package org.starkeylab.dre.ruleengine.model;

public class Invoice {
	
	private String status = "unpaid";
	private double amount;

	public Invoice() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}