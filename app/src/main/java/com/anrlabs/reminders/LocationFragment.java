package com.anrlabs.reminders;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Archie on 12/8/2014.
 */
public class LocationFragment extends Fragment {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private MapView map;
    //private MapHelper mh;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.location_frag, container, false);

        // Gets to GoogleMap from the MapView and does initialization stuff
        //mMap = map.getMap();
        return v;
    }

}
