package com.example.capstone.mathnote_capstone.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.mathnote_capstone.adapter.SliderAdapter;
import com.example.capstone.mathnote_capstone.R;

public class InstructionActivity extends AppCompatActivity {
    private ViewPager sliderViewPager;
    private LinearLayout dotLayout;

    private SliderAdapter sliderAdapter;
    private int currentSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        sliderViewPager = findViewById(R.id.slideViewPager);
        dotLayout = findViewById(R.id.dotsLayout);

        Button btnNext = findViewById(R.id.btnNext);
        Button btnSkip = findViewById(R.id.btnSkip);

        sliderAdapter = new SliderAdapter(this);
        sliderViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        sliderViewPager.addOnPageChangeListener(viewListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sliderViewPager.setCurrentItem(currentSlide + 1);
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String previousActivity = getIntent().getStringExtra("activity");
                if (previousActivity.equals("progress")) {
                    Intent intent = new Intent(InstructionActivity.this, GradeActivity.class);
                    intent.putExtra("activity", "instruction");
                    startActivity(intent);
                } else if (previousActivity.equals("main")) {
                    setResult(Activity.RESULT_OK);
                    finish();
                }
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
    }

    public void addDotsIndicator(int position) {
        TextView[] mDots = new TextView[sliderAdapter.slide_images.length];
        dotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            dotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryLight));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);

            currentSlide = position;
//            if (position==0){
//                btnNext.setEnabled(true);
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onBackPressed() {
    }
}