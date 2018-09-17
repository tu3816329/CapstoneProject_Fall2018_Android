package com.example.capstone.mathnote_capstone.Adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.Activity.AlgebraDetailActivity;
import com.example.capstone.mathnote_capstone.R;

import java.io.Serializable;
import java.util.ArrayList;

public class AlgebraAdapter extends RecyclerView.Adapter<AlgebraAdapter.ViewHolder> implements Serializable {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mLabels;

    public AlgebraAdapter(Context mContext, ArrayList<String> mLabels) {
        this.mContext = mContext;
        this.mLabels = mLabels;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_algebra_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String title = mLabels.get(position);
        holder.algebraTitle.setText(title);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AlgebraDetailActivity.class);
                intent.putExtra("AlgebraTitle", title);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLabels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View item;
        TextView algebraTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            algebraTitle = itemView.findViewById(R.id.algebraTitle);
        }

        @Override
        public void onClick(View view) {

        }
    }
}