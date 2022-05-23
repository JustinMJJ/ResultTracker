package com.example.ResultTracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class StudentDatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private Context mContext;
    private static final String DB_NAME = "SimpleStudentPortalSystem.db";
    public static final String CREATE_STUDENT = "create table student(" +
            "sid int primary key," +
            "sname text not null," +
            "sgender text not null," +
            "sdob date not null," +
            "senrollDate date not null," +
            "sfacility text not null," +
            "smajor text not null," +
            "sdegree text not null," +
            "sphone int not null," +
            "semail text not null)";
    public static final String CREATE_COURSE = "create table course(" +
            "cid text primary key," +
            "cname text unique," +
            "clecturer text not null," +
            "ccredit int not null)" ;
    public static final String CREATE_GRADE = "create table grade(" +
            "sid int not null," +
            "cid text not null," +
            "grade int not null," +
            "gpa float not null," +
            "primary key(sid,cid)," +
            "foreign key(sid) references student(sid)on delete cascade," +
            "foreign key(cid) references course(cid)on delete cascade)";
    public static final String CREATE_ACCOUNT = "create table account(" +
            "sid int primary key," +
            "password text not null," +
            "foreign key(sid) references student(sid) on delete cascade)";

    public StudentDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
        db.execSQL(CREATE_COURSE);
        db.execSQL(CREATE_GRADE);
        db.execSQL(CREATE_ACCOUNT);
        db.execSQL("insert into student values ('18047377','Junjie Mai','Male','1992-5-4','2018-02-27','Science','Computer Science','Bachelor', '0211111', 'Junjie@massey.ac.nz')");
        db.execSQL("insert into course values ('159.101','Technical Programming 1','A/Pro Chris Scogings' ,15)");
        db.execSQL("insert into course values ('159.102','Technical Programming 2','Dr Peter Kay' ,15)");
        db.execSQL("insert into course values ('158.100','Information Technology','Dr Arzoo Atiq' ,15)");
        db.execSQL("insert into course values ('158.120','Fundamental Information Technologies','Ms Binglan Han' ,15)");
        db.execSQL("insert into course values ('159.201','Algorithms and Data Structures','Dr Andre Barczak' ,15)");
        db.execSQL("insert into course values ('159.202','Declarative Programming','Dr Daniel Playne' ,15)");
        db.execSQL("insert into course values ('159.234','Object-Oriented Programming','Ms Binglan Han' ,15)");
        db.execSQL("insert into course values ('159.235','Graphical Programming','A/Pro Ian Bond' ,15)");
        db.execSQL("insert into course values ('159.236','Embedded Programming','Dr Martin Johnson' ,15)");
        db.execSQL("insert into course values ('159.261','Games Programming','Dr Nilufar Baghaei' ,15)");
        db.execSQL("insert into course values ('159.270','Hardware-Oriented Computing','Dr Peter Kay' ,15)");
        db.execSQL("insert into course values ('159.302','Artificial Intelligence','Dr Napoleon Reyes' ,15)");
        db.execSQL("insert into course values ('159.333','Programming Project','Dr Martin Johnson' ,15)");
        db.execSQL("insert into course values ('159.336','Mobile Application Development','Dr Martin Johnson' ,15)");
        db.execSQL("insert into course values ('159.339','Internet Programming','A/Pro Ian Bond' ,15)");
        db.execSQL("insert into course values ('159.342','Operating Systems and Networks','Dr Napoleon Reyes' ,15)");
        db.execSQL("insert into course values ('158.212','Application Software Development','Dr Tong Liu' ,15)");
        db.execSQL("insert into course values ('158.222','Data Wrangling and Machine Learning','Dr Teo Susnjak' ,15)");
        db.execSQL("insert into course values ('158.225','Systems Analysis and Modelling','Dr Arzoo Atiq' ,15)");
        db.execSQL("insert into course values ('158.235','Networks, Security and Privacy','Julian Jang-Jaccard' ,15)");
        db.execSQL("insert into course values ('158.244','System Management and Testing','Dr Arzoo Atiq' ,15)");
        db.execSQL("insert into course values ('158.258','Web Development','Dr Tong Liu' ,15)");
        db.execSQL("insert into course values ('158.326','Software Architecture','Dr Anuradha Mathrani' ,15)");
        db.execSQL("insert into course values ('158.337','Database Development','Dr Anuradha Mathrani' ,15)");
        db.execSQL("insert into course values ('158.359','User Experience Design','Dr Arzoo Atiq' ,15)");
        db.execSQL("insert into account values (18047377,'123456')");
        db.execSQL("insert into grade values (18047377,'159.101',78,5)");
        db.execSQL("insert into grade values (18047377,'159.102',78,5)");
        db.execSQL("insert into grade values (18047377,'158.100',78,5)");
        db.execSQL("insert into grade values (18047377,'158.120',78,5)");
        db.execSQL("insert into grade values (18047377,'159.201',78,5)");
        db.execSQL("insert into grade values (18047377,'159.202',78,5)");
        db.execSQL("insert into grade values (18047377,'159.234',78,5)");
        db.execSQL("insert into grade values (18047377,'159.235',78,5)");
        db.execSQL("insert into grade values (18047377,'159.236',78,5)");
        db.execSQL("insert into grade values (18047377,'159.302',78,5)");
        db.execSQL("insert into grade values (18047377,'159.336',78,5)");
        db.execSQL("insert into grade values (18047377,'159.339',78,5)");
        db.execSQL("insert into grade values (18047377,'158.212',78,5)");
        db.execSQL("insert into grade values (18047377,'158.235',78,5)");
        db.execSQL("insert into grade values (18047377,'158.258',78,5)");
        db.execSQL("insert into grade values (18047377,'158.337',78,5)");
        db.execSQL("insert into grade values (18047377,'158.359',78,5)");
    }

    public static class Notice extends DialogFragment {
        private String s;
        public  Notice(String str){
            s = str;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            MainActivity act = (MainActivity)getActivity();
            return new AlertDialog.Builder(act).setMessage(s)
                    .setNeutralButton("OK",null).create();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
