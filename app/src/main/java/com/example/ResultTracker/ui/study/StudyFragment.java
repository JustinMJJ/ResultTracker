package com.example.ResultTracker.ui.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.example.assignment3.R;

public class StudyFragment extends Fragment {
    private ListView list;
    private StudyViewModel studyViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        studyViewModel = new StudyViewModel(getActivity());
//                ViewModelProviders.of(this).get(StudyViewModel.class);
        View root = inflater.inflate(R.layout.fragment_study, container, false);
        list = root.findViewById(R.id.list);
        studyViewModel.getAdpter().observe(getViewLifecycleOwner(), new Observer<StudyDatabaseAdapter>() {
            @Override
            public void onChanged(@Nullable StudyDatabaseAdapter sda) {
                list.setAdapter(sda);
            }
        });
        list.setOnItemClickListener((adapterView, view, i, l)->{
            Intent intent = new Intent(getActivity(), ShowScoreActivity.class);
            TextView temp = (TextView)((StudyDatabaseAdapter.ViewHolder)view.getTag()).id;
            intent.putExtra("cid", temp.getText().toString());
            startActivity(intent);
        });
        return root;
    }
}