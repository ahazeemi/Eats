package com.example.lenovo.eats.ClassModel;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class TDBNewBiAnnualReport {

	private List<String> sixReports;
	@Exclude private String timestamp;
	private int tax, profit, cost, sales;

	public TDBNewBiAnnualReport() {
	}

	public TDBNewBiAnnualReport(List<String> sixReports, String timestamp, int tax, int profit, int cost, int sales) {
		this.sixReports = sixReports;
		this.timestamp = timestamp;
		this.tax = tax;
		this.profit = profit;
		this.cost = cost;
		this.sales = sales;
	}

	public List<String> getSixReports() {
		return sixReports;
	}

	public void setSixReports(List<String> sixReports) {
		this.sixReports = sixReports;
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
