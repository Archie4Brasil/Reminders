package com.anrlabs.reminders;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;


public class Preferences extends Activity implements CompoundButton.OnCheckedChangeListener {

    private CheckBox vibrate, ringer;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public AudioManager mAudioMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        vibrate = (CheckBox) findViewById(R.id.checkBox);
        ringer = (CheckBox) findViewById(R.id.checkBox1);
        vibrate.setOnCheckedChangeListener(this);
        ringer.setOnCheckedChangeListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        preferences = getSharedPreferences("alert", 0);

        if (compoundButton.getId() == R.id.checkBox) {
            // mAudioMgr.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                if(isChecked) {
                    vibrate.setChecked(true);
                    editor = preferences.edit();
                   // editor.putInt("vibrate", 1);
                    ringer.setChecked(false);
                }
                else vibrate.setChecked(false);
            editor.putBoolean("check", isChecked);

        }
        else {
        //mAudioMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            if(isChecked) {
                ringer.setChecked(true);
                editor = preferences.edit();
                vibrate.setChecked(false);
            }

            else ringer.setChecked(false);//editor.putBoolean("check1", isChecked);

            editor.putBoolean("check1", isChecked);


        }
        editor.commit();

    }






    public void onResume(){
        preferences = getSharedPreferences("alert", 0);
        vibrate.setChecked(preferences.getBoolean("check", false));
        ringer.setChecked(preferences.getBoolean("check1", false));
        super.onResume();
    }


}
