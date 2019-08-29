package com.green.watercounter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    int[] fillbar;
    static int cupCounter;
    Daily today;
    final String TAG = "Main Activity";
    ProgressBar pBar;
    private Toolbar toolbar;
    WeekList weekList;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //objects
        ImageButton add = findViewById(R.id.buttonAddWater);
        ImageButton minus = findViewById(R.id.buttonMinus);
        pBar = findViewById(R.id.progressBar);
        pBar.setMax(8);
        cupCounter = 0;

        //toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("Water Counter");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.menu_main);

        //HARDCODED TEST WEEKLIST
        weekList = new WeekList();
        try {
            weekList.addDay(new Daily(3, new GregorianCalendar(2019,7,3)));
            weekList.addDay(new Daily(6, new GregorianCalendar(2019,7,2)));
            weekList.addDay(new Daily(6, new GregorianCalendar(2019,7,1)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //load if exists, new if not
        //final WeekList weekList = new WeekList();
        //weekList.load();


        //what day is it?
        if (weekList.size() < 1) {  //if empty list
            Log.d(TAG, "Week list size is " + weekList.size());
            today = new Daily();
            try {
                weekList.addDay(today);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {  //if list is not empty
            GregorianCalendar date = new GregorianCalendar();
            GregorianCalendar last = new GregorianCalendar();
            int year, month, day;
            year = weekList.get(weekList.size()-1).getYear();
            month = weekList.get(weekList.size()-1).getMonth();
            day = weekList.get(weekList.size()-1).getDay();
            last.set(year, month, day);

            //if its the same day as the last daily on the list, continue adding
            if (last.get(Calendar.YEAR) == date.get(Calendar.YEAR) && last.get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                    last.get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
                cupCounter = weekList.get(weekList.size() - 1).getNumCups();
                pBar.setProgress(cupCounter);
            }
            else {  //if not the same day, create new daily
                today = new Daily();
                try {
                    weekList.addDay(today);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                cupCounter = 0;
                pBar.setProgress(cupCounter);
            }
        }



        //ADD button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cupCounter < 8) {
                    cupCounter++;
                    pBar.setProgress(cupCounter);
                    Log.d(TAG, "Adding... " + cupCounter);
                    weekList.getCurrent().add();
                }
                else {
                    //keep full bar, just update numCups
                    cupCounter++;
                    Log.d(TAG, "Adding... " + cupCounter);
                    weekList.getCurrent().add();

                    Toast toast = Toast.makeText(getApplicationContext(), "You've had over 8 cups of water! Current count: " + cupCounter, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //MINUS button
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cupCounter > 0 && cupCounter <= 8) {  //cupCounter between 0 and 8
                    cupCounter--;
                    pBar.setProgress(cupCounter);
                    Log.d(TAG, "Subtracting..." + cupCounter);
                    weekList.getCurrent().minus();
                }
                else if (cupCounter > 8) {     //cupCounter greater than 8
                    //keep pbar full until less than 8
                    cupCounter--;
                    Log.d(TAG, "Subtracting..." + cupCounter);
                    weekList.getCurrent().minus();
                }
                else {
                    //keep empty bar
                    //stay at 0
                    cupCounter = 0;
                    weekList.getCurrent().setNumCups(cupCounter);
                }
            }
        });



        //TOOLBAR
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_show:
                        Log.d(TAG, "Toolbar action clicked");

                        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                        intent.putExtra("WeekList", (Parcelable) weekList);
                        startActivity(intent);

                    default:
                        return MainActivity.super.onOptionsItemSelected(menuItem);
                }
                //return false;
            }
        });

    }



}
