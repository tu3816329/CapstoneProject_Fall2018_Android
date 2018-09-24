package com.example.capstone.mathnote_capstone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.Activity.AlgebraDetailActivity;
import com.example.capstone.mathnote_capstone.Activity.WebviewActivity;
import com.example.capstone.mathnote_capstone.R;

import java.util.ArrayList;

public class AlgebraDetailAdapter extends RecyclerView.Adapter<AlgebraDetailAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<String> mLabels;

    public AlgebraDetailAdapter(Context mContext, ArrayList<String> mLabels) {
        this.mContext = mContext;
        this.mLabels = mLabels;
        mLayoutInflater = LayoutInflater.from(mContext);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_detail_algebra_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String title = mLabels.get(position);
        holder.algebraDetailTitle.setText(title);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WebviewActivity.class);
                intent.putExtra("AlgebraDetailTitle", title);
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
        TextView algebraDetailTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            algebraDetailTitle = itemView.findViewById(R.id.algebraDetailTitle);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
