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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import datahandler.CharSync;
import datahandler.GuildSync;


public class MainActivity extends Activity {

private Spinner realmSpinner;
private ListView charList;

private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    realmSpinner = (Spinner) findViewById(R.id.realmSpinner);
	    charList = (ListView) findViewById(R.id.charList);

	    Boolean isConnected = getStatus(this);

	    if (isConnected) {

		    //Log.i(TAG, "You are connected");
		    try {
				JSONObject guilds = new GuildSync().execute().get();
				updateSpinner(guilds);
		    } catch (InterruptedException e) {
			    e.printStackTrace();
		    } catch (ExecutionException e) {
			    e.printStackTrace();
		    }

	    } else {
		    Log.i(TAG, "You are not connected");
	    }

	    final EditText guildName = (EditText) findViewById(R.id.guildText);

	    Button submitButton = (Button) findViewById(R.id.submitButton);
	    submitButton.setOnClickListener(new View.OnClickListener()
	    {
		    @Override
		    public void onClick(View v)
		    {
			    String realmName = realmSpinner.getSelectedItem().toString();
			    System.out.println(realmName);
			    String guild = guildName.getText().toString().replace(" ", "%20");
			    updateList(realmName, guild);
		    }
	    });

    }

	private Boolean getStatus(Context c){
        //Log.i(TAG, "In getStatus");
		ConnectivityManager conMan = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();

		if (netInfo != null) {
			return netInfo.isConnected();
		} else {
			return false;
		}
	}

	private void updateSpinner(JSONObject data){

		ArrayList<String> guildNames = new ArrayList<String>();

		try {
			//Log.i(TAG, "Printing Guilds");

			JSONArray dataArray = data.getJSONArray("realms");

			for (int i=0, j=dataArray.length(); i<j; i++) {
				JSONObject guild = (JSONObject) dataArray.get(i);
				guildNames.add(guild.getString("name"));
			}

			writeSpinner(guildNames);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void writeSpinner (ArrayList<String> guilds) {

		ArrayAdapter<String> realmAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, guilds);
		realmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		realmSpinner.setAdapter(realmAdapter);
	}

	private void updateList (String realm, String guild) {
		try {
			JSONObject toons = new CharSync().execute(realm, guild).get();
			if (toons != null) {
				writeList(toons);
			} else {
				charList.setVisibility(View.GONE);
				printToast(getString(R.string.notFound));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	private void writeList (JSONObject data) {

		System.out.println(data.toString());

		ArrayList<String> toonNames = new ArrayList<String>();

		try {

			JSONArray dataArray = data.getJSONArray("members");

			for (int i=0, j=dataArray.length(); i<j; i++) {
				JSONObject toon = (JSONObject) dataArray.get(i);
				toonNames.add(toon.getJSONObject("character").getString("name"));
			}

			ArrayAdapter<String> guildListAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, toonNames);
			//refresh the data
			guildListAdapter.notifyDataSetChanged();
			charList.setAdapter(guildListAdapter);
			charList.setVisibility(View.VISIBLE);
			System.out.println(toonNames);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void printToast(String message) {
		Context c = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast error = Toast.makeText(c, message, duration);
		error.show();
	}
}
