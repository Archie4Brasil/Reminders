package com.anrlabs.reminders;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Archie on 12/8/2014.
 */
public class NewReminder extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_new);
    }
}
