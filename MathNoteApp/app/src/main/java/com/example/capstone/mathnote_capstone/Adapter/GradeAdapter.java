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
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.R;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeAdapterHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Grade> grades;


    public GradeAdapter(Context context, List<Grade> grades) {
        this.context = context;
        this.grades = grades;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public GradeAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_grade_item, parent, false);
        return new GradeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeAdapterHolder holder, int position) {
        final Grade grade = grades.get(position);
        holder.imageView.setImageResource(R.drawable.img000);
        holder.txtGradeName.setText(grade.getGradeName());
        holder.txtNumOfChapters.setText(grade.getNumOfChapters() + " chapters");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MathFormulasDao dao = new MathFormulasDao(context);
                dao.setChosenGrade(grade.getId());
                Activity activity = (Activity) context;
                String previousActivity = activity.getIntent().getStringExtra("activity");
                if (previousActivity.equals("instruction")) { // First time use
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    activity.overridePendingTransition(R.anim.enter, R.anim.exit);
                } else if (previousActivity.equals("main")) { // User click to grade item
                    activity.setResult(Activity.RESULT_OK);
                    activity.finish();
                }
                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return grades.size();
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
