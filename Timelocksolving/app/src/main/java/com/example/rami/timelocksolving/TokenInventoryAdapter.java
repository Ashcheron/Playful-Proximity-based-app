package com.example.rami.timelocksolving;

import android.app.Activity;
import android.content.Context;
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
public class TokenInventoryAdapter extends android.widget.ArrayAdapter {

    private Context mContext;
    private int mCategory;


    /**
     * Constructor for adapter
     * @param context Context
     * @param values String array of tokens
     */
    public TokenInventoryAdapter(Context context, ArrayList<Token> values, int category) {
        super(context, 0, values);
        mContext = context;
        mCategory = category;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Token token = (Token) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rowlayout, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.itemLabel);
        ImageView icon = (ImageView) convertView.findViewById(R.id.itemIcon);
        TextView desc = (TextView) convertView.findViewById(R.id.itemDescription);


        // Random images for the ImageView
        int random = mThumbIds[new Random().nextInt(mThumbIds.length)];
        icon.setImageResource(random);

        name.setText(token.getName());
        desc.setText(token.getTokenTypeString());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, ItemActivity.class);
                intent.putExtra("TITLE", token.getName());
                intent.putExtra(mContext.getResources().getString(R.string.setting_keypair_tokenId), token.getID());
                ((Activity)mContext).setResult(1,intent);
                ((Activity)mContext).finish();
            }
        });

        return convertView;
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
