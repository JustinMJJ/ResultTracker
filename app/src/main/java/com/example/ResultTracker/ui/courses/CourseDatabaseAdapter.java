package com.example.ResultTracker.ui.courses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.ResultTracker.MainActivity;
import com.example.ResultTracker.StudentDatabaseHelper;
import com.example.assignment3.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseDatabaseAdapter extends BaseAdapter {
    private ExecutorService exe= Executors.newFixedThreadPool(4);
    private Activity mActivity;
    private ArrayList<String[]> tal;

    public CourseDatabaseAdapter(Context context, ArrayList<String[]> al){
        mActivity = (Activity)context;
        tal = al;
    }

    class ViewHolder{
        int pos;
        TextView id;
        TextView name;
        TextView lecturer;
        TextView credit;
        Button btn;
    }
    @Override
    public int getCount() { return tal.size(); }
    @Override
    public Object getItem(int i) { return null; }
    @Override
    public long getItemId(int i) { return i; }
    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder vh;
        if (convertView == null) {
            // if it's not recycled, inflate it from xml
            convertView = mActivity.getLayoutInflater().inflate(R.layout.course, viewGroup, false);
            // create a new ViewHolder for it
            vh=new ViewHolder();
            vh.id = convertView.findViewById(R.id._cid);
            vh.name = convertView.findViewById(R.id._cname);
            vh.lecturer = convertView.findViewById(R.id._clectuer);
            vh.credit = convertView.findViewById(R.id._ccr);
            vh.btn = convertView.findViewById(R.id.btn);
            // and set the tag to it
            convertView.setTag(vh);
        } else
            vh=(ViewHolder)convertView.getTag();// otherwise get the viewholder
        // set it's position
        vh.pos=i;
        vh.id.setText("");
        vh.name.setText("");
        vh.lecturer.setText("");
        vh.credit.setText("");
        if(tal.size() > 0) {
//            vh.id.setText(tal.get(i)[0]);
//            vh.name.setText(tal.get(i)[1]);
//            vh.lecturer.setText(tal.get(i)[2]);
//            vh.credit.setText(tal.get(i)[3]);
//            vh.btn.setOnClickListener(view->{addCourse(mActivity, tal.get(i)[0]);});
            new AsyncTask<ViewHolder, Void, String[]>() {
                private ViewHolder vh;
                @Override
                protected String[] doInBackground(ViewHolder... params) {
                    vh = params[0];
                    // get the string for the url
                    String[] str = new String[4];
                    try {
                        // vh position might have changed
                        if (vh.pos != i) {
                            return null;
                        }
                        // decode the jpeg into a bitmap
//                        str[0] = tal.get(i)[0];
//                        str[1] = tal.get(i)[1];
//                        str[2] = tal.get(i)[2];
//                        str[3] = tal.get(i)[3];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return str;
                }

                @Override
                protected void onPostExecute(String[] s) {
                    if (vh.pos == i) {
                        vh.id.setText(tal.get(i)[0]);
                        vh.name.setText(tal.get(i)[1]);
                        vh.lecturer.setText(tal.get(i)[2]);
                        vh.credit.setText(tal.get(i)[3]);
                        vh.btn.setOnClickListener(view->{
                            vh.btn.animate().alpha(0).setDuration(50).withEndAction(()-> {
                                vh.btn.setAlpha(1);
                                vh.btn.animate().scaleX(0.8f).scaleY(0.8f).setDuration(50).withEndAction(() -> {
                                    vh.btn.animate().scaleX(1).scaleY(1);
                                });
                            });
                            StudentDatabaseHelper sdh = new StudentDatabaseHelper((Context) mActivity);
                            SQLiteDatabase db = null;
                            Cursor cursor = null;
                            String sql = "select cid from grade where sid = 18047377 and cid ='" + tal.get(i)[0] + "'";
                            try {
                                db = sdh.getReadableDatabase();
                                cursor = db.rawQuery(sql, null);
                                if (cursor.getCount() <= 0) {
                                    Intent intent = new Intent((Context) mActivity, addCourseActivity.class);
                                    intent.putExtra("cid", tal.get(i)[0]);
                                    ((Context)mActivity).startActivity(intent);
//                new StudentDatabaseHelper.Notice("You have successfully add this course.").show(((MainActivity)this.mActivity).getSupportFragmentManager(),null);
                                } else {
                                    new StudentDatabaseHelper.Notice("You have already add this course.").show(((MainActivity)mActivity).getSupportFragmentManager(), null);
                                }
                            }
                            catch (Exception e) {
                                Log.e("CourseViewAdapter", "", e);
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
                }
            }.executeOnExecutor(exe, vh);
        }
        return convertView;
    }
}