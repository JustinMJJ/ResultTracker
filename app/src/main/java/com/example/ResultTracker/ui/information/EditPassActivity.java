package com.example.ResultTracker.ui.information;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ResultTracker.StudentDatabaseHelper;
import com.example.assignment3.R;

public class EditPassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pass);
        this.setTitle("Alter Password");
        TextView tv[] = {findViewById(R.id.pre_pass), findViewById(R.id.new_pass), findViewById(R.id.con_pass)};
        Button btn = findViewById(R.id.btnn);
        btn.setOnClickListener(view ->{
            btn.animate().alpha(0).setDuration(50).withEndAction(()->{
                btn.setAlpha(1);
                btn.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(()->{
                    btn.animate().scaleX(1).scaleY(1);
                });
            });
            StudentDatabaseHelper sdh = new StudentDatabaseHelper((Context) this);
            SQLiteDatabase db = null;
            Cursor cursor = null;
            SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
            String x = sp.getString("StudentID", "");
            String sql = "select * from account where sid = " + Integer.parseInt(x) + " and password = '" + tv[0].getText() + "'";

            try {
                db = sdh.getReadableDatabase();
                cursor = db.rawQuery(sql,null);
                if("".matches(tv[1].getText().toString()) || "".matches(tv[2].getText().toString()) || "".matches(tv[0].getText().toString())){
                    new Notice("All password fields should not be empty.").show(getSupportFragmentManager(), null);
                    return;
                }
                else if(!tv[1].getText().toString().matches(tv[2].getText().toString())){
                    new Notice("Two entered passwords are different. It should be same.").show(getSupportFragmentManager(), null);
                    return;
                }
                else if(cursor.getCount() <= 0)
                {
                    new Notice("The previous password is incorrect.").show(getSupportFragmentManager(), null);
                    return;
                }
                else
                {
                    db = sdh.getWritableDatabase();
                    sql = "update account set password = '" + tv[1].getText() + "' where sid = " + Integer.parseInt(x);
                    db.execSQL(sql);
                    Toast.makeText(this, "Alter password successfully.",Toast.LENGTH_LONG).show();
                    finish();
                }
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
        });
    }

    public static class Notice extends DialogFragment {
        private String s;
        public  Notice(String str){
            s = str;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            EditPassActivity act = (EditPassActivity)getActivity();
            return new AlertDialog.Builder(act).setMessage(s)
                    .setNeutralButton("OK",null).create();
        }
    }
}