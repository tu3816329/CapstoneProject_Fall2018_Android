package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.adapter.NoteListAdapter;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.UserNote;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.List;

public class NoteListActivity extends AppCompatActivity {

    RecyclerView noteRv;
    MathFormulasDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Ghi chú");
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left);
        setContentView(R.layout.activity_note_list);

        noteRv = findViewById(R.id.note_list_rv);
        noteRv.setLayoutManager(new LinearLayoutManager(this));

        dao = new MathFormulasDao(this);
        List<UserNote> notes = dao.getAllNotes();
        NoteListAdapter adapter = new NoteListAdapter(this, notes);
        noteRv.setAdapter(adapter);

        FloatingActionButton addNoteBtn = findViewById(R.id.add_note_btn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(AppUtils.checkInternetConnection(NoteListActivity.this)) {
                    Intent intent = new Intent(NoteListActivity.this, UserNoteActivity.class);
                    startActivityForResult(intent, 2);
                } else {
                    Toast.makeText(NoteListActivity.this, "Cần có kết nối internet để sử dụng tính năng này", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1 || requestCode == 2) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(new Intent(this, NoteListActivity.class));
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
            }
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
