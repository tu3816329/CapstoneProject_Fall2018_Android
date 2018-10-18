package com.example.capstone.mathnote_capstone.Adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.capstone.mathnote_capstone.R;
import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class AlgebraDetailAdapter extends RecyclerView.Adapter<AlgebraDetailAdapter.RecyclerHolder> {
    private static WebView myWebView;
    private final List<Object> list = new ArrayList<>();

    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();

    public AlgebraDetailAdapter() {
        expansionsCollection.openOnlyOne(false);

    }

    @Override
    public AlgebraDetailAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AlgebraDetailAdapter.RecyclerHolder.buildFor(parent);
    }

    @Override
    public void onBindViewHolder(AlgebraDetailAdapter.RecyclerHolder holder, int position) {
        holder.bind(list.get(position));

        expansionsCollection.add(holder.getExpansionLayout());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItems(List<Object> items) {
        this.list.addAll(items);
        notifyDataSetChanged();
    }

    public final static class RecyclerHolder extends RecyclerView.ViewHolder {

        private static final int LAYOUT = R.layout.list_detail_algebra_item;

        @BindView(R.id.expansionLayout)
        ExpansionLayout expansionLayout;

        public static AlgebraDetailAdapter.RecyclerHolder buildFor(ViewGroup viewGroup) {
            return new AlgebraDetailAdapter.RecyclerHolder(LayoutInflater.from(viewGroup.getContext()).inflate(LAYOUT, viewGroup, false));
        }

        public RecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            myWebView = itemView.findViewById(R.id.webview1);
            myWebView.getSettings().setJavaScriptEnabled(true);
            final String path = Uri.parse("file:///android_asset/index.html").toString();

            myWebView.loadUrl(path);


        }

        public void bind(Object object) {
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