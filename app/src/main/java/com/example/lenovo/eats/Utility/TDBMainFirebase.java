package com.example.lenovo.eats.Utility;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.lenovo.eats.ClassModel.TDBDailyReport;
import com.example.lenovo.eats.ClassModel.TDBEmployee;
import com.example.lenovo.eats.ClassModel.TDBItem;
import com.example.lenovo.eats.ClassModel.TDBMiniOrder;
import com.example.lenovo.eats.ClassModel.TDBOrder;
import com.example.lenovo.eats.Interfaces.TDBOnAnnualReportReceiveCallback;
import com.example.lenovo.eats.Interfaces.TDBOnBiAnnualReportReceiveCallback;
import com.example.lenovo.eats.Interfaces.TDBOnDailyReportReceiveCallback;
import com.example.lenovo.eats.Interfaces.TDBOnMonthlyReportReceiveCallback;
import com.example.lenovo.eats.Interfaces.TDBOnWeeklyReportReceiveCallback;
import com.example.lenovo.eats.ClassModel.TDBNewAnnualReport;
import com.example.lenovo.eats.ClassModel.TDBNewBiAnnualReport;
import com.example.lenovo.eats.ClassModel.TDBNewMonthlyReport;
import com.example.lenovo.eats.ClassModel.TDBNewWeeklyReport;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TDBMainFirebase
{
    private DatabaseReference weeklyReportRef;
    private DatabaseReference dailyReportRef;
    private DatabaseReference monthlyReportRef;
    private DatabaseReference biAnnualReportRef;
    private DatabaseReference yearlyReportRef;

    private DateTime daysDateTime, weeksDateTime;

    private static final TDBMainFirebase ourInstance = new TDBMainFirebase();

    public static TDBMainFirebase getInstance()
    {
        return ourInstance;
    }

    private TDBMainFirebase()
    {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy");
        daysDateTime = formatter.parseDateTime("01-01-2018");
        weeksDateTime = formatter.parseDateTime("01-01-2018");
        FirebaseDatabase dbRef = FirebaseDatabase.getInstance();
//        dbRef.setPersistenceEnabled(true);
        DatabaseReference rootRef = dbRef.getReference();
        dailyReportRef = rootRef.child("dailyReports");
        weeklyReportRef = rootRef.child("weeklyReports");
        monthlyReportRef = rootRef.child("monthlyReports");
        biAnnualReportRef = rootRef.child("biAnnualReports");
        yearlyReportRef = rootRef.child("yearlyReports");
    }

    public DatabaseReference getWeeklyReportRef()
    {
        return weeklyReportRef;
    }

    public DatabaseReference getDailyReportRef()
    {
        return dailyReportRef;
    }

    public void writeDailyReport(TDBDailyReport report)
    {
        dailyReportRef.child(report.getTimestamp()).setValue(report);
    }

    public void writeWeeklyReport(TDBNewWeeklyReport report)
    {
        weeklyReportRef.child(report.getTimestamp()).setValue(report);
    }

    public void writeMonthlyReport(TDBNewMonthlyReport report)
    {
        monthlyReportRef.child(report.getTimestamp()).setValue(report);
    }

    public void writeBiAnnualReport(TDBNewBiAnnualReport report)
    {
        biAnnualReportRef.child(report.getTimestamp()).setValue(report);
    }

    public void writeAnnualReport(TDBNewAnnualReport report)
    {
        yearlyReportRef.child(report.getTimestamp()).setValue(report);
    }

    public void getFbDailyReport(String timestamp, final TDBOnDailyReportReceiveCallback callback)
    {
        final TDBDailyReport dailyReport = new TDBDailyReport();
        dailyReport.setTimestamp(timestamp);

        Query reportQuery = dailyReportRef.orderByKey().equalTo(timestamp);
        reportQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                boolean flag = true;

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    try
                    {
                        // inventory items
                        Iterable<DataSnapshot> snapshot = postSnapshot.child("inventoryItems").getChildren();
                        Iterator<DataSnapshot> iterator = snapshot.iterator();
                        while (iterator.hasNext())
                        {
                            TDBItem item = iterator.next().getValue(TDBItem.class);
                            dailyReport.getInventoryItems().add(item);
                        }

                        // mini orders
                        snapshot = postSnapshot.child("miniOrders").getChildren();
                        iterator = snapshot.iterator();
                        while (iterator.hasNext())
                        {
                            TDBMiniOrder miniOrder = iterator.next().getValue(TDBMiniOrder.class);
                            dailyReport.getMiniOrders().add(miniOrder);
                        }

                        // orders
                        snapshot = postSnapshot.child("orders").getChildren();
                        iterator = snapshot.iterator();
                        while (iterator.hasNext())
                        {
                            TDBOrder order = iterator.next().getValue(TDBOrder.class);
                            dailyReport.getOrders().add(order);
                        }

                        // salaries
                        snapshot = postSnapshot.child("salaries").getChildren();
                        iterator = snapshot.iterator();
                        while (iterator.hasNext())
                        {
                            TDBEmployee employee = iterator.next().getValue(TDBEmployee.class);
                            dailyReport.getSalaries().add(employee);
                        }


                        dailyReport.setCost(postSnapshot.child("cost").getValue(int.class));
	                    dailyReport.setProfit(postSnapshot.child("profit").getValue(int.class));
	                    dailyReport.setSales(postSnapshot.child("sales").getValue(int.class));
	                    dailyReport.setTax(postSnapshot.child("tax").getValue(int.class));

                        flag = false;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                if(flag)
                {
                    callback.onError("No records found.");
                }
                else
                {
                    callback.onDataReceived(dailyReport);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void getFbWeeklyReport(String timestamp, final TDBOnWeeklyReportReceiveCallback callback)
    {
        final TDBNewWeeklyReport newWeeklyReport = new TDBNewWeeklyReport();
        newWeeklyReport.setTimestamp(timestamp);

        Query reportQuery = weeklyReportRef.orderByKey().equalTo(timestamp);
        reportQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                boolean flag = true;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    try
                    {
                        Iterable<DataSnapshot> snapshot =
                                postSnapshot.child("dailyReportTimeStamps").getChildren();
                        Iterator<DataSnapshot> iterator = snapshot.iterator();
                        List<String> dailyTimestamps = new ArrayList<>();

                        while (iterator.hasNext())
                        {
                            String timestamp = iterator.next().getValue(String.class);
                            dailyTimestamps.add(timestamp);
                        }

                        int tax = postSnapshot.child("tax").getValue(int.class);
                        int profit = postSnapshot.child("profit").getValue(int.class);
                        int cost = postSnapshot.child("cost").getValue(int.class);
                        int sales = postSnapshot.child("sales").getValue(int.class);

                        newWeeklyReport.setDailyReportTimeStamps(dailyTimestamps);
                        newWeeklyReport.setTax(tax);
                        newWeeklyReport.setProfit(profit);
                        newWeeklyReport.setCost(cost);
                        newWeeklyReport.setSales(sales);

                        flag = false;
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                if(flag)
                {
                    callback.onError("No records found.");
                }
                else
                {
                    callback.onDataReceived(newWeeklyReport);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void getFbMonthlyReport(String timestamp, final TDBOnMonthlyReportReceiveCallback callback)
    {
        final TDBNewMonthlyReport monthlyReport = new TDBNewMonthlyReport();
        monthlyReport.setTimestamp(timestamp);

        Query reportQuery = monthlyReportRef.orderByKey().equalTo(timestamp);
        reportQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    try
                    {
                        Iterable<DataSnapshot> snapshot =
                                postSnapshot.child("weeklyReportTimeStamps").getChildren();
                        Iterator<DataSnapshot> iterator = snapshot.iterator();
                        List<String> weeklyTimestamps = new ArrayList<>();

                        while (iterator.hasNext())
                        {
                            String timestamp = iterator.next().getValue(String.class);
                            weeklyTimestamps.add(timestamp);
                        }

                        int tax = postSnapshot.child("tax").getValue(int.class);
                        int profit = postSnapshot.child("profit").getValue(int.class);
                        int cost = postSnapshot.child("cost").getValue(int.class);
                        int sales = postSnapshot.child("sales").getValue(int.class);

                        monthlyReport.setTax(tax);
                        monthlyReport.setCost(cost);
                        monthlyReport.setProfit(profit);
                        monthlyReport.setSales(sales);
                        monthlyReport.setWeeklyReportTimeStamps(weeklyTimestamps);

                        callback.onDataReceived(monthlyReport);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void getFbBiAnnualReport(String timestamp, final TDBOnBiAnnualReportReceiveCallback callback)
    {
        final TDBNewBiAnnualReport biAnnualReport = new TDBNewBiAnnualReport();
        biAnnualReport.setTimestamp(timestamp);

        Query reportQuery = biAnnualReportRef.orderByKey().equalTo(timestamp);
        reportQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    try
                    {
                        //	private List<String> sixReports;
                        //	@Exclude
                        //	private String timestamp;
                        //	private int tax, profit, cost, sales;

                        Iterable<DataSnapshot> snapshot = postSnapshot.child("sixReports").getChildren();
                        Iterator<DataSnapshot> iterator = snapshot.iterator();
                        List<String> monthlyTimestamps = new ArrayList<>();

                        while (iterator.hasNext())
                        {
                            String timestamp = iterator.next().getValue(String.class);
                            monthlyTimestamps.add(timestamp);
                        }

                        int tax = postSnapshot.child("tax").getValue(int.class);
                        int profit = postSnapshot.child("profit").getValue(int.class);
                        int cost = postSnapshot.child("cost").getValue(int.class);
                        int sales = postSnapshot.child("sales").getValue(int.class);

                        biAnnualReport.setCost(cost);
                        biAnnualReport.setSales(sales);
                        biAnnualReport.setProfit(profit);
                        biAnnualReport.setTax(tax);
                        biAnnualReport.setSixReports(monthlyTimestamps);

                        callback.onDataReceived(biAnnualReport);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    public void getFbYearlyReport(final String timestamp, final TDBOnAnnualReportReceiveCallback callback)
    {
        final TDBNewAnnualReport yearlyReport = new TDBNewAnnualReport();
        yearlyReport.setTimestamp(timestamp);

        Query reportQuery = yearlyReportRef.orderByKey().equalTo(timestamp);
        reportQuery.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    try
                    {
                        //	private String timeStampOne;
                        //	private String timeStampTwo;
                        //	@Exclude
                        //	private String timestamp;
                        //	private int tax, profit, cost, sales;

                        String timestampOne = postSnapshot.child("timeStampOne").getValue(String.class),
                                timestampTwo = postSnapshot.child("timeStampTwo").getValue(String.class);

                        int tax = postSnapshot.child("tax").getValue(int.class);
                        int profit = postSnapshot.child("profit").getValue(int.class);
                        int cost = postSnapshot.child("cost").getValue(int.class);
                        int sales = postSnapshot.child("sales").getValue(int.class);

                        yearlyReport.setTax(tax);
                        yearlyReport.setCost(cost);
                        yearlyReport.setSales(sales);
                        yearlyReport.setProfit(profit);
                        yearlyReport.setTimeStampOne(timestampOne);
                        yearlyReport.setTimeStampTwo(timestampTwo);

                        callback.onDataReceived(yearlyReport);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                callback.onError(databaseError.getMessage());
            }
        });
    }

    private int getTax(int salePrice)
    {
        return salePrice - (int) (float) (salePrice * 1.0 / 1.2);
    }

    int getProfit(int salesPrice)
    {
        int tax = getTax(salesPrice);
        int salesPriceWithoutProfit = (int) ((salesPrice - tax) / 1.2);
        int salePriceWithProfit = salesPrice - tax;
        return salePriceWithProfit - salesPriceWithoutProfit;
    }

    public TDBDailyReport getDailyReport(String timestamp)
    {
        //timestamp = "25-11-18";


//		if ( !timestamp.substring(timestamp.length()-2, timestamp.length()).equals("18") ){
//			Log.d("wTF", "WTF");
//		}

        ArrayList<TDBItem> items = new ArrayList<>();
        items.add(new TDBItem("Carrot", 100.0, "10 kg", 10.0));

        ArrayList<TDBEmployee> salaries = new ArrayList<>();
        Random random = new Random();

        if (random.nextInt(1) < 0.05)
        {
            salaries.add(new TDBEmployee("Saad", "Emp111", 90000));
            salaries.add(new TDBEmployee("Suleman", "Emp222", 70000));
            salaries.add(new TDBEmployee("Hafsah", "Emp333", 50000));
        }

        ArrayList<TDBOrder> orders = new ArrayList<>();
        orders.add(new TDBOrder(random.nextInt(), random.nextInt(),
                                random.nextInt(5000) + 1000));
        orders.add(new TDBOrder(random.nextInt(), random.nextInt(),
                                random.nextInt(5000) + 1000));
        orders.add(new TDBOrder(random.nextInt(), random.nextInt(),
                                random.nextInt(5000) + 1000));
        orders.add(new TDBOrder(random.nextInt(), random.nextInt(),
                                random.nextInt(5000) + 1000));

        ArrayList<TDBMiniOrder> miniOrders = new ArrayList<>();
        if (random.nextInt(1) < 0.1)
            miniOrders.add(new TDBMiniOrder("1", 1000));

//    TDBDailyReport report = new TDBDailyReport(timestamp, AdminDataProvider.getInstance(context).getPurchaseLog(), BillManagementProvider.getInstance(context).getOrders(), AdminDataProvider.getInstance(context).getSalaries(), MiniOrderProvider.getInstance(context).getMiniOrders());

        int salePrice = new Random().nextInt(900) + 100;
        int sales = 0;
        for (TDBOrder order : orders)
        {
            sales += order.getPrice();
        }

        TDBDailyReport r = new TDBDailyReport(timestamp, items, orders, salaries, miniOrders, getTax(salePrice),
                                              getProfit(salePrice), salePrice, sales);
        writeDailyReport(r);
        return r;
    }

    public TDBNewWeeklyReport getNewWeeklyReport(String timestamp)
    {
//		String [] timestamps = {"26-11-18", "27-11-18",
//				"28-11-18", "29-11-18",
//				"30-11-18", "1-12-18", "2-12-18"};

        ArrayList<String> timestamps = new ArrayList<>();
        ArrayList<TDBDailyReport> dailyReports = new ArrayList<>();

        String d;
        for (int i = 0; i < 7; i++)
        {
            d = daysDateTime.toString("dd-MM-yy");
            timestamps.add(d);
            if (!d.substring(d.length() - 2, d.length()).equals("18"))
            {
                Log.d("wTF", "WTF");
            }
            dailyReports.add(getDailyReport(timestamps.get(i)));
            daysDateTime = daysDateTime.plusDays(1);
//			if (daysDateTime.monthOfYear().get() != daysDateTime.minusDays(1).monthOfYear().get())
//				break;
        }

        int tax, profit, cost, sales;
        tax = profit = cost = sales = 0;

        for (TDBDailyReport report : dailyReports)
        {
            tax += report.getTax();
            profit += report.getProfit();
            cost += report.getCost();
            sales += report.getSales();
        }

        TDBNewWeeklyReport w = new TDBNewWeeklyReport(timestamps, timestamp, tax, profit, cost, sales);
        writeWeeklyReport(w);
        return w;
    }

    public TDBNewMonthlyReport getNewMonthlyReport(String timestamp)
    {
//		String [] timestamps = {"26-11-18", "3-12-18",
//				"10-12-18", "17-12-18",
//				"24-12-18", "25-12-18"};

        ArrayList<String> timestamps = new ArrayList<>();
        ArrayList<TDBNewWeeklyReport> weeklyReports = new ArrayList<>();

        for (int i = 0; i < 6; i++)
        {
            timestamps.add(weeksDateTime.toString("dd-MM-yy"));
            weeklyReports.add(getNewWeeklyReport(timestamps.get(i)));
            weeksDateTime = weeksDateTime.plusWeeks(1);
            if (weeksDateTime.monthOfYear().get() != weeksDateTime.minusWeeks(1).monthOfYear().get())
                break;
        }

        int tax, profit, cost, sales;
        tax = profit = cost = sales = 0;

        for (TDBNewWeeklyReport report : weeklyReports)
        {
            tax += report.getTax();
            profit += report.getProfit();
            cost += report.getCost();
            sales += report.getSales();
        }

        TDBNewMonthlyReport m = new TDBNewMonthlyReport(timestamps, timestamp, tax, profit, cost, sales);
        writeMonthlyReport(m);
        return m;
    }

    public TDBNewBiAnnualReport getNewBiAnnualReport(String timestamp, boolean first)
    {
        String[] timestamps = {"01-01-18", "01-02-18", "01-03-18", "01-04-18", "01-05-18", "01-06-18"};
        String[] timestamps2 = {"01-07-18", "01-08-18", "01-09-18", "01-10-18", "01-11-18", "01-01-19"};

        ArrayList<TDBNewMonthlyReport> monthlyReports = new ArrayList<>();

        if (first)
        {
            for (String timestamp1 : timestamps)
            {
                monthlyReports.add(getNewMonthlyReport(timestamp1));
            }
        } else
        {
            for (String timestamp1 : timestamps2)
            {
                monthlyReports.add(getNewMonthlyReport(timestamp1));
            }
        }

        int tax, profit, cost, sales;
        tax = profit = cost = sales = 0;

        for (TDBNewMonthlyReport report : monthlyReports)
        {
            tax += report.getTax();
            profit += report.getProfit();
            cost += report.getCost();
            sales += report.getSales();
        }

        TDBNewBiAnnualReport b = new TDBNewBiAnnualReport(first ? Arrays.asList(timestamps) : Arrays.asList(timestamps2),
                                                          timestamp, tax, profit, cost, sales);
        writeBiAnnualReport(b);
        return b;
    }

    public TDBNewAnnualReport getNewAnnualReport(String timestamp)
    {
        String[] timestamps = {"01-06-18", "01-01-19"};

        TDBNewBiAnnualReport one = getNewBiAnnualReport(timestamps[0], true);
        TDBNewBiAnnualReport two = getNewBiAnnualReport(timestamps[1], false);

        int tax, profit, cost, sales;
        tax = profit = cost = sales = 0;

        tax += one.getTax() + two.getTax();
        profit += one.getProfit() + two.getProfit();
        cost += one.getCost() + two.getCost();
        sales += one.getSales() + two.getSales();

        //	DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
        //	daysDateTime = formatter.parseDateTime("01/01/2018");
        //	weeksDateTime = formatter.parseDateTime("01/01/2018");

        TDBNewAnnualReport a = new TDBNewAnnualReport(timestamps[0], timestamps[1],
                                                      timestamp, tax, profit, cost, sales);
        writeAnnualReport(a);
        return a;
    }

}
