package com.zbar.lib;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.zbar.lib.Information.LocationFragmentTab;
import com.zbar.lib.NearbyPlaces.NearbyFragmentTab;


public class LocationActivity extends Activity {



    public static final String M_CURRENT_TAB = "M_CURRENT_TAB";


    Fragment routeFragmentTab = new RouteFragmentTab();
    Fragment locationFragmentTab = new LocationFragmentTab();
    Fragment nearbyFragmentTab = new NearbyFragmentTab();



    private TabHost mTabHost;
    private String mCurrentTab;
    public static final String routeTab = "routeTab";
    public static final String locationTab = "locationTab";
    public static final String nearbyTab = "nearbyTab";
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_location);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            location = extras.getString("location");
        }




        mTabHost = (TabHost) findViewById(android.R.id.tabhost);

        mTabHost.setup();

        if (savedInstanceState != null) {
            mCurrentTab = savedInstanceState.getString(M_CURRENT_TAB);
            initializeTabs();
            mTabHost.setCurrentTabByTag(mCurrentTab);
            /*
            when resume state it's important to set listener after initializeTabs
            */
            mTabHost.setOnTabChangedListener(listener);
        } else {
            mTabHost.setOnTabChangedListener(listener);
            initializeTabs();
        }
    }

    private View createTabView(final int id, final String text) {
        View tabview = LayoutInflater.from(LocationActivity.this).inflate(R.layout.tabs_text, null);
        if(id!=0){ImageView imageView = (ImageView) tabview.findViewById(R.id.tab_icon);
            imageView.setImageDrawable(getResources().getDrawable(id));}
        TextView textView = (TextView) tabview.findViewById(R.id.tab_text);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(R.color.textcolor));
        return tabview;
    }

    private void initializeTabs() {
        TabHost.TabSpec spec;

        spec = mTabHost.newTabSpec(locationTab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(0, "Location"));
        mTabHost.addTab(spec);

        spec = mTabHost.newTabSpec(nearbyTab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(0, "Nearby Places"));
        mTabHost.addTab(spec);


        spec = mTabHost.newTabSpec(routeTab);
        spec.setContent(new TabHost.TabContentFactory() {
            public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        spec.setIndicator(createTabView(0, "Route"));
        mTabHost.addTab(spec);

    }

    TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
        public void onTabChanged(String tabId) {

            mCurrentTab = tabId;



            if (tabId.equals(locationTab)) {

               pushFragments(locationFragmentTab,false,false,null);


            } else if (tabId.equals(nearbyTab)) {
                pushFragments(nearbyFragmentTab,false,false,null);

            } else if (tabId.equals(routeTab)) {
                pushFragments(routeFragmentTab,false,false,null);
            }


        }};

    public void pushFragments(Fragment fragment,
                              boolean shouldAnimate, boolean shouldAdd, String tag) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (shouldAnimate) {
            // ft.setCustomAnimations(R.animator.fragment_slide_left_enter,
            //         R.animator.fragment_slide_left_exit,
            //        R.animator.fragment_slide_right_enter,
            //       R.animator.fragment_slide_right_exit);
        }
        ft.replace(R.id.realtabcontent, fragment, tag);

        if (shouldAdd) {
            /*
            here you can create named backstack for realize another logic.
            ft.addToBackStack("name of your backstack");
             */
            ft.addToBackStack(null);
        } else {
            /*
            and remove named backstack:
            manager.popBackStack("name of your backstack", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            or remove whole:
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
             */
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
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
