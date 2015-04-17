package com.zbar.lib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.zbar.lib.NearbyPlaces.Place;
import com.zbar.lib.NearbyPlaces.PlacesService;

import java.util.ArrayList;


public class NearbyMenu extends Activity {

    ImageButton stationbutton;
    ImageButton museumbutton;
    ImageButton churchbutton;
    ImageButton foodbutton;
    ImageButton artbutton;
    ImageButton barbutton;
    ImageButton tmp;
    int position = -1;

    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    private String[] places;
    private LocationManager locationManager;
    private Location loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_menu);
        //setRetainInstance(true);
        initCompo();
        places = getResources().getStringArray(R.array.places);
        currentLocation();
        loc.setLatitude(48.85837);
        loc.setLongitude(2.29448);

        stationbutton = (ImageButton)findViewById(R.id.button1);
        museumbutton = (ImageButton)findViewById(R.id.button2);
        churchbutton = (ImageButton)findViewById(R.id.button3);
        foodbutton = (ImageButton)findViewById(R.id.button4);
        artbutton = (ImageButton)findViewById(R.id.button5);
        barbutton = (ImageButton)findViewById(R.id.button6);



        stationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             position = 0;
                Log.e(TAG,
                        places[position].toLowerCase().replace("-",
                                "_"));
                if (loc != null) {
                    mMap.clear();
                    new GetPlaces(getBaseContext(),
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
                    new GetPlaces(getBaseContext(),
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
                    new GetPlaces(getBaseContext(),
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
                    new GetPlaces(getBaseContext(),
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
                    new GetPlaces(getBaseContext(),
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
                    new GetPlaces(getBaseContext(),
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

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager
                .getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            locationManager.requestLocationUpdates(provider, 0, 0, listener);
        } else {
            LatLng locl = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(locl).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin)));
            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locl, 16.0f));
            }
            loc = location;
            new GetPlaces(getBaseContext(), places[0].toLowerCase().replace(
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





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nearby_menu, menu);
        return true;
    }

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
