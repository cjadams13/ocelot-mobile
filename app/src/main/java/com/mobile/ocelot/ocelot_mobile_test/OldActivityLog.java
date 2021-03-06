package com.mobile.ocelot.ocelot_mobile_test;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Cameron on 3/29/16.
 */
public class OldActivityLog extends MainActivity {

    int day, month, year;
    TextView dateView;
    LinearLayout layout;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_activity_log);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle b = getIntent().getExtras();

        day = b.getInt("day");
        String day_string;
        if (day < 10){
            day_string = "0"+Integer.toString(day);
        }else{
            day_string = Integer.toString(day);
        }

        month = b.getInt("month");
        String month_string;
        if (month < 10){
            month_string = "0"+Integer.toString(month);
        }else{
            month_string = Integer.toString(month);
        }

        year = b.getInt("year");

        dateView = (TextView) findViewById(R.id.date_titleAL);
        showDate();

        String get_records = "SELECT * FROM Activity_Log WHERE date(Date) = date('" +
                year + "-" + month_string + "-" + day_string +"');";

        layout = (LinearLayout) findViewById(R.id.view_al);
        createViews(get_records);
    }

    private void showDate() {
        dateView.setText(new StringBuilder().append("Records for: ").append(month).append("/")
                .append(day).append("/").append(year));
    }

    private void createViews(String selectQuery){
        Log.d("VIEW ---------> ", selectQuery);
        //selectQuery = "SELECT * FROM Activity_Log";
        Cursor c = app_db.rawQuery(selectQuery, null);
        //String test = c.getString(c.getColumnIndex("Thoughts"));
        //Log.d("VIEW ---------> ", test);

        String[] records = new String[c.getCount()];
        String activity;
        Log.d("LOGS ---------> ", Integer.toString(c.getCount()));
        if (c.moveToFirst()) {
            int i = 0;
            while (!c.isAfterLast()) {
                activity = c.getString(c.getColumnIndex("Activity"));
                Log.d("ACTIVITY ---------> ", activity);
                records[i] = activity;
                c.moveToNext();
                i++;
            }
        }
        c.close();

        c = app_db.rawQuery(selectQuery, null);

        String[] moods = new String[c.getCount()];
        String mood;
        Log.d("MOODS ---------> ", Integer.toString(c.getCount()));
        if (c.moveToFirst()) {
            int i = 0;
            while (!c.isAfterLast()) {
                mood = c.getString(c.getColumnIndex("Mood"));
                Log.d("MOOD -------> ", mood);
                moods[i] = mood;
                c.moveToNext();
                i++;
            }
        }
        c.close();


//        Log.d("VIEWS ---------> ", Integer.toString(records.length));

        for (int j=0; j< records.length; j++){
            tv = new TextView(this);
            tv.setText("Activity: " + records[j]);
            tv.setTextAppearance(this, android.R.style.TextAppearance_Large);
            layout.addView(tv);

            tv = new TextView(this);
            tv.setText("Mood Score: " + moods[j]);
            tv.setTextAppearance(this, android.R.style.TextAppearance_Medium);
            Log.d("MOOD FOR VIEW ----> ", records[j]);
            layout.addView(tv);

        }
    }
}
