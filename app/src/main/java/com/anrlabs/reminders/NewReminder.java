package com.anrlabs.reminders;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.CircleOptions;


/**
 * Created by Archie on 12/8/2014.
 */
public class NewReminder extends FragmentActivity {

    protected DatabaseHelper dataCarrier;
    protected ContentValues dataFiller;
    protected EditText titleCarrier, memoCarrier;
    protected Fragment fillFrame, other;
    private FragmentTransaction fragTransaction;
    double latitude;
    double longitude;
    CircleOptions circle;
    private MapHelper mapView = null;
    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private Intent alarmIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_new);

        fillFrame = new TimeFragment();
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.main_frag, fillFrame);
        fragTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();


        circle = new CircleOptions();
        mapView = new MapHelper();
        // setUpMapIfNeeded();

        // Retrieve a PendingIntent that will perform a broadcast
        alarmIntent = new Intent(this, AlarmHandler.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
    }


    //method to handle fragment selected: time or location (default time)
    public void selectFrag(View fragSelected)
   // public void selectFrag(View v)
    {
        //sets fragment with view form class
        if (fragSelected == findViewById(R.id.locationFrag)) {
            //fragTransaction.replace(R.id.main_frag, getFragmentManager().findFragmentById(R.id.map));

            fillFrame =  new LocationFragment();
            //fillFrame = getFragmentManager().findFragmentById(R.id.map);
            // fragTransaction.show(mapFrag);
        } else
            fillFrame = new TimeFragment();

        fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.main_frag, fillFrame);
        fragTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();

        if(mapView == null) {
            mapView = new MapHelper();
            mapView.getRoadMap(this, "Grand Circus, Detroit, MI");
            mapView.setLocationRadius(10);
            latitude = mapView.getLatitude();
            longitude = mapView.getLongitude();
            //sets fragment with view form class
            if (fragSelected == findViewById(R.id.locationFrag)) {
                fillFrame = new LocationFragment();
            } else {
                fillFrame = new TimeFragment();
            }

            dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);
            dataCarrier.addData(dataFiller);
        }
    }

    //saving data
    public void saveData(View v)
    {
        savingData();
        settingAlarm();

        startActivity(new Intent(this, MainActivity.class));
    }

    //populating DataBase
    public void savingData()
    {
        titleCarrier = (EditText) findViewById(R.id.titleBox);
        memoCarrier = (EditText) findViewById(R.id.memoBox);

        dataFiller = new ContentValues();
        dataFiller.put(DatabaseHelper.TITLE, titleCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.MESSAGE, memoCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.TIME, TimeFragment.passTime());
        dataFiller.put(DatabaseHelper.DATE, TimeFragment.passDate());

        dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);
        dataCarrier.addData(dataFiller);
    }

    public void settingAlarm()
    {
        alarmIntent.putExtra("dataID", DatabaseHelper.ID);

        manager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        int interval = 10000;

        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, TimeFragment.timeAlarmMillis(), 0, pendingIntent);
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
    }
}
