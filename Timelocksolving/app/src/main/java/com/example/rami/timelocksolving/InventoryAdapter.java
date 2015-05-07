package com.example.rami.timelocksolving;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Rami on 28.4.2015.
 */
public class InventoryAdapter extends android.widget.ArrayAdapter<String> {

    private Context mContext;
    private String[] items;

    /**
     * Constructor for adapter
     * @param context Context
     * @param values String array of items
     */
    public InventoryAdapter(Context context, String[] values) {
        super(context,R.layout.rowlayout , values);
        mContext = context;
        items = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.profileIcon);

        // Random images for the ImageView
        int random = mThumbIds[new Random().nextInt(mThumbIds.length)];
        imageView.setImageResource(random);

        textView.setText(items[position]);

        return rowView;
    }

    // Contains integer Ids for the random thumbnails
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}
