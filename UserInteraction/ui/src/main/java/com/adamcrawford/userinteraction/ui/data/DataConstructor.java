package com.adamcrawford.userinteraction.ui.data;

import org.json.JSONArray;

/**
 * Created by Adam Crawford on 5/14/14.
 * Full Sail University Online MDVBS
 * Java 1 Week 2 User Interaction
 */

public class DataConstructor
{

	// Create JSON Array of character names per guild
	private static JSONArray remChars;

	static {
		remChars = new JSONArray();
		remChars.put("Erovaan");
		remChars.put("Errorloading");
		remChars.put("Arthen");
	}

	private static JSONArray vetChars;

	static {
		vetChars = new JSONArray();
		vetChars.put("Whoofian");
		vetChars.put("Divinis");
		vetChars.put("Zabrahexx");
	}

	//Create enum for 2 guilds
	public enum Guilds {

		REMNANTS("Remnants of Sanity", "Alliance", "40", remChars),
		VETERANS("Veterans of the Asylum", "Horde", "20", vetChars);

		// Set vars for enum
		private final String guildName;
		private final String guildFaction;
		private final String players;
		private final JSONArray characters;

		// build the guild
		private Guilds(String gName, String gFaction, String playerCount, JSONArray chars){
			this.guildName = gName;
			this.guildFaction = gFaction;
			this.players = playerCount;
			this.characters = chars;
		}

		// getters for guild properties
		public String getGuildName()
		{
			return guildName;
		}

		public String getGuildFaction()
		{
			return guildFaction;
		}

		public String getPlayers()
		{
			return players;
		}

		public JSONArray getCharacters()
		{
			return characters;
		}

	}

	public DataConstructor() {
	}

}
