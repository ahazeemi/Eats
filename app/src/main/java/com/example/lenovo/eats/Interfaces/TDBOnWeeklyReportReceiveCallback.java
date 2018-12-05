package com.example.lenovo.eats.Interfaces;

import com.example.lenovo.eats.ClassModel.TDBNewWeeklyReport;

public interface TDBOnWeeklyReportReceiveCallback {
	void onDataReceived(TDBNewWeeklyReport weeklyReport);
	void onError(String message);
}
