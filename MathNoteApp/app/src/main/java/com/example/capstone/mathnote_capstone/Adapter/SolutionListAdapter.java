package com.example.capstone.mathnote_capstone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Exercise;
import com.example.capstone.mathnote_capstone.model.Solution;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.List;

public class SolutionListAdapter extends RecyclerView.Adapter<SolutionListAdapter.RecyclerHolder> {

    private Context context;
    private List<Solution> solutions;

    public SolutionListAdapter(Context context, List<Solution> solutions) {
        this.context = context;
        this.solutions = solutions;
    }

    @Override
    public SolutionListAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mathform_list_item, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(final SolutionListAdapter.RecyclerHolder holder, int position) {
        Solution solution = solutions.get(position);
        MathFormulasDao dao = new MathFormulasDao(context);
        List<Exercise> exercises = dao.getExercisesBySolution(solution.getId());
        // WebView data
        final StringBuilder data = new StringBuilder(AppUtils.MATHJAX1 + solution.getContent());

        if(!exercises.isEmpty()) {
            data.append("<h4 style=\"color: #6508a8\">Bài tập thực hành</h4>");
            for(Exercise exercise : exercises) {
                data.append("<p style=\"font-weight: bold\">" + exercise.getTopic() + "</p>");
                data.append("<p>Đáp án: " + exercise.getAnswer() + "</p><br>");
            }
        }
        data.append(AppUtils.MATHJAX2);
        //
        holder.solutionTitleTv.setText(solution.getTitle());
        holder.exerciseNumTv.setText(exercises.size() + " bài tập");
        holder.solutionItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.webView.getVisibility() == View.GONE) {
                    if(!holder.isLoaded) {
                        holder.isLoaded = true;
                        holder.webView.loadDataWithBaseURL(null, data.toString(), "text/html",
                                "utf-8", "");
                        holder.webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
                    }
                    holder.webView.setVisibility(View.VISIBLE);
                    holder.mfIndicatorTv.setImageResource(R.drawable.ic_arrow_up);
                } else if (holder.webView.getVisibility() == View.VISIBLE) {
                    holder.webView.setVisibility(View.GONE);
                    holder.mfIndicatorTv.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return solutions.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        private boolean isLoaded;
        private RelativeLayout solutionItemHeader;
        private TextView solutionTitleTv, exerciseNumTv;
        private ImageView mfIndicatorTv;
        private WebView webView;

        RecyclerHolder(View itemView) {
            super(itemView);
            isLoaded = false;
            solutionItemHeader = itemView.findViewById(R.id.solution_item_header);
            solutionTitleTv = itemView.findViewById(R.id.solution_title_tv);
            exerciseNumTv = itemView.findViewById(R.id.exercise_num_tv);
            mfIndicatorTv = itemView.findViewById(R.id.solution_indicator_iv);
            webView = itemView.findViewById(R.id.solution_detail_wv);
            // Web view configuration
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
//            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//            webView.getSettings().setBuiltInZoomControls(true);
//            webView.getSettings().setDisplayZoomControls(false);
        }
    }
}
