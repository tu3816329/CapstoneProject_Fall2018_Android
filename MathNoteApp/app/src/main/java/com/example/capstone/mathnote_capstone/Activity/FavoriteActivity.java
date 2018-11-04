package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.adapter.FavoriteListAdapter;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Lesson;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView favoriteRv;
    MathFormulasDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Danh sách yêu thích");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        setContentView(R.layout.activity_favorite);

        favoriteRv = findViewById(R.id.favorite_list_rv);
        favoriteRv.setLayoutManager(new LinearLayoutManager(this));

        dao = new MathFormulasDao(this);
        List<Lesson> lessons = dao.getFavoriteLessons();
        FavoriteListAdapter adapter = new FavoriteListAdapter(this, lessons);
        favoriteRv.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            finish();
            overridePendingTransition(0, 0);
            startActivity(new Intent(this, FavoriteActivity.class));
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_OK);
                finish();
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_OK);
        finish();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}