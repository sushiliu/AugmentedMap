package com.zbar.lib;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zbar.lib.NearbyPlaces.Place;
import com.zbar.lib.NearbyPlaces.PlacesService;

import java.util.ArrayList;


public class MylocActivity extends Activity {
    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    private Location loc;
    private String[] places;
    private LocationManager locationManager;
    private double stepx =(48.904400 - 48.838753)/18;
    private double stepy = (2.395279 - 2.284223)/20;
    int x;
    int y;
    int position = -1;
    MarkerOptions myl;

    ImageButton stationbutton;
    ImageButton museumbutton;
    ImageButton churchbutton;
    ImageButton foodbutton;
    ImageButton artbutton;
    ImageButton barbutton;


    //private Location loc;48.838753, 2.284223(left down)

            //48.904400, 2.395279


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window myWindow = this.getWindow();
        myWindow.setFlags(flag, flag);
        setContentView(R.layout.activity_myloc);
        places = getResources().getStringArray(R.array.places);
        initCompo();
        currentLocation();
        mMap.setOnMyLocationChangeListener(myLocationChangeListener);


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
            mMap.addMarker(myl);

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
        mMap.clear();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager
                .getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            locationManager.requestLocationUpdates(provider, 0, 0, listener);
        } else {
            LatLng locl = new LatLng(location.getLatitude(), location.getLongitude());
            myl = new MarkerOptions().position(locl).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.man));
            mMap.addMarker(myl);
            if(mMap != null){
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locl, 16.0f));
            }
            loc = location;
            new GetPlaces(getBaseContext(),
                    places[0].toLowerCase().replace(
                    "-", "_")).execute();
            Log.e(TAG, "location : " + location);
        }
        x=(int)((location.getLatitude()-48.838753)/stepx);
        y=(int)((location.getLongitude()-2.284223)/stepy);

        String[] xcor={"10","11","12","13","14","15","16","17","18","19",
                "20","21","22","23","24","25","26","27","28","29"};
        String[] ycor={"A","B","C","D","E","F","G","H","J","K","L","M","N","O","P","Q"};

        TextView textloc = (TextView)findViewById(R.id.text);
        //Button iam = (Button) findViewById(R.id.Iam);
        if(0<=x&&x<20&&0<=y&&y<18){
            textloc.setText("Map Grid\n "+xcor[x]+ycor[y]);}
        else {
            textloc.setText("Out of\n Map Boundry");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_myloc, menu);
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
