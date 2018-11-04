package com.example.capstone.mathnote_capstone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.activity.FavoriteActivity;
import com.example.capstone.mathnote_capstone.activity.NoteListActivity;
import com.example.capstone.mathnote_capstone.activity.WebviewActivity;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Lesson;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.RecyclerHolder> {
    private static TextView lessonTitleTv;
    private List<Lesson> lessons;

    private Context context;

    public FavoriteListAdapter(Context context, List<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
    }

    @Override
    public FavoriteListAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FavoriteListAdapter.RecyclerHolder.buildFor(parent);
    }

    @Override
    public void onBindViewHolder(final FavoriteListAdapter.RecyclerHolder holder, final int position) {
        final int lessonId = lessons.get(position).getId();
        final String lessonTitle = lessons.get(position).getLessonTitle();

        final MathFormulasDao dao = new MathFormulasDao(context);
        int score = dao.getQuizScore(lessonId);
        holder.lessonScorePb.setProgress(score);
        holder.lessonScoreTv.setText(score + "");
        holder.mathformNumTv.setText(dao.getMathformByLesson(lessonId).size() + " dạng bài");
        lessonTitleTv.setText(lessonTitle);
        final Activity activity = (Activity) context;
        holder.lessonDetailIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("activity", "favorite");
                intent.putExtra("lesson", lessons.get(position));
                activity.startActivityForResult(intent, 4);
                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        holder.removeIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int row = dao.removeFavoriteLesson(lessonId);
                if(row > 0) {
                    activity.finish();
                    activity.overridePendingTransition(0, 0);
                    activity.startActivity(new Intent(context, FavoriteActivity.class));
                    activity.overridePendingTransition(0, 0);
                    Toast.makeText(context,
                            "Đã xoá '" + lessonTitle + " ' khỏi danh sách yêu thích",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    final static class RecyclerHolder extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.favorite_list_item;
        private ImageButton removeIb, lessonDetailIb;
        private TextView mathformNumTv, lessonScoreTv;
        ProgressBar lessonScorePb;

        private static FavoriteListAdapter.RecyclerHolder buildFor(ViewGroup viewGroup) {
            return new FavoriteListAdapter.RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        private RecyclerHolder(View itemView) {
            super(itemView);
            lessonScoreTv = itemView.findViewById(R.id.lesson_score_tv2);
            lessonScorePb = itemView.findViewById(R.id.lesson_score_pb2);
            mathformNumTv = itemView.findViewById(R.id.mathforms_num2);
            lessonTitleTv = itemView.findViewById(R.id.lesson_title_tv2);
            lessonDetailIb = itemView.findViewById(R.id.lesson_detail_ib2);
            removeIb = itemView.findViewById(R.id.remove_favorite_ib);
        }
    }
}