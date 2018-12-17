package com.example.capstone.mathnote_capstone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.capstone.mathnote_capstone.fragment.AlgebraFragment;
import com.example.capstone.mathnote_capstone.fragment.GeometryFragment;
import com.example.capstone.mathnote_capstone.model.Subject;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Subject> subjects;

    public ViewPagerAdapter(FragmentManager fm, List<Subject> subjects) {
        super(fm);
        this.subjects = subjects;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (subjects.get(position).getId()) {
            case 1 :
                fragment = new AlgebraFragment();
                break;
            case 2:
                fragment = new GeometryFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return subjects.get(position).getSubjectName();
    }
}
