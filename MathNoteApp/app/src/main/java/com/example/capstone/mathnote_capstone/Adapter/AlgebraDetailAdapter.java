package com.example.capstone.mathnote_capstone.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.activity.WebviewActivity;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Lesson;
import com.example.capstone.mathnote_capstone.utils.AppUtils;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class AlgebraDetailAdapter extends RecyclerView.Adapter<AlgebraDetailAdapter.RecyclerHolder> {
    private static WebView webView;
    private static TextView lessonTitleTv;
    private final List<Lesson> lessons = new ArrayList<>();

    private Context context;
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

    public AlgebraDetailAdapter(Context context) {
        expansionsCollection.openOnlyOne(false);
        this.context = context;
    }

    @Override
    public AlgebraDetailAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AlgebraDetailAdapter.RecyclerHolder.buildFor(parent);
    }

    @Override
    public void onBindViewHolder(AlgebraDetailAdapter.RecyclerHolder holder, final int position) {
        final int lessonId = lessons.get(position).getId();
        final String lessonTitle = lessons.get(position).getLessonTitle();
        final String lessonContent = lessons.get(position).getLessonContent();

        holder.bind(lessons.get(position));
        expansionsCollection.add(holder.getExpansionLayout());
        lessonTitleTv.setText(lessonTitle);
        String data = AppUtils.MATHJAX1 + lessonContent + AppUtils.MATHJAX2;
        webView.loadDataWithBaseURL(null, data, "text/html",
                "utf-8", "");
        webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");

        holder.lessonDetailIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebviewActivity.class);
                intent.putExtra("lessonid", lessonId);
                intent.putExtra("lessontitle", lessonTitle);
                intent.putExtra("lessoncontent", lessonContent);
                context.startActivity(intent);
            }
        });

        // Set favorite icon
        final MathFormulasDao dao = new MathFormulasDao(context);
        final boolean isFavorite = lessons.get(position).isFavorite();
        if (isFavorite) {
            holder.favoriteIv.setBackgroundResource(R.drawable.ic_favorites_white);
        }
        // Click favorite icon
        holder.favoriteIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = dao.isFavoriteLesson(lessonId);
                if(isFavorite) {
                    dao.removeFavoriteLesson(lessonId);
                    view.setBackgroundResource(R.drawable.ic_favorite_border_white);
                    Toast.makeText(context, "Đã xoá khỏi yêu thích", Toast.LENGTH_SHORT).show();
                } else {
                    dao.addFavoriteLesson(lessonId);
                    view.setBackgroundResource(R.drawable.ic_favorites_white);
                    Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                }
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

    public final static class RecyclerHolder extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.list_detail_algebra_item;
        private ImageView lessonDetailIv;
        private ImageView favoriteIv;

        @BindView(R.id.expansionLayout)
        ExpansionLayout expansionLayout;

        private static AlgebraDetailAdapter.RecyclerHolder buildFor(ViewGroup viewGroup) {
            return new AlgebraDetailAdapter.RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        private RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            lessonTitleTv = itemView.findViewById(R.id.tvLessonTitle);
            lessonDetailIv = itemView.findViewById(R.id.ivViewDetail);
            favoriteIv = itemView.findViewById(R.id.ivFavorite);
            webView = itemView.findViewById(R.id.webview1);
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
        }

        private void bind(Object object) {
            expansionLayout.collapse(false);
        }

        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }
}
//public class AlgebraDetailAdapter extends RecyclerView.Adapter<AlgebraDetailAdapter.ViewHolder> {
//
//    private Context mContext;
//    private LayoutInflater mLayoutInflater;
//    private ArrayList<String> mLabels;
//    private final List<Object> list = new ArrayList<>();
//    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();
//    private static WebView myWebView;
//    public AlgebraDetailAdapter() {
//        expansionsCollection.openOnlyOne(false);
//
//    }
//
//        public AlgebraDetailAdapter(Context mContext, ArrayList<String> mLabels) {
//        this.mContext = mContext;
//        this.mLabels = mLabels;
//        mLayoutInflater = LayoutInflater.from(mContext);
//    }
////    @Override
////    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        return ViewHolder.buildFor(parent);
////    }
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mLayoutInflater.inflate(R.layout.list_detail_algebra_item, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.bind(list.get(position));
//        expansionsCollection.add(holder.getExpansionLayout());
//
//    }
//
//    //    @Override
////    public void onBindViewHolder(ViewHolder holder, int position) {
////        final String title = mLabels.get(position);
////        holder.algebraDetailTitle.setText(title);
////        holder.item.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(mContext, WebviewActivity.class);
////                intent.putExtra("AlgebraDetailTitle", title);
////                mContext.startActivity(intent);
////            }
////        });
////    }
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void setItems(List<Object> items) {
//        this.list.addAll(items);
//        notifyDataSetChanged();
//    }
////    @Override
////    public int getItemCount() {
////        return mLabels.size();
////    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private static final int LAYOUT = R.layout.list_detail_algebra_item;
//        @BindView(R.id.expansionLayout)
//        ExpansionLayout expansionLayout;
//
//        View item;
//        TextView algebraDetailTitle;
//        public static ViewHolder buildFor(ViewGroup viewGroup) {
//            return new ViewHolder (LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
//        }
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            ButterKnife.bind(this, itemView);
//            item = itemView;
//            //algebraDetailTitle = itemView.findViewById(R.id.algebraDetailTitle);
//
//            myWebView = itemView.findViewById(R.id.webview);
//            myWebView.getSettings().setJavaScriptEnabled(true);
//            final String path = Uri.parse("file:///android_asset/index.html").toString();
//
//            myWebView.loadUrl(path);
//
//        }
//        public void bind(Object object) {
//            expansionLayout.collapse(false);
//        }
//
//        public ExpansionLayout getExpansionLayout() {
//            return expansionLayout;
//        }
//        @Override
//        public void onClick(View view) {
//
//        }
//    }
//}