package com.example.ResultTracker.ui.courses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.example.assignment3.R;

public class CourseFragment extends Fragment {
    private ListView ll;
    private CourseViewModel courseViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        courseViewModel = new CourseViewModel(getActivity());
//                ViewModelProviders.of(this).get(CourseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_course, container, false);
        ll = root.findViewById(R.id.courseli);
        courseViewModel.getAdpter().observe(getViewLifecycleOwner(), new Observer<CourseDatabaseAdapter>() {
            @Override
            public void onChanged(@Nullable CourseDatabaseAdapter cda) {
                ll.setAdapter(cda);
            }
        });
        return root;
    }
}