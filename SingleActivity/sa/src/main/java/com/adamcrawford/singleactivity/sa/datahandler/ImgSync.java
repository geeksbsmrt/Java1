package com.adamcrawford.singleactivity.sa.datahandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Adam Crawford on 5/29/14.
 * Full Sail University Online MDVBS
 * Java 1 Week 4 - Single Activity
 */
public class ImgSync extends AsyncTask<String, Void, Bitmap>
{

@Override
protected Bitmap doInBackground(String... params)
{
	Bitmap thumbnail = null;
	try {
		URL url = new URL("http://us.battle.net/static-render/us/" + params[0]);


		URLConnection conn = url.openConnection();
		conn.connect();
		InputStream is = conn.getInputStream();


		BufferedInputStream bis = new BufferedInputStream(is);

		thumbnail = BitmapFactory.decodeStream(bis);

		bis.close();
		is.close();
	} catch (IOException e) {
		e.printStackTrace();
	}

	return thumbnail;

}
}
