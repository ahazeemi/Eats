package com.example.lenovo.eats.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.eats.R;;
import com.example.lenovo.eats.Utility.TDBGlobalVars;
import com.example.lenovo.eats.Utility.TDBAdminDataProvider;
import com.example.lenovo.eats.Utility.TDBBillManagementProvider;
import com.example.lenovo.eats.Utility.TDBMiniOrderProvider;

public class TDBReportingMainActivity extends AppCompatActivity
{
    Button dailyReportButton, weeklyReportButton, monthlyReportButton,
		    biAnnualReportButton, annualReportButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tdb_activity_main);

        final Context context = getApplicationContext();

        // to initialize provider
        TDBMiniOrderProvider.getInstance(this);
        TDBAdminDataProvider.getInstance(this);
        TDBBillManagementProvider.getInstance(this);

	    dailyReportButton = findViewById(R.id.dailyReportButton);
	    weeklyReportButton = findViewById(R.id.weeklyReportButton);
	    monthlyReportButton = findViewById(R.id.monthlyReportButton);
	    biAnnualReportButton = findViewById(R.id.biAnnualReportButton);
	    annualReportButton = findViewById(R.id.annualReportButton);

        dailyReportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                MiniOrderProvider.getInstance(context).getOrders();
//                AdminDataProvider.getInstance(context).getSalaries();

//                DateTime now = DateTime.now();
//                String monthYear = now.monthOfYear().getAsText() + now.getYear();
//                Toast.makeText(context, monthYear, Toast.LENGTH_SHORT).show();

//                TDBDailyReport dailyReport = MainFirebase.getInstance().getDailyReport("25-11-18");
//                Toast.makeText(context, dailyReport.getInventoryItems().size() + ", " + dailyReport.getMiniOrders().size() + ", " + dailyReport.getOrders().size() + ", " + dailyReport.getSalaries().size(), Toast.LENGTH_LONG).show();
                gotoActivity(TDBGlobalVars.DAILY_REPORT_ACTIVITY);
            }
        });

        weeklyReportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoActivity(TDBGlobalVars.WEEKLY_REPORT_ACTIVITY);
            }
        });

        monthlyReportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoActivity(TDBGlobalVars.MONTHLY_REPORT_ACTIVITY);
            }
        });

        biAnnualReportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoActivity(TDBGlobalVars.BI_ANNUAL_REPORT_ACTIVITY);
            }
        });

        annualReportButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                gotoActivity(TDBGlobalVars.ANNUAL_REPORT_ACTIVITY);
            }
        });

	   // MainFirebase.getInstance().getNewAnnualReport("01-01-19");

//        String timestamp = "25-11-18";
//        TDBDailyReport report = new TDBDailyReport(timestamp, AdminDataProvider.getInstance(this).getPurchaseLog(), BillManagementProvider.getInstance(this).getOrders(), AdminDataProvider.getInstance(this).getSalaries(), MiniOrderProvider.getInstance(this).getMiniOrders());
//
//        Toast.makeText(ReportingMainActivity.this, "Writing report", Toast.LENGTH_SHORT).show();
//        MainFirebase.getInstance().writeDailyReport(report);
//        Toast.makeText(ReportingMainActivity.this, "Done", Toast.LENGTH_SHORT).show();
    }

    private void gotoActivity(int activity)
    {
        // take start and end dates from firebase
        Intent intent = new Intent(TDBReportingMainActivity.this, TDBReportSelectionActivity.class);
        intent.putExtra("activity", activity);
        startActivity(intent);
    }
}
