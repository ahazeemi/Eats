package com.example.lenovo.eats.Interfaces;

import com.example.lenovo.eats.ClassModel.TDBNewBiAnnualReport;

public interface TDBOnBiAnnualReportReceiveCallback {
	void onDataReceived(TDBNewBiAnnualReport biAnnualReport);
	void onError(String message);
}
