package com.crazy.gemi.secondarymenu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crazy.gemi.R;
import com.crazy.gemi.businessjson.BusinessInfo;
import com.crazy.gemi.businessjson.BusinessMenuTotal;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;


import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class BusinessSecMenu extends Activity {

    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_sec_menu_layout);

        init();
    }

    private void init() {
        listView = (ListView)findViewById(R.id.business_sec_menu_list);

        Intent intent = getIntent();
        String url = intent.getStringExtra("BUSINESS_URL");

        getAsyncDataList(url);

    }

    public void getAsyncDataList(String url) {
        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(BusinessSecMenu.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                forGsonCheaper(s);
            }
        });
    }

    private void forGsonCheaper(String str) {
        Gson gson = new Gson();
        BusinessMenuTotal menuTotal = gson.fromJson(str, BusinessMenuTotal.class);
        List<BusinessInfo> businessInfos = menuTotal.getInfo();

        BusinessMenuAdapter adapter = new BusinessMenuAdapter(this, businessInfos);
        listView.setAdapter(adapter);
    }

    private class BusinessMenuAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
        private List<BusinessInfo> businessInfos = new ArrayList<>();

        public BusinessMenuAdapter(Context context, List<BusinessInfo> businessInfos) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.businessInfos = businessInfos;
        }

        @Override
        public int getCount() {
            return businessInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return businessInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.business_sec_menu_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.img = (ImageView)convertView.findViewById(R.id.business_sec_menu_item_img);
                viewHolder.txt = (TextView)convertView.findViewById(R.id.business_sec_menu_item_txt);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            BusinessInfo infos = (BusinessInfo)getItem(position);
            Glide.with(context).load(infos.getIconUrl()).into(viewHolder.img);
            viewHolder.txt.setText(infos.getIndustry());

            return convertView;
        }

        private class ViewHolder{
            ImageView img;
            TextView txt;
        }
    }
}
