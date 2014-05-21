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
import android.view.inputmethod.InputMethodManager;
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
import datahandler.RealmSync;


public class MainActivity extends Activity {

//create class variables
private Spinner realmSpinner;
private ListView charList;
private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    //get dynamically updated items
	    realmSpinner = (Spinner) findViewById(R.id.realmSpinner);
	    charList = (ListView) findViewById(R.id.charList);

	    //check internet connectivity
	    Boolean isConnected = getStatus(this);

	    if (isConnected) {

		    //Log.i(TAG, "You are connected");
		    try {
			    //get list of Realms
				JSONObject realms = new RealmSync().execute().get();
			    //call method to populate spinner
				updateSpinner(realms);

			    //handle errors
		    } catch (InterruptedException e) {
			    e.printStackTrace();
		    } catch (ExecutionException e) {
			    e.printStackTrace();
		    }

	    } else {
		    //Throw not connected message
		    Log.i(TAG, "You are not connected");
		    printToast(getString(R.string.notConnected));
	    }

	    //get input field
	    final EditText guildName = (EditText) findViewById(R.id.guildText);

	    //get and create onclick for button
	    Button submitButton = (Button) findViewById(R.id.submitButton);
	    submitButton.setOnClickListener(new View.OnClickListener()
	    {
		    @Override
		    public void onClick(View v)
		    {
			    //dismiss keyboard
			    InputMethodManager manager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			    manager.hideSoftInputFromWindow(v.getWindowToken(),0);

			    //pull selected name out of spinner
				String realmName = realmSpinner.getSelectedItem().toString();
//			    System.out.println(realmName);

			    //pull entered user text
			    String guild = guildName.getText().toString().replace(" ", "%20");

			    //ensure entry in guildEdit
			    if (!guild.equals("")) {
				    //call method to populate members list
				    updateList(realmName, guild);
			    } else {
				    //warn if edit is blank
				    printToast(getString(R.string.noEntry));
			    }


		    }
	    });

    }

	//method to check for connectivity
	private Boolean getStatus(Context c){
        //Log.i(TAG, "In getStatus");
		//build connectivity manager and network info
		ConnectivityManager conMan = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = conMan.getActiveNetworkInfo();

		//true/false based on connectivity
		return netInfo != null && netInfo.isConnected();
	}

	//method to get information to put into spinner
	private void updateSpinner(JSONObject data){

		//build an array of strings
		ArrayList<String> guildNames = new ArrayList<String>();

		try {
			//Log.i(TAG, "Printing Guilds");

			//pull realms array out of incoming json
			JSONArray dataArray = data.getJSONArray("realms");

			//loop json array inputing values into string array
			for (int i=0, j=dataArray.length(); i<j; i++) {
				JSONObject guild = (JSONObject) dataArray.get(i);
				guildNames.add(guild.getString("name"));
			}

			//call method to output values to spinner
			writeSpinner(guildNames);

		//handle errors
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//method to write values to spinner
	private void writeSpinner (ArrayList<String> guilds) {

		//build adapter for spinner using parameters from method call
		ArrayAdapter<String> realmAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, guilds);
		realmAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		realmSpinner.setAdapter(realmAdapter);
	}

	//method to get information to put into listview
	private void updateList (String realm, String guild) {

		try {
			//call class to connect to network and pull info based on realm selection and input guild
			JSONObject toons = new CharSync().execute(realm, guild).get();

			//check for data inside JSON object
			if (toons != null) {
				//data exists - put it out to screen
				writeList(toons);
			} else {
				//data does not exist

				//clear screen if successful query previously run
				charList.setVisibility(View.GONE);
				//throw error to screen
				printToast(getString(R.string.notFound));
			}
		//handle processing errors
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	//method to output values to list
	private void writeList (JSONObject data) {

		//System.out.println(data.toString());

		//create string array
		ArrayList<String> toonNames = new ArrayList<String>();

		try {

			//get members array out of returned JSON object
			JSONArray dataArray = data.getJSONArray("members");

			//loop through array putting member names into string array
			for (int i=0, j=dataArray.length(); i<j; i++) {
				JSONObject toon = (JSONObject) dataArray.get(i);
				toonNames.add(toon.getJSONObject("character").getString("name"));
			}

			//build listAdapter
			ArrayAdapter<String> guildListAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, toonNames);
			//refresh the data
			guildListAdapter.notifyDataSetChanged();
			charList.setAdapter(guildListAdapter);
			//show view on screen
			charList.setVisibility(View.VISIBLE);
			//System.out.println(toonNames);

		//handle errors
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//method to display error to user
	private void printToast(String message) {
		//get active context
		Context c = getApplicationContext();
		//set lenght for message to be displayed
		int duration = Toast.LENGTH_LONG;
		//create message based on input parameter then display it
		Toast error = Toast.makeText(c, message, duration);
		error.show();
	}
}
