package org.starkeylab.dre.ruleengine.model;

public class AccountControl {

	private double creditLimit;

	private boolean enableInvoice;

	private double invoiceAmount;

	private String invoiceStatus;

	private boolean teenagerPackageProvided;

	public AccountControl() {
		this.creditLimit = 4000;
		this.enableInvoice = true;
		this.invoiceAmount = 2000;
		this.invoiceStatus = "unpaid";
	}

	public final double getCreditLimit() {
		return this.creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public final void decrementCreditLimit(double amount) {
		setCreditLimit(getCreditLimit() - amount);
	}

	public double getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public final void disableInvoice() {
		this.enableInvoice = false;
	}

	public final void enableInvoice() {
		this.enableInvoice = true;
	}

	public boolean isEnableInvoice() {
		return enableInvoice;
	}

	public void provideTeenagerPackage() {
		this.teenagerPackageProvided = true;
	}

	public boolean isTeenagerPackageProvided() {
		return teenagerPackageProvided;
	}

	@Override
	public String toString() {
		return "AccountControl [creditLimit=" + creditLimit
				+ ", enableInvoice=" + enableInvoice + ", invoiceAmount="
				+ invoiceAmount + ", invoiceStatus=" + invoiceStatus + "]";
	}

}
