package com.example.capstone.mathnote_capstone.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.activity.AlgebraDetailActivity;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.model.Category;

import java.io.Serializable;
import java.util.List;

public class AlgebraAdapter extends RecyclerView.Adapter<AlgebraAdapter.MyViewHolder>
        implements Serializable {

    private List<Category> categories;
    private Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View categoryView;
        public TextView categoryTv;
        public ProgressBar categoryPb;
        public TextView categoryProgressTv;

        public MyViewHolder(View v) {
            super(v);
            categoryView = v;
            categoryTv = v.findViewById(R.id.algebraTitle);
            categoryPb = v.findViewById(R.id.category_pb);
            categoryProgressTv = v.findViewById(R.id.category_progress_tv);
        }
    }

    public AlgebraAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_algebra_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.categoryTv.setText(categories.get(position).getCategoryName());
        holder.categoryPb.setProgress((int) categories.get(position).getProgress());
        holder.categoryProgressTv.setText((int) categories.get(position).getProgress() + "");
        holder.categoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlgebraDetailActivity.class);
                intent.putExtra("categoryid", categories.get(position).getId());
                intent.putExtra("categoryname", categories.get(position).getCategoryName());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}