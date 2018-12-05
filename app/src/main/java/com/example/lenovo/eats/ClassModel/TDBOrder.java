package com.example.lenovo.eats.ClassModel;

public class TDBOrder {
	private int orderId;
	private int orderNumber;
	private int price;

	public TDBOrder() {
	}

	public TDBOrder(int orderId, int orderNumber, int price) {
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.price = price;
	}

	public int getOrderId() {
		return orderId;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public int getPrice() {
		return price;
	}
}
