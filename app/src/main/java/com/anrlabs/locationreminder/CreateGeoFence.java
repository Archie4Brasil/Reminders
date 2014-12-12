package com.anrlabs.locationreminder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import java.util.List;

/**
 * Class for connecting to Location Services and requesting geofences.
 * <b>
 * Note: Clients must ensure that Google Play services is available before requesting geofences.
 * </b> Use GooglePlayServicesUtil.isGooglePlayServicesAvailable() to check.
 *
 *
 * To use a GeofenceRequester, instantiate it and call AddGeofence(). Everything else is done
 * automatically.
 *
 */
public class CreateGeoFence
                implements
        OnAddGeofencesResultListener,
        ConnectionCallbacks,
        OnConnectionFailedListener {
    private Geofence mGeoFence;
    private LocationClient mLocationClient;
    private PendingIntent mGeofencePendingIntent;
    private Activity mActivity;
    List<Geofence> mLstGeoFence;
    private boolean mGeofenceAdded = false;
    public CreateGeoFence(Activity activityContext) {
        // Save the context
        mActivity = activityContext;
        mGeofencePendingIntent = null;
        mLocationClient = null;

    }
    public void addGeoFence(List<Geofence> geofence) {
        mLstGeoFence = geofence;
        this.requestConnection().connect();
    }
     private GooglePlayServicesClient requestConnection() {
         if (mLocationClient == null) {

             mLocationClient = new LocationClient(mActivity, this, this);
         }
        return mLocationClient;
    }

    private void requestDisconnection() {

        requestConnection().disconnect();
    }

    @Override
    public void onAddGeofencesResult(int code, String[] strings) {
      Intent broadcastIntent = new Intent();
        if (LocationStatusCodes.SUCCESS == code) {
            broadcastIntent.setAction(GeoFenceConsants.ACTION_GEOFENCES_ADDED)
                    .addCategory(GeoFenceConsants.CATEGORY_LOCATION_SERVICES);

        } else {

            broadcastIntent.setAction(GeoFenceConsants.ACTION_GEOFENCE_ERROR)
                    .addCategory(GeoFenceConsants.CATEGORY_LOCATION_SERVICES);
        }
         LocalBroadcastManager.getInstance(mActivity).sendBroadcast(broadcastIntent);

         requestDisconnection();

    }


    @Override
    public void onConnected(Bundle bundle) {
        this.continueAddGeofences();
    }

    @Override
    public void onDisconnected() {
        mLocationClient = null;
    }

    /**
     * Once the connection is available, send a request to add the Geofences
     */
    private void continueAddGeofences() {

        if (null == mGeofencePendingIntent) {
            Intent intent = new Intent("com.anrlabs.ACTION_RECEIVE_GEOFENCE");
             mGeofencePendingIntent = PendingIntent.getBroadcast(
                    mActivity,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
       // Send a request to add the current geofences
        mLocationClient.addGeofences(mLstGeoFence,mGeofencePendingIntent,this);
    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
        //do something
        }
    }
}
