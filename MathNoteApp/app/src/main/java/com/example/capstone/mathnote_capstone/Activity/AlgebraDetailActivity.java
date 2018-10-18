package com.example.capstone.mathnote_capstone.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.example.capstone.mathnote_capstone.Adapter.AlgebraDetailAdapter;
import com.example.capstone.mathnote_capstone.R;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlgebraDetailActivity extends AppCompatActivity implements Serializable {


    @BindView(R.id.listAlgebraDetail)
    RecyclerView recyclerView;
    private static WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String title = getIntent().getExtras().getString("AlgebraTitle", "");

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.listAlgebraDetail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final AlgebraDetailAdapter adapter = new AlgebraDetailAdapter();
        recyclerView.setAdapter(adapter);

        //fill with empty objects
        final List<Object> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Object());
        }
        adapter.setItems(list);
        recyclerView.setItemViewCacheSize(list.size());

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
//    private RecyclerView mRecyclerView;
//    //private AlgebraDetailAdapter mAlgebraAdapter;
//    final AlgebraDetailAdapter adapter = new AlgebraDetailAdapter();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_algebra_detail);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        String title = getIntent().getExtras().getString("AlgebraTitle","");
//
//        toolbar.setTitle(title);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        mRecyclerView =(RecyclerView) findViewById(R.id.listAlgebraDetail);
//        mRecyclerView.setLayoutManager(layoutManager);

//        ArrayList<String> datatmp = new ArrayList<>();
//        datatmp.add("Sai số tuyệt đối của một số gần đúng ");
//        datatmp.add("Độ chính xác của một số gần đúng ");
//        datatmp.add("Sự biến thiên của hàm số ");
//        datatmp.add("Hàm số chẳn, hàm số lẻ ");
//        datatmp.add("Chiều biến thiên của hàm số bậc 2 ");
//        datatmp.add("Cách giải phương trình bậc 1 ");
//        datatmp.add("Cách giải phương trình bậc 2 ");
//        datatmp.add("Định lý Vi-ét ");
//        datatmp.add("Tính chất của bất đằng thức ");
//
//        mAlgebraAdapter = new AlgebraDetailAdapter(this, datatmp);
//        mListLabel.setAdapter(mAlgebraAdapter);

//        mRecyclerView = findViewById(R.id.listAlgebraDetail);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setAdapter(adapter);
//
//        //fill with empty objects
//        final List<Object> list = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            list.add(new Object());
//        }
//        adapter.setItems(list);
//        mRecyclerView.setItemViewCacheSize(list.size());
