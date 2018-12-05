package com.example.lenovo.eats.Interfaces;

import com.example.lenovo.eats.ClassModel.TDBNewMonthlyReport;

public interface TDBOnMonthlyReportReceiveCallback {
	void onDataReceived(TDBNewMonthlyReport monthlyReport);
	void onError(String message);
}
