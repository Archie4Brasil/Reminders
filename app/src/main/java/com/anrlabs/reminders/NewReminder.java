package com.anrlabs.reminders;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Archie on 12/8/2014.
 */
public class NewReminder extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_new);
    }

    //method to handle fragment selected: time or location (default time)
    public void selectFrag(View fragSelected)
    {
        Fragment fillFrame;

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
        FragmentManager manager = getFragmentManager();
        FragmentTransaction fragTransaction = manager.beginTransaction();
        fragTransaction.replace(R.id.main_frag, fillFrame);
        fragTransaction.commit();
    }

}
