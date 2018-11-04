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
import com.example.capstone.mathnote_capstone.model.Mathform;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.List;

public class MathformListAdapter extends RecyclerView.Adapter<MathformListAdapter.RecyclerHolder> {

    private Context context;
    private List<Mathform> mathforms;

    public MathformListAdapter(Context context, List<Mathform> mathforms) {
        this.context = context;
        this.mathforms = mathforms;
    }

    @Override
    public MathformListAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mathform_list_item, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(final MathformListAdapter.RecyclerHolder holder, int position) {
        Mathform mathform = mathforms.get(position);
        MathFormulasDao dao = new MathFormulasDao(context);
        List<Exercise> exercises = dao.getExercisesByMathform(mathform.getId());
        // WebView data
        StringBuilder data = new StringBuilder(AppUtils.MATHJAX1 + mathform.getMathformContent());

        if(!exercises.isEmpty()) {
            data.append("<h4 style=\"color: #6508a8\">Bài tập thực hành</h4>");
            int seq = 1;
            for(Exercise exercise : exercises) {
                data.append("<p style=\"font-weight: bold\">Bài " + (seq++));
                data.append(exercise.getTopic().substring(3));
                data.append("<p>Đáp án: " + exercise.getAnswer().substring(3) + "<br>");
            }
        }
        data.append(AppUtils.MATHJAX2);
        holder.webView.loadDataWithBaseURL(null, data.toString(), "text/html",
                "utf-8", "");
        holder.webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
        //
        holder.mathformTitleTv.setText(mathform.getMathformTitle());
        holder.exerciseNumTv.setText(exercises.size() + " bài tập");
        holder.mathformItemHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.webView.getVisibility() == View.GONE) {
                    holder.webView.setVisibility(View.VISIBLE);
                    holder.mfIndicatorTv.setImageResource(R.drawable.ic_arrow_down);
                } else if (holder.webView.getVisibility() == View.VISIBLE) {
                    holder.webView.setVisibility(View.GONE);
                    holder.mfIndicatorTv.setImageResource(R.drawable.ic_arrow_right);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mathforms.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        private RelativeLayout mathformItemHeader;
        private TextView mathformTitleTv, exerciseNumTv;
        private ImageView mfIndicatorTv;
        private WebView webView;

        RecyclerHolder(View itemView) {
            super(itemView);
            mathformItemHeader = itemView.findViewById(R.id.mathform_item_header);
            mathformTitleTv = itemView.findViewById(R.id.mathform_title_tv);
            exerciseNumTv = itemView.findViewById(R.id.exercise_num_tv);
            mfIndicatorTv = itemView.findViewById(R.id.mf_indicator_iv);
            webView = itemView.findViewById(R.id.mathform_detail_wv);
        }
    }
}
