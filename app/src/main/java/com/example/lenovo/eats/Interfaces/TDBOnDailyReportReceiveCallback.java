package com.example.lenovo.eats.Interfaces;

import com.example.lenovo.eats.ClassModel.TDBDailyReport;

public interface TDBOnDailyReportReceiveCallback {
	void onDataReceived(TDBDailyReport dailyReport);
	void onError(String message);
}
