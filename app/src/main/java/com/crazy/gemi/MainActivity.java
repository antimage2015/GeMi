package com.crazy.gemi;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy.gemi.ui.cheaper.CheaperFragment;
import com.crazy.gemi.ui.cheaper.SearchSuggestionSampleProvider;
import com.crazy.gemi.ui.favor.FavorFragment;
import com.crazy.gemi.ui.more.MoreFragment;
import com.crazy.gemi.ui.near.NearFragment;
import com.crazy.gemi.ui.pocket.PocketFragment;

public class MainActivity extends FragmentActivity
        implements View.OnClickListener, CheaperFragment.SearchResult{

    private TextView[] textView = new TextView[5];
    private View[] views = new View[5];
    // 其中的 firstFragment 相当于是个中间变量
    private Fragment firstFragment, nearFragment, cheaperFragment, favorFragment, pocketFragmnet, moreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initFragment();
    }

    private void init() {

        textView[0] = (TextView)findViewById(R.id.near);
        textView[1] = (TextView)findViewById(R.id.search_cheaper);
        textView[2] = (TextView)findViewById(R.id.favor);
        textView[3] = (TextView)findViewById(R.id.pocket);
        textView[4] = (TextView)findViewById(R.id.more);

        views[0] = findViewById(R.id.near_top_line);
        views[1] = findViewById(R.id.cheaper_top_line);
        views[2] = findViewById(R.id.favor_top_line);
        views[3] = findViewById(R.id.pocket_top_line);
        views[4] = findViewById(R.id.more_top_line);

        textView[0].setOnClickListener(this);
        textView[1].setOnClickListener(this);
        textView[2].setOnClickListener(this);
        textView[3].setOnClickListener(this);
        textView[4].setOnClickListener(this);

    }

    private void initFragment() {
        firstFragment = FavorFragment.newInstance();
        favorFragment = firstFragment;
        // 最先加载的 fragment
        getSupportFragmentManager().beginTransaction().
                add(R.id.frame_layout, favorFragment).commit();
        textView[2].setTextColor(Color.BLACK);
        views[2].setBackgroundColor(Color.parseColor("#FF6600"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.near:

                if(nearFragment==null){
                    nearFragment= NearFragment.newInstance();
                }
                switchContent(firstFragment, nearFragment, getSupportFragmentManager().beginTransaction());
                firstFragment = nearFragment;

                selectStringAndBackgroundColor(0);
                break;
            case R.id.search_cheaper:
                if(cheaperFragment==null){
                    cheaperFragment= CheaperFragment.newInstance();
                }
                switchContent(firstFragment, cheaperFragment, getSupportFragmentManager().beginTransaction());
                firstFragment = cheaperFragment;

                selectStringAndBackgroundColor(1);
                break;
            case R.id.favor:
                if(favorFragment==null){
                    favorFragment= FavorFragment.newInstance();
                }
                switchContent(firstFragment, favorFragment, getSupportFragmentManager().beginTransaction());
                firstFragment = favorFragment;

                selectStringAndBackgroundColor(2);
                break;
            case R.id.pocket:
                if(pocketFragmnet==null){
                    pocketFragmnet= PocketFragment.newInstance();
                }
                switchContent(firstFragment, pocketFragmnet, getSupportFragmentManager().beginTransaction());
                firstFragment = pocketFragmnet;

                selectStringAndBackgroundColor(3);
                break;
            case R.id.more:
                if(moreFragment==null){
                    moreFragment= MoreFragment.newInstance();
                }
                switchContent(firstFragment, moreFragment, getSupportFragmentManager().beginTransaction());
                firstFragment = moreFragment;

                selectStringAndBackgroundColor(4);
                break;
        }
    }

    /**
     *  通过 position 的位置改变文字和 View 的颜色
     * @param position
     */
    private void selectStringAndBackgroundColor(int position){
        int sum = textView.length;
        for (int i = 0; i < sum; i++) {
            if (position == i) {
                textView[i].setTextColor(Color.BLACK);
                views[i].setBackgroundColor(Color.parseColor("#FF6600"));
            } else {
                textView[i].setTextColor(Color.GRAY);
                views[i].setBackgroundColor(Color.parseColor("#f0f0f0"));
            }
        }
    }

    /**
     * 判断是否添加了界面，以保存当前状态
     */
    public void switchContent(Fragment from, Fragment to,
                              FragmentTransaction transaction) {

        if (!to.isAdded()) { // 先判断是否被add过

            transaction.hide(from).add(R.id.frame_layout, to)
                    .commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }

    }

    /**
     *  由于 Activity 的启动模式为 singleTop ,搜索的 Action 必须为:
     *  android.intent.action.SEARCH，所以需要重写该方法，以便获取 Action 的“动作”。
     *  如果不重写，Action 的返回值为 android.intent.action.MAIN
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 设置 intent 这样就能得到正确的 Action 了
        setIntent(intent);
    }

    /**
     *  该方法在 onNewIntent() 方法后执行，可以获取 onNewIntent() 方法设置的 intent，
     *  也可以直接 onNewIntent() 方法进行得到 intent 等的操作
     */
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchQurey(query, intent);
        }
    }

    @Override
    public void doSearch() {
        onSearchRequested();
    }

    private void searchQurey(String query , Intent intent) {
        //保存搜索记录
        SearchRecentSuggestions suggestions=new SearchRecentSuggestions(this,
                SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);

        // 点击 “清空历史记录” 删除记录
        if(query.equals(getString(R.string.clear_search_keyword))){
            suggestions.clearHistory();
        }else{
            suggestions.saveRecentQuery(query, null);
        }

    }

//    private void clearSearchHistory() {
//        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
//                SearchSuggestionSampleProvider.AUTHORITY, SearchSuggestionSampleProvider.MODE);
//        suggestions.clearHistory();
//    }


    /**
     * 返回键两次点击，第一次点击的时间
     */
    private long exitTime = 0;
    @Override
    public void onBackPressed() {
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        // 这里处理逻辑代码，大家注意：该方法仅适用于2.0或更新版的sdk
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
