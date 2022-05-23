package com.example.ResultTracker.ui.courses;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ResultTracker.StudentDatabaseHelper;

import java.util.ArrayList;

public class CourseViewModel extends ViewModel {
    private MutableLiveData<CourseDatabaseAdapter> adpter;

    public CourseViewModel(Context context) {
        adpter = new MutableLiveData<>();
        StudentDatabaseHelper sdh = new StudentDatabaseHelper(context);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select cid, cname, clecturer, ccredit from course order by cid asc";

        try {
            db = sdh.getReadableDatabase();
            cursor = db.rawQuery(sql,null);
            if (cursor.getCount() > 0) {
                ArrayList<String[]> tal = new ArrayList<String[]>();
                while (cursor.moveToNext()) {
                    String str[] = new String[4];
                    str[0] = cursor.getString(cursor.getColumnIndex("cid"));
                    str[1] = cursor.getString(cursor.getColumnIndex("cname"));
                    str[2] = cursor.getString(cursor.getColumnIndex("clecturer"));
                    str[3] = cursor.getString(cursor.getColumnIndex("ccredit"));
                    tal.add(str);
                }
                CourseDatabaseAdapter cda = new CourseDatabaseAdapter(context, tal);
                adpter.setValue(cda);
            }
        }
        catch (Exception e) {
            Log.e("CourseViewModel", "", e);
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

    public LiveData<CourseDatabaseAdapter> getAdpter() {
        return adpter;
    }
}