package com.example.lenovo.eats.Activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.example.lenovo.eats.Adapters.PagerAdapterHallAssignWaiters;
import com.example.lenovo.eats.R;

import java.util.List;

public class HallassignTablesWaiters extends AppCompatActivity {
    //private List<chatTab> obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);


        setContentView(R.layout.activity_main_page);



        TabLayout tabLayout=(TabLayout) findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("Assign Waiters"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        //yaha se Suleman
        final ViewPager viewPager=(ViewPager)findViewById(R.id.pager);
        final PagerAdapterHallAssignWaiters pagerAdapter=new PagerAdapterHallAssignWaiters(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        //viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //MenuInflater inflater=getMenuInflater();
        //inflater.inflate(R.menu.menu,menu);
        //return super.onCreateOptionsMenu(menu);
        return false;
    }



}
