package org.starkeylab.dre.ruleengine.model;

public class Customer {

	private double creditLimit;

	private boolean isVIP;

	private int age;

	public Customer() {
	}

	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	// public void decrementCreditLimit(Double amount) {
	// creditLimit = creditLimit-amount.doubleValue();
	// }

	public void decrementCreditLimit(double amount) {
		creditLimit = creditLimit - amount;
	}

	public boolean isVIP() {
		return isVIP;
	}

	public void setVIP(boolean isVIP) {
		this.isVIP = isVIP;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}