package com.vino.phe.modernartui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PHE on 15/7/19.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<View> arrayList = null;

    public GridViewAdapter(Context c, int gridItemsSize) {
        mContext = c;
        initGridView(gridItemsSize);
    }

    // Total number of things contained within the adapter
    public int getCount() {
        return arrayList.size();
    }

    public View getItem(int position) {
        return arrayList.get(position);
    }

    public void setItem(int position, View v) {
        arrayList.set(position, v);
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    public long getItemId(int position) {
        return position;
    }

    public static int getColorFromPosition(int position, int offset) {
        int mod = (position + offset) % 8;
        switch (mod) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.rgb(33,22,22);
            case 2:
                return Color.rgb(30, 100, 100);
            case 3:
                return Color.rgb(0, 64, 0);
            case 4:
                return Color.rgb(30, 60, 96);
            case 5:
                return Color.rgb(128, 32, 62);
            case 6:
                return Color.rgb(64, 10, 64);
            case 7:
                return Color.rgb(128, 128, 128);
            default:
                return Color.WHITE;
        }
    }

    public void initGridView(int gridItems) {
        arrayList = new ArrayList<View>(256);
        for (int i = 0; i < gridItems; i++) {
            View gridItemView = null;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridItemView = inflater.inflate(R.layout.grid_item, null);
            TextView textView = (TextView) gridItemView.findViewById(R.id.grid_frame);

            textView.setBackgroundColor(GridViewAdapter.getColorFromPosition(i, 0));

            arrayList.add(i, gridItemView);
        }


    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return arrayList.get(position);
    }
}
