package com.adamcrawford.datamanagement.dm;

/**
 * Created by Adam Crawford on May 18, 2014.
 * Full Sail University Online MDVBS
 * Java 1 Week 3 - Data Management
 */


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import datahandler.NetSync;


public class MainActivity extends Activity {

private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    Boolean isConnected = getStatus(this);

	    if (isConnected) {

            //TODO DO NOT FORGET TO String Replace SPACES WITH %%20

		    Log.i(TAG, "You are connected");
            AsyncTask<String, Void, JSONObject> data = new NetSync().execute("winterhoof", "veterans%%20of%%20the%%20asylum");
            //Log.i(TAG, "check me");
	    } else {
		    Log.i(TAG, "You are not connected");
	    }

    }

	public Boolean getStatus(Context c)
	{
        Log.i(TAG, "In getStatus");
		ConnectivityManager conMan = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();

		if (netInfo != null) {
			return netInfo.isConnected();
		} else {
			return false;
		}
	}
}
