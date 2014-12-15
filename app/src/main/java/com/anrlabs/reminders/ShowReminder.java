package com.anrlabs.reminders;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ShowReminder extends Activity {
    int id = 0;
    private GoogleMap googleMap;
    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminder);
        String strId = getIntent().getStringExtra("notificationId");

        Integer id = Integer.parseInt(strId);
        Cursor cursor = DatabaseHelper.getInstance(this).loadRemiderDetails(id);
        cursor.moveToFirst();

        ((TextView)findViewById(R.id.TV_TODO_value))
                    .setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.TITLE)));
        ((TextView)findViewById(R.id.TV_NotesValue))
                    .setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.MESSAGE)));
        if (cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE) )!= null) {
            ((TextView) findViewById(R.id.TV_Time_Value))
                    .setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.DATE)) + " - " +
                            cursor.getString(cursor.getColumnIndex(DatabaseHelper.TIME)));
        }
        googleMap = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.map_view)).getMap();

        String str_latitude = cursor.getString(cursor.getColumnIndex(DatabaseHelper.XCOORDS));
        String str_longitude = cursor.getString(cursor.getColumnIndex(DatabaseHelper.YCOORDS));
        String str_radius = cursor.getString(cursor.getColumnIndex(DatabaseHelper.RADIUS));
        if (str_latitude != null && str_longitude!=null && str_radius !=null) {
            ((TextView)findViewById(R.id.TV_Location_Value))
                    .setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.LOCATION_NAME)));

            geocoder = new Geocoder(this);
            final LatLng latLng = new LatLng(Double.parseDouble(str_latitude), Double.parseDouble(str_longitude));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            googleMap.addCircle(new CircleOptions()
                    .center(latLng)
                    .radius(Double.parseDouble(str_radius))
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));
            googleMap.animateCamera(cameraUpdate);
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_reminder, menu);
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
    /**
     * Called when the activity has detected the user's press of the back
     * key.  The default implementation simply finishes the current activity,
     * but you can override this to do whatever you want.
     */
    public void onBackPressed() {
        Intent mainActivity = new Intent(this,MainActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);

    }
}
