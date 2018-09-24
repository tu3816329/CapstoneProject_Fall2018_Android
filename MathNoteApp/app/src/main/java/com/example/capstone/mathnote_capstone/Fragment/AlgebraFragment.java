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
        datatmp.add("Mệnh Đề- Tập Hợp");
        datatmp.add("Hàm Số Bậc Nhất, Bậc Hai");
        datatmp.add("Phương Trình – Hệ Phương Trình");
        datatmp.add("Bất Đẳng Thức – Bất Phương Trình");
        datatmp.add("Thống Kê");
        datatmp.add("Cung, Lượng Giác, Công Thức Lượng Giác");

        mAlgebraAdapter = new AlgebraAdapter(getContext(), datatmp);
        mListLabel.setAdapter(mAlgebraAdapter);

        return view;
    }



}
