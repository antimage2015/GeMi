package com.crazy.gemi.ui.near;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crazy.gemi.R;
import com.crazy.gemi.ui.adapter.NearAdapter;
import com.crazy.gemi.ui.nearjson.AreaKeyInfo;
import com.crazy.gemi.ui.nearjson.InfoT;
import com.crazy.gemi.ui.nearjson.TotalInfo;
import com.crazy.gemi.ui.nearlistjson.MerchantKeyT;
import com.crazy.gemi.ui.nearlistjson.TotalT;
import com.crazy.gemi.ui.utils.JsonCache;
import com.crazy.gemi.ui.utils.MyApplication;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.warmtel.android.xlistview.XListView;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopOneListView;
import com.warmtel.expandtab.PopTwoListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NearFragment extends Fragment {

    private ExpandPopTabView expandTabView;
    private NearAdapter adapter;
    private XListView listView;
    private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    private Context context = MyApplication.getInstance();

    public static NearFragment newInstance() {
        NearFragment fragment = new NearFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_near_layout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();
    }

    private void init() {
        expandTabView = (ExpandPopTabView)getView().findViewById(R.id.expandtab_view);
        String urlTitle = "http://www.warmtel.com:8080/configs";
        new DownloadTaskTitle().execute(urlTitle);

        String urlList = "http://www.warmtel.com:8080/around";
 //       new DownloadTaskList().execute(urlList);

        ProgressBar progressBar = (ProgressBar)getView().findViewById(R.id.progressbar);
        listView = (XListView)getView().findViewById(R.id.fg_near_list_view);

        getAsyncDataList(urlList);
        pullForXListView(urlList);

        adapter = new NearAdapter(getActivity());
        // 读取缓存 ListView
        String itemStr = JsonCache.getInstance(context).getJsonCache("around");
        if (itemStr != null){
            forGsonList(itemStr);
        }

        listView.setAdapter(adapter);
        listView.setEmptyView(progressBar);

    }

    private void pullForXListView(final String urlList){
        // 默认是 false，这里改为 true
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        getAsyncDataList(urlList);
                    }

                }.execute();
            }

            @Override
            public void onLoadMore() {
                getAsyncDataList(urlList);
            }
        });
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

                // 写入缓存（ListView）
                JsonCache.getInstance(getActivity()).setJsonCache("around", s);

                forGsonList(s);

                Toast.makeText(context, "加载成功", Toast.LENGTH_SHORT).show();

                listView.setRefreshTime(
                        new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()));
                // 下载成功之后停止刷新（减少内存消耗）
                listView.stopRefresh();
                // 下载成功后停止下载（减少内存消耗）
                listView.stopLoadMore();
            }
        });
    }

    private class DownloadTaskTitle extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 读取缓存 菜单
            String cacheStr = JsonCache.getInstance(context).getJsonCache("title");
            if (cacheStr != null) {
                forGsonTitle(cacheStr);
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String str = params[0];
            return dataFromNet(str);
        }

        @Override
        protected void onPostExecute(String forNet) {
            if (forNet != null)
                forGsonTitle(forNet);
        }
    }

    private void forGsonTitle(String str){

        Gson gson = new Gson();
        TotalInfo totalInfo = gson.fromJson(str, TotalInfo.class);

        InfoT infoT = totalInfo.getInfo();

        List<KeyValueBean> distanceKeyInfos = infoT.getDistanceKey();
        List<KeyValueBean> industryList  = infoT.getIndustryKey();
        List<KeyValueBean> sortList = infoT.getSortKey();


        // 二级菜单
        List<KeyValueBean> parentList = new ArrayList<>();
        List<ArrayList<KeyValueBean>> childrenList = new ArrayList<>();
        List<AreaKeyInfo> areaKeyInfos = infoT.getAreaKey();

        for (AreaKeyInfo areas : areaKeyInfos) {

            KeyValueBean keyValueBean = new KeyValueBean();
            keyValueBean.setKey(areas.getKey());
            keyValueBean.setValue(areas.getValue());
            parentList.add(keyValueBean);

            List<KeyValueBean> circlesList = areas.getCircles();
            childrenList.add((ArrayList<KeyValueBean>) circlesList);
        }
        expandTabView.removeAllViews();
        // 添加一级菜单的数据
        addItem(expandTabView, distanceKeyInfos, "", "距离");
        addItem(expandTabView, industryList, "", "行业");
        addItem(expandTabView, sortList, "", "排序");
        // 添加二级菜单数据
        addItem(expandTabView, parentList, childrenList, "武侯区", "磨子桥", "区域");
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> lists, String defaultSelect, String defaultShowText) {

        PopOneListView popOneListView = new PopOneListView(context);
        popOneListView.setDefaultSelectByValue(defaultSelect);
        //popViewOne.setDefaultSelectByKey(defaultSelect);
        popOneListView.setCallBackAndData(lists, expandTabView, new PopOneListView.OnSelectListener() {
            @Override
            public void getValue(String key, String value) {
                //弹出框选项点击选中回调方法
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popOneListView);
    }

    public void addItem(ExpandPopTabView expandTabView, List<KeyValueBean> parentLists,
                        List<ArrayList<KeyValueBean>> childrenListLists, String defaultParentSelect, String defaultChildSelect, String defaultShowText) {
        PopTwoListView popTwoListView = new PopTwoListView(context);
        popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect);
        //distanceView.setDefaultSelectByKey(defaultParent, defaultChild);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, new PopTwoListView.OnSelectListener() {
            @Override
            public void getValue(String showText, String parentKey, String childrenKey) {
                //弹出框选项点击选中回调方法
            }
        });
        expandTabView.addItemToExpandTab(defaultShowText, popTwoListView);
    }

//    private class DownloadTaskList extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            String str = params[0];
//            return dataFromNet(str);
//        }
//
//        @Override
//        protected void onPostExecute(String forNet) {
//            forGsonList(forNet);
//        }
//    }

    private void forGsonList(String str){

        Gson gson = new Gson();
        TotalT totalT = gson.fromJson(str, TotalT.class);

        com.crazy.gemi.ui.nearlistjson.InfoT infoT = totalT.getInfo();
        List<MerchantKeyT> merchantKeyTList = infoT.getMerchantKey();

        adapter.setForMerchantKeyT(merchantKeyTList);
    }

    private String dataFromNet(String str){
        HttpURLConnection conn = null;
        BufferedReader br = null;
        InputStream is = null;
        try {
            URL url = new URL(str);
            conn = (HttpURLConnection)url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setConnectTimeout(5000);
            conn.connect();

            if(conn.getResponseCode() == 200){

                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder sb = new StringBuilder("");
                String str1 = null;
                while ((str1 = br.readLine()) != null) {
                    sb.append(str1);
                }
                // 菜单的缓存（写入）
                JsonCache.getInstance(context).setJsonCache("title", sb.toString());
                return sb.toString();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null && is != null && conn != null) {
                try {
                    br.close();
                    is.close();
                    conn.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
