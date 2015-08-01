package com.vino.phe.dailyselfie;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PHE on 15/7/20.
 */
public class gridViewAdaper extends BaseAdapter {
    private Context mContext;
    private List<Integer> mThumbIds;
    private static final int PADDING = 8;
    private static final int WIDTH = 250;
    private static final int HEIGHT = 250;

    private ArrayList arrayList=new ArrayList();

    // Store the list of image IDs
    public gridViewAdaper(Context c,ArrayList arrayList) {
        mContext = c;
//        this.mThumbIds = ids;
        this.arrayList=arrayList;
    }


    // Return the number of items in the Adapter
    @Override
    public int getCount() {
        return arrayList.size();
    }

    // Return the data item at position
    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    // Will get called to provide the ID that
    // is passed to OnItemClickListener.onItemClick()
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView;


        // if convertView's not recycled, initialize some attributes
        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(WIDTH, HEIGHT));
            imageView.setPadding(PADDING, PADDING, PADDING, PADDING);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);



        }

//        imageView.setImageResource(arrayList.get(position));

//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

        imageArray image_array=(imageArray) arrayList.get(position);
        imageView.setImageBitmap(image_array.getImage());


        return imageView;

    }

}
