package com.example.fantuan.andrioddemos;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.fantuan.andrioddemos.adapters.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView myRecylerView;
    private RecyclerView.LayoutManager layoutManager;
    private MyAdapter adapter;
    private List<String> datas = new ArrayList<>();;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initView();
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swiperefresh);

        swipeRefreshLayout.setOnRefreshListener(this);

        myRecylerView = findViewById(R.id.my_recycler_view);
        myRecylerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        myRecylerView.setLayoutManager(layoutManager);


        adapter = new MyAdapter(datas);

        myRecylerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

    }

    private void initDatas() {
        for (int i = 0; i < 50; i++) {
            datas.add("数据"+i);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.this,"Click:"+position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClick(View view, int position) {
        adapter.delateData(position);
    }
    // 下拉刷新

    @Override
    public void onRefresh() {
        datas.clear();
        initDatas();
        swipeRefreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
    }
}
