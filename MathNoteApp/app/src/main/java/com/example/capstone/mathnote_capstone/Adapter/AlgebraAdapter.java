package com.example.capstone.mathnote_capstone.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.activity.AlgebraDetailActivity;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.model.Chapter;

import java.io.Serializable;
import java.util.List;

public class AlgebraAdapter extends RecyclerView.Adapter<AlgebraAdapter.MyViewHolder>
        implements Serializable {

    private List<Chapter> chapters;
    private Context context;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        View chapterView;
        TextView chapterTv;
        ImageView chapterIconIv;
        ProgressBar chapterPb;
        TextView chapterProgressTv;

        MyViewHolder(View v) {
            super(v);
            chapterView = v;
            chapterTv = v.findViewById(R.id.algebraTitle);
            chapterIconIv = v.findViewById(R.id.chapter_icon_iv);
            chapterPb = v.findViewById(R.id.category_pb);
            chapterProgressTv = v.findViewById(R.id.category_progress_tv);
        }
    }

    public AlgebraAdapter(Context context, List<Chapter> chapters) {
        this.context = context;
        this.chapters = chapters;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_algebra_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Chapter chapter = chapters.get(position);
        holder.chapterTv.setText(chapter.getChapterName());
        if(chapter.getChapterIcon() != null) {
            byte[] bytes = Base64.decode(chapter.getChapterIcon(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
            if(bitmap!= null) {
                holder.chapterIconIv.setImageBitmap(bitmap);
            }
        }
        //
        holder.chapterPb.setProgress((int) chapters.get(position).getProgress());
        holder.chapterProgressTv.setText(String.valueOf((int) chapters.get(position).getProgress()));
        holder.chapterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AlgebraDetailActivity.class);
                intent.putExtra("categoryid", chapter.getId());
                intent.putExtra("categoryname", chapter.getChapterName());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapters.size();
    }
}