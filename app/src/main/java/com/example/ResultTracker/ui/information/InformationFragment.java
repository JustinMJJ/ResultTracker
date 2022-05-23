package com.example.ResultTracker.ui.information;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.ResultTracker.MainActivity;
import com.example.ResultTracker.StudentDatabaseHelper;
import com.example.assignment3.R;

import java.util.ArrayList;

public class InformationFragment extends Fragment {
    private Menu mmenu;
    private InformationViewModel informationViewModel;
    private final TextView textView[] = new TextView[10];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationViewModel = new InformationViewModel(getActivity());
//                ViewModelProviders.of(this).get(InformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_information, container, false);
        textView[0] = root.findViewById(R.id.sid);
        textView[1] = root.findViewById(R.id.sname);
        textView[2] = root.findViewById(R.id.sgender);
        textView[3] = root.findViewById(R.id.sdob);
        textView[4] = root.findViewById(R.id.enter_date);
        textView[5] = root.findViewById(R.id.facilty);
        textView[6] = root.findViewById(R.id.major);
        textView[7] = root.findViewById(R.id.degree);
        textView[8] = root.findViewById(R.id.phone);
        textView[9] = root.findViewById(R.id.email);
        informationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> s) {
                for(int i = 0; i < 10; ++i) {
                    textView[i].setText(s.get(i));
                }
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        mmenu = menu;
        menu.setGroupVisible(R.menu.submenu,true);
        menu.findItem(R.id.edit).setVisible(true);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                for(int i = 1; i < 10; ++i){
                    textView[i].setFocusableInTouchMode(true);
                    textView[i].setFocusable(true);
                }
                mmenu.findItem(R.id.save).setVisible(true);
                item.setVisible(false);
                break;
            case R.id.save:
                boolean x = true;
                for(int i = 0; i < 7; ++i){
                    if("".matches(textView[i].getText().toString())) {
                        new StudentDatabaseHelper.Notice("Details can not be empty.").show(((MainActivity)getActivity()).getSupportFragmentManager(),null);
                        x = false;
                    }
                }
                if(x) {
                    StudentDatabaseHelper sdh = new StudentDatabaseHelper(getActivity());
                    SQLiteDatabase db = null;
                    Cursor cursor = null;
                    try {
                        db = sdh.getWritableDatabase();
                        String sql = "update student set sname = '" + textView[1].getText() + "', sgender ='" + textView[2].getText() + "', sdob = '" + textView[3].getText() + "', senrollDate = '" + textView[4].getText() + "', sfacility = '" + textView[5].getText() + "', smajor = '" + textView[5].getText() + "' where sid=18047377";
                        db.execSQL(sql);
                        for(int i = 0; i < 10; ++i){
                            textView[i].setFocusableInTouchMode(false);
                            textView[i].setFocusable(false);
                        }
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                        }
                        Toast.makeText(getActivity(), "You have successfully edit and save your details.",Toast.LENGTH_LONG).show();
                        item.setVisible(false);
                        mmenu.findItem(R.id.edit).setVisible(true);
                    } catch (Exception e) {
                        Log.e("InformationFragment", "", e);
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        if (db != null) {
                            db.close();
                        }
                    }
                }
                break;
            case R.id.logout:
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isLogin", false);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}