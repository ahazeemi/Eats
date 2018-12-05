package com.example.lenovo.eats.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.lenovo.eats.Fragments.HallAssignTablets;

import java.util.List;

/**
 * Created by Awais on 11/19/2017.
 */

public class PagerAdapterAssign extends FragmentStatePagerAdapter{
    int noOfTabs;
    //List<chatTab> t;
    boolean i=true;

    public PagerAdapterAssign(FragmentManager fragmentManager, int numberOfTabs){
        super(fragmentManager);
        this.noOfTabs=numberOfTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HallAssignTablets chats = null; //suleman
                chats=new HallAssignTablets();

                //CHATS chats = new CHATS();
                return chats;



            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
