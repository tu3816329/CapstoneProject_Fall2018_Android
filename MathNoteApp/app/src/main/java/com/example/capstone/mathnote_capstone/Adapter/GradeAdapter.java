package com.example.capstone.mathnote_capstone.adapter;

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
import com.example.capstone.mathnote_capstone.model.Grade;
import com.example.capstone.mathnote_capstone.R;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeAdapterHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Grade> grades;


    public GradeAdapter(Context mContext, List<Grade> grades) {
        this.mContext = mContext;
        this.grades = grades;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public GradeAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_grade_item, parent, false);
        return new GradeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeAdapterHolder holder, int position) {
        final Grade grade = grades.get(position);
        holder.setDetails(grade);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("gradeid", grade.getId());
                intent.putExtra("gradename", grade.getGradeName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }


    public class GradeAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtGradeName, txtNumOfChapters;


        public GradeAdapterHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivImg);
            txtGradeName = (TextView) itemView.findViewById(R.id.txtGrade);
            txtNumOfChapters = (TextView) itemView.findViewById(R.id.txtNumberOfChapter);
        }

        public void setDetails(Grade grade) {
            imageView.setImageResource(R.drawable.img000);
            txtGradeName.setText(grade.getGradeName());
            txtNumOfChapters.setText(String.format("%d chapters", grade.getNumOfChapters()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

}
