package com.example.capstone.mathnote_capstone.activity;

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
    private ViewPager mSliderViewPager;
    private LinearLayout mDotLayout;

    private SliderAdapter sliderAdapter;
    private int mCurrentSlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        mSliderViewPager = findViewById(R.id.slideViewPager);
        mDotLayout = findViewById(R.id.dotsLayout);

        Button btnNext = findViewById(R.id.btnNext);
        Button btnSkip = findViewById(R.id.btnSkip);

        sliderAdapter = new SliderAdapter(this);
        mSliderViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        mSliderViewPager.addOnPageChangeListener(viewListener);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSliderViewPager.setCurrentItem(mCurrentSlide + 1);
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InstructionActivity.this, GradeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void addDotsIndicator(int position) {
        TextView[] mDots = new TextView[sliderAdapter.slide_images.length];
        mDotLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
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

            mCurrentSlide = position;
//            if (position==0){
//                btnNext.setEnabled(true);
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

}
