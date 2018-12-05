package com.example.lenovo.eats.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.eats.R;;

public class TDBMonthlyReportActivity extends AppCompatActivity
{
	TextView mainHeadingTv;
	ListView expensesLv, summaryLv;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tdb_activity_monthly_report);

	    mainHeadingTv = findViewById(R.id.mainHeadingTv);
	    expensesLv = findViewById(R.id.expensesLv);
	    summaryLv = findViewById(R.id.summaryLv);

//        String timestamp = getIntent().getStringExtra("timestamp");
//        int totalProfit;
//
//        MonthlyReport monthlyReport = MainFirebase.getInstance().getMonthlyReport(timestamp);
//
//        totalProfit = monthlyReport.getProfit();
//
//        String startDate = timestamp.substring(timestamp.lastIndexOf('-') + 1)
//                + timestamp.substring(timestamp.indexOf('-'), timestamp.lastIndexOf('-'))
//                + "-" + timestamp.substring(0, timestamp.indexOf('-'));
//        DateTime startDateTime = new DateTime(startDate);
//
//        List<WeeklyReport> weeklyReports = monthlyReport.getWeeklyReports();
//        mainHeadingTv.setText("Monthly Report for " + GlobalVars.getMonthFromNumber(startDateTime.getMonthOfYear()) + ", " + startDateTime.getYear());
//
//        ArrayList<TDBPair> summaryList = new ArrayList<>();
//        summaryList.add(new TDBPair("Title", "Amount"));
//        summaryList.add(new TDBPair("Total Order Sales", "" + monthlyReport.getSales()));
//        summaryList.add(new TDBPair("Total Cost", "" + monthlyReport.getCost()));
//        summaryList.add(new TDBPair("Total Tax", "" + monthlyReport.getTax()));
//        summaryList.add(new TDBPair(totalProfit >= 0? "Profit": "Loss", (totalProfit >= 0? "" + totalProfit: ("" + totalProfit).substring(1))));
//
//        TwoFieldAdapter summaryAdapter = new TwoFieldAdapter(this, R.layout.tdb_two_field_body, summaryList);
//        summaryLv.setAdapter(summaryAdapter);
//
//        final ArrayList<TDBTriplet> weeklyData = new ArrayList<>();
//        weeklyData.add(new TDBTriplet("Week Start", "Week End", "Profit"));
//
//        for(int i = 0, s = weeklyReports.size(); i < s; i++)
//        {
//            WeeklyReport tempWeeklyReport = weeklyReports.get(i);
//            List<TDBDailyReport> dailyReports = tempWeeklyReport.getDailyReports();
//
//            weeklyData.add(new TDBTriplet(dailyReports.get(0).getTimestamp(), dailyReports.get(dailyReports.size() - 1).getTimestamp(), "" + tempWeeklyReport.getProfit()));
//        }
//
//        weeklyData.add(new TDBTriplet("Total", "", "" + totalProfit));
//
//        ThreeFieldAdapter expensesAdapter = new ThreeFieldAdapter(this, R.layout.tdb_three_field_body, weeklyData);
//        expensesLv.setAdapter(expensesAdapter);
//
//        expensesLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                if(position > 0 && position < weeklyData.size() - 1)
//                {
//                    Intent intent = new Intent(MonthlyReportActivity.this, WeeklyReportActivity.class);
//                    intent.putExtra("timestamp", weeklyData.get(position).getSecond());
//                    startActivity(intent);
//                }
//            }
//        });
    }
}
