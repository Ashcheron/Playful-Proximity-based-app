package com.example.rami.timelocksolving;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rami on 28.4.2015.
 */
public class InventoryAdapter extends android.widget.ArrayAdapter {

    private Context mContext;
    private ArrayList<Clue> clueItems;
    private ArrayList<Token> tokenItems;
    private int mCategory;


    /**
     * Constructor for adapter
     * @param context Context
     * @param values String array of clueItems
     */
    public InventoryAdapter(Context context, ArrayList<Clue> values) {
        super(context, R.layout.rowlayout, values);
        mContext = context;
        clueItems = values;
        mCategory = -1;
    }

    public InventoryAdapter(Context context, ArrayList<Token> values, int category) {
        super(context, R.layout.rowlayout, values);
        mContext = context;
        tokenItems = values;
        mCategory = category;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView name = (TextView) rowView.findViewById(R.id.itemLabel);
        ImageView icon = (ImageView) rowView.findViewById(R.id.itemIcon);
        TextView desc = (TextView) rowView.findViewById(R.id.itemDescription);

        // Random images for the ImageView
        int random = mThumbIds[new Random().nextInt(mThumbIds.length)];
        icon.setImageResource(random);
        if (mCategory < 0) {
            name.setText(clueItems.get(position).getClueTitle());
            desc.setText(clueItems.get(position).getGetClueDescriptionShort());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(mContext, ItemActivity.class);
                    String clue = clueItems.get(position).getClueId();

                    intent.putExtra("CLUEID", clue);

                    mContext.startActivity(intent);

                }
            });

        }
        else {
            name.setText(tokenItems.get(position).getName());
            desc.setText(tokenItems.get(position).getTokenTypeString());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(mContext, ItemActivity.class);
                    intent.putExtra("TITLE",tokenItems.get(position).getName());
                    intent.putExtra("TOKENID", tokenItems.get(position).getID());
                    ((Activity)mContext).setResult(1,intent);
                    ((Activity)mContext).finish();

                }
            });

        }

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
