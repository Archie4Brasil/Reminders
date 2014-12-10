package com.anrlabs.reminders;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by Archie on 12/8/2014.
 */
public class NewReminder extends Activity{

    protected Fragment fillFrame;
    protected DatabaseHelper dataCarrier;
    protected ContentValues dataFiller;
    protected EditText titleCarrier, memoCarrier;
    private DatePicker datePicked;
    private TimePicker timePicked;
    public static String dateDB, timeDB;
    private int myHour, myMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_new);

        fillFrame = new TimeFragment();
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.main_frag, fillFrame);
        fragTransaction.commit();

        titleCarrier = (EditText) findViewById(R.id.titleBox);
        memoCarrier = (EditText) findViewById(R.id.memoBox);

        datePicked = (DatePicker) findViewById(R.id.dateRemember);
        timePicked = (TimePicker) findViewById(R.id.timeRemeber);

    }



    //method to handle fragment selected: time or location (default time)
    public void selectFrag(View fragSelected)
    {
        //sets fragment with view form class
        if(fragSelected==findViewById(R.id.locationFrag))
        {
            fillFrame = new LocationFragment();
        }
        else
        {
            fillFrame = new TimeFragment();
        }

        //switch the content of the fragment
        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.main_frag, fillFrame);
        //fragTransaction.addToBackStack(null);
        fragTransaction.commit();
    }

    //return to main without saving entry
    public void cancelSaveData(View view) {
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this, timeDB, Toast.LENGTH_SHORT);
    }



    //overriding back button to save data
    @Override
    public void onBackPressed() {
        savingData();

        super.onBackPressed();
    }

    public void savingData()
    {
        titleCarrier.setInputType(InputType.TYPE_CLASS_TEXT);
        memoCarrier.setInputType(InputType.TYPE_CLASS_TEXT);

        dataFiller = new ContentValues();
        dataFiller.put(DatabaseHelper.TITLE, titleCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.MESSAGE, memoCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.TIME, TimeFragment.passTime());
        dataFiller.put(DatabaseHelper.DATE, TimeFragment.passDate());

        dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);
        dataCarrier.addData(dataFiller);
    }


}
