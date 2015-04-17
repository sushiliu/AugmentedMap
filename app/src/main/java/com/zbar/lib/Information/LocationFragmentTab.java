package com.zbar.lib.Information;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.zbar.lib.R;

import java.util.ArrayList;


/**
 * Created by sushi on 01/04/15.
 */
public class LocationFragmentTab extends Fragment {
    ArrayAdapter<String> mAdapter;
    private ListView listView;
    private View view;
    private String location;
    private ListView list_location;
    private ListAdapter listAdapter_location;
    private ArrayList<dumpclass> listdata_location;
    private int value;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {



            view = inflater.inflate(R.layout.location_layout, container, false);
    } catch (InflateException e) {
        /* map is already there, just return view as it is */
    }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        location=getActivity().getIntent().getExtras().getString("location");
        list_location = (ListView) view.findViewById(R.id.list_location);
        listdata_location = new ArrayList<dumpclass>();

        SlidingUpPanelLayout slidepanel = (SlidingUpPanelLayout)view.findViewById(R.id.sliding_layout);
        //slidepanel.setAnchorPoint(0.5f);
        //slidepanel.setMinimumHeight(600);
        slidepanel.setPanelHeight(800);

        InitializeValues();

        ImageView image = (ImageView)view.findViewById(R.id.imageview) ;
        image.setBackgroundResource(R.drawable.eiffel);

        final ListViewSwipeGesture touchListener = new ListViewSwipeGesture(list_location, swipeListener_Location, this.getActivity());
        touchListener.SwipeType = ListViewSwipeGesture.Dismiss;    //Set two options at background of list item
        list_location.setOnTouchListener(touchListener);

    }

        ListViewSwipeGesture.TouchCallbacks swipeListener_Location = new ListViewSwipeGesture.TouchCallbacks() {

            @Override
            public void FullSwipeListView(int position) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity().getApplicationContext(), "Action_2", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void HalfSwipeListView(int position) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity().getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void LoadDataForScroll(int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                // TODO Auto-generated method stub
                Toast.makeText(getActivity().getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
                for (int i : reverseSortedPositions) {
                    listdata_location.remove(i);
                    listAdapter_location.notifyDataSetChanged();
                }
            }

            @Override
            public void OnClickListView(int position) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    startActivity(new Intent(getActivity().getApplicationContext(), Eiffel_main.class));
                } else if (position == 1) {
                    startActivity(new Intent(getActivity().getApplicationContext(), Eiffel_main.class));
                } else if (position == 2) {
                    startActivity(new Intent(getActivity().getApplicationContext(), Eiffel_history.class));
                } else if (position == 3) {
                    startActivity(new Intent(getActivity().getApplicationContext(), Eiffel_art.class));
                }else if (position == 4) {
                    startActivity(new Intent(getActivity().getApplicationContext(), Eiffel_architecture.class));
                }

            }};







    private void InitializeValues() {
        // TODO Auto-generated method stub
        listdata_location.add(new dumpclass(" Opening hours: 9:30 a.m. to 11 p.m \n \n Phone: +33 01 25679823"));
        listdata_location.add(new dumpclass("The Eiffel Tower is an iron lattice tower located on the Champ de Mars in Paris, France but has become both a global cultural icon of France and one of the most recognizable structures in the world. The tower is the tallest structure in Paris..."));
        listdata_location.add(new dumpclass("The design of the Eiffel Tower was originated by Maurice Koechlin and Émile Nouguier, two senior engineers who worked for the Compagnie des Établissements Eiffel, after discussion about a suitable centrepiece for the proposed 1889 Exposition..."));
        listdata_location.add(new dumpclass("The puddled iron (wrought iron) structure of the Eiffel Tower weighs 7,300 tonnes, while the entire structure, including non-metal components, is approximately 10,000 tonnes. As a demonstration of the economy of design, if the 7,300 tonnes..."));
        listdata_location.add(new dumpclass("Work on the foundations started on 28 January 1887. Those for the east and south legs were straightforward, each leg resting on four 2 m (6.6 ft) concrete slabs, one for each of the principal girders of each leg but the other two, being..."));
        //Louvre
        /**
         * listdata_location.add(new dumpclass("The origin of the name Louvre is unclear. The French historian Henri Sauval, probably writing in the 1660s, stated..."));
         listdata_location.add(new dumpclass("The Louvre Palace is a former royal palace located on the Right Bank ..."));
         listdata_location.add(new dumpclass("he Louvre or the Louvre Museum is one of the world's largest museums and a historic monument..."));
         listdata_location.add(new dumpclass("The Louvre Pyramid (Pyramide du Louvre) is a large glass and metal pyramid, surrounded by three smaller pyramids..."));
         **/
        //Set adapter for the list
        listAdapter_location = new ListAdapter(this.getActivity(), listdata_location);
        list_location.setAdapter(listAdapter_location);
    }


}
