package com.crazy.gemi.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crazy.gemi.R;
import com.crazy.gemi.ui.nearlistjson.MerchantKeyT;

import java.util.ArrayList;
import java.util.List;


public class NearAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private List<MerchantKeyT> list = new ArrayList<>();

    public NearAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setForMerchantKeyT(List<MerchantKeyT> merchantKeyTList) {
        list = merchantKeyTList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // 一级优化
            convertView = inflater.inflate(R.layout.near_list_item_layout, parent, false);
            // 二级优化
            viewHolder = new ViewHolder();
            viewHolder.iconImg = (ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.titleTxt = (TextView)convertView.findViewById(R.id.title);
            viewHolder.cardImg = (ImageView)convertView.findViewById(R.id.image_card);
            viewHolder.groupImg = (ImageView)convertView.findViewById(R.id.image_group);
            viewHolder.ticketImg = (ImageView)convertView.findViewById(R.id.image_ticket);
            viewHolder.contentTxt = (TextView)convertView.findViewById(R.id.content);
            viewHolder.addressTxt = (TextView)convertView.findViewById(R.id.address);
            viewHolder.distanceTxt = (TextView)convertView.findViewById(R.id.distance);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        MerchantKeyT merchantKeyT = (MerchantKeyT)getItem(position);
        Glide.with(context).load(merchantKeyT.getPicUrl()).into(viewHolder.iconImg);
        viewHolder.titleTxt.setText(merchantKeyT.getName());
        viewHolder.contentTxt.setText(merchantKeyT.getCoupon());
        viewHolder.addressTxt.setText(merchantKeyT.getLocation());
        viewHolder.distanceTxt.setText(merchantKeyT.getDistance());

        if(merchantKeyT.getCardType().equalsIgnoreCase("YES")){
            viewHolder.cardImg.setVisibility(View.VISIBLE);
        }else{
            viewHolder.cardImg.setVisibility(View.GONE);
        }

        if(merchantKeyT.getGroupType().equalsIgnoreCase("YES")){
            viewHolder.groupImg.setVisibility(View.VISIBLE);
        }else{
            viewHolder.groupImg.setVisibility(View.GONE);
        }

        if(merchantKeyT.getCouponType().equalsIgnoreCase("YES")){
            viewHolder.ticketImg.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ticketImg.setVisibility(View.GONE);
        }

        return convertView;

    }


    class ViewHolder {

        ImageView iconImg;

        TextView titleTxt;
        ImageView cardImg;
        ImageView groupImg;
        ImageView ticketImg;

        TextView contentTxt;
        TextView addressTxt;
        TextView distanceTxt;
    }

}
