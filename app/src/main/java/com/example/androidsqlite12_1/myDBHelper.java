package com.example.androidsqlite12_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper{
    myDBHelper(Context context){
        super(context, "charsDB", null, 1);
        // 프로젝트 dir에 dabase 아래 charsDB 파일 생성
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE charTable ( title CHAR(20) PRIMARY KEY, name CHAR(20));");
        //charsDB 파일에  charTable생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS charTable");
        onCreate(db);
        // 이전에 생성된 charTable 이 있으면 삭제해주고 다시 생성해준다
    }
}
