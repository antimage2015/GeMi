package com.crazy.gemi.ui.favor;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.crazy.gemi.R;
import com.crazy.gemi.ui.adapter.FavorImgPagerAdapter;
import com.crazy.gemi.ui.favorjson.FavorInfo;
import com.crazy.gemi.ui.favorjson.FavorMainKey;
import com.crazy.gemi.ui.favorjson.FavorTotal;
import com.crazy.gemi.ui.utils.ImageFileCache;
import com.crazy.gemi.ui.utils.JsonCache;
import com.crazy.gemi.ui.utils.MyApplication;
import com.crazy.gemi.ui.utils.NetState;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class FavorFragment extends Fragment {

    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private ViewPager mViewPager;
    private ImageView imgLeft, imgRight;
    private List<FavorMainKey> mainKeys;

    private int[] images = {
            R.drawable.favor_page_0, R.drawable.favor_page_1, R.drawable.favor_page_2,
            R.drawable.favor_page_3, R.drawable.favor_page_4, R.drawable.favor_page_5,
            R.drawable.favor_page_6, R.drawable.favor_page_7, R.drawable.favor_page_8,
            R.drawable.favor_page_9,
    };

    private String favor_url = "http://www.warmtel.com:8080/maininit";

    private Context context = MyApplication.getInstance();

    private List<String> urlList;

    public static FavorFragment newInstance() {
        FavorFragment fragment = new FavorFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favor_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {

        mViewPager = (ViewPager)getView().findViewById(R.id.favor_view_pager);

        urlList = new ArrayList<>();

        imgLeft = (ImageView)getView().findViewById(R.id.favor_page_count_left);
        imgRight = (ImageView)getView().findViewById(R.id.favor_page_count_right);

        String strFavor = JsonCache.getInstance(context).getJsonCache("favor");
        if (NetState.getAPNType(getActivity()) == -1 ){
            forGsonFavor(strFavor);
            getForInfo();
        }

        getAsyncDataList(favor_url);

    }

    public void getAsyncDataList(String url) {
        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                JsonCache.getInstance(context).setJsonCache("favor", s);
                forGsonFavor(s);
            }
        });
    }

    private void forGsonFavor(String str) {
        Gson gson = new Gson();
        FavorTotal favorTotal = gson.fromJson(str, FavorTotal.class);
        FavorInfo favorInfo = favorTotal.getInfo();

        mainKeys = favorInfo.getMainKey();

        for (FavorMainKey favorKeys : mainKeys) {
            urlList.add(favorKeys.getBigpicUrl());
        }
        getForInfo();
    }


    private void getForInfo(){

        FavorImgPagerAdapter adapter = new FavorImgPagerAdapter(context, urlList);
        mViewPager.setAdapter(adapter);

        pagerChanged();
    }

    private void pagerChanged(){
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position < 9) {
                    imgLeft.setImageResource(images[0]);
                    imgRight.setImageResource(images[position + 1]);
                } else {
                    imgLeft.setImageResource(images[1]);
                    imgRight.setImageResource(images[position - 9]);
                }
            }
        });
    }
}
