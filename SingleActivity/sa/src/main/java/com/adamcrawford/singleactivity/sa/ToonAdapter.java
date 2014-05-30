package com.adamcrawford.singleactivity.sa;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adamcrawford.singleactivity.sa.datahandler.ImgSync;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Adam Crawford on 5/27/14.
 * Full Sail University Online MDVBS
 * Java 1 Week 4 - Single Activity
 */

public class ToonAdapter extends ArrayAdapter<ToonConstructor>
{
	private Context context;
	private ArrayList<ToonConstructor> objects;

	public ToonAdapter(Context context, int resource, ArrayList<ToonConstructor> objects)
	{
		super(context, resource, objects);
		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ToonConstructor toon = objects.get(position);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View listItemView = inflater.inflate(R.layout.item_toon, null);

		ImageView toonImgView = (ImageView) listItemView.findViewById(R.id.toonImg);
		try {
			Bitmap thumbnail = new ImgSync().execute(toon.toonIcon).get();
			toonImgView.setImageBitmap(thumbnail);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		TextView toonNameView = (TextView) listItemView.findViewById(R.id.toonName);
		toonNameView.setText(toon.toonName);

		TextView toonLevelView = (TextView) listItemView.findViewById(R.id.toonLevel);
		toonLevelView.setText(toon.toonLevel);

		TextView toonClassView = (TextView) listItemView.findViewById(R.id.toonClass);
		toonClassView.setText(toon.tnClass);

		return listItemView;
	}
}
