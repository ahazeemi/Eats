package com.example.lenovo.eats.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.eats.Utility.TDBGlobalVars;
import com.example.lenovo.eats.R;;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TDBReportSelectionActivity extends AppCompatActivity
{
    int activityNumber;

	TextView mainHeading, dateTv;
	ListView expensesLv, summaryLv;
	Button nextButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tdb_activity_report_selection);

        dateTv = findViewById(R.id.dateTv);
	    mainHeading = findViewById(R.id.mainHeading);
	    expensesLv = findViewById(R.id.expensesLv);
	    summaryLv = findViewById(R.id.summaryLv);

	    nextButton = findViewById(R.id.nextButton);

        activityNumber = getIntent().getIntExtra("activity", TDBGlobalVars.DAILY_REPORT_ACTIVITY);

        DateTime now = DateTime.now(), startTimestamp;

        if(activityNumber == TDBGlobalVars.DAILY_REPORT_ACTIVITY)
        {
            mainHeading.setText("Choose Date to View Report");
            startTimestamp = now.minusDays(1);
        }
        else if(activityNumber == TDBGlobalVars.WEEKLY_REPORT_ACTIVITY)
        {
            mainHeading.setText("Choose Starting Date of the Week");
            startTimestamp = now.minusDays(now.getDayOfWeek());
        }
        else if(activityNumber == TDBGlobalVars.MONTHLY_REPORT_ACTIVITY)
        {
            mainHeading.setText("Choose Month");
            startTimestamp = now.minusDays(now.getDayOfMonth());
        }
        else if(activityNumber == TDBGlobalVars.BI_ANNUAL_REPORT_ACTIVITY)
        {
            mainHeading.setText("Choose Month and Year");
            startTimestamp = now.minusDays(now.getDayOfWeek());
        }
        else if(activityNumber == TDBGlobalVars.ANNUAL_REPORT_ACTIVITY)
        {
            mainHeading.setText("Choose Year");
            startTimestamp = now.minusDays(now.getDayOfYear());
        }
        else
        {
            finish();
        }

        final Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, now.minusDays(1).getYear());
        myCalendar.set(Calendar.MONTH, now.minusDays(1).getMonthOfYear());
        myCalendar.set(Calendar.DAY_OF_MONTH, now.minusDays(1).getDayOfMonth());
        myCalendar.setFirstDayOfWeek(Calendar.MONDAY);

        {
            String myFormat = "dd-MM-yy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            String date = sdf.format(myCalendar.getTime());
            dateTv.setText(date);
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(activityNumber == TDBGlobalVars.WEEKLY_REPORT_ACTIVITY)
                {
                    DateTime dateTime = new DateTime(year + "-" + monthOfYear + "-" + dayOfMonth);
                    dateTime = dateTime.withDayOfWeek(DateTimeConstants.MONDAY);

                    myCalendar.set(Calendar.YEAR, dateTime.getYear());
                    myCalendar.set(Calendar.MONTH, dateTime.getMonthOfYear());
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    myCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                }
                else if(activityNumber == TDBGlobalVars.MONTHLY_REPORT_ACTIVITY)
                {
                    dayOfMonth = 1;

                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                }
                else if(activityNumber == TDBGlobalVars.BI_ANNUAL_REPORT_ACTIVITY)
                {
                    myCalendar.set(Calendar.YEAR, year);

                    if(monthOfYear > 5)
                    {
                        monthOfYear = 6;
                    }
                    else
                    {
                        monthOfYear = 0;
                    }

                    dayOfMonth = 1;

                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                }
                else if(activityNumber == TDBGlobalVars.ANNUAL_REPORT_ACTIVITY)
                {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, 0);
                    myCalendar.set(Calendar.DAY_OF_MONTH, 1);
                }
                else
                {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                }

                String myFormat = "dd-MM-yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                String date = sdf.format(myCalendar.getTime());
                dateTv.setText(date);
            }
        };

        dateTv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DatePickerDialog dialog = new DatePickerDialog(TDBReportSelectionActivity.this,
                        date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMaxDate(DateTime.now().minusDays(1).toDate().getTime());
                dialog.show();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = null;

                if(activityNumber == TDBGlobalVars.DAILY_REPORT_ACTIVITY)
                {
                    intent = new Intent(TDBReportSelectionActivity.this, TDBDailyReportActivity.class);
                }
                else if(activityNumber == TDBGlobalVars.WEEKLY_REPORT_ACTIVITY)
                {
                    intent = new Intent(TDBReportSelectionActivity.this, TDBWeeklyReportActivity.class);
                }
                else if(activityNumber == TDBGlobalVars.MONTHLY_REPORT_ACTIVITY)
                {
                    intent = new Intent(TDBReportSelectionActivity.this, TDBMonthlyReportActivity.class);
                }
                else if(activityNumber == TDBGlobalVars.BI_ANNUAL_REPORT_ACTIVITY)
                {
                    intent = new Intent(TDBReportSelectionActivity.this, TDBBiAnnualReportActivity.class);
                    intent.putExtra("isFirst", myCalendar.get(Calendar.MONTH) < 6);
                }
                else if(activityNumber == TDBGlobalVars.ANNUAL_REPORT_ACTIVITY)
                {
                    intent = new Intent(TDBReportSelectionActivity.this, TDBAnnualReportActivity.class);
                }

                intent.putExtra("timestamp", dateTv.getText().toString());
                startActivity(intent);
            }
        });
    }
}
