package com.example.capstone.mathnote_capstone.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        public MyViewHolder(View v) {
            super(v);
            categoryView = v;
            categoryTv = v.findViewById(R.id.algebraTitle);
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
        holder.categoryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlgebraDetailActivity.class);
                intent.putExtra("categoryid", categories.get(position).getId());
                intent.putExtra("categoryname", categories.get(position).getCategoryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
//    private List<Category> categories;
//    private Context context;
//
//    public AlgebraAdapter(Context mContext, ArrayList<String> mLabels) {
//        this.mContext = mContext;
//        this.mLabels = mLabels;
//        mLayoutInflater = LayoutInflater.from(mContext);
//
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mLayoutInflater.inflate(R.layout.list_algebra_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        final String title = mLabels.get(position);
//        holder.algebraTitle.setText(title);
//        holder.item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, AlgebraDetailActivity.class);
//                intent.putExtra("AlgebraTitle", title);
//                mContext.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mLabels.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        View item;
//        TextView algebraTitle;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            item = itemView;
//            algebraTitle = itemView.findViewById(R.id.algebraTitle);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
//    }
}