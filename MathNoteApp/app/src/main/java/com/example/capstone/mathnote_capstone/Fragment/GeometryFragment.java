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

import com.example.capstone.mathnote_capstone.R;
import com.example.capstone.mathnote_capstone.adapter.AlgebraAdapter;
import com.example.capstone.mathnote_capstone.database.MathFormulasDao;
import com.example.capstone.mathnote_capstone.model.Category;
import com.example.capstone.mathnote_capstone.model.Grade;
import java.util.List;

public class GeometryFragment extends Fragment {

    View view;
    RecyclerView categoryRv;

    private MathFormulasDao dao = null;
    private static Grade grade = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        categoryRv = view.findViewById(R.id.recycler_view_summary2);
        categoryRv.setLayoutManager(layoutManager);

        dao = new MathFormulasDao(getContext());
        // Get chosen grade
        grade = dao.getChosenGrade();
        // Get categories by grade and division
        List<Category> categories = dao.getCategoriesByGradeAndDivision(grade.getId(), 2);
        AlgebraAdapter algebraAdapter = new AlgebraAdapter(GeometryFragment.this.getContext(), categories);
        categoryRv.addItemDecoration (
                new DividerItemDecoration(GeometryFragment.this.getContext(), LinearLayoutManager.VERTICAL)
        );
        categoryRv.setAdapter(algebraAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        grade = dao.getChosenGrade();
        List<Category> categories = dao.getCategoriesByGradeAndDivision(grade.getId(), 2);
        AlgebraAdapter adapter = new AlgebraAdapter(this.getContext(), categories);
        categoryRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
