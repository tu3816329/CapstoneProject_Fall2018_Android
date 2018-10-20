package com.example.capstone.mathnote_capstone.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.adapter.SearchListAdapter;
import com.example.capstone.mathnote_capstone.adapter.ViewPagerAdapter;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Division;
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.model.SearchResults;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SearchListAdapter adapter;
    List<Lesson> lessons = null;
    String title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView titleTv = findViewById(R.id.titleTv);

        /* Get grade name for title */
        MathFormulasDao dao = new MathFormulasDao(this);
        Grade grade = dao.getChosenGrade();
        if (grade != null) {
            title = grade.getGradeName();
        } else {
            // First time use app
            int gradeId = Objects.requireNonNull(getIntent().getExtras()).getInt("gradeid");
            title = getIntent().getExtras().getString("gradename");
            dao.setChosenGrade(gradeId);
        }
        titleTv.setText(title);

        /* Search lesson */
        final ListView suggestionLv = findViewById(R.id.suggestionLv);
        final SearchView searchView = findViewById(R.id.lessonSv);
        // Hide title while searching
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setAlpha(0f);
            }
        });

        // Show title
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                titleTv.setAlpha(1f);
                titleTv.setText(title);
                suggestionLv.setAdapter(null);
                return false;
            }
        });

        suggestionLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lesson lesson = (Lesson) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                intent.putExtra("lessonid", lesson.getId());
                intent.putExtra("lessontitle", lesson.getLessonTitle());
                intent.putExtra("lessoncontent", lesson.getLessonContent());
                startActivity(intent);
            }
        });
        lessons = dao.getAllLessons();
        adapter = new SearchListAdapter(this, lessons);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(MainActivity.this, AlgebraDetailActivity.class);
                intent.putExtra("categoryid", -1);
                intent.putExtra("categoryname", "Kết quả tìm kiếm");
                intent.putExtra("results", new SearchResults(lessons));
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s);
                suggestionLv.setAdapter(adapter);
                return false;
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Add division tab
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager_id);
        List<Division> divisions = dao.getAllDivisions();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), divisions);
        viewPagerAdapter.notifyDataSetChanged();

        //Add Fragment
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the camera action
        } else if (id == R.id.nav_favorited) {

        } else if (id == R.id.nav_notes) {
            Intent intent = new Intent(this, UserNoteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_feedback) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
