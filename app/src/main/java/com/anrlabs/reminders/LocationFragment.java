package com.anrlabs.reminders;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.internal.r;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Archie on 12/8/2014.
 */
public   class LocationFragment extends Fragment implements View.OnClickListener {
    private GoogleMap googleMap;


    Double latitude;
    Double longitude;
    NewReminder remiderActivity;
    Geocoder geocoder;
    List<Address> addresses;
    Long radiusValue;
    EditText textAddress;
    EditText textRadius;
    View view;
    //private MapHelper mh;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.location_frag, container, false);

        textAddress = (EditText) view.findViewById(R.id.address);
        textRadius = (EditText) view.findViewById(R.id.radius);

        remiderActivity = (NewReminder) getActivity();
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().
                    findFragmentById(R.id.map)).getMap();
            geocoder = new Geocoder(view.getContext());
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    radiusValue = Long.parseLong(textRadius.getText().toString());
                    if (radiusValue != null) {
                        dropMarker(latLng);
                    } else {
                        //TODO show dialog message
                    }

                }
            });
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        Button button = (Button) view.findViewById(R.id.buttonShow);
        button.setOnClickListener(this);
        return view;
    }

      private void dropMarker(LatLng position) {
          googleMap.clear();

          try {
              addresses = geocoder.getFromLocation(position.latitude,position.longitude,1);
              if (addresses.size() > 0) {
                  latitude = addresses.get(0).getLatitude();
                  longitude = addresses.get(0).getLongitude();
                  Address address = addresses.get(0);

                  textAddress.setText(address.getAddressLine(0)
                          +" "+ address.getAddressLine(1));

              }
          } catch (IOException e) {
            e.printStackTrace();
          }
          updateCamera(position);

      }

    private void updateCamera(LatLng position) {

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 15);
        googleMap.addCircle(new CircleOptions()
                .center(position)
                .radius(radiusValue)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));
        googleMap.animateCamera(cameraUpdate);
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        remiderActivity.setLatitude(latitude);
        remiderActivity.setLongitude(longitude);
        remiderActivity.setRadius(radiusValue);
        remiderActivity.setLocationName(textAddress.getText().toString());
    }
    @Override
    public void onClick(View v) {
        if ( textAddress.getText() == null || textRadius.getText()==null
                || textAddress.getText().toString().isEmpty() ||
                textRadius.getText().toString().isEmpty()) {
            //TODO- create a dialogue box for message
            return;
        }
        radiusValue = Long.parseLong(textRadius.getText().toString());
        try {
            addresses = geocoder.getFromLocationName(textAddress.getText().toString(), 1);
            if (addresses.size() > 0) {
                 latitude = addresses.get(0).getLatitude();
                 longitude = addresses.get(0).getLongitude();
                final LatLng latLng = new LatLng(latitude, longitude);
                updateCamera(latLng);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
