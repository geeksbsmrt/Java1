package datahandler;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Adam Crawford on May 19, 2014.
 * Full Sail University Online MDVBS
 * Java 1 Week 3 - Data Management
 */
public class NetSync extends AsyncTask<String, Void, JSONObject> {

    private static final String TAG = "NetSync";

    public NetSync () {
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url;
        StringBuffer contentBuffer = null;
        InputStream bin = null;

        try {
            //String loc = String.format("https://us.battle.net/api/wow/guild/%s/%s?fields=members", server, guild);
            String loc = "https://us.battle.net/api/wow/guild/winterhoof/veterans%20of%20the%20asylum?fields=members";
            url = new URL(loc);

            Log.d(TAG, "host = " + url.getHost());

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


        //System.out.println(contentBuffer);
        JSONObject myJson = null;

        try {
            myJson = new JSONObject(String.valueOf(contentBuffer));
        } catch (JSONException e) {
            Log.wtf(TAG, "JSON Error");
            e.printStackTrace();
        }

        return myJson;

    }

    @Override
    protected void onPostExecute(JSONObject thisJson){
        System.out.println(thisJson.toString());
    }

}
