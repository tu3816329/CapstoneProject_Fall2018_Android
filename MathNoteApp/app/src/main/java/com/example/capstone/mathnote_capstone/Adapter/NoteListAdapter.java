package com.example.capstone.mathnote_capstone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.activity.NoteListActivity;
import com.example.capstone.mathnote_capstone.activity.UserNoteActivity;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.UserNote;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteListHolder> {

    private Context context;
    private List<UserNote> notes;

    public NoteListAdapter(Context context, List<UserNote> notes) {
        this.context = context;
        this.notes = notes;
    }

    @Override
    public NoteListAdapter.NoteListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_list_item, parent, false);
        return new NoteListHolder(view);
    }

    @Override
    public void onBindViewHolder(final NoteListAdapter.NoteListHolder holder, final int position) {
        final UserNote note = notes.get(position);
        holder.noteTitleTv.setText(note.getTitle());
        holder.dateTv.setText(note.getDate());
        final MathFormulasDao dao = new MathFormulasDao(context);
        final Activity activity = ((Activity) context);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(context, UserNoteActivity.class);
                    intent.putExtra("usernote", note);
                    activity.startActivityForResult(intent, 1);
            }
        });
        holder.deleteNoteIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int row = dao.removeUserNote(note.getId());
                if (row > 0) {
                    Toast.makeText(context, "Đã xoá ghi chú '" + note.getTitle() + "'", Toast.LENGTH_SHORT).show();
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    activity.startActivity(new Intent(context, NoteListActivity.class));
                    activity.overridePendingTransition(0, 0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteListHolder extends RecyclerView.ViewHolder {
        private TextView noteTitleTv, dateTv;
        private ImageButton deleteNoteIb;
        private View item;

        public NoteListHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.front_layout);
            noteTitleTv = itemView.findViewById(R.id.note_title_tv);
            dateTv = itemView.findViewById(R.id.date_tv);
            deleteNoteIb = itemView.findViewById(R.id.delete_note_ib);
        }
    }
}
