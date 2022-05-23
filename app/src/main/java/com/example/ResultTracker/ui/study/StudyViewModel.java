package com.example.ResultTracker.ui.study;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ResultTracker.StudentDatabaseHelper;

import java.util.ArrayList;

public class StudyViewModel extends ViewModel {
    private MutableLiveData<StudyDatabaseAdapter> adpter;

    public StudyViewModel(Context context) {
        adpter = new MutableLiveData<>();
        StudentDatabaseHelper sdh = new StudentDatabaseHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String x = sp.getString("StudentID", "");
        String sql = "select c.cid, c.cname, c.ccredit,g.grade from course c join grade g on c.cid = g.cid where g.sid = " + Integer.parseInt(x) + " order by c.cid asc";

        try {
            db = sdh.getReadableDatabase();
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                ArrayList<String[]>  tal = new ArrayList<String[]>();
                while (cursor.moveToNext()) {
                    String str[] = new String[4];
                    str[0] = cursor.getString(cursor.getColumnIndex("cid"));
                    str[1] = cursor.getString(cursor.getColumnIndex("cname"));
                    str[2] = cursor.getString(cursor.getColumnIndex("ccredit"));
                    str[3] = cursor.getString(cursor.getColumnIndex("grade"));
                    if(Integer.parseInt(str[3]) >= 90) str[3] = "A+";
                    else if(Integer.parseInt(str[3]) >= 85 && Integer.parseInt(str[3]) <= 89) str[3] = "A";
                    else if(Integer.parseInt(str[3]) >= 80 && Integer.parseInt(str[3]) <= 84) str[3] = "A-";
                    else if(Integer.parseInt(str[3]) >= 75 && Integer.parseInt(str[3]) <= 79) str[3] = "B+";
                    else if(Integer.parseInt(str[3]) >= 70 && Integer.parseInt(str[3]) <= 74) str[3] = "B";
                    else if(Integer.parseInt(str[3]) >= 65 && Integer.parseInt(str[3]) <= 69) str[3] = "B-";
                    else if(Integer.parseInt(str[3]) >= 60 && Integer.parseInt(str[3]) <= 64) str[3] = "C+";
                    else if(Integer.parseInt(str[3]) >= 55 && Integer.parseInt(str[3]) <= 59) str[3] = "C";
                    else if(Integer.parseInt(str[3]) >= 50 && Integer.parseInt(str[3]) <= 54) str[3] = "C-";
                    else str[3] = "D";
                    tal.add(str);
                }
                StudyDatabaseAdapter sda = new StudyDatabaseAdapter(context, tal);
                adpter.setValue(sda);
            }
        }
        catch (Exception e) {
            Log.e("StudentViewModel", "", e);
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

    public LiveData<StudyDatabaseAdapter> getAdpter() {
        return adpter;
    }
}