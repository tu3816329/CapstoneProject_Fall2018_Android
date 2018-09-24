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
        mListLabel = findViewById(R.id.listAlgebraDetail);
        mListLabel.setLayoutManager(layoutManager);

        ArrayList<String> datatmp = new ArrayList<>();
        datatmp.add("Số nguyên tố");
        datatmp.add("Bất đẳng thức");
        datatmp.add("Thập phân");
        datatmp.add("Phân số");
        datatmp.add("Bất đẳng thức");

        mAlgebraAdapter = new AlgebraDetailAdapter(this, datatmp);
        mListLabel.setAdapter(mAlgebraAdapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
