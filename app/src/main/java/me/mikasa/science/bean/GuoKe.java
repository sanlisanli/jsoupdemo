package me.mikasa.science.bean;

import java.io.Serializable;

/**
 * Created by mikasacos on 2018/9/27.
 */

public class GuoKe implements Serializable {
    private String title;
    private String link;
    private String imgUrl;
    private String label="关注";
    private String author;
    private String time="666";
    private String comment;
    private String desc;
    public void setTitle(String s){
        this.title=s;
    }
    public String getTitle(){
        return title;
    }
    public void setLink(String s){
        this.link=s;
    }
    public String getLink(){
        return link;
    }
    public void setImgUrl(String s){
        this.imgUrl=s;
    }
    public String getImgUrl(){
        return imgUrl;
    }
    public void setLabel(String s){
        this.label=s;
    }
    public String getLabel(){
        return label;
    }
    public void setAuthor(String s){
        this.author=s;
    }
    public String getAuthor(){
        return author;
    }
    public void setTime(String s){
        this.time=s;
    }
    public String getTime(){
        return time;
    }
    public void setComment(String s){
        this.comment=s;
    }
    public String getComment(){
        return comment;
    }
    public void setDesc(String s){
        this.desc=s;
    }
    public String getDesc(){
        return desc;
    }
}
