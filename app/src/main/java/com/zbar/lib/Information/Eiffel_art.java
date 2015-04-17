package com.zbar.lib.Information;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import com.zbar.lib.R;

public class Eiffel_art extends Activity {

    private static final String URL = "http://en.wikipedia.org/wiki/Eiffel_Tower";
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
       // getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    private class FetchWebsiteData extends AsyncTask<Void, Void, Void> {
        String web_desc_art;
        String test1, test2, test3;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Eiffel_art.this);
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
                //History header
                test1 = desc_architecture.get(34).text();
                //History paragraph
                test2 = desc_architecture.get(35).text();
                test3 = desc_architecture.get(36).text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set information to architecture category
            TextView textElement_art = (TextView) findViewById(R.id.textView2);
            textElement_art.setText('\n' + '\n' + test2 + '\n' + '\n' + test3);
            mProgressDialog.dismiss();
        }
    }

}
