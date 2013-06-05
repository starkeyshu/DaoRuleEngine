package org.starkeylab.dre.script;

import java.io.Serializable;
import java.util.Date;

public class CarModel implements Serializable{

	private String name;
	private String mode;
	private String year;
	private Date startDate;
	private double price;
	
	public CarModel(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public static CarModel getExample(){
		CarModel car=new CarModel("HSV Maloo");
		car.setPrice(300000);
		return car;
	}

}
