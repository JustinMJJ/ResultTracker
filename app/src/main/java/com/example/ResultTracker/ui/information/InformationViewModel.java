package com.example.ResultTracker.ui.information;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ResultTracker.StudentDatabaseHelper;

import java.util.ArrayList;

public class InformationViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> mText;

    public InformationViewModel(Context context) {
        StudentDatabaseHelper sdh = new StudentDatabaseHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String x = sp.getString("StudentID", "");
        String sql = "select * from student where sid = " + Integer.parseInt(x);
        mText = new MutableLiveData<>();
        try {
            db = sdh.getReadableDatabase();
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                ArrayList<String> al = new ArrayList<>();
                while (cursor.moveToNext()) {
                    al.add(cursor.getString(cursor.getColumnIndex("sid")));
                    al.add(cursor.getString(cursor.getColumnIndex("sname")));
                    al.add(cursor.getString(cursor.getColumnIndex("sgender")));
                    al.add(cursor.getString(cursor.getColumnIndex("sdob")));
                    al.add(cursor.getString(cursor.getColumnIndex("senrollDate")));
                    al.add(cursor.getString(cursor.getColumnIndex("sfacility")));
                    al.add(cursor.getString(cursor.getColumnIndex("smajor")));
                    al.add(cursor.getString(cursor.getColumnIndex("sdegree")));
                    al.add(cursor.getString(cursor.getColumnIndex("sphone")));
                    al.add(cursor.getString(cursor.getColumnIndex("semail")));
                }
                mText.setValue(al);
            }
        }
        catch (Exception e) {
            Log.e("PersonalViewModel", "", e);
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public MutableLiveData<ArrayList<String>> getText() {
        return mText;
    }
}