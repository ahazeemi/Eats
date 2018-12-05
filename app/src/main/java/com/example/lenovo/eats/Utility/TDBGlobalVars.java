package com.example.lenovo.eats.Utility;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.joda.time.DateTimeConstants;

public class TDBGlobalVars
{
    public static final int DAILY_REPORT_ACTIVITY = 1;
    public static final int WEEKLY_REPORT_ACTIVITY = 2;
    public static final int MONTHLY_REPORT_ACTIVITY = 3;
    public static final int BI_ANNUAL_REPORT_ACTIVITY = 4;
    public static final int ANNUAL_REPORT_ACTIVITY = 5;

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth;
        desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static String getDayFromNumber(int day)
    {
        String dayString = "";

        switch (day)
        {
            case DateTimeConstants.MONDAY:
                dayString = "Monday";
                break;
            case DateTimeConstants.TUESDAY:
                dayString = "Tuesday";
                break;
            case DateTimeConstants.WEDNESDAY:
                dayString = "Wednesday";
                break;
            case DateTimeConstants.THURSDAY:
                dayString = "Thursday";
                break;
            case DateTimeConstants.FRIDAY:
                dayString = "Friday";
                break;
            case DateTimeConstants.SATURDAY:
                dayString = "Saturday";
                break;
            case DateTimeConstants.SUNDAY:
                dayString = "Sunday";
                break;
            default:
                dayString = "Unknown Day";
        }

        return dayString;
    }

    public static String getMonthFromNumber(int month)
    {
        String monthString = "";

        switch (month)
        {
            case DateTimeConstants.JANUARY:
                monthString = "January";
                break;
            case DateTimeConstants.FEBRUARY:
                monthString = "February";
                break;
            case DateTimeConstants.MARCH:
                monthString = "March";
                break;
            case DateTimeConstants.APRIL:
                monthString = "April";
                break;
            case DateTimeConstants.MAY:
                monthString = "May";
                break;
            case DateTimeConstants.JUNE:
                monthString = "June";
                break;
            case DateTimeConstants.JULY:
                monthString = "July";
                break;
            case DateTimeConstants.AUGUST:
                monthString = "August";
                break;
            case DateTimeConstants.SEPTEMBER:
                monthString = "September";
                break;
            case DateTimeConstants.OCTOBER:
                monthString = "October";
                break;
            case DateTimeConstants.NOVEMBER:
                monthString = "November";
                break;
            case DateTimeConstants.DECEMBER:
                monthString = "December";
                break;
            default:
                monthString = "Unknown Month";
        }

        return monthString;
    }
}
