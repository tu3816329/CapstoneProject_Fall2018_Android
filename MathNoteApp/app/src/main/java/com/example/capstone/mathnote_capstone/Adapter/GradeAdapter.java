package com.example.capstone.mathnote_capstone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;



import com.example.capstone.mathnote_capstone.Activity.MainActivity;
import com.example.capstone.mathnote_capstone.Model.Grade;
import com.example.capstone.mathnote_capstone.R;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeAdapterHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Grade> grades;


    public GradeAdapter(Context mContext, ArrayList<Grade> grades) {
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
        Grade grade = grades.get(position);
        holder.setDetails(grade);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
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
        private TextView txtGrade, txtNumberOfChapter;


        public GradeAdapterHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivImg);
            txtGrade = (TextView) itemView.findViewById(R.id.txtGrade);
            txtNumberOfChapter = (TextView) itemView.findViewById(R.id.txtNumberOfChapter);
        }

        public void setDetails(Grade grade) {
            imageView.setImageResource(Integer.parseInt(grade.getImg()));
            txtGrade.setText(grade.getGradeName());
            txtNumberOfChapter.setText(String.format("%d chapters", grade.getNumberOfChapter()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });
        }
    }

}
