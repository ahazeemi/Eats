package com.example.lenovo.eats.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.eats.Fragments.HallOrder;
import com.example.lenovo.eats.Fragments.HallBill;
import com.example.lenovo.eats.Fragments.HallStart;

/**
 * Created by Awais on 11/19/2017.
 */

public class PagerAdapterHallManager extends FragmentStatePagerAdapter{
    int noOfTabs;
    //List<chatTab> t;
    boolean i=true;

    public PagerAdapterHallManager(FragmentManager fragmentManager, int numberOfTabs){
        super(fragmentManager);
        this.noOfTabs=numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HallOrder chats = null; //suleman
                chats=new HallOrder();

                //CHATS chats = new CHATS();
                return chats;
                case 1:

                HallBill contacts= null;
                contacts = new HallBill();
                return contacts;
            case 2:
//                CONTACTS contacts1= null;
  //              contacts1 = new CONTACTS();
                HallStart contacts1= null;
                contacts1 = new HallStart();


                return contacts1;


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
