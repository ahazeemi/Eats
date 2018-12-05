package com.example.lenovo.eats.ClassModel;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class TDBNewAnnualReport
{
	private String timeStampOne;
	private String timeStampTwo;
	@Exclude private String timestamp;
	private int tax, profit, cost, sales;

	public TDBNewAnnualReport() {
	}

	public TDBNewAnnualReport(String timeStampOne, String timeStampTwo, String timestamp, int tax, int profit, int cost, int sales) {
		this.timeStampOne = timeStampOne;
		this.timeStampTwo = timeStampTwo;
		this.timestamp = timestamp;
		this.tax = tax;
		this.profit = profit;
		this.cost = cost;
		this.sales = sales;
	}

	public String getTimeStampOne() {
		return timeStampOne;
	}

	public void setTimeStampOne(String timeStampOne) {
		this.timeStampOne = timeStampOne;
	}

	public String getTimeStampTwo() {
		return timeStampTwo;
	}

	public void setTimeStampTwo(String timeStampTwo) {
		this.timeStampTwo = timeStampTwo;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}
}
