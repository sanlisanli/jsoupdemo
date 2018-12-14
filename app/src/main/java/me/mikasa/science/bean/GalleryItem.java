package me.mikasa.science.bean;

import java.io.Serializable;

/**
 * Created by mikasacos on 2018/9/10.
 */

public class GalleryItem implements Serializable {
    private String picUrl;
    private String title;
    private String url;
    private String link;
    public void setLink(String s){
        this.link=s;
    }
    public String getLink(){
        return link;
    }
    public String getPicUrl(){
        return picUrl;
    }
    public void setPicUrl(String s){
        this.picUrl=s;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String s){
        this.title=s;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String s){
        this.url=s;
    }
}
