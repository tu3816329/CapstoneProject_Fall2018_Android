package com.example.capstone.mathnote_capstone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.model.Lesson;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Lesson> lessons;
    private List<Lesson> searchResults;

    public SearchListAdapter(Context context, List<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
        inflater = LayoutInflater.from(context);
        this.searchResults = new ArrayList<>();
        this.searchResults.addAll(lessons);
    }

    @Override
    public int getCount() {
        return lessons.size();
    }

    @Override
    public Object getItem(int i) {
        return lessons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        TextView nameTv;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.search_listview_item, null);
            holder.nameTv = view.findViewById(R.id.search_title_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.nameTv.setText(lessons.get(i).getLessonTitle());
        return view;
    }

    public void filter(String text) {
        text = text.toLowerCase().trim();
        lessons.clear();
        if (text.length() != 0) {
            for (Lesson lesson : searchResults) {
                if(lesson.getLessonTitle().toLowerCase().contains(text)) {
                    lessons.add(lesson);
                }
            }
        }
        notifyDataSetChanged();
    }
}
