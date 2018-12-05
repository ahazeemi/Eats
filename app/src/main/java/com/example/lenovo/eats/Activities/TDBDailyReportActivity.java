package com.example.lenovo.eats.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.eats.ClassModel.TDBItem;
import com.example.lenovo.eats.Adapters.TDBTwoFieldAdapter;
import com.example.lenovo.eats.ClassModel.TDBDailyReport;
import com.example.lenovo.eats.ClassModel.TDBEmployee;
import com.example.lenovo.eats.ClassModel.TDBMiniOrder;
import com.example.lenovo.eats.ClassModel.TDBPair;
import com.example.lenovo.eats.Interfaces.TDBOnDailyReportReceiveCallback;
import com.example.lenovo.eats.Utility.TDBMainFirebase;
import com.example.lenovo.eats.R;;
import java.util.ArrayList;
import java.util.List;

public class TDBDailyReportActivity extends AppCompatActivity
{
    ProgressDialog progressDialog;
	TextView mainHeadingTv;
	ListView expensesLv, summaryLv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tdb_activity_daily_report);

	    mainHeadingTv = findViewById(R.id.mainHeadingTv);
	    expensesLv = findViewById(R.id.expensesLv);
	    summaryLv = findViewById(R.id.summaryLv);
        
        final String timestamp = getIntent().getStringExtra("timestamp");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Retrieving Data... Please wait.");
        progressDialog.show();

        TDBMainFirebase.getInstance().getFbDailyReport(timestamp, new TDBOnDailyReportReceiveCallback()
        {
            @Override
            public void onDataReceived(TDBDailyReport dailyReport)
            {
                int inventoryExpenses = 0, salaryExpenses = 0, miniOrderExpenses = 0 , customerMiniOrderExpenses = 0, totalProfit;

                totalProfit = dailyReport.getProfit();

                List<TDBItem> items = dailyReport.getInventoryItems();
                for(int i = 0, s = items.size(); i < s; i++)
                {
                    inventoryExpenses += items.get(i).getPrice();
                }

                List<TDBEmployee> salaries = dailyReport.getSalaries();
                for(int i = 0, s = items.size(); i < s; i++)
                {
                    salaryExpenses += salaries.get(i).getSalary();
                }

                List<TDBMiniOrder> miniOrders = dailyReport.getMiniOrders();
                for(int i = 0, s = miniOrders.size(); i < s; i++)
                {
                    miniOrderExpenses += miniOrders.get(i).getPrice();
                }

                List<TDBItem> customerMiniOrders = dailyReport.getInventoryItems();
                for(int i = 0, s = items.size(); i < s; i++)
                {
                    customerMiniOrderExpenses += customerMiniOrders.get(i).getPrice();
                }

                mainHeadingTv.setText("Daily Report " + timestamp);

                ArrayList<TDBPair> summaryList = new ArrayList<>();
                summaryList.add(new TDBPair("Title", "Amount"));
                summaryList.add(new TDBPair("Inventory Expenses", "" + inventoryExpenses));
                summaryList.add(new TDBPair("Salary Expenses", "" + salaryExpenses));
                summaryList.add(new TDBPair("Mini Order Expenses", "" + miniOrderExpenses));
                summaryList.add(new TDBPair("Customer Mini Order Expenses", "" + customerMiniOrderExpenses));
                summaryList.add(new TDBPair("Total Expenses", "" + (salaryExpenses + inventoryExpenses + miniOrderExpenses + customerMiniOrderExpenses)));
                summaryList.add(new TDBPair("Total Sales", "" + dailyReport.getSales()));
                summaryList.add(new TDBPair("Total Tax", "" + dailyReport.getTax()));
                summaryList.add(new TDBPair(totalProfit >= 0? "Profit": "Loss", (totalProfit >= 0? "" + totalProfit: ("" + totalProfit).substring(1))));

                TDBTwoFieldAdapter summaryAdapter = new TDBTwoFieldAdapter(TDBDailyReportActivity.this, R.layout.tdb_two_field_body, summaryList);
                summaryLv.setAdapter(summaryAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onError(String message)
            {
                progressDialog.dismiss();

                Dialog dialog = new Dialog(TDBDailyReportActivity.this);
                dialog.setTitle("Error: " + message);
//                dialog.setCancelable(false);

                dialog.show();

                dialog.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialog)
                    {
                        (TDBDailyReportActivity.this).onBackPressed();
                    }
                });

                Toast.makeText(TDBDailyReportActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
