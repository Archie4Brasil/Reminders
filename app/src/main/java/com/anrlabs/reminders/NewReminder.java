package com.anrlabs.reminders;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Archie on 12/8/2014.
 */
public class NewReminder extends FragmentActivity {

    protected DatabaseHelper dataCarrier;
    protected ContentValues dataFiller;
    protected EditText titleCarrier, memoCarrier;
    protected Fragment fillFrame, other;
    private MapView map;
    private FragmentTransaction fragTransaction;
    double latitude;
    double longitude;
    String Address1;
    String Address2;
    String State;
    String zipcode;
    String Country;
    CircleOptions circle;
    LatLng center;
    private double radius;
    private Fragment mapFrag;
    private MapHelper mapView = null;


    /////////////////////////////////// added by michael //////////////////////////////////////////
    private long rowID;
    int year,day,month;
    //SQLiteCursor cursor;
    Cursor cursor;
    ///////////////////////////////////////////////////////////////////////////////////////////////


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
        //++populateReminder();
    }

    //method to handle fragment selected: time or location (default time)
    public void selectFrag(View fragSelected)
    // public void selectFrag(View v)
    {
        //sets fragment with view form class
        if (fragSelected == findViewById(R.id.locationFrag)) {
            //fragTransaction.replace(R.id.main_frag, getFragmentManager().findFragmentById(R.id.map));

            //fillFrame = new LocationFragment();
            fillFrame = getFragmentManager().findFragmentById(R.id.map);
            // fragTransaction.show(mapFrag);
        } else
            fillFrame = new TimeFragment();

        fragTransaction = getFragmentManager().beginTransaction();
        fragTransaction.replace(R.id.main_frag, fillFrame);
        fragTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTransaction.addToBackStack(null);
        fragTransaction.commit();

        if (mapView == null) {
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

            DatabaseHelper.getInstance(this).addData(dataFiller);
    }
    }

    //overriding back button to save data
    @Override
    public void onBackPressed() {
        savingData();

        super.onBackPressed();
    }

    public void savingData()
    {
        titleCarrier = (EditText) findViewById(R.id.titleBox);
        memoCarrier = (EditText) findViewById(R.id.memoBox);

        dataFiller = new ContentValues();
        dataFiller.put(DatabaseHelper.TITLE, titleCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.MESSAGE, memoCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.TIME, TimeFragment.passTime());
        dataFiller.put(DatabaseHelper.DATE, TimeFragment.passDate());

        DatabaseHelper.getInstance(this).addData(dataFiller);

    }


    ////////////////////////////// Code by Michael ///////////////////////////////////
    public void populateReminder(){

        //Toast.makeText(this, "Editing mode!", Toast.LENGTH_LONG).show();

        long dataBaseID = getIntent().getLongExtra("idNumber", rowID);
        cursor = DatabaseHelper.getInstance(this).editRemiders(16);
        //getRowData(dataBaseID);

        EditText editMemo = (EditText)findViewById(R.id.memoBox);
        editMemo.setText(cursor.getString(2));

        EditText editTitle = (EditText) findViewById(R.id.titleBox);
        editTitle.setText(cursor.getString(1));

        DatePicker editDate = (DatePicker)findViewById(R.id.dateRemember);
        //editDate.init(year, month, day, null);
        //editDate.updateDate(2014, 12, 10);


        TimePicker editTIme = (TimePicker)findViewById(R.id.timeRemeber);
        //editTIme.setCurrentHour(1);
          //editTIme.setCurrentMinute(25);

       }

       public void getRowData(long id){

           /*SQLiteDatabase db = dataCarrier.getReadableDatabase();



           //Integer.parseInt(cursor.getString(0));



         /* dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);

           cursor = (SQLiteCursor) dataCarrier.getReadableDatabase().rawQuery("SELECT " + DatabaseHelper.ID + ", "
                   + DatabaseHelper.TITLE + ", " + DatabaseHelper.MESSAGE + ", "
                   + DatabaseHelper.DATE + ", " + DatabaseHelper.TIME + ", "
                   + DatabaseHelper.XCOORDS + ", " + DatabaseHelper.YCOORDS + ", " + DatabaseHelper.RADIUS +
                   " FROM " + DatabaseHelper.TABLE + " ORDER BY " + DatabaseHelper.DATE, null);
           if (cursor != null) {

               cursor.moveToFirst();
           }*/
       }
    ////////////////////////////////////////////////////////////////////////////////////////////

}
