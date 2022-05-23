package com.example.ResultTracker.ui.gpa;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import com.example.assignment3.R;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GpaFragment extends Fragment {
    private GpaViewModel gpaViewModel;
    private final TextView textView[] = new TextView[7];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        gpaViewModel = new GpaViewModel(getActivity());
//                ViewModelProviders.of(this).get(InformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gpa, container, false);
        textView[0] = root.findViewById(R.id.tgpa);
        textView[1] = root.findViewById(R.id.cgpa);
        textView[2] = root.findViewById(R.id.diff);
        textView[3] = root.findViewById(R.id.scs);
        textView[4] = root.findViewById(R.id.sc);
        textView[5] = root.findViewById(R.id.remained);
        textView[6] = root.findViewById(R.id.su);
        textView[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                cal();
            }
        });

        gpaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> s) {
                textView[0].setTag(s.get(0));
                textView[1].setText("" + (Float.parseFloat(s.get(0))/Integer.parseInt(s.get(2))));
                textView[3].setText(s.get(1));
                textView[4].setText(s.get(2));
                textView[5].setText("" + Math.abs(Integer.parseInt(s.get(2)) - Integer.parseInt(s.get(1))));
            }
        });
        return root;
    }

    void cal(){
        if("".matches(textView[0].getText().toString())) return;
        String ss = "";
        textView[2].setText(Math.abs(Integer.parseInt(textView[0].getText().toString()) - Float.parseFloat(textView[1].getText().toString())) + "");
        String s = "";
        float x = Float.parseFloat(textView[3].getText().toString()) * Float.parseFloat(textView[0].getText().toString()) - Float.parseFloat((String)textView[0].getTag());
        x /= Float.parseFloat(textView[5].getText().toString());
        if(x > 9.0) s = "Unfortunately, your target seems unachievable, please choose another targets.";
        else {
            int j = Math.round(x) + 1;
            if(j >= 10) j = 9;
            String[] m = {"D (40-49.99)", "C- (50-54.99)", "C (55-59.99)", "C+ (60-64.99)", "B- (65-69.99)", "B (70-74.99)", "B+ (75-79.99)", "A- (80-84.99)", "A (85-89.99)", "A+ (90-100)"};
            s = "In order to achieve your target GPA, you have to get a GPA at " + new DecimalFormat("###,###,###.##").format(x) + " in average for the remaining " +
                textView[5].getText() + " courses. It means you are required to get a minimum mark level at " + m[j-1] + " or " + m[j] + " for each remaining course.";
        }
        textView[6].setText(s);
    }
}