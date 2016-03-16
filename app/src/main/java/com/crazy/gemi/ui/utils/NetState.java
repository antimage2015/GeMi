package com.crazy.gemi.ui.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetState {

    private static final int CMNET = 3;
    private static final int CMWAP = 2;
    private static final int WIFI = 1;

    /**
     * 获取当前的网络状态  -1：没有网络  1：WIFI网络 2：wap网络  3：net网络
     * @param context
     * @return
     */
    public static int getAPNType(Context context){

        int netType = -1;

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo==null){
            return netType;
        }

        int nType = networkInfo.getType();
        if(nType==ConnectivityManager.TYPE_MOBILE){

            Log.e("tag", "networkInfo.getExtraInfo() is " + networkInfo.getExtraInfo());

            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){
                netType = CMNET;
            } else {
                netType = CMWAP;
            }

        } else if (nType==ConnectivityManager.TYPE_WIFI){
            netType = WIFI;
        }

        return netType;
    }
}
