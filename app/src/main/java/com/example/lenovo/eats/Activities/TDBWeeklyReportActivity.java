package com.example.lenovo.eats.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.eats.Adapters.TDBThreeFieldAdapter;
import com.example.lenovo.eats.Adapters.TDBTwoFieldAdapter;
import com.example.lenovo.eats.ClassModel.TDBDailyReport;
import com.example.lenovo.eats.ClassModel.TDBEmployee;
import com.example.lenovo.eats.ClassModel.TDBItem;
import com.example.lenovo.eats.ClassModel.TDBMiniOrder;
import com.example.lenovo.eats.ClassModel.TDBPair;
import com.example.lenovo.eats.ClassModel.TDBTriplet;
import com.example.lenovo.eats.Interfaces.TDBOnDailyReportReceiveCallback;
import com.example.lenovo.eats.Interfaces.TDBOnWeeklyReportReceiveCallback;
import com.example.lenovo.eats.Utility.TDBGlobalVars;
import com.example.lenovo.eats.ClassModel.TDBNewWeeklyReport;
import com.example.lenovo.eats.Utility.TDBMainFirebase;
import com.example.lenovo.eats.R;;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TDBWeeklyReportActivity extends AppCompatActivity
{
    ProgressDialog progressDialog;

    TextView mainHeadingTv, inventoryExpensesTv, salaryExpensesTv, taxExpensesTv;
    ListView summaryLv, expensesLv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.tdb_activity_weekly_report);
        
	    mainHeadingTv = findViewById(R.id.mainHeadingTv);
	    inventoryExpensesTv= findViewById(R.id.inventoryExpensesTv);
	    salaryExpensesTv= findViewById(R.id.salaryExpensesTv);
	    taxExpensesTv = findViewById(R.id.taxExpensesTv);
	    summaryLv= findViewById(R.id.summaryLv);
	    expensesLv = findViewById(R.id.expensesLv);
        
        String timestamp = getIntent().getStringExtra("timestamp");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Retrieving Data... Please wait.");
        progressDialog.show();

        TDBMainFirebase.getInstance().getFbWeeklyReport(timestamp, new TDBOnWeeklyReportReceiveCallback()
        {
            @Override
            public void onDataReceived(TDBNewWeeklyReport weeklyReport)
            {
                final int[] inventoryExpenses = {0};
                final int[] salaryExpenses = { 0 };
                final int[] miniOrderExpenses = { 0 };
                int totalProfit;

                totalProfit = weeklyReport.getProfit();

                List<String> dailyReports = weeklyReport.getDailyReportTimeStamps();
                mainHeadingTv.setText("Weekly Report for week from " + dailyReports.get(0) + " to " + dailyReports.get(dailyReports.size() - 1));

                ArrayList<TDBPair> summaryList = new ArrayList<>();
                summaryList.add(new TDBPair("Title", "Amount"));
                summaryList.add(new TDBPair("Total Order Sales", "" + weeklyReport.getSales()));
                summaryList.add(new TDBPair("Total Cost", "" + weeklyReport.getCost()));
                summaryList.add(new TDBPair("Total Tax", "" + weeklyReport.getTax()));
                summaryList.add(new TDBPair(totalProfit >= 0? "Profit": "Loss", (totalProfit >= 0? "" + totalProfit: ("" + totalProfit).substring(1))));

                TDBTwoFieldAdapter summaryAdapter = new TDBTwoFieldAdapter(TDBWeeklyReportActivity.this, R.layout.tdb_two_field_body, summaryList);
                summaryLv.setAdapter(summaryAdapter);

                final ArrayList<TDBTriplet> dailyData = new ArrayList<>();
                dailyData.add(new TDBTriplet("Day", "Date", "Profit"));
                final AtomicInteger count = new AtomicInteger(dailyReports.size());

                for(int i = 0, s = dailyReports.size(); i < s; i++)
                {
                    TDBMainFirebase.getInstance().getFbDailyReport(dailyReports.get(i), new TDBOnDailyReportReceiveCallback()
                    {
                        @Override
                        public void onDataReceived(TDBDailyReport dailyReport)
                        {
                            List<TDBItem> items = dailyReport.getInventoryItems();
                            for(int j = 0, s2 = items.size(); j < s2; j++)
                            {
                                inventoryExpenses[0] += items.get(j).getPrice();
                            }

                            List<TDBEmployee> employees = dailyReport.getSalaries();
                            for(int j = 0, s2 = employees.size(); j < s2; j++)
                            {
                                salaryExpenses[0] += employees.get(j).getSalary();
                            }

                            List<TDBMiniOrder> miniOrders = dailyReport.getMiniOrders();
                            for(int j = 0, s2 = miniOrders.size(); j < s2; j++)
                            {
                                miniOrderExpenses[0] += miniOrders.get(j).getPrice();
                            }

                            String tempTimestamp = dailyReport.getTimestamp();
                            String dateString = tempTimestamp.substring(tempTimestamp.lastIndexOf('-') + 1)
                                    + tempTimestamp.substring(tempTimestamp.indexOf('-'), tempTimestamp.lastIndexOf('-'))
                                    + "-" + tempTimestamp.substring(0, tempTimestamp.indexOf('-'));

                            DateTime dateTime = new DateTime(dateString);
                            dailyData.add(new TDBTriplet(TDBGlobalVars.getDayFromNumber(dateTime.getDayOfWeek()), dailyReport.getTimestamp(), "" + dailyReport.getProfit()));

                            count.decrementAndGet();
                        }

                        @Override
                        public void onError(String message)
                        {
                            count.decrementAndGet();
                        }
                    });
                }

                while (count.get() > 0)
                {

                }


                dailyData.add(new TDBTriplet("Total", "", "" + totalProfit));

                inventoryExpensesTv.setText("Inventory Expenses: " + inventoryExpenses[0]);
                salaryExpensesTv.setText("Salary Expenses: " + salaryExpenses[0]);
                taxExpensesTv.setText("Mini Order Expenses: " + miniOrderExpenses[0]);

                TDBThreeFieldAdapter expensesAdapter = new TDBThreeFieldAdapter(TDBWeeklyReportActivity.this, R.layout.tdb_three_field_body, dailyData);
                expensesLv.setAdapter(expensesAdapter);

                expensesLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if(position > 0 && position < dailyData.size() - 1)
                        {
                            Intent intent = new Intent(TDBWeeklyReportActivity.this, TDBDailyReportActivity.class);
                            intent.putExtra("timestamp", dailyData.get(position).getSecond());
                            startActivity(intent);
                        }
                    }
                });

                progressDialog.dismiss();
            }

            @Override
            public void onError(String message)
            {
                Dialog dialog = new Dialog(TDBWeeklyReportActivity.this);
                dialog.setTitle("Error: " + message);
//                dialog.setCancelable(false);

                progressDialog.dismiss();
                dialog.show();

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        (TDBWeeklyReportActivity.this).onBackPressed();
                    }
                });
            }
        });
    }
}
