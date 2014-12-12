package com.anrlabs.reminders;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

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
 * Created by Grand on 12/11/2014.
 */
public class MapHelper extends SupportMapFragment {



    private Context context;
    private CircleOptions circleOption;
    private double radius = 5;
    private GoogleMap mMap;
    private LatLng coordinates;
    private double latitude, longitude;
    private String address1, address2, state, zipCode, country;
    private Marker marker;
    private String address;



    public MapHelper() {
        }

    public GoogleMap getRoadMap(Context context, String address){
        circleOption = new CircleOptions();
        this.address = address;
        this.context = context;
        mMap = this.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
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
        setLocationRadius(5);
        addLocationMarker();
        return mMap;
    }

    public void addLocationMarker(){
        mMap.addMarker(new MarkerOptions().position(getCoordinates()));
    }

    public LatLng getCoordinates(){
        return addrToLoc(address);
    }

    public void setLocationRadius(double radius) {
        mMap.addCircle(circleOption.radius(radius));
    }

    public double getLocationRadius() {

        return circleOption.getRadius();
    }

    public double getLatitude() {
        return getCoordinates().latitude;
    }

    public double getLongitude() {
        return getCoordinates().longitude;
    }


    public LatLng addrToLoc(String currentAddress) {
        List<Address> address = null;

        try {
            address =
                    new Geocoder(context).getFromLocationName(
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
    }

    public List<String> locToAddr(LatLng coordinates) {

        List<Address> currentAddress = null;
        List<String> laterAddress = null;

        try {
            currentAddress =
                    new Geocoder(context).getFromLocation(coordinates.latitude, coordinates.longitude, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        if (!currentAddress.isEmpty()) {
            address1 = currentAddress.get(0).getAddressLine(0);
            address2 = currentAddress.get(0).getAddressLine(1);
            state = currentAddress.get(0).getAdminArea();
            zipCode = currentAddress.get(0).getPostalCode();
            country = currentAddress.get(0).getCountryName();
        }

        laterAddress.add(address1);
        laterAddress.add(address2);
        laterAddress.add(state);
        laterAddress.add(zipCode);
        laterAddress.add(country);

        return laterAddress;
    }

}


