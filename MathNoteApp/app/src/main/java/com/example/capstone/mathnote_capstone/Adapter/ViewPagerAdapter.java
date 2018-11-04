package com.example.capstone.mathnote_capstone.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.capstone.mathnote_capstone.fragment.AlgebraFragment;
import com.example.capstone.mathnote_capstone.fragment.GeometryFragment;
import com.example.capstone.mathnote_capstone.model.Division;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private List<Division> divisions;

    public ViewPagerAdapter(FragmentManager fm, List<Division> divisions) {
        super(fm);
        this.divisions = divisions;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (divisions.get(position).getId()) {
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
        return divisions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return divisions.get(position).getDivisionName();
    }
}
