package com.zbar.lib;

import httptools.HttpUtils;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.zbar.lib.R;
/**
 * Created by sushi on 01/04/15.
 */
public class RouteFragmentTab extends Fragment {

    private String TAG = "gps";
    private EditText editLocation;
    private String destination = "Eiffel%20Tower";
    private double lati, loti;
    private String baseURL = "http://maps.googleapis.com/maps/api/directions/json?origin=";
    private TextView min;
    private LinearLayout linear;
    private Context context;
    private boolean flag = true;
    private String jsonString = "";
    private String total_length;
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {


            view = inflater.inflate(R.layout.route_layout, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();

        min = (TextView) this.getActivity().findViewById(R.id.start);

        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        ImageView image = (ImageView)view.findViewById(R.id.imageview) ;
        String pos=getActivity().getIntent().getExtras().getString("location");
        if(pos.equals("louvre")){

            image.setBackgroundResource(R.drawable.louvre);
        }else {

            image.setBackgroundResource(R.drawable.eiffel);

        }


        SlidingUpPanelLayout slidepanel = (SlidingUpPanelLayout)view.findViewById(R.id.sliding_layout);
        //slidepanel.setAnchorPoint(0.5f);
        //slidepanel.setMinimumHeight(600);
        slidepanel.setPanelHeight(800);

        lati = location.getLatitude();
        loti = location.getLongitude();
        baseURL += "Louvre%20Museum&destination=Eiffel%20Tower&sensor=false&mode=";

        // baseURL += lati + "," + loti
        // + "&destination=Eiffel%20Tower&sensor=false&mode=";

        new LongOperationTransit().execute("");

        CountDownTimer timer;

        (new Thread(new Runnable() {

            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        Thread.sleep(1000);
                        if(getActivity() == null)
                            return;
                        getActivity().runOnUiThread(new Runnable() // start actions in UI
                                // thread
                        {

                            @Override
                            public void run() {
                                if (flag && !jsonString.equals("")) {
                                    min.setText(total_length);
                                    flag = false;
                                    linear = (LinearLayout) getActivity().findViewById(R.id.linear);

                                    JSONObject jObject;
                                    try {
                                        jObject = new JSONObject(jsonString);

                                        JSONArray jArray = jObject
                                                .getJSONArray("routes");
                                        JSONObject route = jArray
                                                .getJSONObject(0);
                                        JSONArray legs = route
                                                .getJSONArray("legs");
                                        JSONObject leg = legs.getJSONObject(0);
                                        JSONArray steps = leg
                                                .getJSONArray("steps");

                                        int length = steps.length();
                                        JSONObject jsonO, jsonDuration, jsonMode;
                                        for (int i = 0; i < length; i++) {
                                            jsonO = steps.getJSONObject(i);
                                            jsonDuration = jsonO
                                                    .getJSONObject("duration");

                                            Log.i("gps", jsonO
                                                    .getString("travel_mode"));

                                            if (jsonO.getString("travel_mode")
                                                    .equals("TRANSIT")) {

                                                final LinearLayout linear_new = new LinearLayout(
                                                        context);

                                                linear_new
                                                        .setOrientation(LinearLayout.VERTICAL);
                                                linear_new
                                                        .setGravity(Gravity.CENTER);
                                                LayoutParams llp = new LayoutParams(
                                                        LayoutParams.FILL_PARENT,
                                                        LayoutParams.WRAP_CONTENT,
                                                        1f);
                                                llp.setMargins(0, 20, 0, 0);
                                                linear_new.setLayoutParams(llp);
                                                final ImageView img = new ImageView(
                                                        context);
                                                img.setImageDrawable(getResources()
                                                        .getDrawable(
                                                                R.drawable.bus));
                                                // linear.addView(img);
                                                final TextView text = new TextView(
                                                        context);
                                                text.setText(jsonDuration
                                                        .getString("text"));
                                                text.setGravity(Gravity.CENTER);
                                                linear_new.addView(img);
                                                text.setTextColor(Color
                                                        .parseColor("#676768"));
                                                text.setTypeface(null,
                                                        Typeface.BOLD);
                                                linear_new.addView(text);

                                                final TextView text2 = new TextView(
                                                        context);
                                                text2.setText("subway "
                                                        + jsonO.getJSONObject(
                                                        "transit_details")
                                                        .getJSONObject(
                                                                "line")
                                                        .getString(
                                                                "short_name"));
                                                text.setTextColor(Color
                                                        .parseColor("#3b3499"));
                                                text2.setGravity(Gravity.CENTER);
                                                linear_new.addView(text2);

                                                final TextView text1 = new TextView(
                                                        context);
                                                text1.setGravity(Gravity.CENTER);
                                                text1.setText(jsonO
                                                        .getJSONObject(
                                                                "transit_details")
                                                        .getJSONObject(
                                                                "departure_stop")
                                                        .getString("name")
                                                        + "  -> "
                                                        + jsonO.getJSONObject(
                                                        "transit_details")
                                                        .getJSONObject(
                                                                "arrival_stop")
                                                        .getString(
                                                                "name"));
                                                linear_new.addView(text1);

                                                if (i != (length - 1)) {

                                                    final ImageView img1 = new ImageView(
                                                            context);

                                                    img1.setImageDrawable(getResources()
                                                            .getDrawable(
                                                                    R.drawable.line));

                                                    LayoutParams layoutParams = new LayoutParams(
                                                            LayoutParams.WRAP_CONTENT,
                                                            LayoutParams.WRAP_CONTENT);
                                                    layoutParams.setMargins(0,
                                                            20, 0, 0);
                                                    layoutParams.gravity = Gravity.CENTER;
                                                    img1.setLayoutParams(layoutParams);
                                                    linear_new.addView(img1);
                                                }

                                                linear.addView(linear_new);
                                            } else if (jsonO.getString(
                                                    "travel_mode").equals(
                                                    "WALKING")) {
                                                final LinearLayout linear_new = new LinearLayout(
                                                        context);
                                                linear_new
                                                        .setOrientation(LinearLayout.VERTICAL);
                                                linear_new
                                                        .setGravity(Gravity.CENTER);
                                                LayoutParams llp = new LayoutParams(
                                                        LayoutParams.FILL_PARENT,
                                                        LayoutParams.WRAP_CONTENT,
                                                        1f);
                                                llp.setMargins(0, 20, 0, 0);
                                                linear_new.setLayoutParams(llp);
                                                final ImageView img = new ImageView(
                                                        context);
                                                img.setImageDrawable(getResources()
                                                        .getDrawable(
                                                                R.drawable.walk));
                                                final TextView text = new TextView(
                                                        context);
                                                text.setText(jsonDuration
                                                        .getString("text"));
                                                text.setGravity(Gravity.CENTER);
                                                text.setTextColor(Color
                                                        .parseColor("#3b3499"));
                                                text.setTypeface(null,
                                                        Typeface.BOLD);
                                                linear_new.addView(img);
                                                linear_new.addView(text);
                                                final TextView text1 = new TextView(
                                                        context);
                                                text1.setGravity(Gravity.CENTER);
                                                text1.setText(jsonO
                                                        .getString("html_instructions"));
                                                linear_new.addView(text1);
                                                if (i != (length - 1)) {

                                                    final ImageView img1 = new ImageView(
                                                            context);

                                                    img1.setImageDrawable(getResources()
                                                            .getDrawable(
                                                                    R.drawable.line));
                                                    LayoutParams layoutParams = new LayoutParams(
                                                            LayoutParams.WRAP_CONTENT,
                                                            LayoutParams.WRAP_CONTENT);
                                                    layoutParams.setMargins(0,
                                                            20, 0, 0);
                                                    layoutParams.gravity = Gravity.CENTER;
                                                    img1.setLayoutParams(layoutParams);
                                                    linear_new.addView(img1);

                                                }
                                                linear.addView(linear_new);
                                            } else if (jsonO.getString(
                                                    "travel_mode").equals(
                                                    "DRIVING")) {

                                                final LinearLayout linear_new = new LinearLayout(
                                                        context);

                                                linear_new
                                                        .setOrientation(LinearLayout.VERTICAL);
                                                linear_new
                                                        .setGravity(Gravity.CENTER);
                                                LayoutParams llp = new LayoutParams(
                                                        LayoutParams.FILL_PARENT,
                                                        LayoutParams.WRAP_CONTENT,
                                                        1f);
                                                llp.setMargins(0, 20, 0, 0);
                                                linear_new.setLayoutParams(llp);
                                                final ImageView img = new ImageView(
                                                        context);
                                                img.setImageDrawable(getResources()
                                                        .getDrawable(
                                                                R.drawable.car));
                                                final TextView text = new TextView(
                                                        context);
                                                text.setText(jsonDuration
                                                        .getString("text"));
                                                text.setGravity(Gravity.CENTER);
                                                text.setTextColor(Color
                                                        .parseColor("#3b3499"));
                                                text.setTypeface(null,
                                                        Typeface.BOLD);
                                                linear_new.addView(img);
                                                linear_new.addView(text);
                                                if (i != (length - 1)) {

                                                    final ImageView img1 = new ImageView(
                                                            context);

                                                    img1.setImageDrawable(getResources()
                                                            .getDrawable(
                                                                    R.drawable.line));
                                                    LayoutParams layoutParams = new LayoutParams(
                                                            LayoutParams.WRAP_CONTENT,
                                                            LayoutParams.WRAP_CONTENT);
                                                    layoutParams.setMargins(0,
                                                            20, 0, 0);
                                                    layoutParams.gravity = Gravity.CENTER;
                                                    img1.setLayoutParams(layoutParams);
                                                    linear_new.addView(img1);
                                                }
                                                linear.addView(linear_new);
                                            }

                                        }
                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        // ooops
                    }
            }
        })).start(); // the while thread will start in BG thread
    }

    private class LongOperationTransit extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                baseURL += "transit";
                URL url = new URL(baseURL);
                int code = 0;
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                code = connection.getResponseCode();
                if (code == 200) {
                    jsonString = HttpUtils.changeInputStream(connection
                            .getInputStream());
                    total_length = HttpUtils.getTransitTime(jsonString);
                    if (total_length.equals("no")) {
                        new LongOperationDrive().execute("");
                    }

                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

    private class LongOperationDrive extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            try {
                baseURL += "drive";
                URL url = new URL(baseURL);
                int code = 0;
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                code = connection.getResponseCode();
                if (code == 200) {
                    jsonString = HttpUtils.changeInputStream(connection
                            .getInputStream());
                    total_length = HttpUtils.getTransitTime(jsonString);
                    Log.i("gps1", total_length);

                    min.setText(total_length);
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
