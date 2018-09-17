package com.example.capstone.mathnote_capstone.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstone.mathnote_capstone.Adapter.AlgebraAdapter;
import com.example.capstone.mathnote_capstone.R;

import java.util.ArrayList;

public class AlgebraFragment extends Fragment {

    private RecyclerView mListLabel;
    private AlgebraAdapter mAlgebraAdapter;

    View view;
    public AlgebraFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container,false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListLabel = view.findViewById(R.id.recycler_view_summary);
        mListLabel.setLayoutManager(layoutManager);

        ArrayList<String> datatmp = new ArrayList<>();
        datatmp.add("Số nguyên tố");
        datatmp.add("Bất đẳng thức");
        datatmp.add("Thập phân");
        datatmp.add("Phân số");
        datatmp.add("Bất đẳng thức");

        mAlgebraAdapter = new AlgebraAdapter(getContext(), datatmp);
        mListLabel.setAdapter(mAlgebraAdapter);

        return view;
    }



}
