package me.mikasa.science.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.mikasa.science.bean.Note;

/**
 * Created by mikasacos on 2018/9/21.
 */

public class NoteManager {
    private Context mContext;
    private NoteDBHelper noteDBHelper;
    private SQLiteDatabase database;
    private final String[] TABLE_COLUMNS=new String[]{"id","content","firsttime","lasttime"};
    public NoteManager(Context context){
        mContext=context;
        noteDBHelper=NoteDBHelper.getInstance(mContext);
    }
    /**
     * 初始化表格
     */
    public void initTable(){
        try {
            database=noteDBHelper.getWritableDatabase();
            database.beginTransaction();
            String[] contents=new String[]{"作者简介：https://github.com/sanlisanlisanli","欢迎使用SNote笔记"};
            for (int i=0;i<contents.length;i++){
                ContentValues values=new ContentValues();
                values.put("id",String.valueOf(i+1));
                values.put("content",contents[i]);
                Date date=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
                String firstTime=sdf.format(date);
                values.put("firsttime",firstTime);
                values.put("lasttime",Long.toString(date.getTime()));
                Log.i("op",Long.toString(date.getTime()));
                database.insert(noteDBHelper.TABLE_NAME,null,values);
            }
            database.setTransactionSuccessful();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (database!=null){
                database.endTransaction();
                database.close();
            }
        }
    }
    public boolean isEmpty(){
        Cursor cursor=null;
        try {
            database=noteDBHelper.getReadableDatabase();
            cursor=database.query(NoteDBHelper.TABLE_NAME,
                    new String[]{"count(id)"},
                    null,null,null,null,null);
            if (cursor.moveToFirst()){
                if (cursor.getInt(0)>0){
                    return false;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            if (database!=null){
                database.close();
            }
        }
        return true;
    }

    /**
     * 清空表格数据
     */
    public void clearTable(){
        try {
            database=noteDBHelper.getWritableDatabase();
            database.delete(noteDBHelper.TABLE_NAME,null,null);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database!=null){
                database.close();
            }
        }
    }
    /**
     * 向表中添加一条数据
     */
    public void insert(Note note){
        try {
            database=noteDBHelper.getWritableDatabase();
            database.beginTransaction();
            ContentValues values=new ContentValues();
            values.put("id",Integer.parseInt(note.getId()));//parseInt
            values.put("content",note.getContent());
            values.put("firsttime",note.getFirstTime());
            values.put("lasttime",note.getLastTime());
            database.insertOrThrow(noteDBHelper.TABLE_NAME,null,values);
            database.setTransactionSuccessful();
        }catch (SQLiteConstraintException e){
            Toast.makeText(mContext, "主键重复", Toast.LENGTH_SHORT).show();
        }finally {
            if (database!=null){
                database.endTransaction();
                database.close();
            }
        }
    }
    /**
     * 从表中删除一条数据
     */
    public void delete(String id){
        try {
            database=noteDBHelper.getWritableDatabase();
            database.beginTransaction();
            database.delete(noteDBHelper.TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database!=null){
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * 修改
     * @param note
     */
    public void update(Note note){
        try {
            database=noteDBHelper.getWritableDatabase();
            database.beginTransaction();
            ContentValues values=new ContentValues();
            if (!TextUtils.isEmpty(note.getContent())){
                values.put("content",note.getContent());
            }
            if (!TextUtils.isEmpty(note.getLastTime())){
                values.put("lasttime",note.getLastTime());
            }
            database.update(noteDBHelper.TABLE_NAME,values,"id=?",
                    new String[]{String.valueOf(note.getId())});
            database.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (database!=null){
                database.endTransaction();
                database.close();
            }
        }
    }

    /**
     * 根据条件查询
     */
    public List<Note> select(Note note){
        Cursor cursor=null;
        try {
            database=noteDBHelper.getReadableDatabase();
            if (!TextUtils.isEmpty(note.getId())){
                cursor=database.query(noteDBHelper.TABLE_NAME,TABLE_COLUMNS,
                        "id=?",new String[]{note.getId()},null,null,null);//三个null
                if (cursor.getCount()>0){
                    List<Note>list=new ArrayList<>(cursor.getCount());
                    while (cursor.moveToNext()){
                        list.add(parseNote(cursor));
                    }
                    return list;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            if (database!=null){
                database.close();
            }
        }
        return null;
    }
    public List<Note>selectAll(){
        Cursor cursor=null;
        try {
            database=noteDBHelper.getReadableDatabase();
            cursor=database.query(noteDBHelper.TABLE_NAME,TABLE_COLUMNS,null,null,null,null,null);//五个null
            if (cursor.getCount()>0){
                List<Note>list=new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()){
                    list.add(parseNote(cursor));
                }
                return list;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null){
                cursor.close();
            }
            if (database!=null){
                database.close();
            }
        }
        return null;//return null
    }
    private Note parseNote(Cursor cursor){//cursor
        Note note=new Note();
        note.setId(String.valueOf(cursor.getInt(cursor.getColumnIndex("id"))));
        note.setContent(cursor.getString(cursor.getColumnIndex("content")));
        note.setFirstTime(cursor.getString(cursor.getColumnIndex("firsttime")));
        note.setLastTime(cursor.getString(cursor.getColumnIndex("lasttime")));
        return note;
    }
}
