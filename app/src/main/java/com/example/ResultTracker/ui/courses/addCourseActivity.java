package com.example.ResultTracker.ui.courses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ResultTracker.StudentDatabaseHelper;
import com.example.assignment3.R;

public class addCourseActivity extends AppCompatActivity {
    TextView[] tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._add);
        this.setTitle("Add Course");
        View v = findViewById(R.id.adcourse_view);
        tv = new TextView[]{findViewById(R.id._acid),findViewById(R.id._acname),findViewById(R.id._acl),
                findViewById(R.id._amark),findViewById(R.id._accr),findViewById(R.id._acgrade)};
        Button btn = findViewById(R.id.btna);
        for(int i = 0; i < 6; ++i){
            tv[i].setText("");
        }
        StudentDatabaseHelper sdh = new StudentDatabaseHelper(this);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        String sql = "select c.cid, c.cname, c.clecturer, c.ccredit from course c  where c.cid = '" + getIntent().getStringExtra("cid") + "'";
        db = sdh.getReadableDatabase();
        cursor = db.rawQuery(sql,null);
        if (cursor.getCount() > 0) {
            try {
                cursor.moveToFirst();
                tv[0].setText(cursor.getString(cursor.getColumnIndex("cid")));
                tv[1].setText(cursor.getString(cursor.getColumnIndex("cname")));
                tv[2].setText(cursor.getString(cursor.getColumnIndex("clecturer")));
                tv[4].setText(cursor.getString(cursor.getColumnIndex("ccredit")));
            }
            catch (Exception e) {
                Log.e("InformationFragment", "", e);
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
        else
        {
            Toast.makeText(this, "Failed to loading.",Toast.LENGTH_LONG).show();
            finish();
        }

        btn.setOnClickListener(view -> {
            btn.animate().alpha(0).setDuration(50).withEndAction(()->{
                btn.setAlpha(1);
                btn.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(()->{
                    btn.animate().scaleX(1).scaleY(1);
                });
            });

            String z = "" + tv[5].getText();
            String y = "" + tv[3].getText();
            if("".matches(z) || "".matches(y)) {
                new Notice("The grade and mark should not be empty.").show(getSupportFragmentManager(), null);
                return;
            }
            else if(!(Float.parseFloat(tv[3].getText().toString()) >=0 && Float.parseFloat(tv[3].getText().toString()) <= 100)){
                new Notice("The mark should be between 0 and 100.").show(getSupportFragmentManager(), null);
                return;
            }
            else if(!(Float.parseFloat(tv[5].getText().toString()) >=0 && Float.parseFloat(tv[5].getText().toString()) <= 9)){
                new Notice("The grade should be between 0 and 9.").show(getSupportFragmentManager(), null);
                return;
            }

            SQLiteDatabase db2 = null;
            try {
                StudentDatabaseHelper sdh2 = new StudentDatabaseHelper(this);
                db2 = sdh.getWritableDatabase();
                SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                String x = sp.getString("StudentID", "");
                String str = "insert into grade values(" + x + " , '" + tv[0].getText().toString() + "', " + y + ", " + z + ")";
                db2.execSQL(str);
                Toast.makeText(this, "Adding course " + tv[0].getText().toString() + " successfully.",Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                Log.e("InformationFragment", "", e);
            }
            finally {
                if (db2 != null) {
                    db2.close();
                }
            }
            finish();
        });
    }

    public static class Notice extends DialogFragment {
        private String s;
        public  Notice(String str){
            s = str;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            addCourseActivity act = (addCourseActivity)getActivity();
            return new AlertDialog.Builder(act).setMessage(s)
                    .setNeutralButton("OK",null).create();
        }
    }
}
