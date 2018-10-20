package com.example.capstone.mathnote_capstone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstone.mathnote_capstone.adapter.AlgebraAdapter;
import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Category;
import com.example.capstone.mathnote_capstone.model.Grade;
import java.util.List;

public class AlgebraFragment extends Fragment {

    View view;

    public AlgebraFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView categoryRv = view.findViewById(R.id.recycler_view_summary);
        categoryRv.setLayoutManager(layoutManager);

        MathFormulasDao dao = new MathFormulasDao(getContext());
        // Get chosen grade
        Grade grade = dao.getChosenGrade();
        // Get categories by grade and division
        List<Category> categories = dao.getCategoriesByGradeAndDivision(grade.getId(), 1);
        AlgebraAdapter algebraAdapter = new AlgebraAdapter(AlgebraFragment.this.getContext(), categories);
        categoryRv.addItemDecoration(
                new DividerItemDecoration(AlgebraFragment.this.getContext(), LinearLayoutManager.VERTICAL)
        );
        categoryRv.setAdapter(algebraAdapter);
        return view;
    }
}
