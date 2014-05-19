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
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity {

private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    Log.wtf(TAG, "test msg");

	    Boolean isConnected = getStatus(this);

	    if (isConnected) {
		    Log.i(TAG, "You are connected");
	    } else {
		    Log.i(TAG, "You are not connected");
	    }

    }

	public Boolean getStatus(Context c)
	{

		ConnectivityManager conMan = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();

		if (netInfo != null) {
			return netInfo.isConnected();
		} else {
			return false;
		}
	}
}
