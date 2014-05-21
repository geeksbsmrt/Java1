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
 */
public class GuildSync extends AsyncTask<String, Void, JSONObject>
{

	private static final String TAG = "GuildSync";

	public GuildSync() {
	}

	@Override
	protected JSONObject doInBackground(String... strings)
	{

		URL url;
		StringBuffer contentBuffer = null;
		InputStream bin = null;

		try {
			String loc = "https://us.battle.net/api/wow/realm/status";

			url = new URL(loc);

			//Log.d(TAG, "host = " + url.getHost());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			bin = new BufferedInputStream(conn.getInputStream());

			byte[] connBytes = new byte[1024];
			int byteRead;
			String content;
			contentBuffer = new StringBuffer();

			while ((byteRead = bin.read(connBytes)) != -1) {
				content = new String(connBytes, 0, byteRead);
				contentBuffer.append(content);
			}
		} catch (IOException e) {
			Log.wtf(TAG, "IOException");
			e.printStackTrace();
		} finally {
			if (bin != null) {
				try {
					bin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		JSONObject myJson = null;

		try {
			myJson = new JSONObject(String.valueOf(contentBuffer));
		} catch (JSONException e) {
			Log.wtf(TAG, "JSON Error");
			e.printStackTrace();
		}

		return myJson;

	}

}
