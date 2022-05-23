package com.example.ResultTracker.ui.study;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ResultTracker.StudentDatabaseHelper;
import com.example.assignment3.R;

public class ShowScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        this.setTitle("Coruse Mark");
        View v = findViewById(R.id.score_view);
        TextView[] tv = {findViewById(R.id._dcid),findViewById(R.id._dcname),findViewById(R.id._dcl),
                        findViewById(R.id._dmark),findViewById(R.id._dccr),findViewById(R.id._dcgrade)};
        StudentDatabaseHelper sdh = new StudentDatabaseHelper(this);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String x = sp.getString("StudentID", "");
        String sql = "select c.cid, c.cname, c.clecturer, c.ccredit, g.grade, g.gpa from course c join grade g on c.cid = g.cid where g.sid = "
                     + Integer.parseInt(x) + " and c.cid = '" + getIntent().getStringExtra("cid") + "'";
        db = sdh.getReadableDatabase();
        cursor = db.rawQuery(sql,null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            tv[0].setText(cursor.getString(cursor.getColumnIndex("cid")));
            tv[1].setText(cursor.getString(cursor.getColumnIndex("cname")));
            tv[2].setText(cursor.getString(cursor.getColumnIndex("clecturer")));
            tv[4].setText(cursor.getString(cursor.getColumnIndex("ccredit")));
            tv[3].setText(cursor.getString(cursor.getColumnIndex("grade")));
            tv[5].setText(cursor.getString(cursor.getColumnIndex("gpa")));
            String[] m = {"D", "C-", "C", "C+", "B-", "B", "B+", "A-", "A", "A+"};
            tv[5].setText(m[Math.round(1+Float.parseFloat(tv[5].getText().toString()))]);
            v.setOnClickListener(view -> {finish();});
        }
        else
        {
            finish();
        }
    }
}