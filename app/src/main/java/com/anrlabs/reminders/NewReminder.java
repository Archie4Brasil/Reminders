package com.anrlabs.reminders;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Archie on 12/8/2014.
 */
public class NewReminder extends Activity{

    protected Fragment fillFrame;
    DatabaseHelper dataCarrier;
    ContentValues dataFiller;
    EditText titleCarrier, memoCarrier;

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
    }

    //overriding back button to save data

    /*@Override
    public void onBackPressed() {

        dataFiller = new ContentValues();
        dataFiller.put(DatabaseHelper.TITLE,);

        dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);

        super.onBackPressed();
    }*/
}
