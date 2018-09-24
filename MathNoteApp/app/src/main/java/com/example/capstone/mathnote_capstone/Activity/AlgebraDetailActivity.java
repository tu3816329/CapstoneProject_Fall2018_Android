package com.example.capstone.mathnote_capstone.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.capstone.mathnote_capstone.Adapter.AlgebraAdapter;
import com.example.capstone.mathnote_capstone.Adapter.AlgebraDetailAdapter;
import com.example.capstone.mathnote_capstone.R;

import java.io.Serializable;
import java.util.ArrayList;

public class AlgebraDetailActivity extends AppCompatActivity implements Serializable {

    private RecyclerView mListLabel;
    private AlgebraDetailAdapter mAlgebraAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algebra_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        String title = getIntent().getExtras().getString("AlgebraTitle","");

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mListLabel =(RecyclerView) findViewById(R.id.listAlgebraDetail);
        mListLabel.setLayoutManager(layoutManager);

        ArrayList<String> datatmp = new ArrayList<>();
        datatmp.add("Sai số tuyệt đối của một số gần đúng ");
        datatmp.add("Độ chính xác của một số gần đúng ");
        datatmp.add("Sự biến thiên của hàm số ");
        datatmp.add("Hàm số chẳn, hàm số lẻ ");
        datatmp.add("Chiều biến thiên của hàm số bậc 2 ");
        datatmp.add("Cách giải phương trình bậc 1 ");
        datatmp.add("Cách giải phương trình bậc 2 ");
        datatmp.add("Định lý Vi-ét ");
        datatmp.add("Tính chất của bất đằng thức ");

        mAlgebraAdapter = new AlgebraDetailAdapter(this, datatmp);
        mListLabel.setAdapter(mAlgebraAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
