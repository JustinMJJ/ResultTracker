package com.example.ResultTracker.ui.study;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.assignment3.R;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudyDatabaseAdapter extends BaseAdapter {
    private ExecutorService exe= Executors.newFixedThreadPool(4);
    private Activity mActivity;
    private ArrayList<String[]> tal;

    public StudyDatabaseAdapter(Context context, ArrayList<String[]> al){
        mActivity = (Activity)context;
        tal = al;
    }

    class ViewHolder{
        int pos;
        TextView id;
        TextView name;
        TextView credit;
        TextView grade;
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
            convertView = mActivity.getLayoutInflater().inflate(R.layout.grade, viewGroup, false);
            // create a new ViewHolder for it
            vh=new ViewHolder();
            vh.id=convertView.findViewById(R.id._gcid);
            vh.name=convertView.findViewById(R.id._gname);
            vh.grade=convertView.findViewById(R.id.csgrade);
            vh.credit=convertView.findViewById(R.id._gcr);
            // and set the tag to it
            convertView.setTag(vh);
        } else
            vh=(ViewHolder)convertView.getTag();// otherwise get the viewholder
        // set it's position
        vh.pos=i;
        vh.id.setText("");
        vh.name.setText("");
        vh.grade.setText("");
        vh.credit.setText("");
        if(tal.size() > 0) {
//            vh.id.setText(tal.get(i)[0]);
//            vh.name.setText(tal.get(i)[1]);
//            vh.credit.setText(tal.get(i)[2]);
//            vh.grade.setText(tal.get(i)[3]);
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
                protected void onPostExecute(String s[]) {
                    if (vh.pos == i) {
                        vh.id.setText(tal.get(i)[0]);
                        vh.name.setText(tal.get(i)[1]);
                        vh.credit.setText(tal.get(i)[2]);
                        vh.grade.setText(tal.get(i)[3]);
                    }
                }
            }.executeOnExecutor(exe, vh);
        }
        return convertView;
    }
}