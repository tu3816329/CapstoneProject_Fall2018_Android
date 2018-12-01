package com.example.capstone.mathnote_capstone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.capstone.mathnote_capstone.activity.MainActivity;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Division;
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.model.Subject;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeAdapterHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Subject> subjects;

    public GradeAdapter(Context context, List<Subject> subjects) {
        this.context = context;
        this.subjects = subjects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GradeAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_grade_item, parent, false);
        return new GradeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeAdapterHolder holder, int position) {
        final Subject subject = this.subjects.get(position);
        final Grade grade = subject.getGrade();
        final Division division = subject.getDivision();
        final MathFormulasDao dao = new MathFormulasDao(context);

        if (grade.getGradeName().contains("10")) {
            if (division.getId() == 1) {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.daiso_10));
            } else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.hinhhoc_10));
            }
        } else if (grade.getGradeName().contains("11")) {
            if (division.getId() == 1) {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.daiso_11));
            } else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.hinhhoc_11));
            }
        } else {
            if (division.getId() == 1) {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.daiso_12));
            } else {
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.hinhhoc_12));
            }
        }
        holder.txtGradeName.setText(grade.getGradeName() + " (" + division.getDivisionName() + ")");
        holder.txtNumOfChapters.setText(String.valueOf(dao.getChaptersByGradeAndDivision(grade.getId(), division.getId()).size() + " chapters"));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.setChosenGrade(grade.getId());
                Activity activity = (Activity) context;
                String previousActivity = activity.getIntent().getStringExtra("activity");
                if (previousActivity.equals("instruction")) { // First time use
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("divisionid", division.getId());
                    context.startActivity(intent);
                } else if (previousActivity.equals("main")) { // User click to grade item
                    activity.setResult(Activity.RESULT_OK, new Intent().putExtra("divisionid", division.getId()));
                    activity.finish();
                }
                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    class GradeAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtGradeName, txtNumOfChapters;

        GradeAdapterHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImg);
            txtGradeName = itemView.findViewById(R.id.txtGrade);
            txtNumOfChapters = itemView.findViewById(R.id.txtNumberOfChapter);
        }
    }

}
