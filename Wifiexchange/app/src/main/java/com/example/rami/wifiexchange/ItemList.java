package com.example.rami.wifiexchange;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class ItemList extends ActionBarActivity implements OnItemClickListener
{
    /* Holds the currently held items */
    ArrayList <Item> m_list;
    ArrayAdapter<String> m_itemListAdapter;

    /* The list view of the currently held items */
    ListView m_itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        /* Get the item list view */
        m_itemListView = (ListView)findViewById(R.id.item_list);

        createItemList();
        buildInterface();
    }

    // Just add some items to array
    private void createItemList()
    {
        m_list = new ArrayList<>();

        Random rand = new Random();
        ArrayList<Integer> obtainedItems = new ArrayList<>();

        /* Add a set of randomized items */
        for(int i = 0; i < 5; i++)
        {
            int itemNum;
            boolean same;

            /* Get a random item until its unique compared to the others */
            do
            {
                same = false;

                /* Get a random item from the item composition */
                itemNum = rand.nextInt(ItemComposition.getSize());

                /* Check that the same item cannot be generated twice */
                for(int num : obtainedItems)
                    if(num == itemNum)
                        same = true;
            } while(same);

            obtainedItems.add(itemNum);
            Item item = new Item();
            item.setName(ItemComposition.getItem(itemNum));
            m_list.add(item);
        }
    }

    private void buildInterface()
    {
        ArrayList<String> itemList = new ArrayList<>();

        /* Retrieve item names */
        for(Item item : m_list)
            itemList.add(item.getName());

        /* Set the adapter */
        m_itemListAdapter = new ArrayAdapter<>(this, R.layout.item_list_row_item, itemList);

        /* Build the item list */
        m_itemListView.setAdapter(m_itemListAdapter);

        /* Set a listener */
        m_itemListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        /* Start trade activity */
        Intent intent = new Intent(this, TradeActivity.class);
        intent.putExtra("ITEM_TO_TRADE", m_list.get(position).getName());
        startActivity(intent);
    }
}
