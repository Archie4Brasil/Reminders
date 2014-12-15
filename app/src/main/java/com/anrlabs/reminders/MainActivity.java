package com.anrlabs.reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.anrlabs.locationreminder.GeoFenceMain;

public class MainActivity extends Activity {
    Context ctx= this;

    Intent intent;
    SQLiteCursor cursor;
    DatabaseHelper db;
    SimpleCursorAdapter myCursorAdapter;
    protected ListView myListView;
    GeoFenceMain geoFenceMain;

    private static DatabaseHelper sInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, NewReminder.class);

        populateListView();



       /////////////////////// on Click listener for ListView (short click)////////////////////////

       myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           TextView pos = (TextView)view.findViewById(R.id.id);
           String index = pos.getText().toString();

           editItemInListDialog(index);
           }
       });

        ////////////////////////////////////////////////////////////////////////////////////



        ////////////////////// long click Listener for ListView ///////////////////////////////////

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {

            TextView pos = (TextView)arg1.findViewById(R.id.id);
            String index = pos.getText().toString();

            deleteItemFromList(index);

            return true;
            }
        });




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


        switch (item.getItemId()) {
             case R.id.add_reminder:
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void populateListView() {


        Cursor cursor = DatabaseHelper.getInstance(this).loadReminders();


        myCursorAdapter = new SimpleCursorAdapter(this, R.layout.row,
                cursor, new String[]{DatabaseHelper.ID, DatabaseHelper.TITLE,
                DatabaseHelper.DATE, DatabaseHelper.TIME,DatabaseHelper.LOCATION_NAME},
                new int[]{R.id.id, R.id.title, R.id.date, R.id.time,R.id.locationName}, 0);

        myListView = (ListView) findViewById(R.id.listViewMain);
        myListView.setAdapter(myCursorAdapter);

    }


    public void editItemInListDialog(String position){
        final long editMessage = Long.parseLong(position);

        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.this);

        alert.setTitle("Edit");
        alert.setMessage("Do you want edit this reminder?");

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent.putExtra("idNumber", editMessage);
                startActivity(intent);
                //populateListView();
            }

        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    public void deleteItemFromList(String position) {

        final long removeMessage = Long.parseLong(position);
        AlertDialog.Builder alert = new AlertDialog.Builder(
                MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this message?");

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper.getInstance(ctx).deleteData(removeMessage);
                geoFenceMain = new GeoFenceMain();
                geoFenceMain.removeGeoFence(getParent(),removeMessage.toString());
                populateListView();
            }

        });


        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        alert.show();


    }

}