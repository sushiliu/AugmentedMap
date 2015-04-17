package com.zbar.lib.Information;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.zbar.lib.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

//import org.jsoup.select.Element;

/**
 * Created by fafa on 4/12/2015.
 */
public class Louvre_architecture extends Activity {
    private static final String URL = "http://en.wikipedia.org/wiki/Louvre_Pyramid";
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_layout);
        new FetchWebsiteData().execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    private class FetchWebsiteData extends AsyncTask<Void, Void, Void> {
        String test1, test2, test3;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Louvre_architecture.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                // Connect to website
                Document document = Jsoup.connect(URL).get();
                // Information about the architecture of Eiffel tower
                Elements desc_architecture = document.select("h2, p");
                test1 = desc_architecture.get(13).text();
                test2 = desc_architecture.get(14).text();
                test3 = desc_architecture.get(15).text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView textElement_architecture = (TextView) findViewById(R.id.textView2);
            textElement_architecture.setText(test1 + '\n' + '\n' + test2 + '\n' + '\n' + test3);
            mProgressDialog.dismiss();
        }
    }
}
