package com.example.capstone.mathnote_capstone.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.activity.WebviewActivity;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;


public final class AlgebraDetailAdapter extends RecyclerView.Adapter<AlgebraDetailAdapter.RecyclerHolder> {
    private final List<Lesson> lessons = new ArrayList<>();

    private Context context;

    public AlgebraDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public AlgebraDetailAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AlgebraDetailAdapter.RecyclerHolder.buildFor(parent);
    }

    @Override
    public void onBindViewHolder(final AlgebraDetailAdapter.RecyclerHolder holder, final int position) {
        final int lessonId = lessons.get(position).getId();
        final String lessonTitle = lessons.get(position).getLessonTitle();
        final String lessonContent = lessons.get(position).getLessonContent();

        final MathFormulasDao dao = new MathFormulasDao(context);
        if(lessons.get(position).isFinished() == 0 || dao.getQuestionsByLesson(lessonId).size() == 0) {
            holder.lessonScorePbBackground.setVisibility(View.INVISIBLE);
            holder.lessonScorePb.setVisibility(View.INVISIBLE);
            holder.lessonScoreTv.setVisibility(View.INVISIBLE);
        } else {
            int score = dao.getQuizScore(lessonId);
            holder.lessonScorePb.setProgress(score);
            holder.lessonScoreTv.setText(String.valueOf(score));
        }

        String lessonDetail = dao.getMathformByLesson(lessonId).size() + " dạng bài, ";
        lessonDetail += dao.getQuestionsByLesson(lessonId).size() + " câu hỏi";
        holder.mathformNumTv.setText(lessonDetail);
        holder.lessonTitleTv.setText(lessonTitle);
        final String data = AppUtils.MATHJAX1 + lessonContent + AppUtils.MATHJAX2;
        final Activity activity = (Activity) context;
        holder.lessonDetailIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("lesson", lessons.get(position));
                activity.startActivityForResult(intent, 3);
                activity.overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        holder.lessonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.expandBodyLayout.getVisibility() == View.VISIBLE) {
                    holder.lessonItem.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                    holder.expandBodyLayout.setVisibility(View.GONE);
                } else if (holder.expandBodyLayout.getVisibility() == View.GONE) {
                    if(!holder.isLoaded) {
                        holder.isLoaded = true;
                        holder.webView.loadDataWithBaseURL(null, data, "text/html",
                                "utf-8", "");
                        holder.webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                    }
                    holder.lessonItem.setBackgroundColor(context.getResources().getColor(R.color.lightGray));
                    holder.expandBodyLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // Set favorite icon
        final boolean isFavorite = lessons.get(position).isFavorite();
        if (isFavorite) {
            holder.favoriteIb.setVisibility(View.VISIBLE);
            holder.unfavoriteIb.setVisibility(View.GONE);
        }
        // Click favorite icon
        holder.favoriteIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.setFavoriteLesson(lessonId, false);
                view.setVisibility(View.GONE);
                holder.unfavoriteIb.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
        holder.unfavoriteIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao.setFavoriteLesson(lessonId, true);
                view.setVisibility(View.GONE);
                holder.favoriteIb.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons.addAll(lessons);
        notifyDataSetChanged();
    }

    static class RecyclerHolder extends RecyclerView.ViewHolder {
        private boolean isLoaded;
        private static final int LAYOUT = R.layout.list_detail_algebra_item;
        private ImageButton unfavoriteIb, favoriteIb, lessonDetailIb;
        private LinearLayout expandBodyLayout;
        private WebView webView;
        private TextView lessonTitleTv;
        private RelativeLayout lessonItem;
        private TextView mathformNumTv, lessonScoreTv;
        ProgressBar lessonScorePb, lessonScorePbBackground;

        private static AlgebraDetailAdapter.RecyclerHolder buildFor(ViewGroup viewGroup) {
            return new AlgebraDetailAdapter.RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        private RecyclerHolder(View itemView) {
            super(itemView);
            isLoaded = false;
            lessonScorePbBackground = itemView.findViewById(R.id.lesson_score_pb_bg);
            lessonScoreTv = itemView.findViewById(R.id.lesson_score_tv);
            lessonScorePb = itemView.findViewById(R.id.lesson_score_pb);
            mathformNumTv = itemView.findViewById(R.id.mathforms_num);
            lessonItem = itemView.findViewById(R.id.lesson_item_rl);
            expandBodyLayout = itemView.findViewById(R.id.expand_body_ll);
            lessonTitleTv = itemView.findViewById(R.id.lesson_title_tv);
            lessonDetailIb = itemView.findViewById(R.id.lesson_detail_ib);
            unfavoriteIb = itemView.findViewById(R.id.unfavorite_ib);
            favoriteIb = itemView.findViewById(R.id.favorite_ib);
            //
            webView = itemView.findViewById(R.id.expand_webview);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
        }
    }
}