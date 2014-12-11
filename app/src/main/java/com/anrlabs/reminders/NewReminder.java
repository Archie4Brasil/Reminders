package com.anrlabs.reminders;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * Created by Archie on 12/8/2014.
 */
public class NewReminder extends FragmentActivity {

    protected Fragment fillFrame;
    protected DatabaseHelper dataCarrier;
    protected ContentValues dataFiller;
    protected EditText titleCarrier, memoCarrier;
    private DatePicker datePicked;
    private TimePicker timePicked;
    public static String dateDB, timeDB;
    private int myHour, myMinute;
    protected Fragment fillFrame, other;
    DatabaseHelper dataCarrier;
    ContentValues dataFiller;
    EditText titleCarrier, memoCarrier;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
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

        titleCarrier = (EditText) findViewById(R.id.titleBox);
        memoCarrier = (EditText) findViewById(R.id.memoBox);

        datePicked = (DatePicker) findViewById(R.id.dateRemember);
        timePicked = (TimePicker) findViewById(R.id.timeRemeber);

        circle = new CircleOptions();
        mapView = new MapHelper();
        // setUpMapIfNeeded();
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

        if(mapView == null) {
            mapView = new MapHelper();
            mapView.getRoadMap(this, "Grand Circus, Detroit, MI");
            mapView.setLocationRadius(10);
            latitude = mapView.getLatitude();
            longitude = mapView.getLongitude();
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
        //FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
        //fragTransaction.replace(R.id.main_frag, fillFrame);
        //fragTransaction.addToBackStack(null);
        //fragTransaction.commit();
        /*
        setUpMapIfNeeded();
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }

        circle.radius(5);
        mMap.addCircle(circle);// map with radius of 5
        radius = circle.getRadius();
        center = circle.getCenter();
        Location l = mMap.getMyLocation();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

            }
        });
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                return false;
            }
        });

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {

            }
        });

    }
//Convert from address to location coordinates
    public LatLng addrToLoc(String currentAddress) {
        List<Address> address = null;

        try {
            address =
                    new Geocoder(this).getFromLocationName(
                            currentAddress, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!currentAddress.isEmpty()) {
            latitude = address.get(0).getLatitude();
            longitude = address.get(0).getLongitude();
        }
        return new LatLng(latitude, longitude);
    //return to main without saving entry
    public void cancelSaveData(View view) {
        startActivity(new Intent(this, MainActivity.class));
        Toast.makeText(this, timeDB, Toast.LENGTH_SHORT);
    }

    public void setLocationRadius(double radius) {
        circle.getRadius();
    }

    public double getLocationRadius() {
        return circle.getRadius();
    }

    //Convert from location coordinates to address text
    public List<String> locToAddr(LatLng coordinates) {

        List<Address> currentAddress = null;
        List<String> laterAddress = null;

        try {
            currentAddress =
                    new Geocoder(this).getFromLocation(coordinates.latitude, coordinates.longitude, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if (!currentAddress.isEmpty()) {
            Address1 = currentAddress.get(0).getAddressLine(0);
            Address2 = currentAddress.get(0).getAddressLine(1);
            State = currentAddress.get(0).getAdminArea();
            Zipcode = currentAddress.get(0).getPostalCode();
            Country = currentAddress.get(0).getCountryName();
        }

        laterAddress.add(Address1);
        laterAddress.add(Address2);
        laterAddress.add(State);
        laterAddress.add(Zipcode);
        laterAddress.add(Country);

        return laterAddress;
    }



    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call  once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    /*
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    //return to main without saving entry
   // public void cancelSaveData(View view) {
   //     startActivity(new Intent(this, MainActivity.class));
    //}

    //overriding back button to save data


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
        //dataFiller.put(DatabaseHelper.TITLE,);
        dataFiller.put(DatabaseHelper.TITLE, titleCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.MESSAGE, memoCarrier.getText().toString());
        dataFiller.put(DatabaseHelper.TIME, TimeFragment.passTime());
        dataFiller.put(DatabaseHelper.DATE, TimeFragment.passDate());

        //dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */

    /*
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    */
        dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);
        dataCarrier.addData(dataFiller);
    }
}
