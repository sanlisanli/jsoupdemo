package me.mikasa.science.bean;

import java.io.Serializable;

/**
 * Created by mikasacos on 2018/9/8.
 */

public class TianWen implements Serializable{
    private String des;
    private String picUrl;
    private String title;
    private String link;
    private String date;
    public String getDes(){
        return des;
    }
    public void setDes(String des){
        this.des=des;
    }
    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setLink(String s){
        this.link=s;
    }
    public String getLink(){
        return link;
    }
    public void setDate(String s){
        this.date=s;
    }
    public String getDate(){
        return date;
    }
}
