package com.example.lenovo.eats.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.eats.R;;

public class TDBBiAnnualReportActivity extends AppCompatActivity
{
	TextView mainHeadingTv;
	ListView expensesLv, summaryLv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tdb_activity_bi_annual_report);

	    mainHeadingTv = findViewById(R.id.mainHeadingTv);
	    expensesLv = findViewById(R.id.expensesLv);
	    summaryLv = findViewById(R.id.summaryLv);

//        String timestamp = getIntent().getStringExtra("timestamp");
//        boolean isFirst = getIntent().getBooleanExtra("isFirst", true);
//        int totalProfit;
//
//        BiAnnualReport biAnnualReport = MainFirebase.getInstance().getBiAnnualReport(timestamp, isFirst);
//
//        totalProfit = biAnnualReport.getProfit();
//
//        String startDate = timestamp.substring(timestamp.lastIndexOf('-') + 1)
//                + timestamp.substring(timestamp.indexOf('-'), timestamp.lastIndexOf('-'))
//                + "-" + timestamp.substring(0, timestamp.indexOf('-'));
//        DateTime startDateTime = new DateTime(startDate);
//
//        final List<MonthlyReport> monthlyReports = biAnnualReport.getSixReports();
//
//        if(isFirst)
//        {
//            mainHeadingTv.setText("Bi-Annual Report from January to June, " + startDateTime.getYear());
//        }
//        else
//        {
//            mainHeadingTv.setText("Bi-Annual Report from July to December, " + startDateTime.getYear());
//        }
//
//        ArrayList<TDBPair> summaryList = new ArrayList<>();
//        summaryList.add(new TDBPair("Title", "Amount"));
//        summaryList.add(new TDBPair("Total Order Sales", "" + biAnnualReport.getSales()));
//        summaryList.add(new TDBPair("Total Cost", "" + biAnnualReport.getCost()));
//        summaryList.add(new TDBPair("Total Tax", "" + biAnnualReport.getTax()));
//        summaryList.add(new TDBPair(totalProfit >= 0? "Profit": "Loss", (totalProfit >= 0? "" + totalProfit: ("" + totalProfit).substring(1))));
//
//        TwoFieldAdapter summaryAdapter = new TwoFieldAdapter(this, R.layout.tdb_two_field_body, summaryList);
//        summaryLv.setAdapter(summaryAdapter);
//
//        final ArrayList<TDBPair> monthlyData = new ArrayList<>();
//        monthlyData.add(new TDBPair("Month", "Profit"));
//
//        for(int i = 0, s = monthlyReports.size(); i < s; i++)
//        {
//            MonthlyReport tempMonthlyReport = monthlyReports.get(i);
//
//            String tempTimestamp = tempMonthlyReport.getTimestamp();
//            String dateString = tempTimestamp.substring(tempTimestamp.lastIndexOf('-') + 1)
//                    + tempTimestamp.substring(tempTimestamp.indexOf('-'), tempTimestamp.lastIndexOf('-'))
//                    + "-" + tempTimestamp.substring(0, tempTimestamp.indexOf('-'));
//            DateTime dateTime = new DateTime(dateString);
//
//            monthlyData.add(new TDBPair(GlobalVars.getMonthFromNumber(dateTime.getMonthOfYear()), "" + tempMonthlyReport.getProfit()));
//        }
//
//        monthlyData.add(new TDBPair("Total", "" + totalProfit));
//
//        TwoFieldAdapter expensesAdapter = new TwoFieldAdapter(this, R.layout.tdb_three_field_body, monthlyData);
//        expensesAdapter.setShowFooter(true);
//        expensesLv.setAdapter(expensesAdapter);
//
//        expensesLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                if(position > 0 && position < monthlyData.size() - 1)
//                {
//                    Intent intent = new Intent(BiAnnualReportActivity.this, MonthlyReportActivity.class);
//                    intent.putExtra("timestamp", monthlyReports.get(position - 1).getTimestamp());
//                    startActivity(intent);
//                }
//            }
//        });
    }
}
