package com.lufficc.demolightadapter;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.lufficc.demolightadapter.model.BigImgModel;
import com.lufficc.demolightadapter.model.ImgModel;
import com.lufficc.demolightadapter.model.TextModel;
import com.lufficc.demolightadapter.viewprovider.BigImgViewProvider;
import com.lufficc.demolightadapter.viewprovider.ImgViewProvider;
import com.lufficc.demolightadapter.viewprovider.TextViewProvider;
import com.lufficc.lightadapter.LightAdapter;
import com.lufficc.lightadapter.OnDataClickListener;
import com.lufficc.lightadapter.OnHeaderClickListener;

import java.util.Random;

public class MultiItemActivity extends AppCompatActivity implements OnDataClickListener, OnHeaderClickListener {
    private Random random = new Random();

    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView recyclerView;

    LightAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        adapter.register(TextModel.class, new TextViewProvider());
        adapter.register(ImgModel.class, new ImgViewProvider());
        adapter.register(BigImgModel.class, new BigImgViewProvider());
        adapter.addHeader(new BigImgModel("I am a Header"));
        adapter.setOnDataClickListener(this);
        adapter.setOnHeaderClickListener(this);
        adapter.addData(DataSource.multiData());
    }

    private void init() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DefaultItemDecoration(
                ContextCompat.getColor(this, R.color.white),
                ContextCompat.getColor(this, R.color.divider),
                getResources().getDimensionPixelSize(R.dimen.zero)));
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new LightAdapter());
    }

    @Override
    public void onDataClick(int position, Object data) {
        Log.i("main", "pos:" + position);
        adapter.removeData(position);
    }

    @Override
    public void onHeaderClick(int position, Object header) {
        adapter.removeHeader(position);
    }
}
