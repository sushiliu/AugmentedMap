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

public class Louvre_history extends Activity {

    private static final String URL = "http://en.wikipedia.org/wiki/Louvre_Palace";
    ProgressDialog mProgressDialog;
    TextView textElement;

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
        String web_desc_history;
        String test1, test2, test3;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(Louvre_history.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Connect to website
                Document document = Jsoup.connect(URL).get();

                Elements desc_architecture = document.select("h2, p");
                //History header
                test1 = desc_architecture.get(4).text();
                //History paragraph
                test2 = desc_architecture.get(5).text();
                //History paragraph
                test3 = desc_architecture.get(6).text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set information to architecture category
            TextView textElement_history = (TextView) findViewById(R.id.textView2);
            textElement_history.setText('\n' + '\n' + test2 + '\n' + '\n' + test3);
            mProgressDialog.dismiss();
        }
    }

}
