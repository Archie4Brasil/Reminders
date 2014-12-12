package com.anrlabs.reminders;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Archie on 12/8/2014.
 */
public class TimeFragment extends Fragment{
    static TimePicker timePicked;
    static DatePicker datePicked;
    static int hoursDB, minDB, yearDB, monthDb, dayDB;
    View fragTrasnport;
    Bundle saveState;
    private static Calendar today;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragTrasnport = inflater.inflate(R.layout.time_fragment, container, false);

        timePicked = (TimePicker) fragTrasnport.findViewById(R.id.timeRemeber);
        timePicked.setIs24HourView(true);
        timePicked.setOnTimeChangedListener(OnTimeChanged);

        Calendar today = Calendar.getInstance();
        datePicked = (DatePicker) fragTrasnport.findViewById(R.id.dateRemember);
        datePicked.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                    today.get(Calendar.DAY_OF_MONTH), onDateChanged);

        /*saveState = new Bundle();

        onResume();

        if (saveState != null)
        {
            hoursDB = saveState.getInt("timeHour");
            minDB = saveState.getInt("timeMin");

            timePicked.setCurrentHour(hoursDB);
            timePicked.setCurrentMinute(minDB);
        }*/

        return fragTrasnport;
    }

    TimePicker.OnTimeChangedListener OnTimeChanged =
            new TimePicker.OnTimeChangedListener() {
                @Override
                public void onTimeChanged(
                        TimePicker view,
                        int hourOfDay,
                        int minute) {
                    hoursDB = hourOfDay;
                    minDB = minute;
                }
            };

    DatePicker.OnDateChangedListener onDateChanged=
            new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(
                        DatePicker view,
                        int year,
                        int monthOfYear,
                        int dayOfMonth) {
                    yearDB = year;
                    monthDb = monthOfYear;
                    dayDB = dayOfMonth;
                }
            };



    @Override
    public void onPause() {
        super.onPause();

        //saveData();
        //saveState.putInt("timeHour", hoursDB);
        //saveState.putInt("timeMin", minDB);
    }

    public void saveData()
    {
        ContentValues temp = new ContentValues();
        temp.put(DatabaseHelper.TIME, hoursDB + ":" + minDB);

        
    }

    public static String passTime()
    {
        return hoursDB + ":" + minDB;
    }

    public static String passDate()
    {
        return monthDb + "/" + dayDB + "/" + yearDB;
    }

    public static void setTime(int hour, int min)
    {
        timePicked.setCurrentMinute(min);
        timePicked.setCurrentHour(hour);

    }

    public static long timeAlarmMillis()
    {
        today.set(yearDB, monthDb, dayDB, hoursDB, minDB);

        //machine milliseconds * Milliseconds * seconds * min * hour * day * month * year
        return (System.currentTimeMillis() + today.getTimeInMillis());
    }
}
