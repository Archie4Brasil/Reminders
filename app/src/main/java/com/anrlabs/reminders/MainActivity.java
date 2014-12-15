package com.anrlabs.reminders;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.widget.AdapterView.AdapterContextMenuInfo;

public class MainActivity extends Activity {

    Context ctx= this;
    Intent intent;
    SQLiteCursor cursor;
    DatabaseHelper db;

    ListView listView;
    SimpleCursorAdapter myCursorAdapter;
    boolean populateEditScreenFlag;
    private static DatabaseHelper sInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(this, NewReminder.class);

        populateListView();
        populateEditScreenFlag = false;
        listView = (ListView) findViewById(R.id.listViewMain);

        /////////////////////// long click (brings up delete dialog box ///////////////////////////

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // setting onItemLongClickListener and passing the position to the function
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {

             TextView pos = (TextView)arg1.findViewById(R.id.id);
                String index = pos.getText().toString();

                deleteItemFromListDialog(index);

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
    protected void onResume() {
        populateListView();
        super.onResume();
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
        //action bar options
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

    //populate list view with database values
    public void populateListView() {

        Cursor cursor = DatabaseHelper.getInstance(this).loadReminders();

        myCursorAdapter = new SimpleCursorAdapter(this, R.layout.row,
                cursor, new String[]{DatabaseHelper.ID, DatabaseHelper.TITLE, DatabaseHelper.MESSAGE,
                DatabaseHelper.DATE, DatabaseHelper.TIME, DatabaseHelper.XCOORDS, DatabaseHelper.YCOORDS,
                DatabaseHelper.RADIUS}, new int[]{R.id.id, R.id.title,
                R.id.memo, R.id.date, R.id.time, R.id.xcoords, R.id.ycoords, R.id.radius}, 0);

        ListView myListView = (ListView) findViewById(R.id.listViewMain);
        myListView.setAdapter(myCursorAdapter);
    }

    //dialog box for deleting
    public void deleteItemFromListDialog(String position) {

        final long removeMessage = Long.parseLong(position);
        AlertDialog.Builder alert = new AlertDialog.Builder(
                                    MainActivity.this);

        alert.setTitle("Delete");
        alert.setMessage("Do you want delete this message?");

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DatabaseHelper.getInstance(ctx).deleteData(removeMessage);
                DatabaseHelper.getInstance(ctx).close();
                populateListView();
                displayToast();
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

    //message prompt to verify message was deleted
    public void displayToast(){
        Toast.makeText(this, "Message Deleted", Toast.LENGTH_SHORT).show();
    }

}