package com.anrlabs.reminders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;

/**
 * Created by Archie on 12/8/2014.
 */
public class LocationFragment extends MapFragment {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapView map;
    //private MapHelper mh;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.location_frag, container, false);
        map = (MapView) v.findViewById(R.id.map);
        map.onCreate(savedInstanceState);
        if(mMap == null){

           // mMap = mh.getRoadMap(context.getApplicationContext(), "600 Independence Ave SW, Washington, DC 20560");

            mMap = map.getMap();
            mMap.setMyLocationEnabled(true);
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        }
        // Gets to GoogleMap from the MapView and does initialization stuff
        //mMap = map.getMap();
        return v;
    }

}
