package com.crazy.gemi.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crazy.gemi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavorImgPagerAdapter extends PagerAdapter {

    private Context context;
    private List<String> urlList = new ArrayList<>();

    public FavorImgPagerAdapter(Context context, List<String> urlList) {
        this.context = context;
        this.urlList = urlList;
    }

    @Override
    public int getCount() {
        return urlList.size();
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(context);

        Glide.with(context)
                .load(urlList.get(position))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
