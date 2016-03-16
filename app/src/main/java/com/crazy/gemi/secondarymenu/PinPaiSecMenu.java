package com.crazy.gemi.secondarymenu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crazy.gemi.R;
import com.crazy.gemi.pinpaijson.PinPaiBrandList;
import com.crazy.gemi.pinpaijson.PinPaiInfo;
import com.crazy.gemi.pinpaijson.PinPaiTotal;
import com.crazy.gemi.ui.utils.JsonCache;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PinPaiSecMenu extends Activity{

    private GridView gridView;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pinpai_sec_menu_layout);

        init();
    }

    private void init() {
        gridView = (GridView)findViewById(R.id.pinpai_sec_menu_grid);

        // 读取缓存 GridView
        String pinpaiStr = JsonCache.getInstance(PinPaiSecMenu.this).getJsonCache("pinpai");
        if (pinpaiStr != null){
            forGsonCheaperPinPai(pinpaiStr);
        }

        Intent intent = getIntent();
        String url = intent.getStringExtra("PINPAI_URL");
        getAsyncDataList(url);
    }

    public void getAsyncDataList(String url) {
        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(PinPaiSecMenu.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                forGsonCheaperPinPai(s);
                JsonCache.getInstance(PinPaiSecMenu.this).setJsonCache("pinpai", s);
            }
        });
    }

    private void forGsonCheaperPinPai(String str) {
        Gson gson = new Gson();
        PinPaiTotal pinPaiTotal = gson.fromJson(str, PinPaiTotal.class);
        PinPaiInfo pinPaiInfo = pinPaiTotal.getInfo();

        List<PinPaiBrandList> paiBrandLists = pinPaiInfo.getBrandList();

        PinPaiMenuAdapter adapter = new PinPaiMenuAdapter(this, paiBrandLists);
        gridView.setAdapter(adapter);
    }

    private class PinPaiMenuAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
        private List<PinPaiBrandList> paiBrandLists = new ArrayList<>();

        public PinPaiMenuAdapter(Context context, List<PinPaiBrandList> paiBrandLists) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.paiBrandLists = paiBrandLists;
        }

        @Override
        public int getCount() {
            return paiBrandLists.size();
        }

        @Override
        public Object getItem(int position) {
            return paiBrandLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.pinpai_sec_menu_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView)convertView.findViewById(R.id.pinpai_sec_menu_item_img);
                viewHolder.txt = (TextView)convertView.findViewById(R.id.pinpai_sec_menu_item_txt);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            PinPaiBrandList infos = (PinPaiBrandList)getItem(position);
            Glide.with(context).load(infos.getIconUrl()).into(viewHolder.img);
            viewHolder.txt.setText(infos.getBrand());

            return convertView;
        }

        private class ViewHolder{
            ImageView img;
            TextView txt;
        }
    }
}
