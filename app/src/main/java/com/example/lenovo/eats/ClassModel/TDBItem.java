package com.example.lenovo.eats.ClassModel;

public class TDBItem
{
	private String name;
	private Double price;
	private String quantity;
	private Double unitPrice;

	public TDBItem() {
	}

	public TDBItem(String name, Double price, String quantity, Double unitPrice) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public String getQuantity() {
		return quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}
}
