package com.mobile.ocelot.ocelot_mobile_test;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by Abby on 3/29/16.
 */
public class ThoughtRecordViewer extends MainActivity {

    int day, month, year;
    TextView dateView;
    LinearLayout layout;
    TextView tv;
    Button b;
    String[] recs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_viewer);

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

        dateView = (TextView) findViewById(R.id.date_title);
        showDate();

        String get_records = "SELECT * FROM Thought_Records WHERE date(Date) = date('" +
                year + "-" + month_string + "-" + day_string +"');";

        layout = (LinearLayout) findViewById(R.id.view_tr);
        createViews(get_records);
    }

    private void showDate() {
        dateView.setText(new StringBuilder().append("Records for: ").append(month).append("/")
                .append(day).append("/").append(year));
    }

    private void createViews(String selectQuery){
        Log.d("VIEW ---------> ", selectQuery);
        Cursor c = app_db.rawQuery(selectQuery, null);

    }

}
