package datahandler;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Adam Crawford on 5/20/14.
 * Full Sail University Online MDVBS
 * Java 1 Week 3 - Data Management
 *
 * Class to pull realm info from network
 */
public class RealmSync extends AsyncTask<String, Void, JSONObject>
{
	//create tag var
	private static final String TAG = "RealmSync";

	//empty default
	public RealmSync() {
	}

	//asyncTask creation
	@Override
	protected JSONObject doInBackground(String... strings)
	{
		//local vars declaration
		URL url;
		StringBuffer contentBuffer = null;
		InputStream bin = null;

		try {
			//create URL
			String loc = "https://us.battle.net/api/wow/realm/status";
			url = new URL(loc);

			//Create HTTPConnection & buffer the incoming information
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			bin = new BufferedInputStream(conn.getInputStream());
			//create new byte = 1mb that will be used to buffer the input stream
			byte[] connBytes = new byte[1024];
			//create var to hold the number of bytes read
			int byteRead;
			//create content string variable
			String content;
			//build stringbuffer
			contentBuffer = new StringBuffer();

			//while data is incoming from the HTTPRequest append data to the string buffer
			//BIS returns -1 when end of content reached
			while ((byteRead = bin.read(connBytes)) != -1) {
				content = new String(connBytes, 0, byteRead);
				contentBuffer.append(content);
			}
		} catch (IOException e) {
			Log.e(TAG, "IOException");
			e.printStackTrace();
		} finally {
			//close the input stream
			if (bin != null) {
				try {
					bin.close();
				} catch (IOException e) {
					Log.e(TAG, "Bin close error");
					e.printStackTrace();
				}
			}
		}
		//create JSON Object to hold data
		JSONObject realmJSON = null;

		try {
			//create JSON from StringBuffer
			realmJSON = new JSONObject(String.valueOf(contentBuffer));
		} catch (JSONException e) {
			Log.e(TAG, "JSON Error");
			e.printStackTrace();
		}
		//Return JSON data
		return realmJSON;

	}

}
