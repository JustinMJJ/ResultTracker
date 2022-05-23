package com.example.ResultTracker.ui.gpa;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ResultTracker.StudentDatabaseHelper;

import java.util.ArrayList;

public class GpaViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> mText;

    public GpaViewModel(Context context) {
        StudentDatabaseHelper sdh = new StudentDatabaseHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SharedPreferences sp = context.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String x = sp.getString("StudentID", "");
        String sql = "select count(cid) as 'studied courses',s.sdegree as 'total courses', sum(g.gpa) as 'current total gpa' from grade g join student s on s.sid = g.sid where g.sid = " + Integer.parseInt(x);
        mText = new MutableLiveData<>();
        try {
            db = sdh.getReadableDatabase();
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                ArrayList<String> al = new ArrayList<>();
                while (cursor.moveToNext()) {
                    al.add("" + cursor.getFloat(cursor.getColumnIndex("current total gpa")));
                    if("Bachelor".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("24");
                    else if("Diploma".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("16");
                    else if("Certificate".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("8");
                    else if("Graduate Diplomas".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("8");
                    else if("Postgraduate Diplomas".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("8");
                    else if("Postgraduate Certificates".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("8");
                    else if("Graduate Certificates".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("8");
                    else if("Masters".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("16");
                    else if("Bachelor Honours".matches(cursor.getString(cursor.getColumnIndex("total courses")))) al.add("32");
                    else al.add("4");
                    al.add(cursor.getString(cursor.getColumnIndex("studied courses")));
                }
                mText.setValue(al);
            }
        }
        catch (Exception e) {
            Log.e("GPAViewModel", "", e);
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