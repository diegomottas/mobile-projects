package com.wbertan.aula_12_exemplo02;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wbertan.aula_12_exemplo02.modelo.MapLocation;
import com.wbertan.aula_12_exemplo02.modelo.Pensamento;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by wbertan on 19/05/15.
 */

public class DetalheFragment extends Fragment implements LocationListener {
    private GoogleMap mMap;

    private MapLocation mapLocation = new MapLocation();

    View view;

    //Método que é chamado quando este Fragment for solicitado
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Infla o layout deste Fragment
        view = inflater.inflate(R.layout.activity_mapa, container, false);

        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(false);

        verifyGPS();
        setCurrentLocation();

        //Retorna a view que deverá ser desenhada na tela
        return view;
    }

    //Métdo
    public void set(Pensamento pensamento) {
    }

    private void setCurrentLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        if (location != null) {
            onLocationChanged(location);

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();

            mapLocation.setLongitude(longitude);
            mapLocation.setLatitude(latitude);

            moveToCurrentLocation(new LatLng(latitude, longitude));

            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if(null!=listAddresses&&listAddresses.size()>0){
                    Address address = listAddresses.get(0);
                    StringBuilder sb = new StringBuilder();
                    String[] arrayAddress = new String[address.getMaxAddressLineIndex()];
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        arrayAddress[i] = address.getAddressLine(i);
                        sb.append(address.getAddressLine(i)).append("\n");
                    }

                    mapLocation.setFullLocation(sb.toString());
                    mapLocation.setCountryCode(address.getCountryCode());
                    mapLocation.setCountryName(address.getCountryName());
                    mapLocation.setFeatureName(address.getFeatureName());
                    mapLocation.setLocality(address.getLocality());
                    mapLocation.setPostalCode(address.getPostalCode());
                    mapLocation.setAdminArea(address.getAdminArea());
                    mapLocation.setSubThoroughfare(address.getSubThoroughfare());
                    mapLocation.setAddress(arrayAddress);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);

        String endereco = mapLocation.getAddress(0);
    }

    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);


    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap(0,0);
            }
        }
    }

    private void setUpMap(long latitude, long longitude) {
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Marker"));
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, getActivity(), 0).show();
            return false;
        }
    }

    private void verifyGPS() {
        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else {
            setCurrentLocation();
        }
    }
}