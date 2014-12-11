package com.anrlabs.reminders;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Archie on 12/8/2014.
 */
public class LocationFragment extends Fragment {
/*
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapView map;
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.location_frag, container, false);
        //map = (MapView) v.findViewById(R.id.map);
        //map.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        //mMap = map.getMap();
        return v;
    }
    /*
    private void setUpMapIfNeeded() {

        //map = (MapView) getView().findViewById(R.id.map);
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            //mMap = ((SupportMapFragment) new FragmentActivity().getSupportFragmentManager().findFragmentById(R.id.map))
                   // .getMap();

            mMap = ((MapView) getView().findViewById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }
    */
    //return to main without saving entry
    // public void cancelSaveData(View view) {
    //     startActivity(new Intent(this, MainActivity.class));
    //}

    //overriding back button to save data


    public void onBackPressed() {

        //dataFiller = new ContentValues();
        //dataFiller.put(DatabaseHelper.TITLE,);

        //dataCarrier = new DatabaseHelper(this, DatabaseHelper.TABLE, null, 1);

       // super.onBackPressed();
    }
    /*
    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
    */
}
