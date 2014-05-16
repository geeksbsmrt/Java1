package com.adamcrawford.userinteraction.ui;

/**
 * Crated by Adam Crawford
 * Full Sail Online MDVBS
 * Java 1 Week 2 - User Interaction
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.adamcrawford.userinteraction.ui.data.DataConstructor;
import com.adamcrawford.userinteraction.ui.data.JsonHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {

//define arraylists
private ArrayList<String> guilds = new ArrayList<String>();
private ArrayList<String> guildSelectors = new ArrayList<String>();
private ArrayAdapter<String> guildListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

	    //build ArrayLists
	    for (DataConstructor.Guilds guild : DataConstructor.Guilds.values()) {
		    System.out.println(guild.name());
		    guildSelectors.add(guild.name());
		    guilds.add(guild.getGuildName());
	    }

	    //setup spinner
	    Spinner mySpin = (Spinner) findViewById(R.id.guildSpinner);
	    ArrayAdapter<String> guildAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, guilds);
	    guildAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    mySpin.setAdapter(guildAdapter);

	    //setup listview
	    final ListView charList = (ListView) findViewById(R.id.charList);

	    //setup action for selection
	    mySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	    {
		    @Override
		    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		    {
			    //create internal vars
			    JSONArray charJSON;
			    ArrayList<String> charNames = new ArrayList<String>();
			    //get JSON Data
				JSONObject myJSON = JsonHandler.getJSON(guildSelectors.get(position));
			    try {
				    //pull data from JSON
					charJSON = myJSON.getJSONObject("data").getJSONObject(guildSelectors.get(position)).getJSONArray("characters");
					if (charJSON != null) {
						for (int i=0, j=charJSON.length();i<j;i++){
							//Set charnames in usable format
							charNames.add(charJSON.get(i).toString());
						}
						//setup list adapter
						guildListAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, charNames);
						//refresh the data
						guildListAdapter.notifyDataSetChanged();
						charList.setAdapter(guildListAdapter);
						charList.setVisibility(View.VISIBLE);
						System.out.println(String.format("CharNames: %s", charNames.toString()));
					}
			    } catch (JSONException e) {
				    e.printStackTrace();
			    }
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent)
		    {

		    }
	    });
    }

}
