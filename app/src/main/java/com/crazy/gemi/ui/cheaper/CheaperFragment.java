package com.crazy.gemi.ui.cheaper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy.gemi.R;
import com.crazy.gemi.secondarymenu.BusinessSecMenu;
import com.crazy.gemi.secondarymenu.PinPaiSecMenu;
import com.crazy.gemi.ui.adapter.CheaperAdapterNull;
import com.crazy.gemi.ui.adapter.CheaperListAdapter;
import com.crazy.gemi.ui.cheaperjson.CheaperAdvertisingKey;
import com.crazy.gemi.ui.cheaperjson.CheaperHotkey;
import com.crazy.gemi.ui.cheaperjson.CheaperInfo;
import com.crazy.gemi.ui.cheaperjson.CheaperTotal;
import com.crazy.gemi.ui.utils.JsonCache;
import com.crazy.gemi.ui.utils.MyApplication;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.warmtel.android.xlistview.XListView;

import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class CheaperFragment extends Fragment implements View.OnClickListener{

    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    private XListView listView;
    private SliderLayout sliderLayout;
    private List<CheaperAdvertisingKey> cakList;
//    private CheaperListAdapter adapter;
    private TextView cai, hot, film, wedding, coffee, hotel, kao, ktv;
    private EditText editText;

    private TextView business, pinpai;

    private SearchResult searchResult;
    public interface SearchResult{
        void doSearch();
    }


    private Context context = MyApplication.getInstance();


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SearchResult) {
            searchResult = (SearchResult) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnMerchantFragmentInteractionListener");
        }
    }

    public static CheaperFragment newInstance() {
        CheaperFragment fragment = new CheaperFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // list 内容部分有内容
    //    return inflater.inflate(R.layout.fragment_cheaper_layout, container, false);
        // list 内容部分为空
        return inflater.inflate(R.layout.fragment_cheaper_layout_yuan, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        inti();
    }

    private void inti() {
 //       listView = (XListView)getView().findViewById(R.id.fg_cheaper_listView);

        // list 内容部分为空的 list
        listView = (XListView)getView().findViewById(R.id.fg_cheaper_listView_yuan);

        initTextView();

        String cheaper_url = "http://www.warmtel.com:8080/keyConfig";
        getAsyncDataList(cheaper_url);

        CheaperAdapterNull cheaperAdapterNull = new CheaperAdapterNull(getActivity());


        // 读取缓存 ListView
        String cheaperStr = JsonCache.getInstance(context).getJsonCache("cheaper");
        if (cheaperStr != null){
            forGsonCheaper(cheaperStr);
        }


        listView.setAdapter(cheaperAdapterNull);
 //       adapter = new CheaperListAdapter(getActivity());
 //       listView.setAdapter(adapter);
    }

    private void initTextView() {
        cai = (TextView)getView().findViewById(R.id.fg_cheaper_choose_chuancai_yuan);
        hot = (TextView)getView().findViewById(R.id.fg_cheaper_choose_huoguo_yuan);
        film = (TextView)getView().findViewById(R.id.fg_cheaper_choose_dianying_yuan);
        wedding = (TextView)getView().findViewById(R.id.fg_cheaper_choose_hunsa_yuan);
        coffee = (TextView)getView().findViewById(R.id.fg_cheaper_choose_coffee_yuan);
        hotel = (TextView)getView().findViewById(R.id.fg_cheaper_choose_hotel_yuan);
        kao = (TextView)getView().findViewById(R.id.fg_cheaper_choose_shakao_yuan);
        ktv = (TextView)getView().findViewById(R.id.fg_cheaper_choose_ktv_yuan);

        business = (TextView)getView().findViewById(R.id.fg_cheaper_choose_bus_txt_yuan);
        pinpai = (TextView)getView().findViewById(R.id.fg_cheaper_choose_brand_txt_yuan);

        editText = (EditText)getView().findViewById(R.id.fg_cheaper_search_hint_yuan);

        business.setOnClickListener(this);
        pinpai.setOnClickListener(this);
        editText.setOnClickListener(this);

    }

    public void getAsyncDataList(String url) {
        asyncHttpClient.get(url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Context context = MyApplication.getInstance();
                Toast.makeText(context, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                forGsonCheaper(s);
                // 写入缓存
                JsonCache.getInstance(context).setJsonCache("cheaper", s);
            }
        });
    }

    private void forGsonCheaper(String str) {
        Gson gson = new Gson();
        CheaperTotal cheaperTotal = gson.fromJson(str, CheaperTotal.class);
        CheaperInfo cheaperInfo = cheaperTotal.getInfo();

        cakList = cheaperInfo.getAdvertisingKey();

        List<CheaperHotkey> cHotkeys = cheaperInfo.getHotkey();
        initListHeader();
 //       adapter.setDataList(cHotkeys);


        // ============================== list 内容部分为空,及其下面部分的内容，如 川菜，火锅等   ==========
        cai.setText(cHotkeys.get(0).getValue());
        hot.setText(cHotkeys.get(1).getValue());
        film.setText(cHotkeys.get(2).getValue());
        wedding.setText(cHotkeys.get(3).getValue());
        coffee.setText(cHotkeys.get(4).getValue());
        hotel.setText(cHotkeys.get(5).getValue());
        kao.setText(cHotkeys.get(6).getValue());
        ktv.setText(cHotkeys.get(7).getValue());
        // ============================== list 内容部分为空,及其下面部分的内容，如 川菜，火锅等   ==========
    }

    private void initListHeader() {
        View sliderHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.cheaper_list_header,null);
        sliderLayout = (SliderLayout) sliderHeaderView.findViewById(R.id.cheaper_list_header_slider);
        listView.addHeaderView(sliderHeaderView);

        HashMap<String,String> sliderList = getData();
        for(String key : sliderList.keySet()){
            String url = sliderList.get(key);
            TextSliderView textSliderView = new TextSliderView(getActivity());
            textSliderView.description(key);
            textSliderView.image(url);
            textSliderView.setScaleType(TextSliderView.ScaleType.CenterCrop);
            sliderLayout.addSlider(textSliderView);

        }
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }

    /* ListView 的头部加载数据来源 */
    private HashMap<String,String> getData(){

        HashMap<String,String> http_url_maps = new HashMap<String, String>();
        for (CheaperAdvertisingKey caks : cakList) {
            http_url_maps.put(caks.getDescription(), caks.getPicUrl());
        }
        return http_url_maps;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fg_cheaper_choose_bus_txt_yuan:
                String bus_url = "http://www.warmtel.com:8080/industry";
                Intent intent = new Intent(getActivity(), BusinessSecMenu.class);
                intent.putExtra("BUSINESS_URL", bus_url);
                startActivity(intent);
                break;
            case R.id.fg_cheaper_choose_brand_txt_yuan:
                String pinpai_url = "http://www.warmtel.com:8080/getBrandByIndustry";
                Intent pinpaiIntent = new Intent(getActivity(), PinPaiSecMenu.class);
                pinpaiIntent.putExtra("PINPAI_URL", pinpai_url);
                startActivity(pinpaiIntent);
                break;
            case R.id.fg_cheaper_search_hint_yuan:
                searchResult.doSearch();
 //               getActivity().onSearchRequested();
                break;
        }
    }

}
