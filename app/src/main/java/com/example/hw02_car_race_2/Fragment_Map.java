package com.example.hw02_car_race_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class Fragment_Map extends Fragment {

    private MaterialButton frame2_BTN_map;
    private AppCompatActivity activity;
    //private CallBack_Map callBack_map;


    private boolean isMapReady = false;
    private GoogleMap googleMap;
    private MarkerOptions markerOptions;
    private final int ZOOM = 15;
    private final LatLng collegeLocation = new LatLng(32.115139,34.817804);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_maps);
        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                Fragment_Map.this.googleMap = googleMap;
                isMapReady = true;

                // Set map to zoom on college location at first
                Fragment_Map.this.googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(collegeLocation, ZOOM));

                // Initialize marker options
                markerOptions = new MarkerOptions();
                markerOptions.position(collegeLocation);
                // Add marker on map
                Fragment_Map.this.googleMap.clear();
                Fragment_Map.this.googleMap.addMarker(markerOptions);
            }
        });

        return view;
    }

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setFocusOnMapByLocation(LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM));
        markerOptions.position(latLng);
        // Add marker on map
        googleMap.clear();
        googleMap.addMarker(markerOptions);
    }

    public boolean isMapReady() {
        return isMapReady;
    }

    public void setMapReady(boolean mapReady) {
        isMapReady = mapReady;
    }


}