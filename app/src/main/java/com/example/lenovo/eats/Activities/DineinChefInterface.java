package com.example.lenovo.eats.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.constraint.ConstraintSet.WRAP_CONTENT;
import android.os.*;

import com.example.lenovo.eats.Adapters.AidListAdapter;
import com.example.lenovo.eats.ClassModel.Dine_In_orderProxy;
import com.example.lenovo.eats.R;
import com.example.lenovo.eats.Utility.Generator;
import com.example.lenovo.eats.Utility.itemFactory;

@SuppressLint("NewApi")
public class DineinChefInterface extends AppCompatActivity  {

    //                  PUBLIC BLOCK

    public static DineinChefInterface Instance;
    public static Context THIS;
    public static int delieveredCount=0;
    public static List<String> backStack=new ArrayList<>();
    public static  List<Dine_In_orderProxy> waitingOrders=new ArrayList<>();

    public static RecyclerView queues[]=new RecyclerView[20];
    public static long handled=0;


    Handler msgHandler=new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            int index=msg.what;
            TextView tv=queues[index].findViewById(R.id.Time);
            if (tv!=null) {
                if (tv.getVisibility()==View.GONE)
                         tv.setVisibility(View.VISIBLE);
                tv.setText(msg.obj.toString());
            }
        }
    };

    Handler textViewHandler=new Handler(){

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);

            TextView tv=findViewById(msg.arg1);
            if (tv!=null) {
                tv.setText(msg.obj.toString());
            }
        }
    };

    public  static  int queueCount=0;

    public static List<List<String>> data = new ArrayList<List<String>>(20);
    public static  List<String> allChefs=new ArrayList<>(Arrays.asList(new String[]{
        "Ali",
        "Kamran",
        "Zulfi",
        "Papu"
}));
    public static  List<Boolean> statChefs=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dine_in_activity_main);
        THIS=this;
        Instance=this;



        for (int i=0;i<20;i++)
        {queues[i]=(RecyclerView) find("r"+(i+1));
        data.add(new ArrayList<String>());
        statChefs.add(false);

        }


        for (int i=0;i<1;i++) {
            addQueue(allChefs.get(i));
            statChefs.set(i,true);
        }
        new java.util.Timer().scheduleAtFixedRate(
                new java.util.TimerTask(){
                    @Override
                    public void run() {
for (int i=0;i<queueCount;i++)
{
    int time=getQueueTime(i);
    if (time>35) time=35;
    Message os= msgHandler.obtainMessage();
    os.what=i;
    os.obj=""+time;
    os.sendToTarget();

    os=textViewHandler.obtainMessage();
    os.arg1=R.id.waiting;
    os.obj=waitingOrders.size();
    os.sendToTarget();


    os=textViewHandler.obtainMessage();
    os.arg1=R.id.completed;
    os.obj=""+delieveredCount;
    os.sendToTarget();
}

                    }
                }, 0,2000);


    }

    // BORDER LINE BUG
    @Override
    protected void onStart() {
        super.onStart();
        // Write a message to the database

     Generator.getInstance();
        // For Testing Only

    }

