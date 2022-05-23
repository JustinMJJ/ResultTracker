package com.example.ResultTracker.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ResultTracker.StudentDatabaseHelper;
import com.example.ResultTracker.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password, Context context) {

        try {
            // TODO: handle loggedInUser authentication
            StudentDatabaseHelper sdh = new StudentDatabaseHelper(context);
            SQLiteDatabase db = null;
            db = sdh.getReadableDatabase();
            Cursor cursor = null;
            String sql = "select * from account where sid = " + username + " and password = '" +password + "'";
            cursor = db.rawQuery(sql,null);

            if(cursor.getCount() <= 0){
                return new Result.Error(new IOException("Incorrect Student ID or Password!"));
            }
            else
            {
                SharedPreferences sp = context.getSharedPreferences("loginInfo", context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putBoolean("isLogin", true);
                editor.putString("StudentID", username);
                editor.putString("password", password);
                editor.commit();

                cursor = db.rawQuery("select sname from student where sid = " + username,null);
                cursor.moveToFirst();
                String name = cursor.getString(cursor.getColumnIndex("sname"));
                LoggedInUser User = new LoggedInUser(java.util.UUID.randomUUID().toString(), name);
                return new Result.Success<>(User);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}