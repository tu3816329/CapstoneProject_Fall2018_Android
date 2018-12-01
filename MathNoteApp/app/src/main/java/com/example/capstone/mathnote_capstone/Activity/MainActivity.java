package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
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
import android.util.Log;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SearchListAdapter adapter;
    List<Lesson> lessons = null;
    String title = "";
    private static int divisionId = 0;

    TextView titleTv;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleTv = findViewById(R.id.titleTv);
        final View v = findViewById(R.id.search_overlay_view);

        /* Get grade name for title */
        MathFormulasDao dao = new MathFormulasDao(this);
        Bundle bundle = getIntent().getExtras();
        Grade grade = dao.getChosenGrade();
        title = grade.getGradeName();
        titleTv.setText(title);

        /* Search lesson */
        final ListView suggestionLv = findViewById(R.id.suggestionLv);
        final SearchView searchView = findViewById(R.id.main_sv);
        // Hide title while searching
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titleTv.setAlpha(0f);
                v.setVisibility(View.VISIBLE);
            }
        });
        // Show title
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                v.setVisibility(View.GONE);
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
                intent.putExtra("lesson", lesson);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
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
                overridePendingTransition(R.anim.enter, R.anim.exit);
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
        tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager_id);
        List<Division> divisions = dao.getAllDivisions();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), divisions);
        viewPagerAdapter.notifyDataSetChanged();

        //Add Fragment
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // Set selected tab
        divisionId = getIntent().getExtras().getInt("divisionid");
        int index = (divisionId == 1) ? 0 : 1;
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        tab.select();
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
        if (id == R.id.nav_grade) {
            Intent intent = new Intent(this, GradeActivity.class);
            intent.putExtra("activity", "main");
            startActivityForResult(intent, 3);
        } else if (id == R.id.nav_favorited) {
            Intent intent = new Intent(this, FavoriteActivity.class);
            intent.putExtra("activity", "main");
            startActivityForResult(intent, 4);
        } else if (id == R.id.nav_notes) {
            Intent intent = new Intent(this, NoteListActivity.class);
            intent.putExtra("activity", "main");
            startActivityForResult(intent, 5);
        } else if (id == R.id.nav_instruction) {
            Intent intent = new Intent(this, InstructionActivity.class);
            intent.putExtra("activity", "main");
            startActivityForResult(intent, 6);
        }
        overridePendingTransition(R.anim.enter, R.anim.exit);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 3) {
                divisionId = data.getExtras().getInt("divisionid");
                int index = (divisionId == 1) ? 0 : 1;
                TabLayout.Tab tab = tabLayout.getTabAt(index);
                tab.select();
                MathFormulasDao dao = new MathFormulasDao(this);
                Grade grade = dao.getChosenGrade();
                titleTv.setText(grade.getGradeName());
            }
        }
    }
}