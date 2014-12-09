package com.anrlabs.reminders;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {

    Intent intent;
    SQLiteCursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this,NewReminder.class);

        //change action bar title color
        int actionBarTitleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");

        if (actionBarTitleId > 0) {
            TextView title = (TextView) findViewById(actionBarTitleId);
            if (title != null) {
                title.setTextColor(Color.GREEN);
            }

            DatabaseHelper db = new DatabaseHelper(this, null, 1);

            cursor = (SQLiteCursor) db.getReadableDatabase().rawQuery(
                    "SELECT _ID, " + DatabaseHelper.TITLE + ", " + DatabaseHelper.MESSAGE + ", " +
                            DatabaseHelper.DATE + " FROM " + DatabaseHelper.TABLE +
                            ", " + DatabaseHelper.LOCATION +" ORDER BY " +
                            DatabaseHelper.DATE, null);

      }
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.add_reminder:
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    public void populateListView(){

        ListView myListView = (ListView) findViewById(R.id.listViewMain);
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(this, R.layout.activity_main,
                cursor, new String[] { DatabaseHelper.TITLE, DatabaseHelper.MESSAGE,
                DatabaseHelper.DATE, DatabaseHelper.TIME, DatabaseHelper.LOCATION }, new int[] { R.id.textView,
                R.id.textView2, R.id.textView3 }, 0);
        myListView.setAdapter(myCursorAdapter);

    }



}
