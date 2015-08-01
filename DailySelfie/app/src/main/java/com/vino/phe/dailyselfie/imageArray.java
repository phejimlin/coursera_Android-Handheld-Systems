package com.vino.phe.dailyselfie;

import android.graphics.Bitmap;

/**
 * Created by PHE on 15/7/31.
 */
public class imageArray  {
    private Bitmap image;
    private String filename;


    public imageArray(Bitmap image, String filename) {
        super();
        this.image = image;
        this.filename = filename;
    }

    public Bitmap getImage() {
        return image;
    }




}
