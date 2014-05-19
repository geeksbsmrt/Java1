package com.adamcrawford.userinteraction.ui.data;

/**
 * Created by Adam Crawford
 * Full Sail Online MDVBS
 * Java 1 Week 2 - User Interaction
 */

import com.adamcrawford.userinteraction.ui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHandler {

    public JsonHandler() {
    }
	// get JSON data from chosen option
    public static JSONObject getJSON (String chosen) {
		// vars for manipulation
        String guild, name, faction, players;
	    JSONArray characters;

	    //build JSON data
	    JSONObject jObject = setJSON();

	    try {
		    //get info from JSON
		    name = jObject.getJSONObject("data").getJSONObject(chosen).getString("name");
		    faction = jObject.getJSONObject("data").getJSONObject(chosen).getString("faction");
		    players = jObject.getJSONObject("data").getJSONObject(chosen).getString("players");
		    characters = jObject.getJSONObject("data").getJSONObject(chosen).getJSONArray("characters");

		    System.out.println(characters.length());

		    for (int i=0,j=characters.length(); i<j; i++) {
			    System.out.println(characters.getString(i));
		    }

		    guild = String.format("%s %s \r\n %s %s \r\n %s %s \r\n %s", R.string.gText, name, R.string.faction, faction, R.string.numPlayers, players, characters.toString());
	    } catch (JSONException e) {
		    e.printStackTrace();
		    guild = String.format("%s", R.string.guildError);
	    }
		System.out.println(guild);
        return jObject;
    }

    public static JSONObject setJSON () {
	    System.out.println("setting JSON");

	    JSONObject root = new JSONObject();
	    try {
		    JSONObject data = new JSONObject();
			//build json from dataconst.
		    for (DataConstructor.Guilds guild : DataConstructor.Guilds.values()) {
			    JSONObject myGuild = new JSONObject();
			    myGuild.put("name", guild.getGuildName());
			    myGuild.put("faction", guild.getGuildFaction());
			    myGuild.put("players", guild.getPlayers());

			    myGuild.put("characters", guild.getCharacters());

			    data.put(guild.name(), myGuild);

		    }

		    root.put("data", data);
	    } catch (JSONException e) {
		    e.printStackTrace();
	    }

	    return root;
    }

}
