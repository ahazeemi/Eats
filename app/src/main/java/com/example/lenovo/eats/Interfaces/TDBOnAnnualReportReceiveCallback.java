package com.example.lenovo.eats.Interfaces;

import com.example.lenovo.eats.ClassModel.TDBNewAnnualReport;

public interface TDBOnAnnualReportReceiveCallback {
	void onDataReceived(TDBNewAnnualReport annualReport);
	void onError(String message);
}
