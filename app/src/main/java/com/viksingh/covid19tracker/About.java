package com.viksingh.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class About extends AppCompatActivity {

    private SimpleArcDialog mDialog;
    private ArcConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = new SimpleArcDialog(this);
        new AboutUS().execute("1","2","0");

    }
    class AboutUS extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
           System.out.println(strings[0]+strings[1]+strings[2]);
           return strings[0]+"."+strings[1];
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            Element versionElement = new Element();
            versionElement.setTitle("Version "+string);
            View aboutPage = new AboutPage(About.this)
                    .isRTL(false)
                    .setDescription(getString(R.string.about_page_description))
                    .setImage(R.drawable.akshardham)
                    .addItem(versionElement)
                    .addGroup("Connect with us")
                    .addEmail("vs98990@gmail.com")
                    .addInstagram("im_singhji")
                    .addGitHub("vikas981")
                    .create();

            setContentView(aboutPage);
            mDialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.setConfiguration(new ArcConfiguration(About.this));
            mDialog.show();

        }
    }
}