package com.crazy.gemi.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crazy.gemi.R;
import com.crazy.gemi.ui.cheaperjson.CheaperHotkey;

import java.util.ArrayList;
import java.util.List;

/**
 *  该类为 list 的内容部分不为空时的适配器，只有 2 行，每行 4 列（暂时没有用到）
 */
public class CheaperListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<CheaperHotkey> cHotkeys = new ArrayList<>();

    public CheaperListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<CheaperHotkey> cHotkeys) {
        this.cHotkeys = cHotkeys;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return cHotkeys.size()/4;
    }

    @Override
    public Object getItem(int position) {
        return cHotkeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.cheaper_list_item, parent, false);
//            viewHolder = new ViewHolder();
//            viewHolder.cai = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_chuancai);
//            viewHolder.hot = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_huoguo);
//            viewHolder.film = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_dianying);
//            viewHolder.wedding = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_hunsa);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder)convertView.getTag();
//        }



        convertView = inflater.inflate(R.layout.cheaper_list_item, parent, false);
        TextView cai = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_chuancai);
        TextView hot = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_huoguo);
        TextView film = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_dianying);
        TextView wedding = (TextView)convertView.findViewById(R.id.fg_cheaper_choose_hunsa);


        if (position == 0){
            CheaperHotkey cheaperHot = (CheaperHotkey)getItem(position);
            cai.setText(cheaperHot.getValue());
        }

        if (position == 1){
            CheaperHotkey cheaperHot1 = (CheaperHotkey)getItem(position);
            hot.setText(cheaperHot1.getValue());
        }
        if (position == 2){
            CheaperHotkey cheaperHot2 = (CheaperHotkey)getItem(position);
            film.setText(cheaperHot2.getValue());
        }
        if (position == 3){
            CheaperHotkey cheaperHot3 = (CheaperHotkey)getItem(position);
            wedding.setText(cheaperHot3.getValue());
        }


        if (getCount() == 2) {
            CheaperHotkey cheaperHot4 = (CheaperHotkey)getItem(position + 3);
            cai.setText(cheaperHot4.getValue());

            CheaperHotkey cheaperHot5 = (CheaperHotkey)getItem(position + 4);
            hot.setText(cheaperHot5.getValue());

            CheaperHotkey cheaperHot6 = (CheaperHotkey)getItem(position + 5);
            film.setText(cheaperHot6.getValue());

            CheaperHotkey cheaperHot7 = (CheaperHotkey)getItem(position + 6);
            wedding.setText(cheaperHot7.getValue());
        }

//        if (getCount() == 1){
//
//            CheaperHotkey cheaperHot = (CheaperHotkey)getItem(position - 3);
//            viewHolder.cai.setText(cheaperHot.getValue());
//
//            CheaperHotkey cheaperHot1 = (CheaperHotkey)getItem(position - 2);
//            viewHolder.hot.setText(cheaperHot1.getValue());
//
//            CheaperHotkey cheaperHot2 = (CheaperHotkey)getItem(position - 1);
//            viewHolder.film.setText(cheaperHot2.getValue());
//
//            CheaperHotkey cheaperHot3 = (CheaperHotkey)getItem(position);
//            viewHolder.wedding.setText(cheaperHot3.getValue());
//        }
//
//        if (getCount() == 2) {
//            CheaperHotkey cheaperHot4 = (CheaperHotkey)getItem(position + 3);
//            viewHolder.cai.setText(cheaperHot4.getValue());
//
//            CheaperHotkey cheaperHot5 = (CheaperHotkey)getItem(position + 4);
//            viewHolder.hot.setText(cheaperHot5.getValue());
//
//            CheaperHotkey cheaperHot6 = (CheaperHotkey)getItem(position + 5);
//            viewHolder.film.setText(cheaperHot6.getValue());
//
//            CheaperHotkey cheaperHot7 = (CheaperHotkey)getItem(position + 6);
//            viewHolder.wedding.setText(cheaperHot7.getValue());
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView cai, hot, film, wedding;
    }
}
