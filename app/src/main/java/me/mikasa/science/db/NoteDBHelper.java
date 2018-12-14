package me.mikasa.science.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mikasacos on 2018/9/21.
 */

public class NoteDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="snote.db";//DB_NAME,可能要upgrade
    public static final String TABLE_NAME="snote";//TABLE_NAME
    public static final int DB_VERSION=1;
    private static NoteDBHelper sInstance=null;
    public NoteDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    static synchronized NoteDBHelper getInstance(Context context){
        if (sInstance==null){
            sInstance=new NoteDBHelper(context);
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table if not exists "+ TABLE_NAME +"("+
                "id integer primary key,"+
                "content text,"+
                "firsttime text,"+
                "lasttime text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists "+TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