public void getMore()
{
 if (!backStack.isEmpty())
 {

     int current=0;
     String item=backStack.get(0);
     itemFactory helper=new itemFactory(item);
     while (current<queueCount && helper.isValid() && !backStack.isEmpty() )
     {   item=backStack.get(0);
         helper=new itemFactory(item);
         if (getQueueTime(current)+helper.getETime()<=35)
         {
             data.get(current).add(item);
             if (queues[current].getAdapter()!=null)
             queues[current].getAdapter().notifyDataSetChanged();
             backStack.remove(0);
         }
         else current++;
     }

 }
}



    void addQueue(@NonNull String name)
    {
        if (queueCount<20)
        {queues[queueCount].setLayoutManager(new LinearLayoutManager(
                                        this,
                                        LinearLayoutManager.VERTICAL,
                                        false));
        data.get(queueCount).add(name);
        AidListAdapter bListAdapter = new AidListAdapter(
                                        data.get(queueCount),queueCount);
        queues[queueCount].setAdapter(bListAdapter);
        statChefs.set(allChefs.indexOf(name),true);

        queueCount++;}
    }

    View find(String what)      // find a recycler view
    {

        int resID = getResources().getIdentifier(what,
                                    "id",
                                    getPackageName());
        return findViewById(resID);
    }

    public void addChef(View v)
    {
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext()
                        .getSystemService
                                (Context.LAYOUT_INFLATER_SERVICE);
       final View  view = inflater
               .inflate(R.layout.select_chef, null);

       // When manager selects a chef!
       View.OnClickListener onClick=new View.OnClickListener() {
           @Override
           public void onClick(View vin) {
               vin.setBackgroundColor(Color.DKGRAY);
               vin.setOnClickListener(null);
               TextView txt=(TextView)vin.findViewById(R.id.text);
               addQueue(txt.getText().toString());
               getMore();

           }
       };

       final PopupWindow mypopupWindow = new PopupWindow(view
               ,WRAP_CONTENT, WRAP_CONTENT
               , true);
        mypopupWindow.showAsDropDown(findViewById(R.id.imageButton));

        Button mButton=view.findViewById(R.id.ok);
        mButton
        .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
mypopupWindow.dismiss();
            }
        });

        // get all offline chefs
        List<String> offline=new ArrayList<>();
        for (int i=0;i<allChefs.size();i++)
        {
        if (!statChefs.get(i))
        {
        offline.add(allChefs.get(i));
        }
        }

        RecyclerView rc=(RecyclerView)view.findViewById(R.id.subR);
        rc.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));

        AidListAdapter bListAdapter = new AidListAdapter(
                offline,true);
        bListAdapter.onClickListener=onClick;
        rc.setAdapter(bListAdapter);
    }

    public void removeChef(View v)
    {
        LayoutInflater inflater = (LayoutInflater)
                getApplicationContext()
                        .getSystemService
                                (Context.LAYOUT_INFLATER_SERVICE);
        final View  view = inflater
                .inflate(R.layout.select_chef, null);

        // When manager selects a chef!
        View.OnClickListener onClick=new View.OnClickListener() {
            @Override
            public void onClick(View vin) {
                vin.setBackgroundColor(Color.DKGRAY);
                vin.setOnClickListener(null);

                if (queueCount<=1)
                {
                    Toast.makeText(DineinChefInterface.this
                            ,"Can't remove only chef",
                            Toast.LENGTH_SHORT).show();
                    return;

                }
                TextView txt=(TextView)vin.findViewById(R.id.text);
                int index=findQueueOfChef(txt.getText().toString());

                if (index==-1)
                {
                    Toast.makeText(DineinChefInterface.this, "Chef already removed!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (data.get(index).size()>1)
                    {
                        Toast.makeText(DineinChefInterface.this, "Chefs's list not empty! Chef not removed.", Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        toggleStatofChef(txt.getText().toString());
                        for (int i=index;i<queueCount-1;i++)
                        {
                            data.get(i).clear();
                            queues[i]
                            .getAdapter()
                                    .notifyDataSetChanged();
                            data.get(i).addAll(data.get(i+1));
                            queues[i]
                                    .getAdapter()
                                    .notifyDataSetChanged();

                        }
                        data.get(queueCount-1).clear();
                        queues[queueCount-1]
                                .getAdapter()
                                    .notifyDataSetChanged();
                        queueCount--;

                    }


                }

            }
        };

        final PopupWindow mypopupWindow = new PopupWindow(view
                ,WRAP_CONTENT, WRAP_CONTENT
                , true);
        mypopupWindow.showAsDropDown(findViewById(R.id.imageButton));

        Button mButton=view.findViewById(R.id.ok);
        mButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mypopupWindow.dismiss();
                    }
                });

        // get all online chefs
        List<String> online=new ArrayList<>();
        for (int i=0;i<allChefs.size();i++)
        {
            if (statChefs.get(i))
            {
                online.add(allChefs.get(i));
            }
        }

        RecyclerView rc=(RecyclerView)view.findViewById(R.id.subR);
        rc.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false));

        AidListAdapter bListAdapter = new AidListAdapter(
                online,true);
        bListAdapter.onClickListener=onClick;
        rc.setAdapter(bListAdapter);
    }
    int findQueueOfChef(String name)
    {

        for (int i=0;i<queueCount;i++)
        {
            if (data.get(i).get(0).equals(name))
            {
                return i;
            }

        }
        return -1;

    }
    public void toggleStatofChef(String name)
    {
        int res=-1;
        for (int i = 0; i<DineinChefInterface.allChefs.size() && res==-1; i++)
        {
            if (allChefs.get(i).equals(name))
            {
                res=i;
            }

        }
        if (res!=-1)
        {
            statChefs.set(res,!statChefs.get(res));

        }
    }


    public void balance(View v)
    {
        int timecount=0;
        for (int i=0;i<queueCount;i++)
        {
            timecount=timecount+(data.get(i).size()-1);

        }

        final int dividedTime=timecount/queueCount;
        DialogInterface.OnClickListener dialogClickListener = new               DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked

                        for (int i=0;i<queueCount-1;i++)
                        {

                            for (int j=dividedTime+1;j<data.get(i).size();j++)
                            {
                                data.get(i+1).add(data.get(i).get(j));
                                queues[i+1].getAdapter()
                                        .notifyDataSetChanged();

                            }
                            while (data.get(i).size()>dividedTime+1)
                            {
                                int lastIndex=data.get(i).size()-1;
                                data.get(i).remove(lastIndex);
                                queues[i].getAdapter()
                                        .notifyDataSetChanged();
                            }




                        }


                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;}
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder
                (DineinChefInterface.THIS);
        builder.setTitle("Confirm")
                .setMessage("New time of each Queue "+dividedTime)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();






    }
    int getQueueTime(int index)
    {int time=0;
        List<String> col=data.get(index);
        for (int i=1;i<col.size();i++)
        {
            itemFactory newItem=new itemFactory(col.get(i));
if (newItem.isValid())
{
    time+=newItem.getETime();
}
        }
        return time;

    }
public boolean completed(String item)
{itemFactory helper=new itemFactory(item);

for (int i=0;i<waitingOrders.size();i++)
{
    Dine_In_orderProxy order=waitingOrders.get(i);
    for (int j=0;j<order.items.size();j++)
    {itemFactory src=new itemFactory(order.items.get(j));
        if (src.getOrderID().equals(helper.getOrderID())
                && src.getID().equals(helper.getID()))
    {
        waitingOrders.get(i).items.remove(j);
        if (waitingOrders.get(i).items.isEmpty()) {
            waitingOrders.remove(i);
            return true;
        }
        else return  false;

    }

    }

}
return  false;
}
}