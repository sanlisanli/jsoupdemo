package me.mikasa.science.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

import me.mikasa.science.utils.SpUtil;

/**
 * Created by mikasacos on 2018/9/18.
 */

public class Note implements Serializable,Comparable<Note> {
    private String id;
    private String content;
    private String firstTime;
    private String lastTime;
    private boolean isFlag=false;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String s){
        this.content=s;
    }
    public void setFirstTime(String s){
        this.firstTime=s;
    }
    public String getFirstTime(){
        return firstTime;
    }
    public void setLastTime(String s){
        this.lastTime=s;
    }
    public String getLastTime(){
        return lastTime;
    }
    public void setFlag(boolean isFlag){
        this.isFlag=isFlag;
    }
    public boolean isFlag(){
        return isFlag;
    }

    @Override
    public int compareTo(@NonNull Note o) {
        if (Long.parseLong(lastTime)==Long.parseLong(o.lastTime)){//String.valueOf
            return 0;
        }else if (Long.parseLong(lastTime)>Long.parseLong(o.lastTime)){
            return -1;
        }else {
            return 1;
        }
    }
}
