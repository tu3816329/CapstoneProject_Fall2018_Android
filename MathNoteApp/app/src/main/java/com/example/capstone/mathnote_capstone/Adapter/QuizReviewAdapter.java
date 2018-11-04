package com.example.capstone.mathnote_capstone.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.example.capstone.mathnote_capstone.model.Question;
import com.example.capstone.mathnote_capstone.model.QuestionChoice;
import com.example.capstone.mathnote_capstone.model.UserChoice;
import com.example.capstone.mathnote_capstone.utils.AppUtils;

import java.util.List;

public class QuizReviewAdapter extends RecyclerView.Adapter<QuizReviewAdapter.RecyclerHolder> {

    private Context context;
    private List<UserChoice> userChoices;

    public QuizReviewAdapter(Context context, List<UserChoice> userChoices) {
        this.context = context;
        this.userChoices = userChoices;
    }

    @Override
    public QuizReviewAdapter.RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.quiz_review_item, parent, false);
        return new RecyclerHolder(v);
    }

    @Override
    public void onBindViewHolder(QuizReviewAdapter.RecyclerHolder holder, int position) {
        UserChoice userChoice = userChoices.get(position);
        Question question = userChoice.getQuestion();
        MathFormulasDao dao = new MathFormulasDao(context);
        List<QuestionChoice> choices = dao.getQuestionChoices(question);
        String content = AppUtils.MATHJAX1 + question.getContent();
        boolean userCorrect = false;
        for (int i = 0; i < choices.size(); i++) {
            QuestionChoice choice = choices.get(i);
            if (choice.isCorrect()) {
                userCorrect = choice.getId() == userChoice.getChoice().getId() || userCorrect;
                content += "<p style=\"color: #25db46\">" + AppUtils.alphas[i] + choice.getContent().substring(3);
            } else {
                if (choice.getId() == userChoice.getChoice().getId()) {
                    content += "<p style=\"color: #ff2e00\">" + AppUtils.alphas[i] + choice.getContent().substring(3);
                } else {
                    content += "<p>" + AppUtils.alphas[i] + choice.getContent().substring(3);
                }
            }
        }
        content += AppUtils.MATHJAX2;
        holder.webView.loadDataWithBaseURL(null, content, "text/html",
                "utf-8", "");
        holder.webView.loadUrl("javascript:MathJax.Hub.Queue(['Typeset',MathJax.Hub]);");
        if (userCorrect) {
            holder.isCorrectContainer.setBackgroundColor(Color.parseColor("#25db46"));
            holder.isCorrectIv.setBackgroundResource(R.drawable.ic_check);
            holder.isCorrectTv.setText("Đúng");
        } else {
            holder.isCorrectContainer.setBackgroundColor(Color.parseColor("#ff2e00"));
            holder.isCorrectIv.setBackgroundResource(R.drawable.ic_close_white);
            holder.isCorrectTv.setText("Sai");
        }
    }

    @Override
    public int getItemCount() {
        return userChoices.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        private WebView webView;
        private RelativeLayout isCorrectContainer;
        private ImageView isCorrectIv;
        private TextView isCorrectTv;

        public RecyclerHolder(View itemView) {
            super(itemView);
            isCorrectContainer = itemView.findViewById(R.id.is_correct_container);
            isCorrectIv = itemView.findViewById(R.id.is_correct_iv);
            isCorrectTv = itemView.findViewById(R.id.is_correct_tv);
            webView = itemView.findViewById(R.id.quiz_review_wv);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);
        }
    }
}
