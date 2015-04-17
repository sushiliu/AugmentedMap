package com.zbar.lib.NearbyPlaces;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import com.zbar.lib.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * Created by sushi on 01/04/15.
 */
public class NearbyFragmentTab extends Fragment {



    public static final String M_CURRENT_TAB = "M_CURRENT_TAB";

    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    private String[] places;
    private Location loc;
    private LocationManager locationManager;

    private TabHost pTabHost;
    private String mCurrentTab;
    private String location;

    private View view;
    private SlidingUpPanelLayout slidepanel;

    public static final String station = "subway_station";
    public static final String museum = "museum";
    public static final String church = "church";
    public static final String food = "food";
    public static final String art = "art_gallery";
    public static final String bar = "bar";

    ImageButton stationbutton;
    ImageButton museumbutton;
    ImageButton churchbutton;
    ImageButton foodbutton;
    ImageButton artbutton;
    ImageButton barbutton;

    int position = -1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {

        view = inflater.inflate(R.layout.nearby_layout, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
        initCompo();
        places = getResources().getStringArray(R.array.places);
        currentLocation();
        ImageView image = (ImageView)view.findViewById(R.id.imageview) ;
        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        location=getActivity().getIntent().getExtras().getString("location");
        if(location.equals("louvre")){
            loc.setLatitude(48.86061);
            loc.setLongitude(2.33764);
            image.setBackgroundResource(R.drawable.louvre);
        }else {

            loc.setLatitude(48.85837);
            loc.setLongitude(2.29448);
            image.setBackgroundResource(R.drawable.eiffel);

        }


        slidepanel = (SlidingUpPanelLayout)view.findViewById(R.id.sliding_layout);
        //slidepanel.setAnchorPoint(0.5f);
        //slidepanel.setMinimumHeight(600);
        slidepanel.setPanelHeight(800);


        mMap.setOnMyLocationChangeListener(myLocationChangeListener);


        stationbutton = (ImageButton)view.findViewById(R.id.button1);
        museumbutton = (ImageButton)view.findViewById(R.id.button2);
        churchbutton = (ImageButton)view.findViewById(R.id.button3);
        foodbutton = (ImageButton)view.findViewById(R.id.button4);
        artbutton = (ImageButton)view.findViewById(R.id.button5);
        barbutton = (ImageButton)view.findViewById(R.id.button6);


        stationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 0;
                Log.e(TAG,
                        places[position].toLowerCase().replace("-",
                                "_"));
                if (loc != null) {
                    mMap.clear();
                    new GetPlaces(getActivity().getBaseContext(),
                            places[position].toLowerCase().replace(
                                    "-", "_").replace(" ", "_")).execute();

                }

            }
        });

        museumbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 1;
                Log.e(TAG,
                        places[position].toLowerCase().replace("-",
                                "_"));
                if (loc != null) {
                    mMap.clear();
                    new GetPlaces(getActivity().getBaseContext(),
                            places[position].toLowerCase().replace(
                                    "-", "_").replace(" ", "_")).execute();

                }


            }
        });

        churchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 2;
                Log.e(TAG,
                        places[position].toLowerCase().replace("-",
                                "_"));
                if (loc != null) {
                    mMap.clear();
                    new GetPlaces(getActivity().getBaseContext(),
                            places[position].toLowerCase().replace(
                                    "-", "_").replace(" ", "_")).execute();

                }

            }

        });

        foodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 3;
                Log.e(TAG,
                        places[position].toLowerCase().replace("-",
                                "_"));
                if (loc != null) {
                    mMap.clear();
                    new GetPlaces(getActivity().getBaseContext(),
                            places[position].toLowerCase().replace(
                                    "-", "_").replace(" ", "_")).execute();

                }
            }
        });

        artbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 4;
                Log.e(TAG,
                        places[position].toLowerCase().replace("-",
                                "_"));
                if (loc != null) {
                    mMap.clear();
                    new GetPlaces(getActivity().getBaseContext(),
                            places[position].toLowerCase().replace(
                                    "-", "_").replace(" ", "_")).execute();

                }
            }
        });

        barbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = 5;
                Log.e(TAG,
                        places[position].toLowerCase().replace("-",
                                "_"));
                if (loc != null) {
                    mMap.clear();
                    new GetPlaces(getActivity().getBaseContext(),
                            places[position].toLowerCase().replace(
                                    "-", "_").replace(" ", "_")).execute();

                }
            }
        });



    }

    private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

        private ProgressDialog dialog;
        private Context context;
        private String places;

        public GetPlaces(Context context, String places) {
            this.context = context;
            this.places = places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> result) {
            super.onPostExecute(result);

            for (int i = 0; i < result.size(); i++) {
                mMap.addMarker(new MarkerOptions()
                        .title(result.get(i).getName())
                        .position(
                                new LatLng(result.get(i).getLatitude(), result
                                        .get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.pin))
                        .snippet(result.get(i).getVicinity()));
            }


            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(loc.getLatitude(), loc.getLongitude())) // Sets the center of the map to
                            // Mountain View
                    .zoom(14) // Sets the zoom
                    .tilt(30) // Sets the tilt of the camera to 30 degrees
                    .build(); // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));
        }


        @Override
        protected ArrayList<Place> doInBackground(Void... arg0) {
            PlacesService service = new PlacesService(
                    "AIzaSyBuI6tyNRBAMD2EEUnyrJTbcvMQnKQgeYg");
            ArrayList<Place> findPlaces = service.findPlaces(loc.getLatitude(), // 28.632808 loc.getLatitude()
                    loc.getLongitude(), places); // 77.218276 loc.getLongitude()

            for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                Log.e(TAG, "places : " + placeDetail.getName());
            }
            return findPlaces;
        }

    }




    private void initCompo() {
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
    }

    private void currentLocation() {

        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager
                .getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            locationManager.requestLocationUpdates(provider, 0, 0, listener);
        } else {
            loc = location;
            new GetPlaces(view.getContext(), places[0].toLowerCase().replace(
                    "-", "_")).execute();
            Log.e(TAG, "location : " + location);
        }

    }


    private LocationListener listener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "location update : " + location);
            loc = location;

            locationManager.removeUpdates(listener);
        }
    };


    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

            mMap.addMarker(new MarkerOptions().position(loc).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


