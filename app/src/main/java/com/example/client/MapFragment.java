package com.example.client;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.client.Models.Event;
import com.example.client.Models.Person;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Set;
import java.util.TreeMap;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback,
        GoogleMap.OnMarkerClickListener
{
    final static String TAG = "MapFragment";
    private GoogleMap map;
    TextView description;

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);
        description = (TextView) view.findViewById(R.id.description);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }
    @Override
    public void onMapLoaded()
    {

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {

        map = googleMap;
        map.setOnMapLoadedCallback(this);
        TreeMap<String, Event> events = PersonData.getHappenings();
        Set<String> keys = events.keySet();
        for(String x: keys)
        {
            Event currentEvent = events.get(x);
            LatLng currentPlace = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());
            Marker current = map.addMarker(new MarkerOptions().position(currentPlace).title(events.get(x).getEventType()));
            current.setTag(currentEvent);
            Float color = PersonData.getColors().get(currentEvent.getEventType());
            Log.d(TAG,"ID: " + color);
            current.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        }
        map.setOnMarkerClickListener(this);
        //map.animateCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        Event event = (Event) marker.getTag();
        Person name = PersonData.getPerson(event.getPersonID());
        String text = name.getFirstName() + " " + name.getLastName() + "\n" + event.getEventType() + ": "
                + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
        Log.d(TAG, "Setting up text");
        description.setText(text);

        return false;
    }
};
