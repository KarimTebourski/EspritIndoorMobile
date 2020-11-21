package com.example.espritindoor.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class ImageAdapter extends BaseAdapter {
    List<Integer> image ;
    Context context ;

    public ImageAdapter(List<Integer> image, Context context) {
        this.image = image;
        this.context = context;
    }

    @Override
    public int getCount() {
        return image.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return image.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = (ImageView) convertView ;

        if(imageView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(350,450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        imageView.setImageResource(image.get(position));
        return imageView;
    }
}
